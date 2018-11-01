package com.dbs.replsdk;

import com.dbs.replsdk.persistence.Message;

public class MessageTestUtil {

    public static Message createMessage(int n) {
        Message message = new Message();
        message.setMe(true);
        message.setQuickReplies("quick replies " + n + 1);
        message.setAlternativeQuestions("alternative questions " + n + 1);
        message.setCurrentIntent("current intents " + n + 1);
        message.setMessageContents("Message contents " + n + 1);
        return message;
    }
}
