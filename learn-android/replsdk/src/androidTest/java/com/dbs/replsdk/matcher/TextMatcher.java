package com.dbs.replsdk.matcher;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

class TextMatcher extends TypeSafeMatcher<View> {
    private final String expectedText;

    public TextMatcher(String expectedText) {
        this.expectedText = expectedText;
    }

    @Override
    public boolean matchesSafely(View view) {
        if (!(view instanceof TextView) && !(view instanceof EditText)) {
            return false;
        }
        if (view != null) {
            String text;
            if (view instanceof TextView) {
                text = ((TextView) view).getText().toString();
            } else {
                text = ((EditText) view).getText().toString();
            }

            return (text.equals(expectedText));
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Match Edit Text Value with View ID Value : :  " + expectedText);
    }
}
