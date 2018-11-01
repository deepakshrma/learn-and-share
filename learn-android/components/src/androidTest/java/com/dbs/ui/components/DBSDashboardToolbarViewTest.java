package com.dbs.ui.components;

import android.support.annotation.DrawableRes;

import com.dbs.components.R;
import com.dbs.ui.components.testRule.ViewTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.dbs.ui.components.Matchers.CustomTestMatcher.withDrawable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DBSDashboardToolbarViewTest {
    @Rule
    public ViewTestRule<DBSDashboardToolbarView> rule = new ViewTestRule<>(com.dbs.components.test.R.layout.dbs_dashboard_toolbar_view_test);

    @Test
    public void shouldShowToolbar() {
        onView(withId(R.id.leftNavigationItem)).check(matches(withDrawable(R.drawable.ic_bell)));
        onView(withId(R.id.rightNavigationItem)).check(matches(withDrawable(R.drawable.ic_chatbot)));

        @DrawableRes int newLeftIconDrawable = R.drawable.ic_lock;
        rule.runOnMainSynchronously(view -> view.setLeftNavigationItemDrawable(newLeftIconDrawable));
        onView(withId(R.id.leftNavigationItem)).check(matches(withDrawable(newLeftIconDrawable)));

        @DrawableRes int newRightIconDrawable = R.drawable.ic_bell;
        rule.runOnMainSynchronously(view -> view.setRightNavigationItemDrawable(newRightIconDrawable));
        onView(withId(R.id.rightNavigationItem)).check(matches(withDrawable(newRightIconDrawable)));

        DBSDashboardToolbarView.ILeftItemClickListener mockLeftIconListener = mock(DBSDashboardToolbarView.ILeftItemClickListener.class);
        DBSDashboardToolbarView.IRightItemClickListener mockRightIconListener = mock(DBSDashboardToolbarView.IRightItemClickListener.class);

        rule.runOnMainSynchronously(view -> {
            view.setOnLeftItemClickListener(mockLeftIconListener);
            view.setOnRightItemClickListener(mockRightIconListener);
        });

        onView(withId(R.id.leftNavigationItem))
                .perform(click())
                .check((view, noViewFoundException) -> verify(mockLeftIconListener).onLeftItemClick());
        onView(withId(R.id.rightNavigationItem))
                .perform(click())
                .check((view1, noViewFoundException) -> verify(mockRightIconListener).onRightItemClick());
    }
}