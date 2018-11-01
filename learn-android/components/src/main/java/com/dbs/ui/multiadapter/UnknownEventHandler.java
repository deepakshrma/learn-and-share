package com.dbs.ui.multiadapter;

import android.support.annotation.Nullable;
import android.util.Log;

public class UnknownEventHandler extends GroupEventHandler<GroupEvent> {

    public UnknownEventHandler() {
        super(GroupEvent.class);
    }

    @Override
    public int getKey() {
        return Integer.MIN_VALUE; // doesn't matter
    }

    @Override
    public void handle(@Nullable GroupEvent event) {
        if (event != null) {
            Log.i("Default Event Handler", "No proper handler for " + event.getClass());
        }
    }
}