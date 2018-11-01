package com.dbs.ui.components.forms;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dbs.components.test.R;
import com.dbs.ui.Await;
import com.dbs.ui.components.testRule.ViewTestRule;
import com.dbs.ui.utils.StringUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasTextColor;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.dbs.ui.components.Matchers.CustomTestMatcher.withDrawable;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.allOf;

@RunWith(AndroidJUnit4.class)
public class DBSTextInputCardLayoutTest {
    @Rule
    public ViewTestRule<LinearLayout> viewTestRule = new ViewTestRule<>(R.layout.dbs_text_input_card_layout_test);
    private DBSTextInputCardLayout defaultTextInputLayout;
    private DBSTextInputCardLayout customTextInputLayout;
    private String inputText = "abc";

    @Before
    public void setUp() {
        defaultTextInputLayout = viewTestRule.getView().findViewById(R.id.defaultTextInputLayout);
        customTextInputLayout = viewTestRule.getView().findViewById(R.id.customTextInputLayout);
    }

    @Test
    public void shouldBeAbleToSetDefaultAttributes() {
        viewTestRule.getActivity().setTheme(R.style.DBSBase_NoActionBar_India);

        String errorText = "Error !!";
        TextView errorTextView = defaultTextInputLayout.getErrorTextView();
        viewTestRule.runOnMainSynchronously(view -> defaultTextInputLayout.setError(errorText));

        TextInputEditText textInputEditTextView = defaultTextInputLayout.getTextInputEditTextView();
        onView(allOf(withId(textInputEditTextView.getId()), isDescendantOfA(withId(R.id.defaultTextInputLayout)))).perform(typeText(inputText));

        assertEquals(errorText, errorTextView.getText());
        onView(withText(inputText)).check(matches(hasTextColor(R.color.india_color_primary_text)));
        onView(withText(errorText)).check(matches(hasTextColor(R.color.india_color_red_primary)));
    }

    @Test
    public void shouldBeAbleToSetCustomAttributes() {
        String errorText = "Error message";
        TextInputLayout textInputLayout = customTextInputLayout.getTextInputLayout();
        TextInputEditText textInputEditTextView = customTextInputLayout.getTextInputEditTextView();
        viewTestRule.runOnMainSynchronously(view -> customTextInputLayout.showError());

        TextView errorTextView = customTextInputLayout.getErrorTextView();
        onView(allOf(withId(textInputEditTextView.getId()), isDescendantOfA(withId(R.id.customTextInputLayout)))).perform(typeText(inputText));

        assertEquals("ABC", textInputLayout.getHint());
        assertEquals(inputText, textInputEditTextView.getText().toString());
        assertEquals(errorText, errorTextView.getText());
        onView(withText(inputText)).check(matches(hasTextColor(R.color.india_color_red_primary)));
        onView(withText(errorText)).check(matches(hasTextColor(R.color.india_color_red_primary)));
    }

    @Test
    public void shouldBeAbleToSetCustomErrorTextWithCustomColor() {
        String expectedErrorText = "Some Error";
        defaultTextInputLayout.setErrorColor(ContextCompat.getColor(viewTestRule.getActivity(), R.color.india_color_primary));
        viewTestRule.runOnMainSynchronously(view -> defaultTextInputLayout.setError(expectedErrorText));

        assertEquals(expectedErrorText, defaultTextInputLayout.getErrorTextView().getText());
        onView(withText(expectedErrorText)).check(matches(hasTextColor(R.color.india_color_primary)));
    }

    @Test
    public void shouldBeAbleToDisplayCustomDrawableIcon() {
        TextInputEditText textInputEditTextView = customTextInputLayout.getTextInputEditTextView();

        onView(allOf(withId(textInputEditTextView.getId()), isDescendantOfA(withId(R.id.customTextInputLayout)))).check(matches(withDrawable(R.drawable.ic_chevron_right_arrow, 2)));
    }

    @Test
    public void shouldSetErrorWhenPatternNotMatched() {
        FormValidateRule patternRule = Validators.EMAIL_PATTERN;
        ArrayList<FormValidateRule> userNameRules = new ArrayList<>();
        userNameRules.add(patternRule);
        defaultTextInputLayout.validateWith(userNameRules, 0);

        TextInputEditText textInputEditTextView = defaultTextInputLayout.getTextInputEditTextView();
        onView(allOf(withId(textInputEditTextView.getId()), isDescendantOfA(withId(R.id.defaultTextInputLayout)))).perform(typeText(inputText));

        assertEquals(patternRule.errorMessage(), defaultTextInputLayout.getErrorTextView().getText());
    }

    @Test
    public void shouldSetErrorWhenNoTextEnteredOnRequiredField() throws Exception {
        defaultTextInputLayout.setRequired(true);

        ArrayList<FormValidateRule> userNameRules = new ArrayList<>();
        FormValidateRule notNullNotEmptyRule = Validators.NOT_NULL_NOT_EMPTY;
        userNameRules.add(notNullNotEmptyRule);
        defaultTextInputLayout.validateWith(userNameRules, 0);

        TextInputEditText textInputEditTextView = defaultTextInputLayout.getTextInputEditTextView();
        onView(allOf(withId(textInputEditTextView.getId()), isDescendantOfA(withId(R.id.defaultTextInputLayout)))).perform(typeText(inputText));
        onView(allOf(withId(textInputEditTextView.getId()), isDescendantOfA(withId(R.id.defaultTextInputLayout)))).perform(replaceText(""));

        Await.await(() -> !StringUtils.isEmpty(defaultTextInputLayout.getErrorTextView().getText().toString()), 60);
        assertEquals(notNullNotEmptyRule.errorMessage(), defaultTextInputLayout.getErrorTextView().getText().toString());
    }

    @Test
    public void shouldSetErrorWhenTimeoutIsSet() throws Exception {
        defaultTextInputLayout.setRequired(true);

        ArrayList<FormValidateRule> userNameRules = new ArrayList<>();
        FormValidateRule notNullNotEmptyRule = Validators.NOT_NULL_NOT_EMPTY;
        userNameRules.add(notNullNotEmptyRule);
        defaultTextInputLayout.validateWith(userNameRules, 300);

        TextInputEditText textInputEditTextView = defaultTextInputLayout.getTextInputEditTextView();
        onView(allOf(withId(textInputEditTextView.getId()), isDescendantOfA(withId(R.id.defaultTextInputLayout)))).perform(typeText(inputText));
        onView(allOf(withId(textInputEditTextView.getId()), isDescendantOfA(withId(R.id.defaultTextInputLayout)))).perform(replaceText(""));

        Await.await(() -> !StringUtils.isEmpty(defaultTextInputLayout.getErrorTextView().getText().toString()), 300);
        assertEquals(notNullNotEmptyRule.errorMessage(), defaultTextInputLayout.getErrorTextView().getText().toString());
    }

    @Test
    public void shouldHandleIsRequiredFlag() throws Exception {
        defaultTextInputLayout.setRequired(false);

        ArrayList<FormValidateRule> userNameRules = new ArrayList<>();
        FormValidateRule patternRule = Validators.EMAIL_PATTERN;
        userNameRules.add(patternRule);
        defaultTextInputLayout.validateWith(userNameRules);

        TextInputEditText textInputEditTextView = defaultTextInputLayout.getTextInputEditTextView();
        onView(allOf(withId(textInputEditTextView.getId()), isDescendantOfA(withId(R.id.defaultTextInputLayout)))).perform(typeText(inputText));
        onView(allOf(withId(textInputEditTextView.getId()), isDescendantOfA(withId(R.id.defaultTextInputLayout)))).perform(replaceText(""));

        Await.await(() -> !patternRule.errorMessage().isEmpty(), 10);
        assertEquals("", defaultTextInputLayout.getErrorTextView().getText().toString());
    }

    @Test
    public void shouldNotSetErrorIfRulesFollowed() throws Exception {
        ArrayList<FormValidateRule> userNameRules = new ArrayList<>();
        FormValidateRule patternRule = Validators.EMAIL_PATTERN;
        userNameRules.add(patternRule);
        defaultTextInputLayout.validateWith(userNameRules);

        TextInputEditText textInputEditTextView = defaultTextInputLayout.getTextInputEditTextView();
        onView(allOf(withId(textInputEditTextView.getId()), isDescendantOfA(withId(R.id.defaultTextInputLayout)))).perform(typeText("abc@xyz.com"));

        Await.await(() -> !patternRule.errorMessage().isEmpty(), 10);
        assertEquals("", defaultTextInputLayout.getErrorTextView().getText().toString());
    }

    @Test
    public void shouldUnsubscribeForTextChangeAfterFocusIsGone() throws Exception {
        ArrayList<FormValidateRule> userNameRules = new ArrayList<>();
        FormValidateRule patternRule = Validators.EMAIL_PATTERN;
        userNameRules.add(patternRule);
        defaultTextInputLayout.validateWith(userNameRules);

        TextInputEditText textInputEditTextView = defaultTextInputLayout.getTextInputEditTextView();
        onView(allOf(withId(textInputEditTextView.getId()), isDescendantOfA(withId(R.id.defaultTextInputLayout)))).perform(typeText("abc@xyz.com"), pressImeActionButton());

        Await.await(() -> !patternRule.errorMessage().isEmpty(), 10);
        assertEquals("", defaultTextInputLayout.getErrorTextView().getText().toString());
    }

    @Test
    public void shouldHandleWhenEditTextIsNotPresent() {
        ArrayList<FormValidateRule> userNameRules = new ArrayList<>();
        userNameRules.add(Validators.EMAIL_PATTERN);
        defaultTextInputLayout.validateWith(userNameRules);

        assertEquals("", defaultTextInputLayout.getErrorTextView().getText().toString());
    }
}
