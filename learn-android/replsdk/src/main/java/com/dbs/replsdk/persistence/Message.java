package com.dbs.replsdk.persistence;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "tbl_message")
public class Message {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "isMe")
    private boolean isMe;

    @ColumnInfo(name = "message_contents")
    @SerializedName("message_contents")
    @Expose
    private String messageContents;

    @ColumnInfo(name = "current_intent")
    @SerializedName("current_intent")
    @Expose
    private String currentIntent;

    @ColumnInfo(name = "alternative_questions")
    @SerializedName("alternative_questions")
    @Expose
    private String alternativeQuestions;

    @ColumnInfo(name = "quick_replies")
    @SerializedName("quick_replies")
    @Expose
    private String quickReplies;
    private long timeStamp;

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
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


    public String getMessageContents() {
        return messageContents;
    }

    public void setMessageContents(String messageContents) {
        this.messageContents = messageContents;
    }

    public String getCurrentIntent() {
        return currentIntent;
    }

    public void setCurrentIntent(String currentIntent) {
        this.currentIntent = currentIntent;
    }

    public String getAlternativeQuestions() {
        return alternativeQuestions;
    }

    public void setAlternativeQuestions(String alternativeQuestions) {
        this.alternativeQuestions = alternativeQuestions;
    }

    public String getQuickReplies() {
        return quickReplies;
    }

    public void setQuickReplies(String quickReplies) {
        this.quickReplies = quickReplies;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (isMe != message.isMe) return false;
        if (messageContents != null ? !messageContents.equals(message.messageContents) : message.messageContents != null)
            return false;
        if (currentIntent != null ? !currentIntent.equals(message.currentIntent) : message.currentIntent != null)
            return false;
        if (alternativeQuestions != null ? !alternativeQuestions.equals(message.alternativeQuestions) : message.alternativeQuestions != null)
            return false;
        return quickReplies != null ? quickReplies.equals(message.quickReplies) : message.quickReplies == null;
    }

}
