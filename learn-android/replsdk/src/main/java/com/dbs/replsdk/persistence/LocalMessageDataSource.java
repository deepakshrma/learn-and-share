package com.dbs.replsdk.persistence;

import com.dbs.replsdk.MessageDataSource;

import java.util.List;

import io.reactivex.Flowable;

public class LocalMessageDataSource implements MessageDataSource {

    private MessageDao dao;

    public LocalMessageDataSource(MessageDao messageDao) {
        dao = messageDao;
    }

    @Override
    public Flowable<List<Message>> getAllMessages() {
        return dao.getAllMessage();
    }

    @Override
    public void insertOrUpdate(Message message) {
        dao.insert(message);
    }

    @Override
    public void deleteAllMessage() {
        dao.deleteAll();
    }
}
