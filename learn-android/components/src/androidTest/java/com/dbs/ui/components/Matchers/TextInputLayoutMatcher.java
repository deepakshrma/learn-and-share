package com.dbs.ui.components.Matchers;

import android.support.design.widget.TextInputLayout;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class TextInputLayoutMatcher {
    public static Matcher<View> hasErrortext(final String expectedErrorText) {
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof TextInputLayout)) {
                    return false;
                }
                CharSequence error = ((TextInputLayout) view).getError();
                return error != null && expectedErrorText.equals(error.toString());
            }
            @Override
            public void describeTo(Description description) {
            }
        };
    }
}
