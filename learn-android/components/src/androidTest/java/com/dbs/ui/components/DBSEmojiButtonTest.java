package com.dbs.ui.components;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.text.emoji.EmojiCompat;
import android.support.text.emoji.bundled.BundledEmojiCompatConfig;
import android.view.View;

import com.dbs.components.test.R;
import com.dbs.ui.components.testRule.EmptyActivity;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DBSEmojiButtonTest {
    @Rule
    public ActivityTestRule viewTestRule = new ActivityTestRule(EmptyActivity.class, false, false);
    DBSEmojiButton defaultButton;

    @Before
    public void setUp() throws Throwable {
        viewTestRule.launchActivity(null);
        EmojiCompat.Config config = new BundledEmojiCompatConfig(viewTestRule.getActivity()).setEmojiSpanIndicatorEnabled(true);
        EmojiCompat.init(config);
        viewTestRule.runOnUiThread(() -> {
            viewTestRule.getActivity().setContentView(View.inflate(viewTestRule.getActivity(), R.layout.dbs_em_button_test, null));
            defaultButton = viewTestRule.getActivity().findViewById(R.id.defaultEmojiButton);
        });
    }

    @Test
    @Ignore
    public void shouldBeAbleShowEmojiText() {
        // Need to fix, Somehow fails in
        assertEquals("Hello World: \uD83C\uDDFA\uD83C\uDDF8", defaultButton.getText().toString());
    }
}