package com.dbs.replsdk.uimodel;

import com.google.gson.annotations.SerializedName;

public enum MessageContentType {
    @SerializedName("TEXT")
    TEXT,
    @SerializedName("CONTAINER")
    CONTAINER,
    @SerializedName("BUTTON")
    BUTTON,
    @SerializedName("MEDIUM")
    MEDIUM,
    @SerializedName("CARD")
    CARD
}
