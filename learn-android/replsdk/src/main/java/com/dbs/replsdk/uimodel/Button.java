package com.dbs.replsdk.uimodel;

import com.dbs.replsdk.model.Payload;
import com.google.gson.annotations.SerializedName;

public class Button implements Payload {
    @SerializedName("label")
    private String label;
    @SerializedName("type")
    private ButtonType type;
    @SerializedName("payload")
    private String payload;

    public ButtonType getType() {
        return type;
    }

    public void setType(ButtonType type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }


    public enum ButtonType {

        @SerializedName("HYPERLINK")
        HYPERLINK,
        @SerializedName("POSTBACK")
        POSTBACK,
        @SerializedName("CALL")
        CALL,
        @SerializedName("LOGIN")
        LOGIN,
        @SerializedName("LOCATION")
        LOCATION
    }

}