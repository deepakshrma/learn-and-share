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

package com.dbs.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.dbs.components.R;
import com.dbs.ui.BaseView;
import com.dbs.ui.DBSViewPager;

/**
 * The MultiStepsPager component is to create multi page view.
 *
 * @author DBS Bank, AppStudio Team
 */
public class MultiStepsPager extends BaseView {
    private DBSViewPager multiStepsPager;
    private OnMultiPageChangeListener onPageChangeListener;
    private boolean enableSwipe;

    public MultiStepsPager(@NonNull Context context) {
        super(context);
    }

    public MultiStepsPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MultiStepsPager(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void viewInflated(View view, String type, AttributeSet attrs) {
        multiStepsPager = findViewById(R.id.multiStepsPager);
        if (attrs != null) {
            TypedArray attributes = view.getContext().obtainStyledAttributes(attrs, R.styleable.MultiStepsPager, 0, 0);
            enableSwipe = attributes.getBoolean(R.styleable.MultiStepsPager_android_enabled, false);
            attributes.recycle();
        }
        if (enableSwipe) {
            multiStepsPager.setPagingEnabled(true);
        }
        multiStepsPager.setPageTransformer(true, new CarouselPageTransformer());
    }

    @Override
    protected int provideLayoutId(String type) {
        return R.layout.multi_steps_pager_layout;
    }

    /**
     * Sets pager adapter
     */
    public void setMultiStepsPagerAdaptor(PagerAdapter pagerAdaptor) {
        multiStepsPager.setAdapter(pagerAdaptor);
    }

    /**
     * Navigate to next page
     */
    public void goToNextPage() {
        int currentPageNum = multiStepsPager.getCurrentItem();
        int nextPageNum = multiStepsPager.getCurrentItem() + 1;
        if (currentPageNum != nextPageNum) {
            multiStepsPager.setCurrentItem(nextPageNum);
        }
    }

    /**
     * Navigate to previous page
     */
    public void goToBackPage() {
        int currentPageNum = multiStepsPager.getCurrentItem();
        int nextPageNum = multiStepsPager.getCurrentItem() - 1;
        if (currentPageNum != nextPageNum) {
            multiStepsPager.setCurrentItem(nextPageNum);
        }
    }

    /**
     * Navigate to a specific page
     * @param nextPageNum page index to navigate to.
     */
    public void goToPage(int nextPageNum) {
        int currentPageNum = multiStepsPager.getCurrentItem();
        if (currentPageNum != nextPageNum) {
            multiStepsPager.setCurrentItem(nextPageNum);
        }
    }

    /**
     * Sets touch listener
     */
    public void setMultiStepsPagerOnTouchListener(View.OnTouchListener pagerAdaptor) {
        multiStepsPager.setOnTouchListener(pagerAdaptor);
    }

    /**
     * Sets page change listener
     */
    public void setMultiStepsPagerListener(ViewPager.OnPageChangeListener listener) {
        multiStepsPager.addOnPageChangeListener(listener);
    }

    /**
     * Returns view pager
     */
    public DBSViewPager getViewPager() {
        return multiStepsPager;
    }

    /**
     * When an object of a type is attached to {@link MultiStepsPager}, its methods will
     * be called to notify that page needs to change.
     */
    public interface OnMultiPageChangeListener {
        /**
         * This method is called to notify you that,
         * next page should open
         */
        default boolean onNext() {
            // do nothing
            return false;
        }

        /**
         * This method is called to notify you that,
         * previous page should open
         */
        default boolean onBack() {
            // do nothing
            return false;
        }
    }

    // TODO: TO REMOVE, Added for demo purpose
    class CarouselPageTransformer implements ViewPager.PageTransformer {
        @Override
        public void transformPage(View page, float position) {
            float scaleFactor = 0.5f;
            float rotationFactor = 40;

            if (position < 0) {
                page.setRotationY(rotationFactor * -position);
                float scale = 1 + scaleFactor * position;
                page.setScaleX(scale);
                page.setScaleY(scale);

            } else {
                page.setRotationY(rotationFactor * -position);
                float scale = 1 - scaleFactor * position;
                page.setScaleX(scale);
                page.setScaleY(scale);
            }
        }
    }
}