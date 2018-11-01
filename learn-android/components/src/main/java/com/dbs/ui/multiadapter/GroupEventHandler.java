package com.dbs.ui.multiadapter;

public abstract class GroupEventHandler<E extends GroupEvent> extends ClassToKey {


    /**
     * should call this to get the key
     *
     * @param klass
     */
    public GroupEventHandler(Class klass) {
        super(klass);
    }

    /**
     * Handle the event
     *
     * @param event
     */
    public abstract void handle(E event);
}