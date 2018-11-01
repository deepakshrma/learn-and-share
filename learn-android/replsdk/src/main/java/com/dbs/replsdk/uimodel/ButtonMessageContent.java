package com.dbs.replsdk.uimodel;

import android.support.v7.widget.RecyclerView;

import com.dbs.replsdk.ui.items.ButtonItem;
import com.dbs.replsdk.ui.items.DisplayOption;
import com.dbs.ui.multiadapter.GroupEventDispatcher;
import com.xwray.groupie.Group;

public class ButtonMessageContent extends UiMessageContent<Button> {

    public ButtonMessageContent() {
        type = MessageContentType.BUTTON;
    }


    @Override
    public Group createGroup(GroupEventDispatcher groupEventDispatcher, RecyclerView.RecycledViewPool pool, DisplayOption displayOption) {
        return new ButtonItem(groupEventDispatcher, payload, displayOption);
    }
}
