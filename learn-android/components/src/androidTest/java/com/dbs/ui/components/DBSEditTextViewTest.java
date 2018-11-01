package com.dbs.ui.components;

import android.support.test.espresso.action.ViewActions;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;

import com.dbs.components.test.R;
import com.dbs.ui.components.testRule.ViewTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static com.dbs.ui.components.Matchers.CustomTestMatcher.isEditTextValueEqualTo;

@RunWith(AndroidJUnit4.class)
public class DBSEditTextViewTest {
    @Rule
    public ViewTestRule<DBSEditTextView> rule = new ViewTestRule<>(com.dbs.components.test.R.layout.dbs_edit_text_view);

    @Test
    public void shouldDisplayCountryCodeForPhoneType() {
        onView(withId(R.id.phone_edit_text)).perform(typeText("1"));

        onView(withId(R.id.phone_edit_text)).check(matches(isEditTextValueEqualTo("+91-1")));
    }

    @Test
    public void shouldDisplayNumbersInExpectedFormatForPhoneType() {
        onView(withId(R.id.phone_edit_text)).perform(typeText("9012345678"));

        onView(withId(R.id.phone_edit_text)).check(matches(isEditTextValueEqualTo("+91-901-(234)(5678)")));
    }

    @Test
    public void shouldNotTakeMoreThan10CharactersForPhoneType() {
        onView(withId(R.id.phone_edit_text)).perform(typeText("9012345678000"));

        onView(withId(R.id.phone_edit_text)).check(matches(isEditTextValueEqualTo("+91-901-(234)(5678)")));
    }

    @Test
    public void shouldEscapeCharactersOtherThanNumbersForPhoneType() {
        onView(withId(R.id.phone_edit_text)).perform(typeText("EscapeMe9012345678"));

        onView(withId(R.id.phone_edit_text)).check(matches(isEditTextValueEqualTo("+91-901-(234)(5678)")));
    }

    @Test
    public void shouldShowCurrencySignForCurrencyType() {
        onView(withId(R.id.currency_edit_text)).perform(typeText("1"));

        onView(withId(R.id.currency_edit_text)).check(matches(isEditTextValueEqualTo("1¥")));
    }

    @Test
    public void shouldShowCurrencyAsExceptedFormatForCurrencyType() {
        onView(withId(R.id.currency_edit_text)).perform(typeText("12345678"));

        onView(withId(R.id.currency_edit_text)).check(matches(isEditTextValueEqualTo("12,345,678¥")));
    }

    @Test
    public void shouldEscapeCharactersOtherThanNumbersForCurrencyType() {
        onView(withId(R.id.currency_edit_text)).perform(typeText("EscapeMe12345678"));

        onView(withId(R.id.currency_edit_text)).check(matches(isEditTextValueEqualTo("12,345,678¥")));
    }

    @Test
    public void shouldNotBeAbleTypeMoreThanOneDecimalPointForCurrencyType() {
        onView(withId(R.id.currency_edit_text)).perform(typeText("123456.78."), pressImeActionButton());

        onView(withId(R.id.currency_edit_text)).check(matches(isEditTextValueEqualTo("123,456.780¥")));
    }

    @Test
    public void shouldNotBeAbleTypeNumbersAfterFractionLengthForCurrencyType() {
        onView(withId(R.id.currency_edit_text)).perform(typeText("123456.78655"), pressImeActionButton());

        onView(withId(R.id.currency_edit_text)).check(matches(isEditTextValueEqualTo("123,456.786¥")));
    }

    @Test
    public void shouldAddExtrasZeroAfterFocusChangeForCurrencyType() {
        onView(withId(R.id.currency_edit_text)).perform(typeText("123456"), pressImeActionButton());

        onView(withId(R.id.currency_edit_text)).check(matches(isEditTextValueEqualTo("123,456.000¥")));
    }

    @Test
    public void shouldMoveTheNumberAfterCurrencySymbolWhenUserTypesBeforeCurrencyRightSymbol() throws Throwable {
        DBSEditTextView editTextView = rule.getActivity().findViewById(R.id.currency_edit_text);
        onView(withId(R.id.currency_edit_text)).perform(typeText("4 "));
        runOnUiThread(() -> editTextView.setSelection(0));
        onView(withId(R.id.currency_edit_text)).perform(typeText("123456"), pressImeActionButton());

        onView(withId(R.id.currency_edit_text)).check(matches(isEditTextValueEqualTo("4,123,456.000¥")));
    }

    @Test
    public void shouldShowCurrencySymbolAtLeftChangeForCurrencyType() {
        onView(withId(R.id.left_symbol_currency_edit_text)).perform(typeText("123456"), pressImeActionButton());

        onView(withId(R.id.left_symbol_currency_edit_text)).check(matches(isEditTextValueEqualTo("¥123,456.00")));
    }

    @Test
    public void shouldMoveTheNumberAfterCurrencySymbolWhenUserTypesBeforeCurrencyLeftSymbol() throws Throwable {
        DBSEditTextView editTextView = rule.getActivity().findViewById(R.id.left_symbol_currency_edit_text);
        onView(withId(R.id.left_symbol_currency_edit_text)).perform(typeText("4 "));
        runOnUiThread(() -> editTextView.setSelection(0));
        onView(withId(R.id.left_symbol_currency_edit_text)).perform(typeText("123456"), pressImeActionButton());

        onView(withId(R.id.left_symbol_currency_edit_text)).check(matches(isEditTextValueEqualTo("¥4,123,456.00")));
    }

    @Test
    public void shouldNotAddDecimalPointIfItISDecimalPointOnly() {
        onView(withId(R.id.left_symbol_currency_edit_text)).perform(typeText(". "));
        onView(withId(R.id.left_symbol_currency_edit_text)).check(matches(isEditTextValueEqualTo("")));
    }

    @Test
    public void shouldFormatTheCurrentForDefaultText() {
        onView(withId(R.id.default_currency_edit_text)).check(matches(isEditTextValueEqualTo("¥1,234")));
    }

    @Test
    public void shouldBeAbleDeleteTheTextWhenBackspacePressedTwiceAtCommaPosition() throws Throwable {
        DBSEditTextView editTextView = rule.getActivity().findViewById(R.id.left_symbol_currency_edit_text);
        onView(withId(R.id.left_symbol_currency_edit_text)).perform(typeText("42324"));
        runOnUiThread(() -> editTextView.setSelection(4));
        onView(withId(R.id.left_symbol_currency_edit_text)).perform(ViewActions.pressKey(KeyEvent.KEYCODE_DEL), ViewActions.pressKey(KeyEvent.KEYCODE_DEL));
        onView(withId(R.id.left_symbol_currency_edit_text)).check(matches(isEditTextValueEqualTo("¥4,324")));
    }

}
