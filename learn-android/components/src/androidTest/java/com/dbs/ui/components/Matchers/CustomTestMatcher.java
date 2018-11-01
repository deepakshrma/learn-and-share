package com.dbs.ui.components.Matchers;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.internal.util.Checks;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.dbs.ui.components.dbsradiobutton.DBSRadioButton;
import com.dbs.ui.utils.LayoutUtils;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.Arrays;
import java.util.Calendar;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.CoreMatchers.allOf;

public class CustomTestMatcher {


    public static Matcher<View> hasSampleGradientDrawable(GradientDrawable drawable) {
        return new GradientDrawableMatcher(drawable);
    }


    public static Matcher<View> isEditTextValueEqualTo(final String text) {
        return new TextMatcher(text);
    }

    public static Matcher<View> withDrawable(int resourceId) {
        return new DrawableMatcher(resourceId);
    }

    public static Matcher<View> withDrawable(int resourceId, int drawablePosition) {
        return new DrawableMatcher(resourceId, drawablePosition);
    }

    // Custom Matcher to check buttons' tint color
    public static Matcher<View> withColorStateList() {
        return new BoundedMatcher<View, DBSRadioButton>(DBSRadioButton.class) {

            @Override
            public boolean matchesSafely(DBSRadioButton radioButton) {
                ColorStateList colorStateList = radioButton.getButtonTintList();
                return (colorStateList != null && colorStateList.getColorForState(new int[]{-android.R.attr.state_checked}, -1) == Color.parseColor("#FDB037") &&
                        colorStateList.getColorForState(new int[]{android.R.attr.state_checked}, -1) == Color.parseColor("#FF4081"));
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Button ColorStateList did not set: ");
            }
        };
    }

    public static Matcher<View> matchesDate(final int year, final int month, final int day) {
        return new BoundedMatcher<View, DatePicker>(DatePicker.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("Matches date: ");
            }

            @Override
            protected boolean matchesSafely(DatePicker item) {
                return (year == item.getYear() && month == item.getMonth() && day == item.getDayOfMonth());
            }
        };
    }

    public static Matcher<View> matchesCalendar(final Calendar calendar) {
        return new BoundedMatcher<View, DatePicker>(DatePicker.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("Matches date: ");
            }

            @Override
            protected boolean matchesSafely(DatePicker item) {
                return (calendar.get(Calendar.YEAR) == item.getYear() && calendar.get(Calendar.MONTH) == item.getMonth() && calendar.get(Calendar.DAY_OF_MONTH) == item.getDayOfMonth());
            }
        };
    }

    public static Matcher<View> withTextViewColorHex(final String color) {
        Checks.checkNotNull(color);
        return new BoundedMatcher<View, TextView>(TextView.class) {
            @Override
            public boolean matchesSafely(TextView warning) {
                int colorCode = getColorCode(warning);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorCode));
                return !color.isEmpty() && color.equalsIgnoreCase(hexColor);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with text color: ");
            }
        };
    }

    public static Matcher<View> withColor(@ColorInt final int color) {
        Checks.checkNotNull(color);
        return new BoundedMatcher<View, TextView>(TextView.class) {
            @Override
            public boolean matchesSafely(TextView warning) {
                int colorCode = getColorCode(warning);
                return color == colorCode;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with text color: ");
            }
        };
    }

    private static int getColorCode(TextView textView) {
        int colorCode = Color.TRANSPARENT;
        Drawable backGroundDrawable = textView.getBackground();
        if (backGroundDrawable instanceof ColorDrawable) {
            ColorDrawable cd = (ColorDrawable) backGroundDrawable;
            colorCode = cd.getColor();
        }
        if (backGroundDrawable instanceof LayoutUtils.ColorGradientDrawable) {
            LayoutUtils.ColorGradientDrawable cd = (LayoutUtils.ColorGradientDrawable) backGroundDrawable;
            int[] colors = cd.getColors();
            if (colors != null && colors.length > 0) {
                colorCode = colors[0];// Take default first color
            }
        }
        return colorCode;
    }

    @NonNull
    public static Matcher<View> getViewMather(int viewId, int... ancestors) {
        if (ancestors.length == 1) {
            return allOf(
                    withId(viewId),
                    withParent(withId(ancestors[0])));
        }
        return allOf(
                withId(viewId),
                withParent(getViewMather(ancestors[0], Arrays.copyOfRange(ancestors, 1, ancestors.length))));
    }
}