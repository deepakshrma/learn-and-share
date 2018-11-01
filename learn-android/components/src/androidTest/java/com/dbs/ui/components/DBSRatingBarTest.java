package com.dbs.ui.components;

import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dbs.components.R;
import com.dbs.ui.components.dbsrating.DBSRatingBar;
import com.dbs.ui.components.testRule.ViewTestRule;
import com.dbs.ui.utils.ViewUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isSelected;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withParentIndex;
import static com.dbs.ui.components.Matchers.CustomTestMatcher.withDrawable;
import static com.dbs.ui.components.viewassertions.CustomViewAssertions.hasNChildViewsOfType;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(AndroidJUnit4.class)
public class DBSRatingBarTest {

    DBSRatingBar dbsRatingBar;

    @Rule
    public ViewTestRule<View> rule = new ViewTestRule<>(com.dbs.components.test.R.layout.dbs_rating_bar_default);

    @Before
    public void before() {
        dbsRatingBar = rule.getView().findViewById(com.dbs.components.test.R.id.rating_bar);
    }

    @Test
    public void shouldHave5StarsByDefault() {
        onView(withId(R.id.stars_container)).check(hasNChildViewsOfType(5, ImageView.class));
        assertEquals(5, dbsRatingBar.getMaxRating());
    }

    @Test
    public void allStarsShouldBeUnselectedByDefault() {
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(0))).check(matches(allOf(not(isSelected()), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(1))).check(matches(allOf(not(isSelected()), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(2))).check(matches(allOf(not(isSelected()), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(3))).check(matches(allOf(not(isSelected()), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(4))).check(matches(allOf(not(isSelected()), isDisplayed())));
    }

    @Test
    public void allStarsShouldDisplayStarImage() {
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(0))).check(matches(allOf(withDrawable(R.drawable.rating_star))));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(1))).check(matches(allOf(withDrawable(R.drawable.rating_star))));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(2))).check(matches(allOf(withDrawable(R.drawable.rating_star))));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(3))).check(matches(allOf(withDrawable(R.drawable.rating_star))));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(4))).check(matches(allOf(withDrawable(R.drawable.rating_star))));

    }

    @Test
    public void shouldHaveDefaultDimensionsForAllStars() {
        ViewGroup viewGroup = dbsRatingBar.findViewById(R.id.stars_container);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewGroup.getChildAt(0).getLayoutParams();

        assertEquals(ViewUtils.dpToPixels(rule.getActivity(), 35), layoutParams.width);
        assertEquals(ViewUtils.dpToPixels(rule.getActivity(), 35), layoutParams.height);
        assertEquals(layoutParams, viewGroup.getChildAt(1).getLayoutParams());
        assertEquals(layoutParams, viewGroup.getChildAt(2).getLayoutParams());
        assertEquals(layoutParams, viewGroup.getChildAt(3).getLayoutParams());
        assertEquals(layoutParams, viewGroup.getChildAt(4).getLayoutParams());
    }

    @Test
    public void shouldHaveDefaultGapBetweenStars() {
        ViewGroup viewGroup = dbsRatingBar.findViewById(R.id.stars_container);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewGroup.getChildAt(0).getLayoutParams();

        assertEquals(ViewUtils.dpToPixels(rule.getActivity(), 15), layoutParams.getMarginEnd());
    }

    @Test
    public void shouldSelectAll5StarsWhen5thStarIsClicked() {
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(4))).perform(click());


        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(0))).check(matches(allOf(isSelected(), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(1))).check(matches(allOf(isSelected(), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(2))).check(matches(allOf(isSelected(), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(3))).check(matches(allOf(isSelected(), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(4))).check(matches(allOf(isSelected(), isDisplayed())));
    }

    @Test
    public void shouldSelectStarsUntil3When3rdStarIsClicked() {
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(2))).perform(click());


        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(0))).check(matches(allOf(isSelected(), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(1))).check(matches(allOf(isSelected(), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(2))).check(matches(allOf(isSelected(), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(3))).check(matches(allOf(not(isSelected()), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(4))).check(matches(allOf(not(isSelected()), isDisplayed())));
    }

    @Test
    public void shouldSelectOnly1StarsAfterUpdatingRatingFrom5To1() {
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(4))).perform(click());

        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(0))).check(matches(allOf(isSelected(), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(1))).check(matches(allOf(isSelected(), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(2))).check(matches(allOf(isSelected(), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(3))).check(matches(allOf(isSelected(), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(4))).check(matches(allOf(isSelected(), isDisplayed())));

        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(0))).perform(click());

        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(0))).check(matches(allOf(isSelected(), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(1))).check(matches(allOf(not(isSelected()), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(2))).check(matches(allOf(not(isSelected()), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(3))).check(matches(allOf(not(isSelected()), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(4))).check(matches(allOf(not(isSelected()), isDisplayed())));
    }


    @Test
    public void shouldHave0RatingByDefault() {
        assertEquals(0, dbsRatingBar.getRating());
    }

    @Test
    public void shouldGetCurrentRating() {
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(2))).perform(click());

        assertEquals(3, dbsRatingBar.getRating());

    }

    @Test
    public void shouldCallOnRatingChangeListenerWhenRatingChanges() {
        DBSRatingBar.OnRatingChangeListener mockedRatingChangeListener = mock(DBSRatingBar.OnRatingChangeListener.class);
        dbsRatingBar.setRatingChangeListener(mockedRatingChangeListener);

        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(2))).perform(click());

        verify(mockedRatingChangeListener).onRatingUpdated(3);
    }

    @Test
    public void shouldNotCallOnRatingChangeListenerWhenRatingDoesNotChange() {
        DBSRatingBar.OnRatingChangeListener mockedRatingChangeListener = mock(DBSRatingBar.OnRatingChangeListener.class);
        dbsRatingBar.setRatingChangeListener(mockedRatingChangeListener);

        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(2))).perform(click());
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(2))).perform(click());
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(2))).perform(click());
        dbsRatingBar.setRating(3);
        dbsRatingBar.setRating(3);
        dbsRatingBar.setRating(3);

        verify(mockedRatingChangeListener, times(1)).onRatingUpdated(3);
    }

    @Test
    public void shouldBeAbleToSetNumberOfStarsFromXml() {
        rule.updateActivityToNewLayout(com.dbs.components.test.R.layout.dbs_rating_bar_3_stars);

        dbsRatingBar = rule.getActivity().findViewById(com.dbs.components.test.R.id.rating_bar_3);
        onView(withId(R.id.stars_container)).check(hasNChildViewsOfType(3, ImageView.class));
        assertEquals(3, dbsRatingBar.getMaxRating());
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(0))).check(matches(allOf(isSelected(), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(1))).check(matches(allOf(isSelected(), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(2))).check(matches(allOf(not(isSelected()), isDisplayed())));
    }

    @Test
    public void shouldBeAbleToSetPaddingBetweenStarsFromXML() {
        rule.updateActivityToNewLayout(com.dbs.components.test.R.layout.dbs_rating_bar_3_stars);

        dbsRatingBar = rule.getActivity().findViewById(com.dbs.components.test.R.id.rating_bar_3);
        ViewGroup viewGroup = dbsRatingBar.findViewById(R.id.stars_container);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewGroup.getChildAt(0).getLayoutParams();
        assertEquals(ViewUtils.dpToPixels(rule.getActivity(), 25), layoutParams.getMarginEnd());
        assertEquals(layoutParams, viewGroup.getChildAt(1).getLayoutParams());
        assertEquals(layoutParams, viewGroup.getChildAt(2).getLayoutParams());

    }

    @Test
    public void shouldBeAbleToSetLengthOfStarImageFromXML() {
        rule.updateActivityToNewLayout(com.dbs.components.test.R.layout.dbs_rating_bar_3_stars);

        dbsRatingBar = rule.getActivity().findViewById(com.dbs.components.test.R.id.rating_bar_3);

        ViewGroup viewGroup = dbsRatingBar.findViewById(R.id.stars_container);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewGroup.getChildAt(0).getLayoutParams();
        assertEquals(ViewUtils.dpToPixels(rule.getActivity(), 30), layoutParams.width);
        assertEquals(ViewUtils.dpToPixels(rule.getActivity(), 30), layoutParams.height);
    }

    @Test
    public void shouldBeAbleToSetPaddingBetweenStarsFromJava() {
        dbsRatingBar.setGapBetweenStarsInDp(35);

        ViewGroup viewGroup = dbsRatingBar.findViewById(R.id.stars_container);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewGroup.getChildAt(0).getLayoutParams();
        assertEquals(ViewUtils.dpToPixels(rule.getActivity(), 35), layoutParams.getMarginEnd());
        assertEquals(layoutParams, viewGroup.getChildAt(1).getLayoutParams());
        assertEquals(layoutParams, viewGroup.getChildAt(2).getLayoutParams());
        assertEquals(layoutParams, viewGroup.getChildAt(3).getLayoutParams());
        assertEquals(layoutParams, viewGroup.getChildAt(4).getLayoutParams());

    }

    @Test
    public void shouldBeAbleToSetStarImageLengthFromJava() {
        dbsRatingBar.setStarImageLengthInDp(15);

        ViewGroup viewGroup = dbsRatingBar.findViewById(R.id.stars_container);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewGroup.getChildAt(0).getLayoutParams();
        assertEquals(ViewUtils.dpToPixels(rule.getActivity(), 15), layoutParams.width);
        assertEquals(ViewUtils.dpToPixels(rule.getActivity(), 15), layoutParams.height);
        assertEquals(layoutParams, viewGroup.getChildAt(1).getLayoutParams());
        assertEquals(layoutParams, viewGroup.getChildAt(2).getLayoutParams());
        assertEquals(layoutParams, viewGroup.getChildAt(3).getLayoutParams());
        assertEquals(layoutParams, viewGroup.getChildAt(4).getLayoutParams());

    }

    @Test
    public void shouldBeAbleToSetNumberOfStarsFromJava() {
        rule.getActivity().runOnUiThread(() -> dbsRatingBar.setMaxRating(4));

        onView(withId(R.id.stars_container)).check(hasNChildViewsOfType(4, ImageView.class));
        assertEquals(4, dbsRatingBar.getMaxRating());
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(0))).check(matches(allOf(not(isSelected()), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(1))).check(matches(allOf(not(isSelected()), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(2))).check(matches(allOf(not(isSelected()), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(3))).check(matches(allOf(not(isSelected()), isDisplayed())));

    }

    @Test
    public void shouldBeAbleToSetRatingFromXMl() {
        rule.updateActivityToNewLayout(com.dbs.components.test.R.layout.dbs_rating_bar_3_stars);
        dbsRatingBar = rule.getActivity().findViewById(com.dbs.components.test.R.id.rating_bar_3);


        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(0))).check(matches(allOf(isSelected(), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(1))).check(matches(allOf(isSelected(), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(2))).check(matches(allOf(not(isSelected()), isDisplayed())));
    }

    @Test
    public void shouldBeAbleToSetRatingFromJava() {
        dbsRatingBar.setRating(2);

        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(0))).check(matches(allOf(isSelected(), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(1))).check(matches(allOf(isSelected(), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(2))).check(matches(allOf(not(isSelected()), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(3))).check(matches(allOf(not(isSelected()), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(4))).check(matches(allOf(not(isSelected()), isDisplayed())));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotBeAbleToSetNegativeRating() {
        dbsRatingBar.setRating(-1);
    }


    @Test
    public void shouldBeAbleToCreateRatingBarFromJava() {
        dbsRatingBar = new DBSRatingBar(rule.getActivity());
        dbsRatingBar.setMaxRating(4);
        dbsRatingBar.setRating(1);

        rule.updateActivityToNewView(dbsRatingBar);

        onView(withId(R.id.stars_container)).check(hasNChildViewsOfType(4, ImageView.class));
        assertEquals(4, dbsRatingBar.getMaxRating());

        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(0))).check(matches(allOf(isSelected(), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(1))).check(matches(allOf(not(isSelected()), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(2))).check(matches(allOf(not(isSelected()), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(3))).check(matches(allOf(not(isSelected()), isDisplayed())));
    }

    @Test
    public void wouldSetRatingToFullOnSettingStarsLessThanRating() {
        rule.getActivity().runOnUiThread(() -> {
            dbsRatingBar.setRating(4);
            dbsRatingBar.setMaxRating(3);
        });
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(0))).check(matches(allOf(isSelected(), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(1))).check(matches(allOf(isSelected(), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(2))).check(matches(allOf(isSelected(), isDisplayed())));
        onView(withId(R.id.stars_container)).check(hasNChildViewsOfType(3, ImageView.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotBeAbleToSetMaxRatingLessThanOne() {
        dbsRatingBar.setMaxRating(-1);
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(0))).check(matches(allOf(isSelected(), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(1))).check(matches(allOf(isSelected(), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(2))).check(matches(allOf(isSelected(), isDisplayed())));
        onView(withId(R.id.stars_container)).check(hasNChildViewsOfType(3, ImageView.class));
    }

    @Test
    public void shouldBeAbleToSetZeroRating() {
        rule.getActivity().runOnUiThread(() -> {
            dbsRatingBar.setRating(2);
            dbsRatingBar.setRating(0);
        });
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(0))).check(matches(allOf(not(isSelected()), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(1))).check(matches(allOf(not(isSelected()), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(2))).check(matches(allOf(not(isSelected()), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(3))).check(matches(allOf(not(isSelected()), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(4))).check(matches(allOf(not(isSelected()), isDisplayed())));
    }

    @Test
    public void wouldSetRatingAsIsOnSettingStarsGreaterThanRating() {
        rule.getActivity().runOnUiThread(() -> {
            dbsRatingBar.setRating(3);
            dbsRatingBar.setMaxRating(6);
        });
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(0))).check(matches(allOf(isSelected(), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(1))).check(matches(allOf(isSelected(), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(2))).check(matches(allOf(isSelected(), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(3))).check(matches(allOf(not(isSelected()), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(4))).check(matches(allOf(not(isSelected()), isDisplayed())));
        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(5))).check(matches(allOf(not(isSelected()), isDisplayed())));

        onView(withId(R.id.stars_container)).check(hasNChildViewsOfType(6, ImageView.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotBeAbleToSetRatingGreaterThanMaxRating() {
        dbsRatingBar.setRating(8);
    }

    @Test
    public void shouldBeAbleToChangeStarDrawableFromXML() {
        rule.updateActivityToNewLayout(com.dbs.components.test.R.layout.dbs_rating_bar_3_stars);

        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(0))).check(matches(withDrawable(com.dbs.components.test.R.drawable.happy_image)));

    }

    @Test
    public void shouldBeAbleToChangeStarDrawableFromJava() {
        rule.getActivity().runOnUiThread(() -> dbsRatingBar.setStarDrawableResourceId(com.dbs.components.test.R.drawable.happy_image));

        onView(allOf(withParent(withId(R.id.stars_container)), withParentIndex(0))).check(matches(withDrawable(com.dbs.components.test.R.drawable.happy_image)));
    }
}