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
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dbs.components.R;
import com.dbs.ui.BaseView;

/**
 * The DBSActionBar component helps to create top navbar with text in middle and icon on the left.
 *
 * @author DBS Bank, AppStudio Team
 * @version 1.0.0
 * @since 2018-05-01
 */

public class DBSActionBar extends BaseView {
    private OnActionBarClickListener actionBarClickListener;
    private ImageView backIcon;
    private TextView barTitle;

    public DBSActionBar(Context context) {
        super(context);
    }

    public DBSActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DBSActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * This method is invoked after view is initialized and
     * checks for actionbarBackIcon and actionbarTitle,
     * update view accordingly
     *
     * @param view { Actual view of the Custom view }
     * @param type { type of the Custom view, which inflated }
     * @param attrs { AttributeSet of the Custom view }
     */
    @Override
    protected void viewInflated(View view, String type, AttributeSet attrs) {
        backIcon = findViewById(R.id.actionbarBackIcon);
        barTitle = findViewById(R.id.actionbarTitle);
        if (attrs != null) {
            TypedArray attributes = view.getContext().obtainStyledAttributes(attrs, R.styleable.DBSActionBar, 0, 0);
            int backIconId = attributes.getResourceId(R.styleable.DBSActionBar_backIcon, -1);
            Drawable backIconDrawable = attributes.getDrawable(R.styleable.DBSActionBar_backIcon);
            String title = attributes.getString(R.styleable.DBSActionBar_title);
            attributes.recycle();
            if (backIconId != -1) {
                backIcon.setImageDrawable(backIconDrawable);
            }
            if (title != null && !title.isEmpty()) {
                barTitle.setText(title);
            }
            bindListeners();
        }
    }

    /**
     * Provide layout id needs to be inflated while building custom components
     * @param type layout could be differentiated depending on the type.
     * @return layout id to be inflated for this view.
     */
    @Override
    protected int provideLayoutId(String type) {
        return R.layout.action_toolbar;
    }

    /**
     * This method is to set icons click listeners
     */
    public void setOnActionBarClickListener(OnActionBarClickListener listeners) {
        actionBarClickListener = listeners;
    }

    private void bindListeners() {
        backIcon.setOnClickListener(v -> {
            if (actionBarClickListener != null)
                actionBarClickListener.onBackIconClick();
        });
        barTitle.setOnClickListener(v -> {
            if (actionBarClickListener != null)
                actionBarClickListener.onTitleBarClick();
        });
    }

    /**
     * When an object of a type is attached to DBSActionBar, its methods will
     * be called when action bar clicked.
     */
    public interface OnActionBarClickListener {
        /**
         * This method is called to notify you that, the back icon was clicked.
         */
        void onBackIconClick();

        /**
         * This method is called to notify you that, the title bar was clicked.
         */
        void onTitleBarClick();
    }
}