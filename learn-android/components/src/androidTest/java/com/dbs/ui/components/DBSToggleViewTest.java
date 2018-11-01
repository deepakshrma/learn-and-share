package com.dbs.ui.components;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dbs.components.R;
import com.dbs.ui.components.testRule.ViewTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static org.hamcrest.CoreMatchers.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class DBSToggleViewTest {
    @Rule
    public ViewTestRule<LinearLayout> rule = new ViewTestRule<>(com.dbs.components.test.R.layout.dbs_toggle_view_test);

    private LinearLayout testLayout;

    @Before
    public void setUp() {
        testLayout = rule.getView();
    }

    @Test
    public void whenToggledProgramatically_shouldNotCallOnToggleChangeListener(){
        DBSToggleView dbsToggleView = rule.getActivity().findViewById(com.dbs.components.test.R.id.dbs_toggle_view_with_all_attributes);
        CompoundButton.OnCheckedChangeListener mockedListener = Mockito.mock(CompoundButton.OnCheckedChangeListener.class);
        dbsToggleView.setOnCheckedChangeListener(mockedListener);
        rule.getActivity().runOnUiThread(() -> {
            dbsToggleView.setChecked(false);
            assertFalse(dbsToggleView.isChecked());
            verify(mockedListener, times(0)).onCheckedChanged(any(CompoundButton.class), anyBoolean());
        });
    }

    @Test
    public void whenToggledWithClick_shouldCallOnToggleChangeListener(){
        DBSToggleView dbsToggleView = rule.getActivity().findViewById(com.dbs.components.test.R.id.dbs_toggle_view_with_all_attributes);
        CompoundButton.OnCheckedChangeListener mockedListener = Mockito.mock(CompoundButton.OnCheckedChangeListener.class);
        dbsToggleView.setOnCheckedChangeListener(mockedListener);
        onView(allOf(withId(R.id.toggle_switch), withParent(withParent(withParent((withId(com.dbs.components.test.R.id.dbs_toggle_view_with_all_attributes))))))).perform(click());
        verify(mockedListener, times(1)).onCheckedChanged(any(CompoundButton.class), eq(false));
    }

    @Test
    public void shouldDisplayLabelWithAllDataAsAttributes() {
        DBSToggleView dbsToggleView = rule.getActivity().findViewById(com.dbs.components.test.R.id.dbs_toggle_view_with_all_attributes);

        int layoutId = dbsToggleView.provideLayoutId("");
        TextView titleText = testLayout.findViewById(R.id.toggle_title);
        TextView descriptionText = dbsToggleView.findViewById(R.id.toggle_description);
        SwitchCompat toggleSwitch = dbsToggleView.findViewById(R.id.toggle_switch);
        String expectedTitleText = "title";
        String expectedPrefixText = "description";


        assertEquals(R.layout.dbs_toggle_view, layoutId);
        assertEquals(expectedTitleText, titleText.getText().toString());
        assertEquals(View.VISIBLE, descriptionText.getVisibility());
        assertEquals(expectedPrefixText, descriptionText.getText().toString());
        assertTrue(toggleSwitch.isChecked());

    }

    @Test
    public void shouldDisplayToggleViewWithoutDescription() {
        DBSToggleView dbsToggleView = testLayout.findViewById(com.dbs.components.test.R.id.dbs_toggle_view_with_out_description);

        TextView descriptionText = dbsToggleView.findViewById(R.id.toggle_description);

        assertEquals(View.GONE, descriptionText.getVisibility());
    }


    @Test
    public void shouldDisplayToggleViewWithoutDescriptionIfDescriptionIsSetNull() {
        DBSToggleView dbsToggleView = testLayout.findViewById(com.dbs.components.test.R.id.dbs_toggle_view_with_out_description);

        TextView descriptionText = dbsToggleView.findViewById(R.id.toggle_description);
        dbsToggleView.setDescription(null);
        assertEquals(View.GONE, descriptionText.getVisibility());
    }

    @Test
    public void shouldDisplayToggleViewWithoutDescriptionIfDescriptionIsEmpty() {
        DBSToggleView dbsToggleView = testLayout.findViewById(com.dbs.components.test.R.id.dbs_toggle_view_with_out_description);

        TextView descriptionText = dbsToggleView.findViewById(R.id.toggle_description);
        dbsToggleView.setDescription("");
        assertEquals(View.GONE, descriptionText.getVisibility());
    }

    @Test
    public void shouldDisplayToggleViewSetWithValidDescription() throws Throwable {
        runOnUiThread(() -> {
            DBSToggleView dbsToggleView = testLayout.findViewById(com.dbs.components.test.R.id.dbs_toggle_view_with_out_description);

            TextView description = dbsToggleView.findViewById(R.id.toggle_description);

            String expectedDescriptionText = "description";
            dbsToggleView.setDescription(expectedDescriptionText);
            assertEquals(expectedDescriptionText, description.getText().toString());
            assertEquals(View.VISIBLE, description.getVisibility());
        });
    }

    @Test
    public void shouldDisplayToggleViewWhenTitleIsSet() {
        DBSToggleView dbsToggleView = testLayout.findViewById(com.dbs.components.test.R.id.dbs_toggle_view_with_all_attributes);

        TextView titleText = dbsToggleView.findViewById(R.id.toggle_title);
        String expectedTitleText = "title";
        dbsToggleView.setTitle(expectedTitleText);
        assertEquals(expectedTitleText, titleText.getText().toString());
    }

    @Test
    public void shouldDisplayToggleViewWhenDescriptionIsSet() {
        DBSToggleView dbsToggleView = testLayout.findViewById(com.dbs.components.test.R.id.dbs_toggle_view_with_all_attributes);


        TextView valueText = dbsToggleView.findViewById(R.id.toggle_description);
        String expectedDescriptionText = "MyDescription";
        dbsToggleView.setDescription(expectedDescriptionText);
        assertEquals(expectedDescriptionText, valueText.getText().toString());
    }

    @Test
    public void shouldDisplayToggleViewWhenSwitchIsChecked() {
        DBSToggleView dbsToggleView = testLayout.findViewById(com.dbs.components.test.R.id.dbs_toggle_view_with_all_attributes);


        SwitchCompat toggleSwitch = dbsToggleView.findViewById(R.id.toggle_switch);
        dbsToggleView.setChecked(true);
        assertTrue(toggleSwitch.isChecked());
    }

    @Test
    public void shouldDisplayToggleViewWithStyles() {
        DBSToggleView dbsLabel = testLayout.findViewById(com.dbs.components.test.R.id.dbs_toggle_view_with_all_attributes);

        assertDescriptionStyle(dbsLabel);
        assertTitleStyle(dbsLabel);
    }

    @Test
    public void shouldCreateToggleViewWithoutAttributes() {
        DBSToggleView dbsToggleView = testLayout.findViewById(com.dbs.components.test.R.id.dbs_toggle_view_without_attributes);

        assertNotNull(dbsToggleView.findViewById(R.id.toggle_title));
        assertNotNull(dbsToggleView.findViewById(R.id.toggle_description));
        assertNotNull(dbsToggleView.findViewById(R.id.toggle_switch));
    }

    @Test
    public void shouldCreateToggleViewWithCustomSwitchColors() {
        DBSToggleView dbsToggleView = testLayout.findViewById(com.dbs.components.test.R.id.dbs_toggle_view_with_tint_attributes);

        assertEquals(dbsToggleView.getToggle().getThumbTintList().getDefaultColor(), Color.GREEN);
        assertEquals(dbsToggleView.getToggle().getTrackTintList().getDefaultColor(), Color.RED);
    }

    @Test
    public void shouldCreateToggleViewWithDefaultSwitchColors() {
        DBSToggleView dbsToggleView = testLayout.findViewById(com.dbs.components.test.R.id.dbs_toggle_view_with_all_attributes);
        ColorStateList thumbTintList = ContextCompat.getColorStateList(rule.getActivity(), R.color.toggle_thumb);
        ColorStateList trackTintList = ContextCompat.getColorStateList(rule.getActivity(), R.color.toggle_track);
        assertEquals(thumbTintList, dbsToggleView.getToggle().getThumbTintList());
        assertEquals(trackTintList, dbsToggleView.getToggle().getTrackTintList());
    }

    private void assertTitleStyle(DBSToggleView dbsToggleView) {
        int expectedColor = ContextCompat.getColor(dbsToggleView.getContext(), R.color.sg_ascent);

        TextView titleText = dbsToggleView.findViewById(R.id.toggle_title);

        assertNotEquals(expectedColor, titleText.getCurrentTextColor());

        dbsToggleView.setTitleStyle(com.dbs.components.test.R.style.DBSToggleVIew);

        assertEquals(expectedColor, titleText.getCurrentTextColor());
    }

    private void assertDescriptionStyle(DBSToggleView dbsToggleView) {
        int expectedColor = ContextCompat.getColor(dbsToggleView.getContext(), R.color.sg_ascent);

        TextView descriptionText = dbsToggleView.findViewById(R.id.toggle_description);

        assertNotEquals(expectedColor, descriptionText.getCurrentTextColor());

        dbsToggleView.setDescriptionStyle(com.dbs.components.test.R.style.DBSToggleVIew);

        assertEquals(expectedColor, descriptionText.getCurrentTextColor());
    }


}
