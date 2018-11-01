package com.dbs.replsdk.component;

import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dbs.replsdk.R;
import com.dbs.replsdk.ui.fragment.ChatWindowFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ChatbotScreenTest {

    @Rule
    public final ActivityTestRule<ChatActivity> activityTestRule =
            new ActivityTestRule<>(ChatActivity.class, true, false);

    private ChatWindowFragment chatScreen;

    @Before
    public void setup() {
        activityTestRule.launchActivity(new Intent());
        chatScreen = (ChatWindowFragment) this.activityTestRule.getActivity()
                .getSupportFragmentManager()
                .findFragmentById(R.id.chatWindow);
    }

    @Test
    public void onInit_testVisibility() {
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.chatRecyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.quickRepliesRecyclerView)).check(matches(not(isDisplayed())));
        onView(withId(R.id.chatToolbar)).check(matches(isDisplayed()));
    }
}