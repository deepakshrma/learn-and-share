package com.dbs.replsdk.model;

import com.dbs.replsdk.uimodel.TextMessageContent;
import com.google.gson.annotations.SerializedName;

public class Request {

    @SerializedName("context")
    private RequestContext context;
    @SerializedName("type")
    private String type;
    @SerializedName("payload")
    private Payload payload;
    private TextMessageContent persistedMessage;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public RequestContext getContext() {
        return context;
    }

    public void setContext(RequestContext context) {
        this.context = context;
    }

    public TextMessageContent getPersistedMessage() {
        return persistedMessage;
    }

    /**
     * Create persisted message , that will display as your typed message
     *
     * @param message
     */
    public void createPersistedMessage(String message) {
        TextMessageContent content = new TextMessageContent();
        content.setPayload(new TextMessageContent.Payload(message));
        persistedMessage = content;
    }


}
