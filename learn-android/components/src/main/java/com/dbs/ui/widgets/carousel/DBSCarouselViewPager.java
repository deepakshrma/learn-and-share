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
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;

import java.lang.reflect.Field;

/**
 * The DBSCarouselViewPager is viewPager for carousel
 *
 * @author DBS Bank, AppStudio Team
 */
public class DBSCarouselViewPager extends ViewPager {

    private ImageClickListener imageClickListener;
    private float oldX = 0;
    private CarouselViewPagerScroller mScroller = null;

    public DBSCarouselViewPager(Context context) {
        super(context);
        postInitViewPager();
    }

    public DBSCarouselViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        postInitViewPager();
    }

    public void setImageClickListener(ImageClickListener imageClickListener) {
        this.imageClickListener = imageClickListener;
    }

    /**
     * Override the Scroller instance with our own class so we can change the
     * duration
     */
    private void postInitViewPager() {
        try {
            Class<?> viewpager = ViewPager.class;
            Field scroller = viewpager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            Field interpolator = viewpager.getDeclaredField("sInterpolator");
            interpolator.setAccessible(true);

            mScroller = new CarouselViewPagerScroller(getContext(),
                    (Interpolator) interpolator.get(null));
            scroller.set(this, mScroller);
        } catch (Exception e) {
        }
    }

    /**
     * Set the factor by which the duration will change
     */
    public void setTransitionVelocity(int scrollFactor) {
        mScroller.setScrollDuration(scrollFactor);
    }

    /**
     * @inheritDoc
     * Invoke image click listener if the touch event was click
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldX = ev.getX();
                break;

            case MotionEvent.ACTION_UP:
                float newX = ev.getX();
                float sens = 5;
                if (Math.abs(oldX - newX) < sens) {
                    if (imageClickListener != null)
                        imageClickListener.onClick(getCurrentItem());
                    return true;
                }
                oldX = 0;
                break;
        }

        return super.onTouchEvent(ev);
    }
}