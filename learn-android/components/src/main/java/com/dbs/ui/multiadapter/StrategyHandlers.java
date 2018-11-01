package com.dbs.ui.multiadapter;

import android.annotation.SuppressLint;
import android.support.v4.util.SparseArrayCompat;

public class StrategyHandlers extends GroupEventHandler {

    /**
     * Store all handlers, the key should be the Event class hashcode
     */
    private SparseArrayCompat<GroupEventHandler> handlers;

    /**
     * A fallback handler
     */
    private GroupEventHandler defaultHandler;

    /**
     * Construct one with fallback
     */
    public StrategyHandlers() {
        this(new UnknownEventHandler());
    }

    /**
     * construct with default handler
     *
     * @param defaultHandler
     */
    public StrategyHandlers(GroupEventHandler defaultHandler) {
        super(GroupEvent.class);
        handlers = new SparseArrayCompat<>();
        this.defaultHandler = defaultHandler;
    }

    /**
     * Register a handler
     * Actually put in the array
     *
     * @param handler
     */
    public void registerHandler(GroupEventHandler handler) {
        handlers.put(handler.getKey(), handler);
    }

    /**
     * Get all handlers except for the default Handler
     */
    public SparseArrayCompat<GroupEventHandler> getHandlers() {
        return handlers.clone();
    }

    @SuppressWarnings("unchecked")
    @SuppressLint("unchecked")
    @Override
    public void handle(GroupEvent event) {
        GroupEventHandler handler = handlers.get(event.getClass().hashCode(), defaultHandler);
        handler.handle(event);
    }
}