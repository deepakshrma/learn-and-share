package com.dbs.replsdk.model;

import com.dbs.replsdk.ui.events.KaiEvent;
import com.dbs.replsdk.uimodel.DifferModel;
import com.dbs.replsdk.uimodel.TextMessageContent;
import com.google.gson.annotations.SerializedName;

/**
 * Created by deepak on 5/10/18.
 */

public class QuickReply extends KaiEvent implements DifferModel {
    @SerializedName("display_text")
    private String text;
    @SerializedName("payload")
    private String payload;
    @SerializedName("type")
    private String type;

    public QuickReply(String text, String payload, String type) {
        this.text = text;
        this.payload = payload;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public String getPayload() {
        return payload;
    }

    public String getType() {
        return type;
    }

    @Override
    public Request createRequest() {
        Request request = new Request();
        request.setType("TEXT");
        request.setPayload(new TextMessageContent.Payload(payload));
        request.createPersistedMessage(text);
        return request;
    }

    @Override
    public boolean areItemsTheSame(DifferModel b) {
        if (!(b instanceof QuickReply)) return false;
        return ((QuickReply) b).text.equals(this.text);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuickReply that = (QuickReply) o;

        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        if (payload != null ? !payload.equals(that.payload) : that.payload != null) return false;
        return type != null ? type.equals(that.type) : that.type == null;
    }

    @Override
    public boolean areContentsTheSame(DifferModel b) {
        return b.equals(this);
    }
}