package com.dbs.replsdk.arch;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.dbs.replsdk.MessageDataSource;
import com.dbs.replsdk.MessageUtils;
import com.dbs.replsdk.model.QuickReply;
import com.dbs.replsdk.network.ApiClient;
import com.dbs.replsdk.persistence.Message;
import com.dbs.replsdk.uimodel.UiChatMessage;
import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class WorkerTest {


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    @Captor
    ArgumentCaptor<android.support.v4.util.Pair<List<UiChatMessage>, List<QuickReply>>> argument;
    @Mock
    private MessageDataSource mDataSource;
    private Worker worker;
    private Gson gson;

    @Before
    public void setUp() throws Exception {
        ApiClient.init(new ApiClient.ApiConfig().withBaseUrl("https://10.93.131.13/kai/api/v2/capi/").withApiKey(""));
        worker = new Worker(mDataSource);
        gson = GsonWrapper.getInstance();
    }


    @Test
    public void getAll_afterInsert() throws InterruptedException {

        Message mock = MessageUtils.generate();
        when(mDataSource.getAllMessages()).thenReturn(Flowable.just(Collections.singletonList(mock)));
        worker.getChatStream().test().assertValue(pair ->
                {
                    List<UiChatMessage> messages = pair.first;
                    if (messages.size() < 1) return false;
                    UiChatMessage chatMessage = messages.get(0);
                    if (chatMessage.getMessageContents().size() < 2) return false;
                    if (chatMessage.getAlternateQuestions() != null) return false;
                    return true;
                }
        );

    }

    @Test
    public void saveMessage_dataSourceInsert() {
        Message mock = MessageUtils.generate();
        worker.saveMessage(mock);
        verify(mDataSource, times(1)).insertOrUpdate(mock);
    }

    @After
    public void clear() {
        mDataSource.deleteAllMessage();
    }


}
