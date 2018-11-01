package com.dbs.ui.components;

import android.support.test.runner.AndroidJUnit4;
import android.widget.LinearLayout;

import com.dbs.components.test.R;
import com.dbs.ui.components.testRule.ViewTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.dbs.ui.components.utils.CustomActions.clickAnchor;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DBSTextViewTest {
    static boolean isClicked = false;
    @Rule
    public ViewTestRule<LinearLayout> viewTestRule = new ViewTestRule<>(R.layout.dbs_test_view_test);
    private DBSTextView dbsTextViewWithHtml;

    @Before
    public void setUp() {
        dbsTextViewWithHtml = viewTestRule.getView().findViewById(R.id.textViewWithHtml);
    }

    @Test
    public void shouldAbleToCreateHtmlView() {
        onView(withId(R.id.defaultTextView)).check(matches(withText("Hello test")));
        onView(withId(R.id.textViewWithHtml)).check(matches(withText("Hello HTML TEXT")));

    }

    @Test
    public void shouldAbleToListenAnchorClick() {
        onView(withId(R.id.textViewWithHtml)).check(matches(is(not(withText("href")))));
        isClicked = false;
        dbsTextViewWithHtml.setOnAnchorClickListener(v -> isClicked = true);
        onView(withId(R.id.textViewWithHtml)).perform(clickAnchor("Hello HTML TEXT"));
        assertEquals(isClicked, true);
    }
    @Test
    public void shouldAbleToSetHtmlByCode() {
        isClicked = false;
        dbsTextViewWithHtml.setOnAnchorClickListener(v -> isClicked = true);
        dbsTextViewWithHtml.setTextViewHTML("<a href='google.com'>Hello Code</a>");
        onView(withId(R.id.textViewWithHtml)).perform(clickAnchor("Hello Code"));
        assertEquals(isClicked, true);
    }

}