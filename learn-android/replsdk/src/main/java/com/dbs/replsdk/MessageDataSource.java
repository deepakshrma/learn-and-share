package com.dbs.replsdk;

import com.dbs.replsdk.persistence.Message;

import java.util.List;

import io.reactivex.Flowable;

public interface MessageDataSource {

    /**
     * Get All message
     * Use get last instead
     */
    Flowable<List<Message>> getAllMessages();

    /**
     * Insert of update a message
     *
     * @param message
     */
    void insertOrUpdate(Message message);


    /**
     * Delete all message;
     */
    void deleteAllMessage();
}
