package com.dbs.ui.multiadapter;

import android.support.annotation.Nullable;

import com.xwray.groupie.Group;

/**
 * The factory that create {@link Group}
 *
 * @param <M>
 */
public abstract class GroupItemFactory<M> extends ClassToKey {

    /**
     * should call this to get the key
     *
     * @param klass
     */
    public GroupItemFactory(Class klass) {
        super(klass);
    }


    /**
     * Create Group
     *
     * @param model      An object data to build your group item
     * @param dispatcher a dispatcher that dispatch event to the adapter
     * @return {@link Group}
     */
    public abstract Group create(@Nullable M model, @Nullable GroupEventDispatcher dispatcher);
}