package com.dbs.ui.components;

import com.dbs.components.R;
import com.dbs.ui.components.testRule.ViewTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.dbs.ui.components.Matchers.CustomTestMatcher.withDrawable;
import static org.junit.Assert.*;

public class DBSImageBadgeViewTest {

    @Rule
    public ViewTestRule<DBSImageBadgeView> rule = new ViewTestRule<>(com.dbs.components.test.R.layout.dbs_image_badge_view_test);

    @Test
    public void shouldShowImageView() {
        onView(withId(com.dbs.components.test.R.id.imageBadgeView)).check(matches(withDrawable(R.drawable.ic_rainy)));
    }
}