package com.dbs.replsdk.component;

import android.support.constraint.ConstraintLayout;
import android.support.test.runner.AndroidJUnit4;
import android.support.text.emoji.EmojiCompat;
import android.support.text.emoji.bundled.BundledEmojiCompatConfig;

import com.dbs.replsdk.R;
import com.dbs.replsdk.matcher.RecyclerViewMatcher;
import com.dbs.replsdk.model.QuickReply;
import com.dbs.replsdk.test.ViewTestRule;
import com.dbs.replsdk.ui.components.QuickReplyView;
import com.dbs.ui.components.DBSEmojiButton;
import com.dbs.ui.components.DBSTextView;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class QuickRepliesTest {
    @Rule
    public ViewTestRule<ConstraintLayout> rule = new ViewTestRule<>(R.layout.quick_replies_test_layout);
    List<QuickReply> quickReplyArrayList;
    QuickReplyView quickReplyView;

    @Before
    public void setUp() {
        quickReplyArrayList = new ArrayList<>();
        quickReplyView = rule.getView().findViewById(R.id.test_quick_reply_view);
        EmojiCompat.Config config = new BundledEmojiCompatConfig(rule.getActivity()).setEmojiSpanIndicatorEnabled(true);
        EmojiCompat.init(config);

    }


    @Test
    public void shouldDisplaySingleTextOnlyData() {
        String text = "Send SMS";
        String payload = "Send SMS Payload";
        quickReplyArrayList.add(new QuickReply(text, payload, "TEXT"));
        quickReplyView.updateReplies(quickReplyArrayList);
        onView(withText(text)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldDisplayMultipleTextOnlyData() {
        String text1 = "Send SMS 1";
        String payload1 = "Send SMS Payload 1";
        String text2 = "Send SMS 2";
        String payload2 = "Send SMS Payload 2";
        String text3 = "Send SMS 3";
        String payload3 = "Send SMS Payload 3";
        quickReplyArrayList.add(new QuickReply(text1, payload1, "TEXT"));
        quickReplyArrayList.add(new QuickReply(text2, payload2, "TEXT"));
        quickReplyArrayList.add(new QuickReply(text3, payload3, "TEXT"));
        quickReplyView.updateReplies(quickReplyArrayList);
        onView(withText(text1)).check(matches(isDisplayed()));
        onView(withText(text2)).check(matches(isDisplayed()));
        onView(withText(text3)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldDisplaySingleEmojiOnlyData() {
        String text = "\uD83E\uDD29 Send SMS";
        String payload = "Send SMS Payload";
        rule.runOnMainSynchronously((c) -> {
            quickReplyArrayList.add(new QuickReply(text, payload, "TEXT"));
            quickReplyView.updateReplies(quickReplyArrayList);
        });
        onView(withText(text)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldDisplayMultipleEmojiOnlyData() {
        String text1 = "\uD83E\uDD29 Send SMS 1";
        String payload1 = "Send SMS Payload 1";
        String text2 = "\uD83E\uDD29 Send SMS 2";
        String payload2 = "Send SMS Payload 2";
        String text3 = "\uD83E\uDD29 Send SMS 3";
        String payload3 = "Send SMS Payload 3";
        quickReplyArrayList.add(new QuickReply(text1, payload1, "TEXT"));
        quickReplyArrayList.add(new QuickReply(text2, payload2, "TEXT"));
        quickReplyArrayList.add(new QuickReply(text3, payload3, "TEXT"));
        quickReplyView.updateReplies(quickReplyArrayList);
        onView(withText(text1)).check(matches(isDisplayed()));
        onView(withText(text2)).check(matches(isDisplayed()));
        onView(withText(text3)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldDisplayMixedData() {
        String text1 = "\uD83E\uDD29 Send SMS 1";
        String payload1 = "Send SMS Payload 1";
        String text2 = "Send SMS 2";
        String payload2 = "Send SMS Payload 2";
        String text3 = "\uD83E\uDD29 Send SMS 3";
        String payload3 = "Send SMS Payload 3";
        quickReplyArrayList.add(new QuickReply(text1, payload1, "TEXT"));
        quickReplyArrayList.add(new QuickReply(text2, payload2, "TEXT"));
        quickReplyArrayList.add(new QuickReply(text3, payload3, "TEXT"));
        quickReplyView.updateReplies(quickReplyArrayList);
        onView(withText(text1)).check(matches(isDisplayed()));
        onView(withText(text2)).check(matches(isDisplayed()));
        onView(withText(text3)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldDisplayPayloadIfNullText() {
        String payload = "Send SMS Payload";
        quickReplyArrayList.add(new QuickReply(null, payload, "TEXT"));
        quickReplyView.updateReplies(quickReplyArrayList);
        onView(withText(payload)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldDisplayPayloadIfEmptyText() {
        String payload = "Send SMS Payload";
        quickReplyArrayList.add(new QuickReply("", payload, "TEXT"));
        quickReplyView.updateReplies(quickReplyArrayList);
        onView(withText(payload)).check(matches(isDisplayed()));
    }


    @Test
    public void shouldGiveCallbackWhenClicked() {
        quickReplyArrayList.clear();
        quickReplyArrayList.add(new QuickReply("TEXT", "PAYLOAD", "TEXT"));
        quickReplyView.updateReplies(quickReplyArrayList);
        QuickReplyView.OnQuickReplyListener onClickListener = mock(QuickReplyView.OnQuickReplyListener.class);
        rule.runOnMainSynchronously(view -> quickReplyView.setOnQuickReplyListener(onClickListener));

        //onView(withClassName(Matchers.equalTo(DBSEmojiButton.class.getName()))).perform(click());
        onView(withClassName(Matchers.equalTo(DBSTextView.class.getName()))).perform(click());

        verify(onClickListener, times(1)).onReply(any());
    }

    @Test
    public void shouldClickAllDataProperly() {
        String text1 = "\uD83E\uDD29 Send SMS 1";
        String payload1 = "Send SMS Payload 1";
        String text2 = "Send SMS 2";
        String payload2 = "Send SMS Payload 2";
        quickReplyArrayList.add(new QuickReply(text1, payload1, "TEXT"));
        quickReplyArrayList.add(new QuickReply(text2, payload2, "TEXT"));
        quickReplyView.updateReplies(quickReplyArrayList);

        QuickReplyView.OnQuickReplyListener onClickListener = mock(QuickReplyView.OnQuickReplyListener.class);
        rule.runOnMainSynchronously(view -> quickReplyView.setOnQuickReplyListener(onClickListener));
        onView((new RecyclerViewMatcher(R.id.test_quick_reply_view)).atPosition(0)).perform(click());
        onView((new RecyclerViewMatcher(R.id.test_quick_reply_view)).atPosition(1)).perform(click());

        verify(onClickListener, times(2)).onReply(any());

    }
}

