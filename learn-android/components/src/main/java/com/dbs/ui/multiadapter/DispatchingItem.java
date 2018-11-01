package com.dbs.ui.multiadapter;

import android.support.annotation.Nullable;

import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

/**
 * Extension for {@link Item} that can dispatch {@link GroupEvent}
 */
public abstract class DispatchingItem<V extends ViewHolder> extends Item<V> {

    protected GroupEventDispatcher mDispatcher;

    public DispatchingItem() {
        super();
    }

    protected DispatchingItem(long id) {
        super(id);
    }

    /**
     * Shared method among all extending child
     *
     * @param event
     */
    protected void dispatch(@Nullable GroupEvent event) {
        if (mDispatcher != null) {
            mDispatcher.dispatch(event);
        }
    }
}