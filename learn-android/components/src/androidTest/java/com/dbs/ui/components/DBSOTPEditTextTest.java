package com.dbs.ui.components;

import android.app.Instrumentation;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.AppCompatTextView;
import android.view.KeyEvent;
import android.widget.LinearLayout;


import com.dbs.components.test.R;
import com.dbs.ui.components.testRule.ViewTestRule;
import com.dbs.ui.error.PinLengthException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.dbs.ui.utils.ThemeUtils.getThemeColor;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.allOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class DBSOTPEditTextTest {
    @Rule
    public ViewTestRule<LinearLayout> viewTestRule = new ViewTestRule<>(R.layout.otp_edit_text_view_test);
    private DBSOTPEditText otpPinsView, customizedPinsView;
    private Context context;
    private int defaultEmptyPinColor, defaultFilledPinColor, themeFailColor, themeSecondaryTextColor, themeDividerColor;
    private LinearLayout pins, customizedPins;
    private Instrumentation instrumentation;

    @Before
    public void setUp(){
        otpPinsView = viewTestRule.getView().findViewById(R.id.default_pins);
        customizedPinsView = viewTestRule.getView().findViewById(R.id.customized_pins);
        context = viewTestRule.getActivity();
        defaultEmptyPinColor = getThemeColor(context, com.dbs.components.R.attr.dbs_themeDividerColor);
        defaultFilledPinColor = getThemeColor(context, com.dbs.components.R.attr.dbs_themePrimaryTextColor);
        themeFailColor = getThemeColor(context, com.dbs.components.R.attr.dbs_themeFailColor);
        themeSecondaryTextColor = getThemeColor(context, com.dbs.components.R.attr.dbs_themeSecondaryTextColor);
        themeDividerColor = getThemeColor(context, com.dbs.components.R.attr.dbs_themeDividerColor);
        pins = otpPinsView.findViewById(R.id.pins);
        customizedPins = customizedPinsView.findViewById(R.id.pins);
        instrumentation = viewTestRule.getInstrumentation();
    }

    @Test
    public void validateDefaults() {
        float pinFontSize = context.getResources().getDimension(com.dbs.components.R.dimen.default_otp_pin_size);
        int margin = context.getResources().getDimensionPixelSize(com.dbs.components.R.dimen.x_small_margin);

        assertEquals(6, otpPinsView.getNumberOfPins());
        for(int i = 0; i < otpPinsView.getNumberOfPins(); i++){
            AppCompatTextView pin = (AppCompatTextView) pins.getChildAt(i);
            LinearLayout.LayoutParams pinLayoutParams = (LinearLayout.LayoutParams) pin.getLayoutParams();

            assertEquals(pinFontSize, pin.getTextSize());
            assertEquals("•", pin.getText());

            assertEquals(margin, pinLayoutParams.bottomMargin);
            assertEquals(margin, pinLayoutParams.leftMargin);
            assertEquals(margin, pinLayoutParams.topMargin);
            assertEquals(margin, pinLayoutParams.rightMargin);
            assertEquals(margin, pinLayoutParams.getMarginStart());
            assertEquals(defaultEmptyPinColor, pin.getCurrentTextColor());
        }

        clickOn(R.id.default_pins);
        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_0);

        assertEquals(defaultFilledPinColor, ((AppCompatTextView) pins.getChildAt(0)).getCurrentTextColor());
    }

    @Test
    public void validateCustomizations() {
        float pinFontSize = context.getResources().getDimension(com.dbs.components.R.dimen.medium_margin);
        int margin = context.getResources().getDimensionPixelSize(com.dbs.components.R.dimen.x_small_margin);
        int emptyPinColor = context.getResources().getColor(android.R.color.holo_green_dark);
        int filledPinColor = context.getResources().getColor(android.R.color.holo_orange_dark);

        assertEquals(5, customizedPinsView.getNumberOfPins());
        for(int i = 0; i < customizedPinsView.getNumberOfPins(); i++){
            AppCompatTextView pin = (AppCompatTextView) customizedPins.getChildAt(i);
            LinearLayout.LayoutParams pinLayoutParams = (LinearLayout.LayoutParams) pin.getLayoutParams();

            assertEquals(pinFontSize, pin.getTextSize());
            assertEquals("•", pin.getText());

            assertEquals(margin, pinLayoutParams.bottomMargin);
            assertEquals(margin, pinLayoutParams.leftMargin);
            assertEquals(margin, pinLayoutParams.topMargin);
            assertEquals(margin, pinLayoutParams.rightMargin);
            assertEquals(margin, pinLayoutParams.getMarginStart());
            assertEquals(emptyPinColor, pin.getCurrentTextColor());
        }

        clickOn(R.id.customized_pins);
        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_0);

        assertEquals(filledPinColor, ((AppCompatTextView)customizedPins.getChildAt(0)).getCurrentTextColor());
    }

    @Test
    public void shouldBeAbleToGetPinLength() {
        assertEquals(6, otpPinsView.getNumberOfPins());
        assertEquals(5, customizedPinsView.getNumberOfPins());
    }

    @Test
    public void shouldBeAbleToAddMorePins() throws Throwable {
        viewTestRule.runOnUiThread(() -> otpPinsView.setNumberOfPins(10));

        assertEquals(10, otpPinsView.getNumberOfPins());
    }

    @Test
    public void shouldBeAbleToRemoveSomePins() throws Throwable {
        viewTestRule.runOnUiThread(() -> otpPinsView.setNumberOfPins(3));

        validatePinColors(0, pins);
    }

    @Test
    public void addingMorePins_shouldCreateNewPinsWithEmptyPinBackground() throws Throwable {
        clickOn(R.id.default_pins);
        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_0);
        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_0);
        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_0);
        viewTestRule.runOnUiThread(() -> {
            otpPinsView.setNumberOfPins(10);
        });

        validatePinColors(3, pins);
    }

    @Test
    public void removingPins_shouldNotChangeBackgroundOfRemainingPins() throws Throwable {
        clickOn(R.id.default_pins);
        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_0);
        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_0);
        viewTestRule.runOnUiThread(() -> {
            otpPinsView.setNumberOfPins(4);
        });

        validatePinColors(2, pins);
    }

    @Test
    public void removingPinsIncludingFilledOnes_shouldNotChangeBackgroundOfRemainingPins() throws Throwable {
        viewTestRule.runOnUiThread(() -> {
            otpPinsView.setOtp("123456");
            otpPinsView.setNumberOfPins(4);
        });

        validatePinColors(4, pins);
    }

    @Test(expected = PinLengthException.class)
    public void shouldNotBeAbleToSetPinWithDigitsMoreThanNumberOfPins() {
        otpPinsView.setOtp("12345567");
    }

    @Test(expected = PinLengthException.class)
    public void shouldNotBeAbleToSetPinWithDigitsLessThanNumberOfPins() {
        otpPinsView.setOtp("1234");
    }

    @Test
    public void shouldBeAbleToSetPinWithDigitsSameAsNumberOfPins()  {
        otpPinsView.setOtp("123457");
        assertEquals("123457", otpPinsView.getOtp());

        validatePinColors(6, pins);
    }

    @Test
    public void afterSettingPin_shouldBeAbleToEditIt()  {
        otpPinsView.setOtp("123457");
        clickOn(R.id.default_pins);
        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_DEL);

        assertEquals("12345", otpPinsView.getOtp());
        validatePinColors(5, pins);
    }

    @Test
    public void shouldChangePinColorsAsUserTypes() {
        clickOn(R.id.default_pins);

        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_0);

        validatePinColors(1, pins);

        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_1);

        validatePinColors(2, pins);

        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_3);

        validatePinColors(3, pins);
    }

    @Test
    public void AfterUserTypesInOtp_shouldBeAbleToGetTypedOtp() {
        clickOn(R.id.default_pins);

        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_0);
        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_1);
        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_5);

        assertEquals("015", otpPinsView.getOtp());
    }

    @Test
    public void shouldNotBeAbleTypeMoreThanPinLengthInOTPEditText() {
        clickOn(R.id.default_pins);

        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_0);
        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_1);
        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_5);
        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_1);
        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_2);
        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_3);
        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_5);
        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_6);
        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_9);

        assertEquals("015123", otpPinsView.getOtp());
    }

    @Test
    public void shouldBeAbleToCorrectOtpUsingBackSpace() {
        clickOn(R.id.default_pins);

        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_0);
        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_1);
        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_5);
        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_1);
        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_2);
        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_3);

        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_DEL);
        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_DEL);
        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_DEL);
        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_5);
        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_6);
        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_9);

        assertEquals("015569", otpPinsView.getOtp());
    }

    @Test
    public void whenBackSpaceIsPressed_shouldChangeColorOfCurrentPinBackToUnfilled() {
        clickOn(R.id.default_pins);

        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_0);
        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_1);
        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_5);

        validatePinColors(3, pins);

        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_DEL);

        validatePinColors(2, pins);
    }

    @Test
    public void WhenOtpIsTypedCompletely_shouldCallOTPListener() {
        DBSOTPEditText.OtpEnteredHandler mockOtpEnteredHandler = Mockito.mock(DBSOTPEditText.OtpEnteredHandler.class);
        otpPinsView.setOtpEnteredCallback(mockOtpEnteredHandler);
        clickOn(R.id.default_pins);

        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_0);
        verify(mockOtpEnteredHandler, times(0)).otpEntered(anyString());

        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_1);
        verify(mockOtpEnteredHandler, times(0)).otpEntered(anyString());

        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_5);
        verify(mockOtpEnteredHandler, times(0)).otpEntered(anyString());

        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_1);
        verify(mockOtpEnteredHandler, times(0)).otpEntered(anyString());

        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_2);
        verify(mockOtpEnteredHandler, times(0)).otpEntered(anyString());

        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_3);

        verify(mockOtpEnteredHandler, times(1)).otpEntered("015123");
    }

    @Test
    public void onSettingError_borderBottomShouldBeInThemeFailColor(){
        otpPinsView.showErrorMessage("test error message");

        ColorDrawable background = (ColorDrawable) otpPinsView.findViewById(com.dbs.components.R.id.status_line).getBackground();
        assertEquals(themeFailColor, background.getColor());
    }

    @Test
    public void onSettingError_errorTextViewShouldHaveThemeFailColorAndCorrespondingErrorMessage(){
        otpPinsView.showErrorMessage("test error message");

        onView(allOf(isDescendantOfA(withId(R.id.default_pins)), withId(com.dbs.components.R.id.status_text_view))).check(matches(allOf(isDisplayed(), withText("test error message"))));
        AppCompatTextView errorOrInfoTextView = otpPinsView.findViewById(com.dbs.components.R.id.status_text_view);
        assertEquals(themeFailColor, errorOrInfoTextView.getCurrentTextColor());
    }

    @Test
    public void shouldBeAbleToRemoveError(){
        otpPinsView.showErrorMessage("test error message");
        otpPinsView.resetStatus();

        AppCompatTextView statusTextView = otpPinsView.findViewById(com.dbs.components.R.id.status_text_view);
        ColorDrawable background = (ColorDrawable) otpPinsView.findViewById(com.dbs.components.R.id.status_line).getBackground();

        assertEquals("", statusTextView.getText());
        assertEquals(Color.TRANSPARENT, background.getColor());
    }

    @Test
    public void shouldBeAbleToRemoveHint(){
        otpPinsView.showHint("test hint message");
        otpPinsView.resetStatus();

        AppCompatTextView statusTextView = otpPinsView.findViewById(com.dbs.components.R.id.status_text_view);
        ColorDrawable background = (ColorDrawable) otpPinsView.findViewById(com.dbs.components.R.id.status_line).getBackground();

        assertEquals("", statusTextView.getText());
        assertEquals(Color.TRANSPARENT, background.getColor());
    }

    @Test
    public void shouldBeAbleToResetPin(){
        otpPinsView.setOtp("123456");
        otpPinsView.resetOtp();

        assertEquals("", otpPinsView.getOtp());
        validatePinColors(0, pins);
    }

    @Test
    public void onSettingHint_borderBottomShouldBeInTransparentColor(){
        otpPinsView.showHint("test hint message");

        ColorDrawable background = (ColorDrawable) otpPinsView.findViewById(com.dbs.components.R.id.status_line).getBackground();
        assertEquals(themeDividerColor, background.getColor());
    }

    @Test
    public void onSettingHint_hintTextViewShouldHaveThemeSecondaryColorAndCorrespondingHintMessage(){
        otpPinsView.showHint("test hint message");

        onView(allOf(isDescendantOfA(withId(R.id.default_pins)), withId(com.dbs.components.R.id.status_text_view))).check(matches(allOf(isDisplayed(), withText("test hint message"))));
        AppCompatTextView errorOrInfoTextView = otpPinsView.findViewById(com.dbs.components.R.id.status_text_view);
        assertEquals(themeSecondaryTextColor, errorOrInfoTextView.getCurrentTextColor());
    }

    @Test
    public void shouldBeAbleToSetStyleProgramatically() throws Throwable {
        viewTestRule.runOnUiThread(() -> otpPinsView.applyStyle(R.style.DBSOtpCustom));

        float pinFontSize = (20 * context.getResources().getDisplayMetrics().scaledDensity);
        int margin = context.getResources().getDimensionPixelSize(com.dbs.components.R.dimen.x_small_margin);
        int emptyPinColor = context.getResources().getColor(com.dbs.components.R.color.sg_primary_dark);
        int filledPinColor = context.getResources().getColor(com.dbs.components.R.color.sg_primary);

        assertEquals(10, otpPinsView.getNumberOfPins());
        pins = otpPinsView.findViewById(com.dbs.components.R.id.pins);
        for(int i = 0; i < otpPinsView.getNumberOfPins(); i++){
            AppCompatTextView pin = (AppCompatTextView) pins.getChildAt(i);
            LinearLayout.LayoutParams pinLayoutParams = (LinearLayout.LayoutParams) pin.getLayoutParams();

            assertEquals(pinFontSize, pin.getTextSize());
            assertEquals(margin, pinLayoutParams.bottomMargin);
            assertEquals(margin, pinLayoutParams.leftMargin);
            assertEquals(margin, pinLayoutParams.topMargin);
            assertEquals(margin, pinLayoutParams.rightMargin);
            assertEquals(margin, pinLayoutParams.getMarginStart());
            assertEquals(emptyPinColor, pin.getCurrentTextColor());
        }
        clickOn(R.id.default_pins);
        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_0);

        assertEquals(filledPinColor, ((AppCompatTextView)pins.getChildAt(0)).getCurrentTextColor());


    }


    private void validatePinColors(int numberOfPinsFilled, LinearLayout pinsLayout) {
        for(int i = 0; i < numberOfPinsFilled; i++){
            assertEquals(defaultFilledPinColor, getColor(pinsLayout, i));
        }
        for(int i = numberOfPinsFilled; i < otpPinsView.getNumberOfPins(); i++){
            assertEquals(defaultEmptyPinColor, getColor(pinsLayout, i));
        }
    }

    private int getColor(LinearLayout otpPinsView, int index) {
        return ((AppCompatTextView)otpPinsView.getChildAt(index)).getCurrentTextColor();
    }

    private void clickOn(int parentId) {
        onView(allOf(withId(com.dbs.components.R.id.pins), isDescendantOfA(withId(parentId)))).perform(click());
    }
}