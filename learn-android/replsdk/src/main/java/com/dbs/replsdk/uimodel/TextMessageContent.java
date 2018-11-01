package com.dbs.replsdk.uimodel;

import android.support.v7.widget.RecyclerView;

import com.dbs.replsdk.ui.items.DisplayOption;
import com.dbs.replsdk.ui.items.TextMessageItem;
import com.dbs.ui.multiadapter.GroupEventDispatcher;
import com.google.gson.annotations.SerializedName;
import com.xwray.groupie.Group;

public class TextMessageContent extends UiMessageContent<TextMessageContent.Payload> {

    public TextMessageContent() {
        type = MessageContentType.TEXT;
    }

    @Override
    public Group createGroup(GroupEventDispatcher groupEventDispatcher, RecyclerView.RecycledViewPool pool, DisplayOption displayOption) {
        return new TextMessageItem(payload, displayOption);
    }

    public static final class Payload implements com.dbs.replsdk.model.Payload {
        @SerializedName("text")
        private String text;

        // empty constructor
        public Payload() {
        }

        public Payload(String mess) {
            this.text = mess;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}