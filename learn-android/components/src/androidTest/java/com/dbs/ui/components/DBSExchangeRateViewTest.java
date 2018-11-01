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

import android.graphics.drawable.Drawable;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.LinearLayout;

import com.dbs.components.test.R;
import com.dbs.ui.components.testRule.ViewTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.dbs.ui.components.Matchers.CustomTestMatcher.withDrawable;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class DBSExchangeRateViewTest {
    @Rule
    public ViewTestRule<LinearLayout> viewTestRule = new ViewTestRule<>(R.layout.dbs_exchange_rate_view_test);
    private DBSExchangeRateView dbsExchangeRateView;
    Drawable exchangeIcon;

    @Before
    public void setUp() {
        dbsExchangeRateView = viewTestRule.getView().findViewById(R.id.exchangerate_view);
        exchangeIcon = viewTestRule.getActivity().getResources().getDrawable(R.drawable.happy_image);
    }

    @Test
    public void shouldShowInflatedValue() {
        onView(withId(com.dbs.components.R.id.amt_from)).check(matches(withText("1 SGD")));
        onView(withId(com.dbs.components.R.id.amt_to)).check(matches(withText("999 USD")));
    }

    @Test
    public void shouldAbleToSetValues() {
        dbsExchangeRateView.setValue("2 HDK", "3 Inr");
        onView(withId(com.dbs.components.R.id.amt_from)).check(matches(withText("2 HDK")));
        onView(withId(com.dbs.components.R.id.amt_to)).check(matches(withText("3 Inr")));
    }

    @Test
    public void shouldAbleToSetValuesAndDrawable() {
        dbsExchangeRateView.setValue("2 HDK", "3 Inr", exchangeIcon);
        onView(withId(com.dbs.components.R.id.ic_exchange)).check(matches(withDrawable(R.drawable.happy_image)));
    }

    @Test
    public void shouldAbleToSetValuesAndDrawableWithId() {
        dbsExchangeRateView.setValue("2 HDK", "3 Inr", R.drawable.happy_image);
        onView(withId(com.dbs.components.R.id.ic_exchange)).check(matches(withDrawable(R.drawable.happy_image)));
    }

}
