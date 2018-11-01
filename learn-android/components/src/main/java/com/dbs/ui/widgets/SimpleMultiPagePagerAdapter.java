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

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * The SimpleMultiPagePagerAdapter component is fragment pager adapter for multi pager.
 *
 * @author DBS Bank, AppStudio Team
 */
public class SimpleMultiPagePagerAdapter extends FragmentPagerAdapter {
    private static int PAGE_NUM = 0;
    private List<Fragment> fragments;

    public SimpleMultiPagePagerAdapter(FragmentManager fragmentManager, List<Fragment> fragments) {
        super(fragmentManager);
        this.fragments = fragments;
        PAGE_NUM = fragments.size();
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getCount() {
        return PAGE_NUM;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
}