package com.dbs.ui.components.forms;

import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.LinearLayout;

import com.dbs.components.R;
import com.dbs.ui.Await;
import com.dbs.ui.components.testRule.ViewTestRule;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasTextColor;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.dbs.ui.components.Matchers.CustomTestMatcher.withDrawable;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.hamcrest.CoreMatchers.allOf;

@RunWith(AndroidJUnit4.class)
public class DBSTextInputLayoutTest {
    @Rule
    public ViewTestRule<LinearLayout> viewTestRule = new ViewTestRule<>(com.dbs.components.test.R.layout.dbs_text_input_layout_test);
    private DBSTextInputLayout defaultTextInputLayout;
    private String inputText = "abc";
    private Matcher<View> defaultTextInputLayoutMatcher;
    private Matcher<View> defaultTextInputViewMatcher;

    @Before
    public void setUp() {
        viewTestRule.getActivity().setTheme(R.style.DBSBase_NoActionBar_India);
        defaultTextInputLayout = viewTestRule.getView().findViewById(com.dbs.components.test.R.id.defaultTextInputLayout);
        defaultTextInputLayoutMatcher = allOf(withId(R.id.textInputLayout), isDescendantOfA(withId(com.dbs.components.test.R.id.defaultTextInputLayout)));
        defaultTextInputViewMatcher = allOf(withId(R.id.textInputEditText), isDescendantOfA(withId(com.dbs.components.test.R.id.defaultTextInputLayout)));
    }

    @Test
    public void shouldBeAbleToCreateTextInputLayoutWithDefaultAttributes() throws InterruptedException {
        onView(defaultTextInputLayoutMatcher)
                .check((view, noViewFoundException) -> {
                    TextInputLayout inputLayout = (TextInputLayout) view;
                    assertNull(inputLayout.getHint());
                    assertNull(inputLayout.getError());
                });
        onView(defaultTextInputViewMatcher)
                .perform(typeText(inputText));
        onView(defaultTextInputViewMatcher)
                .check(matches(withText(inputText)))
                .check(matches(hasTextColor(R.color.india_color_primary_text)))
                .check((view, noViewFoundException) -> {
                    TextInputEditText inputEditText = (TextInputEditText) view;
                    Drawable[] compoundDrawables = inputEditText.getCompoundDrawables();
                    assertNull(compoundDrawables[0]);
                    assertNull(compoundDrawables[1]);
                    assertNull(compoundDrawables[2]);
                    assertNull(compoundDrawables[3]);
                });
    }

    @Test
    public void shouldBeAbleToCreateTextInputLayoutWithCustomAttributes() {
        onView(allOf(withId(R.id.textInputLayout), isDescendantOfA(withId(com.dbs.components.test.R.id.customTextInputLayout))))
                .check((view, noViewFoundException) -> {
                    TextInputLayout inputLayout = (TextInputLayout) view;
                    assertEquals("ABC", inputLayout.getHint());
                });
        onView(allOf(withId(R.id.textInputEditText), isDescendantOfA(withId(com.dbs.components.test.R.id.customTextInputLayout))))
                .perform(typeText(inputText))
                .check(matches(withText(inputText)))
                .check(matches(hasTextColor(R.color.india_color_primary_text)));
    }

    @Test
    public void shouldBeAbleToDisplayCustomDrawableIcon() {
        onView(allOf(withId(R.id.textInputEditText), isDescendantOfA(withId(com.dbs.components.test.R.id.customTextInputLayout))))
                .check(matches(withDrawable(R.drawable.ic_chevron_right_arrow, 2)));
    }

    @Test
    public void shouldSetErrorWhenPatternNotMatched() {
        FormValidateRule patternRule = Validators.EMAIL_PATTERN;
        ArrayList<FormValidateRule> userNameRules = new ArrayList<>();
        userNameRules.add(patternRule);
        defaultTextInputLayout.validateWith(userNameRules, 0);

        onView(defaultTextInputViewMatcher).perform(typeText(inputText));

        onView(defaultTextInputLayoutMatcher)
                .check((view, noViewFoundException) -> {
                    TextInputLayout inputLayout = (TextInputLayout) view;
                    assertEquals(patternRule.errorMessage(), inputLayout.getError());
                });
    }

    @Test
    public void shouldSetErrorWhenNoTextEnteredOnRequiredField() {
        defaultTextInputLayout.setRequired(true);

        ArrayList<FormValidateRule> userNameRules = new ArrayList<>();
        FormValidateRule notNullNotEmptyRule = Validators.NOT_NULL_NOT_EMPTY;
        userNameRules.add(notNullNotEmptyRule);
        defaultTextInputLayout.validateWith(userNameRules, 0);

        onView(defaultTextInputViewMatcher)
                .perform(typeText(inputText))
                .perform(replaceText(""));

        onView(defaultTextInputLayoutMatcher)
                .check((view, noViewFoundException) -> {
                    TextInputLayout inputLayout = (TextInputLayout) view;
                    assertEquals(notNullNotEmptyRule.errorMessage(), inputLayout.getError());
                });
    }

    @Test
    public void shouldNotShowErrorIfNoValueForFieldNotRequired() {
        defaultTextInputLayout.setRequired(false);

        onView(defaultTextInputViewMatcher).perform(replaceText(""));

        onView(defaultTextInputLayoutMatcher)
                .check((view, noViewFoundException) -> {
                    TextInputLayout inputLayout = (TextInputLayout) view;
                    assertNull(inputLayout.getError());
                });
    }

    @Test
    public void shouldNotSetErrorIfRulesFollowed() throws Exception {
        ArrayList<FormValidateRule> userNameRules = new ArrayList<>();
        FormValidateRule patternRule = Validators.EMAIL_PATTERN;
        userNameRules.add(patternRule);
        defaultTextInputLayout.validateWith(userNameRules);

        onView(defaultTextInputViewMatcher).perform(typeText("abc@xyz.com"));

        Await.await(() -> !patternRule.errorMessage().isEmpty(), 10);

        onView(defaultTextInputLayoutMatcher)
                .check((view, noViewFoundException) -> {
                    TextInputLayout inputLayout = (TextInputLayout) view;
                    assertNull(inputLayout.getError());
                });
    }

    @Test
    public void shouldSetError() {
        String errorText = "Some error";
        viewTestRule.runOnMainSynchronously(view -> defaultTextInputLayout.setError(errorText));

        onView(defaultTextInputLayoutMatcher)
                .check((view, noViewFoundException) -> {
                    TextInputLayout inputLayout = (TextInputLayout) view;
                    assertEquals(errorText, inputLayout.getError());
                });
    }
}