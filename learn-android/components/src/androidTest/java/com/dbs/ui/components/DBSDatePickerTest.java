package com.dbs.ui.components;

import android.app.DatePickerDialog;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.runner.AndroidJUnit4;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import com.dbs.ui.components.testRule.ViewTestRule;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.dbs.ui.components.Matchers.CustomTestMatcher.matchesCalendar;
import static com.dbs.ui.components.Matchers.CustomTestMatcher.matchesDate;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class DBSDatePickerTest {

    @Rule
    public ViewTestRule<LinearLayout> rule = new ViewTestRule<>(com.dbs.components.test.R.layout.dbs_date_picker);

    @Test
    public void shouldReturnDateSelectedInDefaultFormatOnSelectionInDatePicker() {
        int year = 2017;
        int month = 11;
        int day = 15;
        onView(withId(com.dbs.components.test.R.id.datePickerOne)).perform(ViewActions.click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, month + 1, day));
        onView(withText("OK")).perform(ViewActions.click());

        String date = ((DBSDatePicker) rule.getView().findViewById(com.dbs.components.test.R.id.datePickerOne)).getDate();

        assertEquals("15/12/2017", date);
    }

    @Test
    public void shouldReturnDateSelectedInFormatSpecified() {
        int year = 2017;
        int month = 11;
        int day = 15;
        onView(withId(com.dbs.components.test.R.id.datePickerTwo)).perform(ViewActions.click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, month + 1, day));
        onView(withText("OK")).perform(ViewActions.click());

        String date = ((DBSDatePicker) rule.getView().findViewById(com.dbs.components.test.R.id.datePickerTwo)).getDate();

        assertEquals("12/15/2017", date);
    }

    @Test
    public void shouldReturnDateSelectedWithoutPrefixOnSelectingTodayWithoutIncludeTodayAttribute() {
        Calendar today = Calendar.getInstance();
        onView(withId(com.dbs.components.test.R.id.datePickerTwo)).perform(ViewActions.click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(today.get(Calendar.YEAR), today.get(Calendar.MONTH) + 1, today.get(Calendar.DAY_OF_MONTH)));
        onView(withText("OK")).perform(ViewActions.click());

        String date = ((DBSDatePicker) rule.getView().findViewById(com.dbs.components.test.R.id.datePickerTwo)).getDate();

        String expectedDate = new SimpleDateFormat("MM/dd/yyyy").format(today.getTime());
        assertEquals(expectedDate, date);
    }

    @Test
    public void testGetDateDisplayedReturnDateSelectedWithTodayPrefixOnSelectingTodayWithIncludeTodayAttribute() {
        Calendar today = Calendar.getInstance();
        onView(withId(com.dbs.components.test.R.id.datePickerOne)).perform(ViewActions.click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(today.get(Calendar.YEAR), today.get(Calendar.MONTH) + 1, today.get(Calendar.DAY_OF_MONTH)));
        onView(withText("OK")).perform(ViewActions.click());

        String date = ((DBSDatePicker) rule.getView().findViewById(com.dbs.components.test.R.id.datePickerOne)).getDateDisplayed();

        String expectedDate = "Today, " + new SimpleDateFormat("dd/MM/yyyy").format(today.getTime());
        assertEquals(expectedDate, date);
    }

    @Test
    public void testGetDateReturnsDateSelectedWithoutTodayPrefixOnSelectingTodayWithIncludeTodayAttribute() {
        Calendar today = Calendar.getInstance();
        onView(withId(com.dbs.components.test.R.id.datePickerOne)).perform(ViewActions.click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(today.get(Calendar.YEAR), today.get(Calendar.MONTH) + 1, today.get(Calendar.DAY_OF_MONTH)));
        onView(withText("OK")).perform(ViewActions.click());

        String date = ((DBSDatePicker) rule.getView().findViewById(com.dbs.components.test.R.id.datePickerOne)).getDate();

        String expectedDate = new SimpleDateFormat("dd/MM/yyyy").format(today.getTime());
        assertEquals(expectedDate, date);
    }

    @Test
    public void shouldCallListenerWhenDateIsSelected() {
        int year = 2017;
        int month = 11;
        int day = 15;
        DatePickerDialog.OnDateSetListener onDateSetListener = Mockito.mock(DatePickerDialog.OnDateSetListener.class);
        DBSDatePicker datePicker = rule.getView().findViewById(com.dbs.components.test.R.id.datePickerTwo);

        datePicker.setOnDateSetListener(onDateSetListener);

        onView(withId(com.dbs.components.test.R.id.datePickerTwo)).perform(ViewActions.click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, month + 1, day));
        onView(withText("OK")).perform(ViewActions.click());

        verify(onDateSetListener, times(1)).onDateSet(any(DatePicker.class), eq(year), eq(month), eq(day));
    }

    @Test
    public void shouldBeDisplayedWhenAddedProgrammatically() {
        int viewId = 1000;
        DBSDatePicker datePicker = new DBSDatePicker(rule.getActivity());
        datePicker.setId(viewId);
        rule.addView(datePicker);

        onView(withId(viewId)).check(matches(isDisplayed()));
        onView(withId(viewId)).check(matches(isClickable()));
    }

    @Test
    public void shouldBeDisplayedWhenAddedProgrammaticallyWithoutAttributes() {
        int viewId = 1010;
        DBSDatePicker datePicker = new DBSDatePicker(rule.getActivity(), null, -1);
        datePicker.setId(viewId);
        datePicker.setMinDateForPicker(Calendar.getInstance());
        datePicker.setMaxDateForPicker(Calendar.getInstance());
        rule.addView(datePicker);

        onView(withId(viewId)).check(matches(isDisplayed()));
        onView(withId(viewId)).check(matches(isClickable()));
        assertEquals("", datePicker.getDate());
    }

    @Test
    public void shouldDisplayPreviouslySelectedDateWhenDatePickerIsClicked() {
        int year = 2017;
        int month = 11;
        int day = 15;

        onView(withId(com.dbs.components.test.R.id.datePickerOne)).perform(ViewActions.click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, month + 1, day));
        onView(withText("OK")).perform(ViewActions.click());
        onView(withId(com.dbs.components.test.R.id.datePickerOne)).perform(ViewActions.click());

        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).check(matches(matchesDate(year, month, day)));
    }

    @Test
    public void shouldIgnoreDateAttributesSetInInvalidFormatInLayout() {
        onView(withId(com.dbs.components.test.R.id.datePickerThree)).perform(ViewActions.click());

        Calendar today = Calendar.getInstance();
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).check(matches(matchesCalendar(today)));
    }
}
