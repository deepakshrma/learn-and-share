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
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * The DBSViewPager class helps to create basic ViewPager.
 * Developer can enable and disable Swipe on ViewPager.
 *
 * @author DBS Bank, AppStudio Team
 * @version 1.0.0
 * @since 2018-05-01
 */
public class DBSViewPager extends ViewPager {
    private boolean enabledSwipe;

    public DBSViewPager(Context context) {
        super(context);
    }

    public DBSViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        enabledSwipe = false;
    }

    /**
     * @inheritDoc
     * Handle touch event only if swipe enabled.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return enabledSwipe && super.onTouchEvent(event);
    }

    /**
     * @inheritDoc
     * Intercept touch event only if swipe enabled.
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return enabledSwipe && super.onInterceptTouchEvent(event);
    }

    /**
     * This method is to disable Paging on ViewPager
     *
     * @param enabled {boolean}
     */
    public void setPagingEnabled(boolean enabled) {
        this.enabledSwipe = enabled;
    }
}