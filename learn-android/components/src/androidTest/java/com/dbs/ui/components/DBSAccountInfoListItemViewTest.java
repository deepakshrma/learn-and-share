package com.dbs.ui.components;

import android.graphics.drawable.Drawable;

import com.dbs.components.R;
import com.dbs.ui.components.testRule.ViewTestRule;
import com.dbs.ui.models.DBSAccountInfo;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.Visibility.GONE;
import static android.support.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.dbs.components.test.R.drawable.placeholder;
import static com.dbs.components.test.R.id.account_image;
import static com.dbs.components.test.R.id.right_nav_image;
import static com.dbs.components.test.R.layout.dbs_account_list;
import static com.dbs.ui.components.Matchers.CustomTestMatcher.withDrawable;

public class DBSAccountInfoListItemViewTest {
    @Rule
    public ViewTestRule<DBSAccountInfoListItemView> rule = new ViewTestRule<>(dbs_account_list);

    @Test
    public void shouldInitAccountInfoToViewGone() {
        onView(withId(R.id.account_number)).check(matches((withEffectiveVisibility(GONE))));
        onView(withId(R.id.account_balance)).check(matches((withEffectiveVisibility(GONE))));
        onView(withId(R.id.currency_label)).check(matches((withEffectiveVisibility(GONE))));
        onView(withId(R.id.right_nav_image)).check(matches((withEffectiveVisibility(GONE))));
    }

    @Test
    public void shouldDisplayAccountInfoWhenSet() {
        rule.runOnMainSynchronously(view -> {
            DBSAccountInfo sourceAccountInfo = new DBSAccountInfo("Account Name","Account Number", "Account Balance");

            rule.getView().setAccountDetails(sourceAccountInfo);
        });

        onView(withId(R.id.account_description_layout)).
                check(matches((withEffectiveVisibility(VISIBLE))));

        onView(withId(R.id.account_name))
                .check(matches((withEffectiveVisibility(VISIBLE))))
                .check(matches(withText("Account Name")));

        onView(withId(R.id.account_number))
                .check(matches((withEffectiveVisibility(VISIBLE))))
                .check(matches(withText("Account Number")));

        onView(withId(R.id.account_balance))
                .check(matches((withEffectiveVisibility(VISIBLE))))
                .check(matches(withText("Account Balance")));
    }

    @Test
    public void shouldSetGivenAccountImage() {
        Drawable drawable = rule.getView().getResources().getDrawable(placeholder);

        rule.runOnMainSynchronously(view -> {

            DBSAccountInfo sourceAccountInfo = new DBSAccountInfo("Account Name","Account Number", "Account Balance");
            sourceAccountInfo.setAccountImage(drawable);

            rule.getView().setAccountDetails(sourceAccountInfo);

        });

        onView(withId(R.id.account_name))
                .check(matches((withEffectiveVisibility(VISIBLE))))
                .check(matches(withText("Account Name")));

        onView(withId(R.id.account_number))
                .check(matches((withEffectiveVisibility(VISIBLE))))
                .check(matches(withText("Account Number")));

        onView(withId(R.id.account_balance))
                .check(matches((withEffectiveVisibility(VISIBLE))))
                .check(matches(withText("Account Balance")));

        onView(withId(account_image)).check(matches(withDrawable(placeholder)));
    }

    @Test
    public void shouldSetGivenRightNavImage() {
        Drawable drawable = rule.getView().getResources().getDrawable(placeholder);

        rule.runOnMainSynchronously(view -> {
            DBSAccountInfo sourceAccountInfo = new DBSAccountInfo("Account Name","Account Number", "Account Balance");
            sourceAccountInfo.setAccountImage(drawable);
            sourceAccountInfo.setRightNavImage(drawable);
            rule.getView().setAccountDetails(sourceAccountInfo);
        });

        onView(withId(R.id.account_name))
                .check(matches((withEffectiveVisibility(VISIBLE))))
                .check(matches(withText("Account Name")));

        onView(withId(R.id.account_number))
                .check(matches((withEffectiveVisibility(VISIBLE))))
                .check(matches(withText("Account Number")));

        onView(withId(R.id.account_balance))
                .check(matches((withEffectiveVisibility(VISIBLE))))
                .check(matches(withText("Account Balance")));

        onView(withId(right_nav_image))
                .check(matches(withDrawable(placeholder)));

        onView(withId(account_image))
                .check(matches(withDrawable(placeholder)));
    }

    @Test
    public void shouldSetGivenCurrency() {

        rule.runOnMainSynchronously(view -> {
            DBSAccountInfo sourceAccountInfo = new DBSAccountInfo("Account Name", "Account Number","Account Balance", "INR");
            rule.getView().setAccountDetails(sourceAccountInfo);
        });

        onView(withId(R.id.account_name))
                .check(matches((withEffectiveVisibility(VISIBLE))))
                .check(matches(withText("Account Name")));

        onView(withId(R.id.account_number))
                .check(matches((withEffectiveVisibility(VISIBLE))))
                .check(matches(withText("Account Number")));

        onView(withId(R.id.account_balance))
                .check(matches((withEffectiveVisibility(VISIBLE))))
                .check(matches(withText("Account Balance")));

        onView(withId(R.id.currency_label))
                .check(matches((withEffectiveVisibility(VISIBLE))))
                .check(matches(withText("INR")));
    }
}