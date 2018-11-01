/*
 * - Licensed Materials - Property of DBS Bank SG
 * - "Restricted Materials of DBS Bank"
 *
 * APP Studio SDK, Copyright (c) 2018. DBS Bank SG
 *
 * Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *    - Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *
 *    - Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 */

package com.dbs.ui.adapters;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * The DBSRecyclerViewAdapter is abstract recycler view adapter reducing some boiler plate code.
 *
 * @author DBS Bank, AppStudio Team
 * @version 1.0.0
 * @since 2018-05-01
 */

public abstract class DBSRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<T> items;

    public DBSRecyclerViewAdapter(List<T> items) {
        this.items = items;
    }

    /**
     * This method is called for each item in recycler view to bind the data.
     * @param holder view holder to which data is to be bound
     * @param val data which is to be bound
     * @param position position of the item for which data is being bound
     */
    public abstract void onBindData(RecyclerView.ViewHolder holder, T val, int position);

    /**
     * @inheritDoc
     *
     * {@link DBSRecyclerViewAdapter#onBindData(RecyclerView.ViewHolder, Object, int)} is called with appropriate data.
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindData(holder, items.get(position), position);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * Add items to recycler view
     */
    public void addItems(List<T> savedCardItemz) {
        items.addAll(savedCardItemz);
        this.notifyDataSetChanged();
    }
    /**
     * Add items to recycler view
     */
    public void updateItems(List<T> savedCardItemz) {
        items.clear();
        items.addAll(savedCardItemz);
        this.notifyDataSetChanged();
    }

    /**
     * Returns item at the position.
     */
    public T getItem(int position) {
        return items.get(position);
    }
}
