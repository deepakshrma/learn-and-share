package com.dbs.replsdk.model;

import android.support.annotation.NonNull;

import com.dbs.replsdk.persistence.Message;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatResponse {

    @SerializedName("context")
    @Expose
    private ResponseContext context;
    @SerializedName("status")
    @Expose
    private Status status;

    @SerializedName("current_intent")
    @Expose
    private String currentIntent;
    @SerializedName("message_contents")
    @Expose
    private Object messageContents = null;
    @SerializedName("quick_replies")
    @Expose
    private Object quickReplies = null;

    @SerializedName("alternative_questions")
    @Expose
    private Object alternativeQuestions = null;


    public ResponseContext getContext() {
        return context;
    }

    public void setContext(ResponseContext context) {
        this.context = context;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    @NonNull
    public Message convertToMessage(Gson gson) {
        Message message = new Message();

        if (quickReplies != null)
            message.setQuickReplies(gson.toJson(this.quickReplies));
        if (alternativeQuestions != null)
            message.setAlternativeQuestions(gson.toJson(this.alternativeQuestions));
        if (messageContents != null)
            message.setMessageContents(gson.toJson(this.messageContents));
        message.setCurrentIntent(this.currentIntent);
        return message;
    }


}