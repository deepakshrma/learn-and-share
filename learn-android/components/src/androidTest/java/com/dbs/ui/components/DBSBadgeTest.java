package com.dbs.ui.components;

import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;

import com.dbs.components.test.R;
import com.dbs.ui.components.testRule.ViewTestRule;
import com.dbs.ui.utils.ThemeUtils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.dbs.ui.components.Matchers.CustomTestMatcher.withColor;
import static com.dbs.ui.components.Matchers.CustomTestMatcher.withTextViewColorHex;
import static org.hamcrest.CoreMatchers.containsString;

@RunWith(AndroidJUnit4.class)
public class DBSBadgeTest {
    @Rule
    public ViewTestRule<DBSBadge> viewTestRule = new ViewTestRule<>(R.layout.dbsbadge_view_test);

    @Test
    public void shouldBadgeWithPrimaryColorText() {
        onView(withId(R.id.primaryBadge)).check((matches(withText(containsString("Primary")))));
        int themePrimeColor = ContextCompat.getColor(viewTestRule.getActivity(), com.dbs.components.R.color.dbs_badge_prime);
        onView(withId(R.id.primaryBadge)).check((matches(withColor(themePrimeColor))));
    }

    @Test
    public void shouldBadgeWithSecondaryText() {
        int themePrimeColor = ThemeUtils.getThemeColor(viewTestRule.getActivity(), com.dbs.components.R.attr.colorPrimaryDark);
        onView(withId(R.id.secondaryBadge)).check(matches(withColor(themePrimeColor)));
    }

    @Test
    public void shouldBadgeWithoutCustomAttrText() {
        onView(withId(R.id.withoutCustomAttr)).check(matches(withTextViewColorHex("#000000")));
    }

    @Test
    public void shouldBadgeApplyStyle() {
        DBSBadge dbsBadge = viewTestRule.getActivity().findViewById(R.id.primaryBadge);
        dbsBadge.applyStyle(R.style.DBSBadge_Primary);
        int themePrimeColor = ContextCompat.getColor(viewTestRule.getActivity(), com.dbs.components.R.color.dbs_badge_prime);
        onView(withId(R.id.primaryBadge)).check((matches(withColor(themePrimeColor))));
    }

    @Test
    public void shouldBadgeWithoutStyleText() {
        onView(withId(R.id.withoutStyle)).check(matches(withTextViewColorHex("#000000")));
    }
}

