package com.dbs.ui.components;

import android.graphics.Color;
import android.support.test.runner.AndroidJUnit4;
import android.text.Spannable;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.LinearLayout;

import com.dbs.components.test.R;
import com.dbs.ui.components.testRule.ViewTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class DBSMultiSpanTextViewTest {

    @Rule
    public ViewTestRule<LinearLayout> viewTestRule = new ViewTestRule<>(R.layout.multi_spannable_text_view);
    DBSMultiSpanTextView dbsMultiSpanTextView;
    @Before
    public void before(){
        dbsMultiSpanTextView = viewTestRule.getView().findViewById(R.id.multi_span);
    }

    @Test
    public void shouldBeAbleToSetTextWithDefaultTextAttribute(){//android:text
        assertEquals("this is a very big test string. continued bigger text", dbsMultiSpanTextView.getText().toString());
    }

    @Test
    public void shouldBeAbleToGetTextAfterApplyingASpan(){
        viewTestRule.getActivity().runOnUiThread(() -> {
            dbsMultiSpanTextView.setClickSpannable(0, 5, Mockito.mock(View.OnClickListener.class));
            assertEquals("this is a very big test string. continued bigger text", dbsMultiSpanTextView.getText().toString());
        });
    }

    @Test
    public void addingAClickableSpan_shouldSetHighlightColorAsTransparent(){
        View.OnClickListener mockedClickListener = Mockito.mock(View.OnClickListener.class);
        viewTestRule.getActivity().runOnUiThread(() -> {
            dbsMultiSpanTextView.setClickSpannable(0, 3, mockedClickListener);
            assertEquals(Color.TRANSPARENT, dbsMultiSpanTextView.getHighlightColor());
        });
    }

    @Test
    public void shouldBeAbleToSetClickableSpanUsingStartEndIndex(){
        View.OnClickListener mockedClickListener = Mockito.mock(View.OnClickListener.class);
        viewTestRule.getActivity().runOnUiThread(() -> {
            dbsMultiSpanTextView.setClickSpannable(0, 3, mockedClickListener);
            Spannable spannable = (Spannable) dbsMultiSpanTextView.getText();
            int length = dbsMultiSpanTextView.getText().length();
            ClickableSpan[] spans = spannable.getSpans(0, length, ClickableSpan.class);

            spans[0].onClick(null);

            assertEquals(1, spans.length);
            assertEquals(0, spannable.getSpanStart(spans[0]));
            assertEquals(3, spannable.getSpanEnd(spans[0]));
            verify(mockedClickListener, times(1)).onClick(null);
        });
    }

    @Test
    public void shouldBeAbleToSetClickSpannableUsingText(){
        View.OnClickListener mockedClickListener = Mockito.mock(View.OnClickListener.class);
        viewTestRule.getActivity().runOnUiThread(() -> {
            dbsMultiSpanTextView.setClickSpannable("very big", mockedClickListener);
            Spannable spannable = (Spannable) dbsMultiSpanTextView.getText();
            int length = dbsMultiSpanTextView.getText().length();
            ClickableSpan[] spans = spannable.getSpans(0, length, ClickableSpan.class);

            spans[0].onClick(null);

            assertEquals(1, spans.length);
            assertEquals(10, spannable.getSpanStart(spans[0]));
            assertEquals(18, spannable.getSpanEnd(spans[0]));
            verify(mockedClickListener, times(1)).onClick(null);
        });
    }

    @Test
    public void shouldBeAbleToSetMultipleClickableSpans(){
        View.OnClickListener mockedClickListener1 = Mockito.mock(View.OnClickListener.class);
        View.OnClickListener mockedClickListener2 = Mockito.mock(View.OnClickListener.class);
        viewTestRule.getActivity().runOnUiThread(() -> {
            dbsMultiSpanTextView.setClickSpannable(0, 3, mockedClickListener1);
            dbsMultiSpanTextView.setClickSpannable(5, 7, mockedClickListener2);
            Spannable spannable = (Spannable) dbsMultiSpanTextView.getText();
            int length = dbsMultiSpanTextView.getText().length();
            ClickableSpan[] spans = spannable.getSpans(0, length, ClickableSpan.class);

            spans[0].onClick(null);
            spans[1].onClick(null);

            assertEquals(2, spans.length);
            assertEquals(0, spannable.getSpanStart(spans[0]));
            assertEquals(3, spannable.getSpanEnd(spans[0]));

            assertEquals(5, spannable.getSpanStart(spans[1]));
            assertEquals(7, spannable.getSpanEnd(spans[1]));
            verify(mockedClickListener1, times(1)).onClick(null);
            verify(mockedClickListener2, times(1)).onClick(null);
        });
    }

    @Test
    public void shouldBeAbleToCreateADynamicMultiSpanTextView(){
        addDynamicMultiSpanTextView();

        onView(withText("This is a dynamic span text")).check(matches(isDisplayed()));
    }

    @Test
    public void shouldBeAbleToSetClickableSpanUsingStartEndIndexOnDynamicMultiSpanTextView(){
        addDynamicMultiSpanTextView();
        View.OnClickListener mockedClickListener = Mockito.mock(View.OnClickListener.class);

        viewTestRule.getActivity().runOnUiThread(() -> {
            dbsMultiSpanTextView.setClickSpannable(0, 3, mockedClickListener);
            Spannable spannable = (Spannable) dbsMultiSpanTextView.getText();
            int length = dbsMultiSpanTextView.getText().length();
            ClickableSpan[] spans = spannable.getSpans(0, length, ClickableSpan.class);

            spans[0].onClick(null);

            assertEquals(1, spans.length);
            assertEquals(0, spannable.getSpanStart(spans[0]));
            assertEquals(3, spannable.getSpanEnd(spans[0]));
            verify(mockedClickListener, times(1)).onClick(null);
        });
    }

    @Test
    public void shouldBeAbleToSetClickableSpanUsingTextOnDynamicMultiSpanTextView(){
        addDynamicMultiSpanTextView();
        View.OnClickListener mockedClickListener = Mockito.mock(View.OnClickListener.class);

        viewTestRule.getActivity().runOnUiThread(() -> {
            dbsMultiSpanTextView.setClickSpannable("dynamic", mockedClickListener);
            Spannable spannable = (Spannable) dbsMultiSpanTextView.getText();
            int length = dbsMultiSpanTextView.getText().length();
            ClickableSpan[] spans = spannable.getSpans(0, length, ClickableSpan.class);

            spans[0].onClick(null);

            assertEquals(1, spans.length);
            assertEquals(10, spannable.getSpanStart(spans[0]));
            assertEquals(17, spannable.getSpanEnd(spans[0]));
            verify(mockedClickListener, times(1)).onClick(null);
        });
    }

    private void addDynamicMultiSpanTextView() {
        dbsMultiSpanTextView = new DBSMultiSpanTextView(viewTestRule.getActivity());
        viewTestRule.addView(dbsMultiSpanTextView);
        dbsMultiSpanTextView.setText("This is a dynamic span text");
    }


}