package com.dbs.ui.multiadapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;

import com.xwray.groupie.Group;
import com.xwray.groupie.GroupAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * The base adapter using groupie and manage the factory
 * <p>
 * It has the ability to build adapter using factories of {@link Group}
 * <p>
 * You also might need to construct {@link GroupEventHandler} an pass to the Adapter
 * <p>
 * So that it can handle any event you fire from the {@link com.xwray.groupie.Item}
 */
public class MultipleTypeAdapter extends GroupAdapter {

    /**
     * Event handler
     */
    private GroupEventHandler mGroupEventHandler;

    /**
     * Store all factories that can create Group, key is int, prefer Class hashcode value.
     */
    private SparseArrayCompat<GroupItemFactory> mGroupItemFactoriesArray;


    /**
     * An internal dispatcher, pass to item
     */
    @SuppressWarnings("unchecked")
    private GroupEventDispatcher mInternalDispatcher = event -> mGroupEventHandler.handle(event);


    /**
     * Constructor
     */
    public MultipleTypeAdapter() {
        mGroupItemFactoriesArray = new SparseArrayCompat<>();
    }


    /**
     * create an adapter with array of factories
     *
     * @param factories
     */
    public MultipleTypeAdapter(@NonNull List<GroupItemFactory> factories, GroupEventHandler dispatcher) {
        this();
        for (GroupItemFactory factory : factories) {
            registerFactory(factory);
        }
        mGroupEventHandler = dispatcher;
    }

    /**
     * set an event handler
     *
     * @param handler
     */
    public void setGroupEventHandler(GroupEventHandler handler) {
        this.mGroupEventHandler = handler;
    }

    /**
     * Register a factory manually.
     * If you need to unregister...who on this freaking earth need to unregister????
     *
     * @param factory
     */
    public synchronized void registerFactory(@NonNull GroupItemFactory factory) {
        mGroupItemFactoriesArray.put(factory.getKey(), factory);
    }

    /**
     * Create groups with list of objects
     */
    public void updateListModel(@NonNull List list) {
        update(createGroupsFromModel(list));
    }

    @SuppressWarnings("unchecked")
    @SuppressLint("unchecked")
    @NonNull
    private List<Group> createGroupsFromModel(@NonNull List list) {
        List<Group> groups = new ArrayList<>();
        for (Object model : list) {
            if (model != null) {
                GroupItemFactory factory = mGroupItemFactoriesArray.get(model.getClass().hashCode());
                if (factory != null) {
                    groups.add(factory.create(model, mInternalDispatcher));
                }
            }
        }
        return groups;
    }
}