package com.dbs.ui.components.viewassertions;

import android.support.test.espresso.ViewAssertion;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;


import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;


import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class CustomViewAssertions {

    public static ViewAssertion hasNChildViewsOfType(int number, Class clazz) {
        return (view, noViewFoundException) -> {
            if (noViewFoundException != null)
                throw noViewFoundException;
            ViewGroup viewGroup = (ViewGroup) view;
            assertEquals(number, viewGroup.getChildCount());
            for (int i = 0, childCount = viewGroup.getChildCount(); i < childCount; i++) {
                View childView = viewGroup.getChildAt(i);
                assertTrue("view is not instance of class " + clazz, clazz.isInstance(childView));
                assertTrue(isDisplayed().matches(childView));
            }
        };
    }

    public static ViewAssertion allOf(Matcher<View>... viewMatchers) {
        return (view, noViewFoundException) -> {
            for (Matcher<View> matcher : viewMatchers) {
                StringDescription description = new StringDescription();
                description.appendText("'");
                matcher.describeTo(description);
                if (noViewFoundException != null) {
                    description.appendText(
                            String.format(
                                    "' check could not be performed because view '%s' was not found.\n",
                                    noViewFoundException.getViewMatcherDescription()));
                    Log.e("AllOfMatcher", description.toString());
                    throw noViewFoundException;
                } else {
                    description.appendText("' doesn't match the selected view.");
                    assertThat(description.toString(), view, matcher);
                }
            }
        };
    }
}
