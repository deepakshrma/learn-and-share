package com.dbs.ui.components;

import android.support.test.espresso.action.ViewActions;
import android.support.test.runner.AndroidJUnit4;

import com.dbs.components.R;
import com.dbs.ui.components.dbsrating.DBSRatingBar;
import com.dbs.ui.components.testRule.ViewTestRule;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withParentIndex;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.dbs.ui.components.viewassertions.CustomViewAssertions.allOf;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.not;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class DBSFeedbackFormViewTest {

    @Rule
    public ViewTestRule<DBSFeedbackFormView> rule = new ViewTestRule<>(com.dbs.components.test.R.layout.dbs_feedback_form_test);

    private DBSRatingBar dbsRatingBar;
    private int ratingBarId = com.dbs.components.R.id.rating_bar;
    private DBSFeedbackFormView dbsFeedbackForm;

    @Before
    public void before() {
        updateGlobalReferencesOfViewsInTest();
    }

    @Test
    public void shouldShowRatingBarWith0RatingByDefault() {
        assertEquals(0, dbsRatingBar.getRating());
        onView(withId(ratingBarId)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldShowTitle() {
        onView(withId(com.dbs.components.R.id.title)).check(allOf(isDisplayed(), withText("How would you rate your overall experience today?")));
    }

    @Test
    public void shouldHaveEmptyTextForRatingCommentByDefault() {
        onView(withId(com.dbs.components.R.id.rating_comment)).check(matches(withText("")));
    }

    @Test
    public void shouldHideImprovementsTitleByDefault() {
        onView(withId(R.id.improvements_title)).check(matches(not(isDisplayed())));

    }

    @Test
    public void shouldHideImprovementsByDefault() {
        onView(withId(R.id.improvements_grid)).check(matches(not(isDisplayed())));
    }

    @Test
    public void shouldShowCommentTextAreaByDefaultHavingEmptyTextAndDefaultPlaceholder() {
        onView(withId(com.dbs.components.R.id.feedback)).check(allOf(isDisplayed(), withHint("Feedback (optional)"), withText("")));
    }

    @Test
    public void shouldShowSubmitButtonByDefault() {
        onView(withId(com.dbs.components.R.id.submit)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldBeAbleToGetRating() {
        onView(Matchers.allOf(withParent(withId(com.dbs.components.R.id.stars_container)), withParentIndex(2))).perform(click());
        assertEquals(3, dbsFeedbackForm.getRating());
    }

    @Test
    public void shouldBeAbleToSetRating() {
        rule.runOnMainSynchronously(view -> dbsFeedbackForm.setRating(3));
        assertEquals(3, dbsFeedbackForm.getRating());
    }

    @Test
    public void shouldShowDefaultCommentsWhenRated() {
        onView(Matchers.allOf(withParent(withId(com.dbs.components.R.id.stars_container)), withParentIndex(0))).perform(click());

        onView(withId(com.dbs.components.R.id.rating_comment)).check(allOf(withText("Poor"), isDisplayed()));

        onView(Matchers.allOf(withParent(withId(com.dbs.components.R.id.stars_container)), withParentIndex(1))).perform(click());

        onView(withId(com.dbs.components.R.id.rating_comment)).check(allOf(withText("Fair"), isDisplayed()));

        onView(Matchers.allOf(withParent(withId(com.dbs.components.R.id.stars_container)), withParentIndex(2))).perform(click());

        onView(withId(com.dbs.components.R.id.rating_comment)).check(allOf(withText("Good"), isDisplayed()));

        onView(Matchers.allOf(withParent(withId(com.dbs.components.R.id.stars_container)), withParentIndex(3))).perform(click());

        onView(withId(com.dbs.components.R.id.rating_comment)).check(allOf(withText("Very Good"), isDisplayed()));

        onView(Matchers.allOf(withParent(withId(com.dbs.components.R.id.stars_container)), withParentIndex(4))).perform(click());

        onView(withId(com.dbs.components.R.id.rating_comment)).check(allOf(withText("Excellent"), isDisplayed()));
    }

    @Test
    public void shouldShowThingsToBeImprovedWhenRatingIsLessThan4() {
        rule.getActivity().runOnUiThread(() -> dbsFeedbackForm.setRating(3));

        onView((withId(R.id.improvements_title))).check(allOf(isDisplayed(), withText("What can be improved?")));
        onView((withId(R.id.improvements_grid))).check(matches(isDisplayed()));
        onView(withText("Look and feel")).check(matches(isDisplayed()));
        onView(withText("Ease of finding")).check(matches(isDisplayed()));
        onView(withText("Ease of use")).check(matches(isDisplayed()));
        onView(withText("Technical issues")).check(matches(isDisplayed()));
        onView(withText("Range of services")).check(matches(isDisplayed()));
        onView(withText("Others")).check(matches(isDisplayed()));
    }

    @Test
    public void shouldHideImprovementsWhenRatingIsGreaterThan3() {
        rule.getActivity().runOnUiThread(() -> {
            dbsFeedbackForm.setRating(1);
            dbsFeedbackForm.setRating(4);
        });

        onView(withId(R.id.improvements_title)).check(matches(not(isDisplayed())));
        onView(withId(R.id.improvements_grid)).check(matches(not(isDisplayed())));
    }

    @Test
    public void shouldCallOnSubmitCallbackWhenUserSubmitsFeedback() {
        DBSFeedbackFormView.OnFeedbackFormSubmitListener mockedOnFeedbackFormSubmitListener = mock(DBSFeedbackFormView.OnFeedbackFormSubmitListener.class);
        rule.getActivity().runOnUiThread(() -> {
            dbsFeedbackForm.setRating(4);
            dbsFeedbackForm.setOnFeedbackFormSubmitListener(mockedOnFeedbackFormSubmitListener);
        });
        onView((withId(R.id.feedback))).perform(ViewActions.typeText("nice experience"));
        pressBack();
        onView(withId(R.id.submit)).perform(click());

        verify(mockedOnFeedbackFormSubmitListener, times(1)).onSubmit(4, "nice experience");
        verify(mockedOnFeedbackFormSubmitListener, never()).onSubmitWithThingsToBeImproved(anyInt(), anyString(), any(List.class));
    }

    @Test
    public void shouldCallOnSubmitWithThingsToBeImprovedCallbackWhenUserSubmitsFeedback() {
        DBSFeedbackFormView.OnFeedbackFormSubmitListener mockedOnFeedbackFormSubmitListener = mock(DBSFeedbackFormView.OnFeedbackFormSubmitListener.class);
        dbsFeedbackForm.setOnFeedbackFormSubmitListener(mockedOnFeedbackFormSubmitListener);

        onView(Matchers.allOf(withParent(withId(R.id.stars_container)), withParentIndex(2))).perform(click());
        onView((withId(R.id.feedback))).perform(ViewActions.typeText("bad experience"));
        onView(withId(R.id.feedback)).perform(closeSoftKeyboard());
        onView(withText("Look and feel")).perform(click());
        onView(withText("Ease of finding")).perform(click());
        onView(withId(R.id.submit)).perform(click());

        verify(mockedOnFeedbackFormSubmitListener, never()).onSubmit(anyInt(), anyString());

        verify(mockedOnFeedbackFormSubmitListener, times(1)).onSubmitWithThingsToBeImproved(3, "bad experience", Arrays.asList("Look and feel", "Ease of finding"));
    }


    @Test
    public void shouldBeAbleToCustomizeImprovementsList() {
        rule.getActivity().runOnUiThread(() -> {
            dbsFeedbackForm.setImprovements(new String[]{"Improvement 1", "Improvement 2", "Improvement 3", "Improvement 4", "Improvement 5", "Improvement 6"});
            dbsFeedbackForm.setRating(2);
        });
        onView(Matchers.allOf(withId(R.id.improvement), withParent(Matchers.allOf(withParentIndex(0), withParent(withId(R.id.improvements_grid)))))).check(allOf(withText("Improvement 1"), isDisplayed()));
        onView(Matchers.allOf(withId(R.id.improvement), withParent(Matchers.allOf(withParentIndex(1), withParent(withId(R.id.improvements_grid)))))).check(allOf(withText("Improvement 2"), isDisplayed()));
        onView(Matchers.allOf(withId(R.id.improvement), withParent(Matchers.allOf(withParentIndex(2), withParent(withId(R.id.improvements_grid)))))).check(allOf(withText("Improvement 3"), isDisplayed()));
        onView(Matchers.allOf(withId(R.id.improvement), withParent(Matchers.allOf(withParentIndex(3), withParent(withId(R.id.improvements_grid)))))).check(allOf(withText("Improvement 4"), isDisplayed()));
        onView(Matchers.allOf(withId(R.id.improvement), withParent(Matchers.allOf(withParentIndex(4), withParent(withId(R.id.improvements_grid)))))).check(allOf(withText("Improvement 5"), isDisplayed()));
        onView(Matchers.allOf(withId(R.id.improvement), withParent(Matchers.allOf(withParentIndex(5), withParent(withId(R.id.improvements_grid)))))).check(allOf(withText("Improvement 6"), isDisplayed()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotBeAbleToSetNegativeRating() {
        rule.getActivity().runOnUiThread(() -> {
            dbsFeedbackForm.setRating(1);
        });
        rule.waitForIdleSync();

        dbsFeedbackForm.setRating(-1);
    }

    @Test
    public void shouldBeAbleToSetZeroRating() {
        rule.getActivity().runOnUiThread(() -> {
            dbsFeedbackForm.setRating(1);
            dbsFeedbackForm.setRating(0);
        });
        onView(withId(R.id.improvements_grid)).check(matches(not(isDisplayed())));
        onView(withId(R.id.improvements_title)).check(matches(not(isDisplayed())));
        assertEquals(0, dbsFeedbackForm.getRating());
    }

    @Test
    public void shouldBeInvalidFormIfRatingLessThan4AndNotSelectedAreaOfImprovement() {
        rule.getActivity().runOnUiThread(() -> {
            dbsFeedbackForm.setRating(3);
            assertEquals(false, dbsFeedbackForm.isValid());
        });
        rule.getActivity().runOnUiThread(() -> {
            dbsFeedbackForm.setRating(1);
            assertEquals(false, dbsFeedbackForm.isValid());
        });
    }

    @Test
    public void shouldBeValidFormIfRatingLessThan4AndSelectedAreaOfImprovement() {
        rule.getActivity().runOnUiThread(() -> {
            dbsFeedbackForm.setRating(3);
            assertEquals(false, dbsFeedbackForm.isValid());
        });
        onView(withId(R.id.submit)).check(matches(not(isEnabled())));
        rule.getActivity().runOnUiThread(() -> {
            dbsFeedbackForm.getFeedbackEditText().setText(
                    "12345678901234567890123456789012345678901234567890");
            dbsFeedbackForm.setRating(3);
        });
        onView(Matchers.allOf(withId(R.id.improvement), withParent(Matchers.allOf(withParentIndex(0), withParent(withId(R.id.improvements_grid)))))).perform(click());
        onView(withId(R.id.submit)).check(matches(isEnabled()));
    }

    @Test
    public void shouldBeInvalidFormIfFeedBackCommentMoreThanLimit150Default() {
        rule.getActivity().runOnUiThread(() -> {
            dbsFeedbackForm.getFeedbackEditText().setText(
                    "12345678901234567890123456789012345678901234567890" +
                            "12345678901234567890123456789012345678901234567890" +
                            "12345678901234567890123456789012345678901234567890" +
                            "11");
            assertEquals(false, dbsFeedbackForm.isValid());
        });
        rule.getActivity().runOnUiThread(() -> {
            dbsFeedbackForm.setRating(4);
            dbsFeedbackForm.getFeedbackEditText().setText(
                    "12345678901234567890123456789012345678901234567890" +
                            "12345678901234567890123456789012345678901234567890" +
                            "12345678901234567890123456789012345678901234567890");
            assertEquals(true, dbsFeedbackForm.isValid());
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotBeAbleToSetRatingGreaterThanMaxRating() {
        dbsFeedbackForm.setRating(8);
    }

    private void updateGlobalReferencesOfViewsInTest() {
        dbsRatingBar = rule.getView().findViewById(com.dbs.components.R.id.rating_bar);
        dbsFeedbackForm = rule.getView();
    }
}