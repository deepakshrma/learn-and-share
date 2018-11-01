package com.dbs.replsdk.uimodel;

import com.dbs.replsdk.model.AlternateQuestion;

import java.util.List;

public class UiChatMessage {

    private boolean isMe;
    private long id;
    private List<UiMessageContent> messageContents;
    private List<AlternateQuestion> alternateQuestions;

    public UiChatMessage(long id, boolean isMe, List<UiMessageContent> messageContents, List<AlternateQuestion> alternateQuestions) {
        this.isMe = isMe;
        this.id = id;
        this.messageContents = messageContents;
        this.alternateQuestions = alternateQuestions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isMe() {
        return isMe;
    }

    public void setMe(boolean me) {
        isMe = me;
    }

    public List<UiMessageContent> getMessageContents() {
        return messageContents;
    }

    public void setMessageContents(List<UiMessageContent> messageContents) {
        this.messageContents = messageContents;
    }

    public List<AlternateQuestion> getAlternateQuestions() {
        return alternateQuestions;
    }

    public void setAlternateQuestions(List<AlternateQuestion> alternateQuestions) {
        this.alternateQuestions = alternateQuestions;
    }
}