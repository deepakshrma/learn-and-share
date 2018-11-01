package com.dbs.ui.components;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.AppCompatButton;
import android.widget.Button;

import com.dbs.components.test.R;
import com.dbs.ui.components.testRule.EmptyActivity;
import com.dbs.ui.components.testRule.ViewTestRule;
import com.dbs.ui.utils.ThemeUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.dbs.ui.utils.ThemeUtils.getThemeColor;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

@RunWith(AndroidJUnit4.class)
public class DBSButtonTest {
    @Rule
    public ViewTestRule<DBSButton> viewTestRule = new ViewTestRule<>(R.layout.dbs_button_test);
    private int expectedTextColor;

    @Before
    public void setUp() {
        expectedTextColor = ThemeUtils.getThemeColor(viewTestRule.getActivity(), R.attr.dbs_themePrimaryTextColorInverse);
    }
    @SdkSuppress(minSdkVersion = Build.VERSION_CODES.M)
    @Test
    public void shouldBeAbleToApplyDefaultBackgroundColor() {
        AppCompatButton defaultButton = viewTestRule.getActivity().findViewById(R.id.defaultButton);
        StateListDrawable background = (StateListDrawable) defaultButton.getBackground();
        GradientDrawable gradientDrawable = (GradientDrawable) background.getCurrent();
        int actualBackgroundColor = gradientDrawable.getColor().getDefaultColor();
        int expectedBackgroundColor = getThemeColor(defaultButton.getContext(), R.attr.colorPrimary);

        assertEquals(expectedBackgroundColor, actualBackgroundColor);
    }

    @SdkSuppress(minSdkVersion = Build.VERSION_CODES.M)
    @Test
    public void whenButtonIsPressed_shouldBeAbleToApplyDefaultBackgroundAndTextColors() {
        EmptyActivity activity = viewTestRule.getActivity();
        activity.setTheme(R.style.DBSBase_NoActionBar_India);

        AppCompatButton defaultButton = activity.findViewById(R.id.defaultButton);

        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> defaultButton.setPressed(true));

        StateListDrawable background = (StateListDrawable) defaultButton.getBackground();
        int actualBackgroundColor = ((GradientDrawable) background.getCurrent()).getColor().getDefaultColor();
        int expectedBackgroundColor = defaultButton.getResources().getColor(R.color.india_color_red_primary);

        assertEquals(expectedBackgroundColor, actualBackgroundColor);
    }

    @SdkSuppress(minSdkVersion = Build.VERSION_CODES.M)
    @Test
    public void whenButtonIsDisabled_shouldBeAbleToApplyDefaultBackgroundAndTextColors() {
        EmptyActivity activity = viewTestRule.getActivity();
        activity.setTheme(R.style.DBSBase_NoActionBar_India);

        AppCompatButton defaultButton = activity.findViewById(R.id.defaultButtonDisabled);

        StateListDrawable background = (StateListDrawable) defaultButton.getBackground();
        int actualBackgroundColor = ((GradientDrawable) background.getCurrent()).getColor().getDefaultColor();
        int expectedBackgroundColor = defaultButton.getResources().getColor(R.color.india_color_red_primary);

        assertEquals(expectedBackgroundColor, actualBackgroundColor);
    }

    @Test
    public void shouldBeAbleToGiveDefaultTextStyle() {
        Button defaultButton = viewTestRule.getActivity().findViewById(R.id.defaultButton);
        Context context = defaultButton.getContext();

        assertEquals(expectedTextColor, defaultButton.getCurrentTextColor());
        assertEquals(context.getResources().getDimension(R.dimen.title_text_size), defaultButton.getTextSize(), 0);
    }

    @SdkSuppress(minSdkVersion = Build.VERSION_CODES.M)
    @Test
    public void withNoStyleAttr_shouldBeAbleToApplyDefaultBackgroundColor() {
        AppCompatButton defaultButton = new DBSButton(viewTestRule.getActivity());

        StateListDrawable background = (StateListDrawable) defaultButton.getBackground();
        GradientDrawable gradientDrawable = (GradientDrawable) background.getCurrent();
        int actualBackgroundColor = gradientDrawable.getColor().getDefaultColor();
        int expectedBackgroundColor = getThemeColor(defaultButton.getContext(), R.attr.colorPrimary);

        assertEquals(expectedBackgroundColor, actualBackgroundColor);
    }

    @Test
    public void withNoStyleAttr_shouldBeAbleToApplyDefaultTextStyles() {
        Button defaultButton = new DBSButton(viewTestRule.getActivity());
        Context context = defaultButton.getContext();

        assertEquals(expectedTextColor, defaultButton.getCurrentTextColor());
        assertEquals(context.getResources().getDimension(R.dimen.title_text_size), defaultButton.getTextSize(), 0);
    }

    @Test
    public void withStyleAttr_shouldNotBeAbleToApplyDefaultColors() {
        AppCompatButton button = new DBSButton(viewTestRule.getActivity(), null, 1);

        assertNull(button.getBackground());
        assertNotEquals(expectedTextColor, button.getCurrentTextColor());
    }
}