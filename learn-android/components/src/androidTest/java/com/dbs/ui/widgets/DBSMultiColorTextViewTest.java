package com.dbs.ui.widgets;

import android.graphics.Color;
import android.support.test.runner.AndroidJUnit4;
import android.text.SpannedString;
import android.text.style.ForegroundColorSpan;
import android.widget.LinearLayout;

import com.dbs.ui.components.testRule.ViewTestRule;
import com.dbs.ui.error.TextNotProvidedException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DBSMultiColorTextViewTest {
    @Rule
    public ViewTestRule<LinearLayout> rule = new ViewTestRule<>(com.dbs.components.test.R.layout.multi_color_text_view_test);

    @Test(expected = TextNotProvidedException.class)
    public void shouldThrowExceptionIfTextNotProvided() {
        new DBSMultiColorTextView(rule.getActivity(), null, 0);
    }

    @Test
    public void shouldDisplayMultiColorTextView() {
        onView(withId(com.dbs.components.test.R.id.multi_color_view_with_all_attributes)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldDisplayTextInMultiColorTextView() {
        onView(withId(com.dbs.components.test.R.id.multi_color_view_with_all_attributes)).check(matches(withText("This is a multi color component")));
    }

    @Test
    public void shouldMatchTheColorThatIsConfiguredInMultiColorTextView() {
        DBSMultiColorTextView dbsMultiColorText = rule.getActivity().findViewById(com.dbs.components.test.R.id.multi_color_view_with_all_attributes);
        ForegroundColorSpan[] colorSpans = ((SpannedString) dbsMultiColorText.getText()).getSpans(0, dbsMultiColorText.getText().length(), ForegroundColorSpan.class);
        assertTrue(colorSpans[0].getForegroundColor() == Color.GREEN);
        assertTrue(colorSpans[1].getForegroundColor() == Color.RED);
    }

    @Test
    public void shouldNotHighlightTextIfAttributesNotProvided() {
        DBSMultiColorTextView dbsMultiColorText = rule.getActivity().findViewById(com.dbs.components.test.R.id.multi_color_view_without_highlight);
        ForegroundColorSpan[] colorSpans = ((SpannedString) dbsMultiColorText.getText()).getSpans(0, dbsMultiColorText.getText().length(), ForegroundColorSpan.class);
        assertTrue(colorSpans[0].getForegroundColor() == Color.GREEN);
        assertEquals(1, colorSpans.length);
    }

    @Test
    public void shouldNotHighlightTextIfRangeEndIndexNotValid() {
        DBSMultiColorTextView dbsMultiColorText = rule.getActivity().findViewById(com.dbs.components.test.R.id.multi_color_view_with_invalid_highlight_end_index);
        ForegroundColorSpan[] colorSpans = ((SpannedString) dbsMultiColorText.getText()).getSpans(0, dbsMultiColorText.getText().length(), ForegroundColorSpan.class);
        assertTrue(colorSpans[0].getForegroundColor() == Color.GREEN);
        assertEquals(1, colorSpans.length);
    }

    @Test
    public void shouldNotHighlightTextIfRangeStartIndexNotValid() {
        DBSMultiColorTextView dbsMultiColorText = rule.getActivity().findViewById(com.dbs.components.test.R.id.multi_color_view_with_invalid_highlight_start_index);
        ForegroundColorSpan[] colorSpans = ((SpannedString) dbsMultiColorText.getText()).getSpans(0, dbsMultiColorText.getText().length(), ForegroundColorSpan.class);
        assertTrue(colorSpans[0].getForegroundColor() == Color.GREEN);
        assertEquals(1, colorSpans.length);
    }
}