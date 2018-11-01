package com.dbs.replsdk.arch;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.support.v4.util.Pair;

import com.dbs.replsdk.MessageDataSource;
import com.dbs.replsdk.model.AlternateQuestion;
import com.dbs.replsdk.model.ChatResponse;
import com.dbs.replsdk.model.QuickReply;
import com.dbs.replsdk.model.Request;
import com.dbs.replsdk.network.ApiClient;
import com.dbs.replsdk.network.services.UserMessageServices;
import com.dbs.replsdk.persistence.Message;
import com.dbs.replsdk.ui.events.ErrorEvent;
import com.dbs.replsdk.ui.events.ReplyingEvent;
import com.dbs.replsdk.ui.events.SubjectEvent;
import com.dbs.replsdk.uimodel.UiChatMessage;
import com.dbs.replsdk.uimodel.UiMessageContent;
import com.dbs.ui.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import retrofit2.Call;
import retrofit2.Response;

/**
 * A worker that serves {@link ChatBotViewModel} to handle request,
 * send and receive message, and put it in DB
 */
public class Worker {

    private final Gson gson;

    private final MessageDataSource mDataSource;

    private final UserMessageServices services;

    private final Type messContentsType;

    private final Type alternateQuestionsType;

    private final Type quickRepliesType;

    private BehaviorSubject<SubjectEvent> eventSource;

    public Worker(MessageDataSource source) {
        // init dao and retrofit
        mDataSource = source;
        gson = GsonWrapper.getInstance();
        services = ApiClient.getRetrofit().create(UserMessageServices.class);
        messContentsType = new TypeToken<ArrayList<UiMessageContent>>() {
        }.getType();
        alternateQuestionsType = new TypeToken<ArrayList<AlternateQuestion>>() {
        }.getType();
        quickRepliesType = new TypeToken<ArrayList<QuickReply>>() {
        }.getType();

        eventSource = BehaviorSubject.create();
    }

    public Observable<SubjectEvent> getEventsObservable() {
        return eventSource;
    }

    @WorkerThread
    private Pair<List<UiChatMessage>, List<QuickReply>> parseMessage(List<Message> messages) {
        List<UiChatMessage> uiChatMessages = new ArrayList<>();
        List<QuickReply> quickReplies = new ArrayList<>();
        if (messages != null && messages.size() > 0) {
            int messageCount = messages.size();
            for (Message message : messages) {
                ArrayList<UiMessageContent> contents = gson.fromJson(message.getMessageContents(), messContentsType);
                ArrayList<AlternateQuestion> questions = gson.fromJson(message.getAlternativeQuestions(), alternateQuestionsType);
                uiChatMessages.add(new UiChatMessage(message.getId(), message.isMe(), contents, questions));
            }
            Message lastMessage = messages.get(messageCount - 1);
            if (StringUtils.isNotEmpty(lastMessage.getQuickReplies())) {
                quickReplies = gson.fromJson(lastMessage.getQuickReplies(), quickRepliesType);
            }
        }
        return new Pair<>(uiChatMessages, quickReplies);
    }

    /**
     * @return
     */
    public Flowable<Pair<List<UiChatMessage>, List<QuickReply>>> getChatStream() {
        return mDataSource.getAllMessages().map(this::parseMessage);
    }


    @MainThread
    public void startSession() {
        Request request = new Request();
        request.setContext(SessionManager.getInstance().createContext());
        request.setPayload(null);
        request.setType("START_SESSION");
        AppExecutors.getInstance().networkIO().execute(() -> {
            Call<ChatResponse> call = services.requestStartSession(request);
            processResponse(call);
        });
    }


    @WorkerThread
    private void processResponse(Call<ChatResponse> call) {
        try {

            eventSource.onNext(new ReplyingEvent());
            Response<ChatResponse> response = call.execute();
            if (response != null && response.isSuccessful() && response.body() != null) {
                ChatResponse chatResponse = response.body();
                // update Section
                SessionManager.getInstance().setUser(chatResponse.getContext().getUser());
                // save message into store
                Message message = chatResponse.convertToMessage(gson);
                message.setMe(false);
                message.setTimeStamp(System.currentTimeMillis());
                saveMessage(message);
            } else {
                throw new RuntimeException(response.errorBody().string());
            }
        } catch (Exception e) {
            eventSource.onNext(new ErrorEvent(e));
        }
    }


    @WorkerThread
    public void saveMessage(@NonNull Message message) {
        mDataSource.insertOrUpdate(message);
    }

    @MainThread
    public void sendRequest(Request request) {
        request.setContext(SessionManager.getInstance().createContext());
        AppExecutors.getInstance().networkIO().execute(() -> {
            if (request.getPersistedMessage() != null) {
                Message myMess = new Message();
                myMess.setMe(true);
                myMess.setTimeStamp(System.currentTimeMillis());
                myMess.setMessageContents(gson.toJson(Collections.singleton(request.getPersistedMessage())));
                saveMessage(myMess);
            }
            Call<ChatResponse> call = services.sendUserMessage(request);
            processResponse(call);
        });
    }
}
