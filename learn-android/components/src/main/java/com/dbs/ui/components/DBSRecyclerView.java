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

package com.dbs.ui.components;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.dbs.components.R;
import com.dbs.ui.BaseView;

/**
 * DBSRecyclerView is a wrapper for recycler view with horizontal linear layout manager.
 *
 * @author DBS Bank, AppStudio Team
 * @version 1.0.0
 * @since 2018-05-01
 */

public class DBSRecyclerView extends BaseView {

    private RecyclerView recyclerView;

    public DBSRecyclerView(@NonNull Context context) {
        super(context);
    }

    public DBSRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DBSRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void viewInflated(View view, String type, AttributeSet attrs) {
        recyclerView = view.findViewById(R.id.dbsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    protected int provideLayoutId(String type) {
        return R.layout.dbs_recycler_view;
    }

    /**
     * Returns the recycler view which is wrapped in this component.
     */
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    /**
     * Sets adapter for recycler view.
     */
    public void setRecyclerViewAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    /**
     * Sets orientation of recycler view with linear layout manager.
     */
    public void setOrientation(int orientation) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), orientation, false));
    }
}
