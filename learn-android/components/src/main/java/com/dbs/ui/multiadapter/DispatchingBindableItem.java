package com.dbs.ui.multiadapter;

import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;

import com.xwray.groupie.databinding.BindableItem;

/**
 * Extension for {@link BindableItem} that can dispatch {@link GroupEvent}
 */
public abstract class DispatchingBindableItem<T extends ViewDataBinding> extends BindableItem<T> {


    protected GroupEventDispatcher mDispatcher;

    public DispatchingBindableItem() {
        super();
    }

    protected DispatchingBindableItem(long id) {
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