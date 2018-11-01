package com.dbs.replsdk.arch;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;

import com.dbs.replsdk.model.QuickReply;
import com.dbs.replsdk.model.Request;
import com.dbs.replsdk.ui.events.ReplyingEvent;
import com.dbs.replsdk.ui.events.SubjectEvent;
import com.dbs.replsdk.uimodel.UiChatMessage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class ChatbotViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private Worker mWorker;

    @Mock
    private Observer<List<UiChatMessage>> observer;

    @Mock
    private Observer<List<QuickReply>> quickReplyObserver;

    @Mock
    private Observer<SubjectEvent> eventObserver;


    private ChatBotViewModel chatBotViewModel;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        chatBotViewModel = new ChatBotViewModel(mWorker);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(h -> Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(h -> Schedulers.trampoline());


    }

    @Test
    public void initWorkerStream_observeLiveDataStream() {

        android.support.v4.util.Pair<List<UiChatMessage>, List<QuickReply>> pair = new android.support.v4.util.Pair<>(Collections.emptyList(), Collections.emptyList());

        when(mWorker.getChatStream()).thenReturn(Flowable.just(pair));

        ReplyingEvent event = new ReplyingEvent();

        when(mWorker.getEventsObservable()).thenReturn(Observable.just(event));

        chatBotViewModel.getChatsLiveData().observeForever(observer);

        chatBotViewModel.getQuickRepliesLiveData().observeForever(quickReplyObserver);

        chatBotViewModel.getEventLiveData().observeForever(eventObserver);

        chatBotViewModel.initStream();

        verify(mWorker).getChatStream();

        verify(mWorker).getEventsObservable();

        verify(observer).onChanged(pair.first);

        verify(quickReplyObserver).onChanged(pair.second);

        verify(eventObserver).onChanged(event);
    }


    @Test
    public void sendRequest_shouldCallWorkerMethods() {
        chatBotViewModel.startSession();
        verify(mWorker).startSession();
        Request request = new Request();
        chatBotViewModel.sendRequest(request);
        verify(mWorker).sendRequest(request);
    }
}
