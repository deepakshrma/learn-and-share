package com.dbs.ui.multiadapter;

import android.support.annotation.Nullable;

/**
 * Group Event Dispatcher
 */
public interface GroupEventDispatcher {
    /**
     * Dispatch the event to handler
     *
     * @param event
     */
    void dispatch(@Nullable GroupEvent event);
}