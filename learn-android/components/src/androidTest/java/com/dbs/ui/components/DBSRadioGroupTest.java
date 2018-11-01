package com.dbs.ui.components;

import android.os.Bundle;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dbs.components.test.R;
import com.dbs.ui.components.dbsradiobutton.DBSRadioGroup;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withTagValue;
import static com.dbs.ui.components.Matchers.CustomTestMatcher.withColorStateList;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class DBSRadioGroupTest {
    private static final String tagValue = "DBSRadioButton";
    @Rule
    public ActivityTestRule<DBSRadioGroupTestActivity> rule = new ActivityTestRule<>(DBSRadioGroupTestActivity.class);

    @Test
    public void shouldHaveFourRadioButtons() {
        onView(allOf(hasChildCount(4), withTagValue(is(tagValue))));
    }

    @Test
    public void shouldShowAllRadioButtons() {
        onView(allOf(isDisplayed(), withTagValue(is(tagValue))));
    }

    @Test
    public void shouldSelectDefaultRadioButton() {
        onView(withId(R.id.rButton4)).check(matches(isChecked()));
    }


    @Test
    public void shouldSetColorStateForAllRadioButton() {
        onView(allOf(withColorStateList(), withTagValue(is(tagValue))));
    }

    @Test
    public void shouldChangeCheckStateOnClickRadioButton() {
        DBSRadioGroup.OnCheckedChangeListener itemClickListener = mock(DBSRadioGroup.OnCheckedChangeListener.class);
        DBSRadioGroup dbsRadioGroup = rule.getActivity().findViewById(R.id.preset_time_radio_group);
        dbsRadioGroup.setOnCheckedChangeListener(itemClickListener);

        onView(withId(R.id.rButton1)).perform(click());
        verify(itemClickListener, times(1)).onChecked(any(View.class), any(View.class), any(String.class), any(Integer.class));
        onView(withId(R.id.rButton1)).check(matches(isChecked()));

        onView(withId(R.id.rButton2)).check(matches(isNotChecked()));
        onView(withId(R.id.rButton3)).check(matches(isNotChecked()));
        onView(withId(R.id.rButton4)).check(matches(isNotChecked()));
    }

    public static class DBSRadioGroupTestActivity extends AppCompatActivity {
        DBSRadioGroup dbsRadioGroup;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dbs_radiobutton_test);
            dbsRadioGroup = findViewById(R.id.preset_time_radio_group);

            dbsRadioGroup.setCheckedStateForView(R.id.rButton4, true); // set default selection
        }
    }
}
