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

import android.support.test.runner.AndroidJUnit4;

import com.dbs.components.R;
import com.dbs.ui.components.testRule.ViewTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class DBSFeedbackFormCustomViewTest {

    @Rule
    public ViewTestRule<DBSFeedbackFormView> rule = new ViewTestRule<>(com.dbs.components.test.R.layout.dbs_feedbackform_customized);

    private DBSFeedbackFormView dbsFeedbackForm;

    @Before
    public void before() {
        updateGlobalReferencesOfViewsInTest();
    }


    @Test
    public void shouldBeAbleToCustomizeTitleText() {
        updateGlobalReferencesOfViewsInTest();

        onView(withId(R.id.title)).check(matches(withText("Custom title")));


        rule.getActivity().runOnUiThread(() -> dbsFeedbackForm.setTitleText("Custom title2"));

        onView(withId(R.id.title)).check(matches(withText("Custom title2")));
    }

    @Test
    public void shouldBeAbleToCustomizeImprovementsTitleText() {
        updateGlobalReferencesOfViewsInTest();

        onView(withId(R.id.improvements_title)).check(matches(withText("Custom improvements title")));


        rule.getActivity().runOnUiThread(() -> dbsFeedbackForm.setImprovementsTitle("Custom improvements title2"));

        onView(withId(R.id.improvements_title)).check(matches(withText("Custom improvements title2")));
    }

    @Test
    public void shouldBeAbleToCustomizeHint() {
        updateGlobalReferencesOfViewsInTest();

        onView(withId(R.id.feedback)).check(matches(withHint("Custom hint")));


        rule.getActivity().runOnUiThread(() -> dbsFeedbackForm.setFeedbackHint("Custom hint2"));

        onView(withId(R.id.feedback)).check(matches(withHint("Custom hint2")));
    }

    @Test
    public void shouldBeAbleToCustomizeSubmitButtonText() {
        updateGlobalReferencesOfViewsInTest();

        onView(withId(R.id.submit)).check(matches(withText("Custom submit")));


        rule.getActivity().runOnUiThread(() -> dbsFeedbackForm.setSubmitButtonText("Custom submit2"));

        onView(withId(R.id.submit)).check(matches(withText("Custom submit2")));
    }


    private void updateGlobalReferencesOfViewsInTest() {
        dbsFeedbackForm = rule.getView();
    }
}