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

package com.dbs.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.dbs.components.R;

/**
 * The BaseView class helps to create custom component/view.
 * It implements basic constructor while extending FrameLayout seamlessly.
 *
 * @author DBS Bank, AppStudio Team
 * @version 1.0.0
 * @since 2018-05-01
 */
public abstract class BaseView extends FrameLayout {
    protected String type = "default"; //Default if not defined
    protected View view;

    public BaseView(@NonNull Context context) {
        this(context, null);
    }

    public BaseView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
        init(context, attrs);
    }

    public BaseView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * Returns this instance of view
     */
    public View getView() {
        return view;
    }

    /**
     * This method initialize the view of custom components based on layout id provided.
     * Developer can override to provide there own init UI based on custom view type
     *
     * @param type {Type of layout xml, derived from attribute "type"}
     */
    protected void initUI(String type) {
        int viewId = provideLayoutId(type);
        if (viewId != -1) {
            removeAllViews();
            view = LayoutInflater.from(getContext()).inflate(provideLayoutId(type), null);
            addView(view);
        }
    }

    /**
     * This method gets called to notify that view is created.
     * The method implementation can take care of the additional setup needed for view.
     */
    protected abstract void viewInflated(View view, String type, AttributeSet attrs);

    /**
     * This method gets called to get the layout to be inflated.
     */
    protected abstract int provideLayoutId(String type);

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseView, 0, 0);
            type = typedArray.getString(R.styleable.BaseView_type);
            typedArray.recycle();
        }
        initUI(type);
        viewInflated(view, type, attrs);
    }
}