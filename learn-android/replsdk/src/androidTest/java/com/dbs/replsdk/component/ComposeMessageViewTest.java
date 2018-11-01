package com.dbs.replsdk.component;

import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dbs.replsdk.R;
import com.dbs.replsdk.test.ViewTestRule;
import com.dbs.replsdk.ui.components.ComposeMessageView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.dbs.replsdk.Await.waitFor;
import static org.hamcrest.core.AllOf.allOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class ComposeMessageViewTest {
    @Rule
    public ViewTestRule<LinearLayout> rule = new ViewTestRule<>(R.layout.compose_message_view_test_layout);
    EditText mEditText;
    ImageView mImageView;
    ComposeMessageView mComposeMessageView;

    @Before
    public void setUp() {
        mEditText = rule.getView().findViewById(R.id.compose_message_text);
        mImageView = rule.getView().findViewById(R.id.send_button);
        mComposeMessageView = rule.getView().findViewById(R.id.chatToolbar);

    }

    @Test
    public void shouldMatchTextAndShowBtn() {
        ComposeMessageView.ComposeMessageViewHost host = mock(ComposeMessageView.ComposeMessageViewHost.class);
        mComposeMessageView.bind(host);

        onView(allOf(withId(R.id.compose_message_text), withParent(withId(R.id.chatToolbar))))
                .check(matches((isDisplayed())));
        onView(allOf(withId(R.id.compose_message_text), withParent(withId(R.id.chatToolbar)))).
                check(matches((isDisplayed()))).perform(typeText("Hello")).check(matches(withText("Hello")));

        onView(isRoot()).perform(waitFor(1000));
        onView(allOf(withId(R.id.send_button), withParent(withId(R.id.chatToolbar))))
                .check(matches((isDisplayed())));

        ComposeMessageView.OnClickListener onClickListener = mock(ComposeMessageView.OnClickListener.class);
        onView(allOf(withId(R.id.send_button), withParent(withId(R.id.chatToolbar))))
                .perform(click()).check((v,g) -> verify(onClickListener, times(1)));
    }

}
