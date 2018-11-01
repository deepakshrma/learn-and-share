package com.dbs.replsdk.component;

import android.support.test.runner.AndroidJUnit4;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dbs.replsdk.R;
import com.dbs.replsdk.test.ViewTestRule;
import com.dbs.replsdk.ui.components.DBSChatToolbarView;
import com.dbs.ui.components.DBSEditTextView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.dbs.replsdk.Await.waitFor;
import static com.dbs.replsdk.matcher.CustomTestMatcher.withDrawable;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class DBSChatToolbarTest {
    @Rule
    public ViewTestRule<LinearLayout> rule = new ViewTestRule<>(R.layout.chat_toolbar_test_layout);
    ImageView mLeftImage;
    ImageView mRightImage;
    DBSEditTextView mDBSEditTextView;
    private DBSChatToolbarView mDBSChatToolbarView;
    private DBSChatToolbarView mDBSChatToolbarViewWithIcon;

    @Before
    public void setUp() {
        mDBSChatToolbarView = rule.getView().findViewById(com.dbs.replsdk.test.R.id.test_toolbar_view);
        mDBSChatToolbarViewWithIcon = rule.getView().findViewById(com.dbs.replsdk.test.R.id.test_toolbar_view);
        mLeftImage = rule.getView().findViewById(R.id.leftNavigationItem);
        mRightImage = rule.getView().findViewById(R.id.rightNavigationItem);
        mDBSEditTextView = rule.getView().findViewById(R.id.messageEditText);
    }

    @Test
    public void shouldMatchText() {
        //onView(withId(R.id.messageEditText)).perform(typeText("Hello"),closeSoftKeyboard());
        onView(withId(R.id.messageEditText)).perform(typeText("Hello World"));
        onView(withId(R.id.messageEditText)).check(matches(withText("Hello World")));
    }

    @Test
    public void shouldMatchIcons() throws Exception {
        //onView(withId(R.id.messageEditText)).perform(clearText());
        onView(withId(R.id.leftNavigationItem)).check(matches(withDrawable(R.drawable.ic_menu_black_24dp)));
        onView(withId(R.id.messageEditText)).perform(typeText("Hello World"), closeSoftKeyboard());
        onView(isRoot()).perform(waitFor(600));
        assertEquals(mRightImage.getTag(), DBSChatToolbarView.ChatToolbarAction.MESSAGE);

        onView(withId(R.id.messageEditText)).perform(clearText());
        onView(isRoot()).perform(waitFor(600));
        assertEquals(mRightImage.getTag(), DBSChatToolbarView.ChatToolbarAction.MIC);
    }

    @Test
    public void shouldClick() {
        DBSChatToolbarView.OnActionClickListener onClickListener = mock(DBSChatToolbarView.OnActionClickListener.class);
        rule.runOnMainSynchronously(view -> mDBSChatToolbarView.setOnActionClickListener(onClickListener));

        onView(withId(R.id.messageEditText)).perform(typeText("Hello World - The quick brown fox jumps over the lazy dog"), closeSoftKeyboard());
        onView(isRoot()).perform(waitFor(1000));
        onView(withId(R.id.rightNavigationItem)).perform(click()).check((v, g) -> {
            verify(onClickListener, times(1)).onClick(DBSChatToolbarView.ChatToolbarAction.MESSAGE, "Hello World - The quick brown fox jumps over the lazy dog");
        });
    }

    @Test
    public void shouldCheckGetIDOfIcons() {
        assertEquals(DBSChatToolbarView.ChatToolbarAction.MIC.getId(), (int) mRightImage.getTag());
    }

}
