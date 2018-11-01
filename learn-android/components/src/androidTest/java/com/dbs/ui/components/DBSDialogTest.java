package com.dbs.ui.components;

import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;

import com.dbs.components.test.R;
import com.dbs.ui.components.testRule.ViewTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class DBSDialogTest {
    private DBSDialog.Builder dialogBuilder;
    @Rule
    public ViewTestRule<DBSTimerView> viewTestRule = new ViewTestRule<>(com.dbs.components.test.R.layout.timer_view);

    @Before
    public void setUp() {
        dialogBuilder = new DBSDialog.Builder("title", "description");
    }


    @Test
    public void shouldShowTitleAndDescription() {
        DBSDialog dbsDialog = dialogBuilder.build();
        showDialog(dbsDialog);
        onView(withText("title")).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("description")).inRoot(isDialog()).check(matches(isDisplayed()));
    }

    @Test
    public void shouldShowTitleAndDescriptionAndImage() {
        DBSDialog dbsDialog = dialogBuilder.withImage(R.drawable.happy_image).build();
        showDialog(dbsDialog);
        onView(withText("title")).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("description")).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withId(R.id.dialogImage)).inRoot(isDialog()).check(matches(isDisplayed()));
    }

    @Test
    public void shouldShowButtons() {
        DBSDialog dbsDialog = dialogBuilder
                .withPositiveButton("confirm")
                .withNegativeButton("cancel")
                .build();
        showDialog(dbsDialog);
        onView(withText("confirm")).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("cancel")).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("confirm")).inRoot(isDialog()).check(matches(is(isClickable())));
        onView(withText("cancel")).inRoot(isDialog()).check(matches(is(isClickable())));
    }

    @Test
    public void shouldCallPositiveOnClickHandlerWhenUserClicksPositiveButton() {
        DBSDialog.DialogOnClickHandler dialogOnClickHandler = mock(DBSDialog.DialogOnClickHandler.class);
        DBSDialog dbsDialog = dialogBuilder
                .withPositiveButton("confirm")
                .withDialogClickHandler(dialogOnClickHandler)
                .build();
        showDialog(dbsDialog);

        onView(withId(R.id.dialogPositiveButton)).perform(click());
        verify(dialogOnClickHandler).positiveOnclick(dbsDialog);
    }

    @Test
    public void shouldCallNegativeOnClickHandlerWhenUserClicksNegativeButton() {
        DBSDialog.DialogOnClickHandler dialogOnClickHandler = mock(DBSDialog.DialogOnClickHandler.class);
        DBSDialog dbsDialog = dialogBuilder
                .withNegativeButton("cancel")
                .withDialogClickHandler(dialogOnClickHandler)
                .build();
        showDialog(dbsDialog);

        onView(withId(R.id.dialogNegativeButton)).perform(click());
        verify(dialogOnClickHandler).negativeOnclick(dbsDialog);
    }

    @Test
    public void shouldShowTitleOnly() {
        DBSDialog dbsDialog = new DBSDialog.Builder("title", "").build();
        showDialog(dbsDialog);
        onView(withText("title")).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withId(R.id.dialogDescription)).check(matches(not(isDisplayed())));
    }

    @Test
    public void shouldShowDescriptionOnly() {
        DBSDialog dbsDialog = new DBSDialog.Builder("", "description").build();
        showDialog(dbsDialog);
        onView(withId(R.id.dialogTitle)).check(matches(not(isDisplayed())));
        onView(withText("description")).inRoot(isDialog()).check(matches(isDisplayed()));
    }

    @Test
    public void shouldShowCustomLayoutWithTextView() {
        LayoutInflater inflater = viewTestRule.getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dbs_dialog_custom_test, null);
        DBSDialog dbsDialog = new DBSDialog.Builder(view).build();
        showDialog(dbsDialog);
        onView(withText("custom")).inRoot(isDialog()).check(matches(isDisplayed()));
    }

    private void showDialog(DialogFragment dialog) {
        FragmentManager fragmentManager = viewTestRule.getActivity().getSupportFragmentManager();
        dialog.show(fragmentManager, "test");
    }

}

