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

package com.dbs.ui.widgets.carousel;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * The CarouselViewPagerScroller is custom Scroller
 *
 * @author DBS Bank, AppStudio Team
 */
public class CarouselViewPagerScroller extends Scroller {

    private int mScrollDuration = 600;

    public CarouselViewPagerScroller(Context context) {
        super(context);
    }

    public CarouselViewPagerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    /**
     * Returns scrollDuration
     */
    public int getScrollDuration() {
        return mScrollDuration;
    }

    /**
     * Sets scrollDuration
     */
    public void setScrollDuration(int mScrollDuration) {
        this.mScrollDuration = mScrollDuration;
    }

    /**
     * @inheritDoc
     * Scroll Duration is overridden
     */
    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mScrollDuration);
    }

    /**
     * @inheritDoc
     * Scroll Duration added as parameter while starting scroll
     */
    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mScrollDuration);
    }
}