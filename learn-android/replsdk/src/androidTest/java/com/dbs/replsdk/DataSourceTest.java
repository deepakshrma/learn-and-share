package com.dbs.replsdk;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.dbs.replsdk.arch.GsonWrapper;
import com.dbs.replsdk.arch.SessionManager;
import com.dbs.replsdk.model.Request;
import com.dbs.replsdk.persistence.LocalMessageDataSource;
import com.dbs.replsdk.persistence.Message;
import com.dbs.replsdk.persistence.MessageDao;
import com.dbs.replsdk.persistence.MessageDatabase;
import com.dbs.replsdk.uimodel.TextMessageContent;
import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class DataSourceTest {

    private MessageDatabase mDb;
    private MessageDataSource dataSource;
    private Gson gson;
    private MessageDao mMessageDao;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, MessageDatabase.class).allowMainThreadQueries().build();
        mMessageDao = mDb.getMessageDao();
        dataSource = new LocalMessageDataSource(mDb.getMessageDao());
        gson = GsonWrapper.getInstance();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void insertMyMessage_shouldGetCorrectItem() {
        Request request = new Request();
        request.setContext(SessionManager.getInstance().createContext());
        request.setPayload(new TextMessageContent.Payload("Find me an ATM"));
        request.setType("TEXT");
        TextMessageContent.Payload payload = (TextMessageContent.Payload) request.getPayload();
        TextMessageContent content = new TextMessageContent();
        content.setPayload(payload);
        Message myMess = new Message();
        myMess.setMe(true);
        myMess.setMessageContents(gson.toJson(content));
        dataSource.insertOrUpdate(myMess);
        List<Message> byName = mMessageDao.getAll();
        assertThat(byName.get(0), equalTo(myMess));

    }


    @Test
    public void insertMessages_shouldGetEnoughItem() {
        Request request = new Request();
        request.setContext(SessionManager.getInstance().createContext());
        request.setPayload(new TextMessageContent.Payload("Find me an ATM"));
        request.setType("TEXT");
        TextMessageContent.Payload payload = (TextMessageContent.Payload) request.getPayload();
        TextMessageContent content = new TextMessageContent();
        content.setPayload(payload);
        Message myMess = new Message();
        myMess.setMe(true);
        myMess.setMessageContents(gson.toJson(content));
        dataSource.insertOrUpdate(myMess);
        dataSource.insertOrUpdate(myMess);
        dataSource.insertOrUpdate(myMess);
        List<Message> all = mMessageDao.getAll();
        assertThat(all.size(), greaterThanOrEqualTo(3));

    }
}
