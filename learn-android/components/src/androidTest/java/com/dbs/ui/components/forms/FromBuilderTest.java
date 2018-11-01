package com.dbs.ui.components.forms;

import android.app.Activity;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dbs.components.test.R;
import com.dbs.ui.components.testRule.ViewTestRule;
import com.dbs.ui.utils.DBSPopup;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
public class FromBuilderTest {
    Activity activity;
    FormGroup userForm;
    Button imperativeSubmit;
    @Rule
    public ViewTestRule<LinearLayout> viewTestRule = new ViewTestRule<>(R.layout.basic_form_validation_test);

    @Before
    public void setUp() {
        activity = viewTestRule.getActivity();
        EditText userName = activity.findViewById(R.id.usernameF);
        EditText password = activity.findViewById(R.id.passwordF);
        imperativeSubmit = activity.findViewById(R.id.imperativeSubmit);
        userForm = FormGroupBuilder.build(activity);
        FormControl userNameFormControl = new FormControl(userName);
        userNameFormControl.add(Validators.NOT_NULL);
        userNameFormControl.add(new Validators.MinLengthRule(5));

        FormControl passwordFormControl = new FormControl(password);
        passwordFormControl.add(Validators.NOT_NULL);
        passwordFormControl.add(new Validators.MinLengthRule(10));
        userForm.add("userName", userNameFormControl);
        userForm.add("password", passwordFormControl);
    }

    @Test
    public void initViewAndPerformValidation() {
        onView(withId(R.id.usernameF)).perform(typeText("411"));
        assertNotEquals(userForm.isValid(), true);
    }

    @Test
    public void validateUsernameMissingPasswordValidation() {
        onView(withId(R.id.usernameF)).perform(typeText("deepak"));
        assertNotEquals(userForm.isValid(), true);
    }

    @Test
    public void validatePasswordMissingUsernameValidation() {
        onView(withId(R.id.usernameF)).perform(typeText(""));
        onView(withId(R.id.passwordF)).perform(typeText("password12345"));
        assertNotEquals(userForm.isValid(), true);
    }

    @Test
    public void validatePasswordUsernameSuccess() {
        onView(withId(R.id.usernameF)).perform(typeText("deepak"));
        onView(withId(R.id.passwordF)).perform(typeText("password12345"));
        assertEquals(userForm.isValid(), true);
    }
}
