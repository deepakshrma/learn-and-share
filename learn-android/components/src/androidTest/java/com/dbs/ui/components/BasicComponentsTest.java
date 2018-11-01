package com.dbs.ui.components;

import android.support.test.runner.AndroidJUnit4;
import android.widget.LinearLayout;

import com.dbs.components.test.R;
import com.dbs.ui.components.testRule.ViewTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by deepak on 26/6/18.
 */

@RunWith(AndroidJUnit4.class)
public class BasicComponentsTest {
    @Rule
    public ViewTestRule<LinearLayout> rule = new ViewTestRule<>(com.dbs.components.test.R.layout.basic_components_test_view);

    @Test
    public void actionBarAndStackCardShouldNotBeNull() {
        onView(withId(R.id.basicActionBar)).check(matches(isDisplayed()));
        onView(withText("custom title")).check(matches(isDisplayed()));
    }
}
