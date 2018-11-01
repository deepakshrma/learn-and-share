package com.dbs.replsdk.uimodel;

import android.support.v7.widget.RecyclerView;

import com.dbs.replsdk.model.Payload;
import com.dbs.replsdk.ui.items.DisplayOption;
import com.dbs.ui.multiadapter.GroupEventDispatcher;
import com.google.gson.annotations.SerializedName;
import com.xwray.groupie.Group;

public abstract class UiMessageContent<P extends Payload> {

    @SerializedName("type")
    protected MessageContentType type;
    @SerializedName("payload")
    protected P payload;

    public MessageContentType getType() {
        return type;
    }

    public void setType(MessageContentType type) {
        this.type = type;
    }

    public P getPayload() {
        return payload;
    }

    public void setPayload(P payload) {
        this.payload = payload;
    }

    /**
     * Create {@link Group} base on type
     *
     * @param groupEventDispatcher
     * @param pool
     * @param displayOption
     * @return
     */
    public abstract Group createGroup(
            GroupEventDispatcher groupEventDispatcher,
            RecyclerView.RecycledViewPool pool,
            DisplayOption displayOption);
}