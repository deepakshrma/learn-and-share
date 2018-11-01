package com.dbs.ui.components;

import android.support.test.runner.AndroidJUnit4;

import com.dbs.components.test.R;
import com.dbs.ui.components.testRule.ViewTestRule;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.dbs.ui.components.Matchers.CustomTestMatcher.withDrawable;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class DBSActionBarTest {
    @Rule
    public ViewTestRule<DBSActionBar> viewTestRule = new ViewTestRule<>(R.layout.dbs_actionbar_test);

    @Test
    public void shouldHaveActionbarWithDefaultTitle() {
        onView(withId(R.id.defaultActionbar)).check(matches(isDisplayed()));
        onView(withText("May i help you?")).check(matches(isDisplayed()));
    }

    @Test
    public void shouldHaveActionbarWithCustomTitle() {
        onView(withId(R.id.withSomeOtherTitle)).check(matches(isDisplayed()));
        onView(withText("SOME_OTHER_TITLE")).check(matches(isDisplayed()));
    }

    @Test
    @Ignore
    public void shouldHaveActionbarWithCustomIcon() {
        onView(allOf(withId(R.id.actionbarBackIcon), isDescendantOfA(withId(R.id.withCustomIcon)))).check(matches(withDrawable(R.drawable.happy_image)));
    }
}

