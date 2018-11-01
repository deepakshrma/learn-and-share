package com.dbs.replsdk.ui.events;

import com.dbs.replsdk.model.Request;
import com.dbs.replsdk.uimodel.TextMessageContent;

public class TextEvent extends KaiEvent {

    private final String message;

    public TextEvent(String message) {
        this.message = message;
    }


    @Override
    public Request createRequest() {
        Request request = new Request();
        request.setType("TEXT");
        request.setPayload(new TextMessageContent.Payload(message));
        request.createPersistedMessage(message);
        return request;
    }
}
