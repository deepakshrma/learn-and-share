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

import android.support.v4.view.ViewPager;

/**
 * A PageIndicator is responsible to show an visual indicator on the total views
 * number and the current visible view.
 *
 * @author DBS Bank, AppStudio Team
 */
public interface PageIndicator extends ViewPager.OnPageChangeListener {
    /**
     * Bind the indicator to a ViewPager.
     *
     * @param view viewPager to be set
     */
    void setViewPager(ViewPager view);

    /**
     * Bind the indicator to a ViewPager.
     *
     * @param view viewPager to be set
     * @param initialPosition initial position of viewPager
     */
    void setViewPager(ViewPager view, int initialPosition);

    /**
     * <p>Set the current page of both the ViewPager and indicator.</p>
     * <p/>
     * <p>This <strong>must</strong> be used if you need to set the page before
     * the views are drawn on screen (e.g., default start page).</p>
     *
     * @param item current item posiiton
     */
    void setCurrentItem(int item);

    /**
     * Set a page change listener which will receive forwarded events.
     *
     * @param listener page change listener
     */
    void setOnPageChangeListener(ViewPager.OnPageChangeListener listener);

    /**
     * Notify the indicator that the fragment list has changed.
     */
    void notifyDataSetChanged();
}