package com.dbs.replsdk.arch;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.dbs.replsdk.model.QuickReply;
import com.dbs.replsdk.model.Request;
import com.dbs.replsdk.ui.events.ErrorEvent;
import com.dbs.replsdk.ui.events.KaiEventHandler;
import com.dbs.replsdk.ui.events.SubjectEvent;
import com.dbs.replsdk.uimodel.UiChatMessage;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * ChatBotViewModel Binding data model for ChatBot Window
 */
public class ChatBotViewModel extends ViewModel implements KaiEventHandler.RequestSender {

    // maybe a bit overkill, but we can revisit later
    private CompositeDisposable compositeDisposable;

    // the worker that talks to DB and network
    private Worker worker;

    // all livedata
    private SingleLiveEvent<List<UiChatMessage>> chatsLiveData;

    private MutableLiveData<List<QuickReply>> quickRepliesLiveData;

    private SingleLiveEvent<SubjectEvent> event;

    public ChatBotViewModel(Worker worker) {
        chatsLiveData = new SingleLiveEvent<>();
        quickRepliesLiveData = new MutableLiveData<>();
        event = new SingleLiveEvent<>();
        compositeDisposable = new CompositeDisposable();
        this.worker = worker;
    }

    public void initStream() {
        // Event, Error
        compositeDisposable.add(worker.getEventsObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(event::setValue, error -> event.setValue(new ErrorEvent(error)))
        );

        compositeDisposable.add(
                worker.getChatStream()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                pair -> {
                                    chatsLiveData.setValue(pair.first);
                                    quickRepliesLiveData.setValue(pair.second);
                                },
                                error -> event.setValue(new ErrorEvent(error))
                        )

        );
    }


    public LiveData<List<UiChatMessage>> getChatsLiveData() {
        return chatsLiveData;
    }

    public LiveData<List<QuickReply>> getQuickRepliesLiveData() {
        return quickRepliesLiveData;
    }

    public LiveData<SubjectEvent> getEventLiveData() {
        return event;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (!compositeDisposable.isDisposed()) compositeDisposable.dispose();
    }

    /**
     * Start a session
     */
    public void startSession() {
        worker.startSession();
    }

    /**
     * send a request
     *
     * @param request
     */
    public void sendRequest(Request request) {
        worker.sendRequest(request);
    }
}