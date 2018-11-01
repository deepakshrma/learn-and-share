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

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dbs.ui.components.testRule.EmptyActivity;
import com.dbs.ui.utils.ThemeUtils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.dbs.ui.utils.ThemeUtils.getThemeName;
import static com.dbs.ui.utils.ThemeUtils.switchTheme;
import static junit.framework.Assert.assertEquals;

/**
 * Created by deepak on 2/10/18.
 */

@RunWith(AndroidJUnit4.class)
public class UtilsTest {
    @Rule
    public ActivityTestRule<EmptyActivity> mockActivityRule = new ActivityTestRule<>(EmptyActivity.class);

    @Test
    public void themeSwitch() {
        Activity mockActivity = mockActivityRule.getActivity();
        //Need to add real test cases, Not in use
        switchTheme(mockActivity, ThemeUtils.DBSCoreThemes.DEFAULT);
        switchTheme(mockActivity, ThemeUtils.DBSCoreThemes.INDIA);
        assertEquals(getThemeName(mockActivity), ThemeUtils.DBSCoreThemes.INDIA.getName());
    }
}
