package com.dbs.ui.components;

import android.support.test.espresso.ViewInteraction;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dbs.components.R;
import com.dbs.ui.components.testRule.ViewTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class DBSLabelTest {
    @Rule
    public ViewTestRule<LinearLayout> rule = new ViewTestRule<>(com.dbs.components.test.R.layout.dbs_label_test);

    private LinearLayout testLayout;

    @Before
    public void setUp() {
        testLayout = rule.getView();
    }

    @Test
    public void shouldDisplayLabelWithAllDataAsAttributes() {
        int parentId = com.dbs.components.test.R.id.dabs_label_with_all_attributes;
        DBSLabel dbsLabel = testLayout.findViewById(parentId);

        int layoutId = dbsLabel.provideLayoutId("");

        assertEquals(R.layout.dbs_label, layoutId);

        getChildView(R.id.prefix, parentId)
                .check(matches(isDisplayed()))
                .check(matches(withText("MyPrefix")));

        getChildView(R.id.value, parentId).check(matches(withText("MyLabel")));
        getChildView(R.id.title, parentId).check(matches(withText("MyTitle")));
        getChildView(R.id.desc, parentId).check(matches(withText("MyDescription")));
    }

    @Test
    public void shouldDisplayLabelWithoutPrefix() {
        getChildView(R.id.prefix, com.dbs.components.test.R.id.dbs_label_view_with_attributes_without_prefix)
                .check(matches(not(isDisplayed())));
    }

    @Test
    public void shouldDisplayLabelWithoutDescription() {
        getChildView(R.id.desc, com.dbs.components.test.R.id.dbs_label_view_with_attributes_without_desc)
                .check(matches(not(isDisplayed())));
    }

    @Test
    public void shouldDisplayLabelWithoutPrefixIfPrefixIsSetNull() {
        int parentId = com.dbs.components.test.R.id.dbs_label_view_with_attributes_without_prefix;
        DBSLabel dbsLabel = testLayout.findViewById(parentId);

        dbsLabel.setPrefix(null);

        getChildView(R.id.prefix, parentId).check(matches(not(isDisplayed())));
    }

    @Test
    public void shouldDisplayLabelWithoutDescriptionIfIsSetToNull() {
        int parentId = com.dbs.components.test.R.id.dbs_label_view_with_attributes_without_desc;
        DBSLabel dbsLabel = testLayout.findViewById(parentId);

        dbsLabel.setDescription(null);
        getChildView(R.id.desc, parentId).check(matches(not(isDisplayed())));
    }

    @Test
    public void shouldDisplayLabelWithoutPrefixIfPrefixIsSetEmpty() {
        int parentId = com.dbs.components.test.R.id.dbs_label_view_with_attributes_without_prefix;
        DBSLabel dbsLabel = testLayout.findViewById(parentId);

        dbsLabel.setPrefix("");
        getChildView(R.id.prefix, parentId).check(matches(not(isDisplayed())));
    }

    @Test
    public void shouldDisplayLabelWithoutDescriptionIfIsSetEmpty() {
        int parentId = com.dbs.components.test.R.id.dbs_label_view_with_attributes_without_desc;
        DBSLabel dbsLabel = testLayout.findViewById(parentId);

        dbsLabel.setDescription("");
        getChildView(R.id.desc, parentId).check(matches(not(isDisplayed())));
    }

    @Test
    public void shouldDisplayLabelSetWithValidPrefix() {
        int parentId = com.dbs.components.test.R.id.dbs_label_view_with_attributes_without_prefix;
        DBSLabel dbsLabel = testLayout.findViewById(parentId);

        String expectedPrefixText = "prefix";
        dbsLabel.setPrefix(expectedPrefixText);

        getChildView(R.id.prefix, parentId)
                .check(matches(isDisplayed()))
                .check(matches(withText(expectedPrefixText)));
    }

    @Test
    public void shouldDisplayLabelSetWithValidDescription() {
        int parentId = com.dbs.components.test.R.id.dbs_label_view_with_attributes_without_desc;
        DBSLabel dbsLabel = testLayout.findViewById(parentId);

        String expectedDescriptionText = "Description";
        dbsLabel.setDescription(expectedDescriptionText);

        getChildView(R.id.desc, parentId)
                .check(matches(isDisplayed()))
                .check(matches(withText(expectedDescriptionText)));
    }

    @Test
    public void shouldDisplayDefaultColorsIfCustomStyleNotSet() {
        DBSLabel dbsLabel = testLayout.findViewById(com.dbs.components.test.R.id.dabs_label_with_all_attributes);

        int expectedDefaultTitleColor = ContextCompat.getColor(dbsLabel.getContext(), R.color.default_dbslabel_color);

        TextView textView = dbsLabel.findViewById(R.id.prefix);

        assertEquals(expectedDefaultTitleColor, textView.getCurrentTextColor());

        textView = dbsLabel.findViewById(R.id.value);

        assertEquals(expectedDefaultTitleColor, textView.getCurrentTextColor());

        textView = dbsLabel.findViewById(R.id.desc);

        assertEquals(expectedDefaultTitleColor, textView.getCurrentTextColor());

        expectedDefaultTitleColor = ContextCompat.getColor(dbsLabel.getContext(), R.color.default_dbslabeltitle_color);

        textView = dbsLabel.findViewById(R.id.title);

        assertEquals(expectedDefaultTitleColor, textView.getCurrentTextColor());
    }

    @Test
    public void shouldDisplayLabelWhenTitleIsSet() {
        DBSLabel dbsLabel = new DBSLabel(rule.getActivity(), null);

        TextView titleText = dbsLabel.findViewById(R.id.title);
        String expectedTitleText = "MyTitle";
        dbsLabel.setTitle(expectedTitleText);
        assertEquals(expectedTitleText, titleText.getText().toString());
    }

    @Test
    public void shouldDisplayLabelWhenValueIsSet() {
        DBSLabel dbsLabel = new DBSLabel(rule.getActivity(), null, 0);

        TextView valueText = dbsLabel.findViewById(R.id.value);
        String expectedValueText = "MyLabel";
        dbsLabel.setValue(expectedValueText);
        assertEquals(expectedValueText, valueText.getText().toString());
    }

    @Test
    public void shouldDisplayLabelWithStyles() {
        DBSLabel dbsLabel = new DBSLabel(rule.getActivity());
        assertPrefixStyle(dbsLabel);
        assertValueStyle(dbsLabel);
        assertTitleStyle(dbsLabel);
        assertDescriptionStyle(dbsLabel);
    }

    @Test
    public void shouldCreateLabelWithoutAttributes() {
        DBSLabel dbsLabel = testLayout.findViewById(com.dbs.components.test.R.id.dbs_label_view_without_attributes);

        assertNotNull(dbsLabel.findViewById(R.id.prefix));
        assertNotNull(dbsLabel.findViewById(R.id.title));
        assertNotNull(dbsLabel.findViewById(R.id.value));
        assertNotNull(dbsLabel.findViewById(R.id.desc));
    }

    private void assertTitleStyle(DBSLabel dbsLabel) {
        int expectedColor = ContextCompat.getColor(dbsLabel.getContext(), R.color.sg_primary);

        TextView titleText = dbsLabel.findViewById(R.id.title);

        assertNotEquals(expectedColor, titleText.getCurrentTextColor());

        dbsLabel.setTitleStyle(com.dbs.components.test.R.style.DBSLabel);

        assertEquals(expectedColor, titleText.getCurrentTextColor());
    }

    private void assertValueStyle(DBSLabel dbsLabel) {
        int expectedColor = ContextCompat.getColor(dbsLabel.getContext(), R.color.sg_primary);

        TextView valueText = dbsLabel.findViewById(R.id.value);

        assertNotEquals(expectedColor, valueText.getCurrentTextColor());

        dbsLabel.setValueStyle(com.dbs.components.test.R.style.DBSLabel);

        assertEquals(expectedColor, valueText.getCurrentTextColor());
    }

    private void assertPrefixStyle(DBSLabel dbsLabel) {
        int expectedColor = ContextCompat.getColor(dbsLabel.getContext(), R.color.sg_primary);

        TextView prefixText = dbsLabel.findViewById(R.id.prefix);

        assertNotEquals(expectedColor, prefixText.getCurrentTextColor());

        dbsLabel.setPrefixStyle(com.dbs.components.test.R.style.DBSLabel);

        assertEquals(expectedColor, prefixText.getCurrentTextColor());
    }

    private void assertDescriptionStyle(DBSLabel dbsLabel) {
        int expectedColor = ContextCompat.getColor(dbsLabel.getContext(), R.color.sg_primary);

        TextView descText = dbsLabel.findViewById(R.id.desc);

        assertNotEquals(expectedColor, descText.getCurrentTextColor());

        dbsLabel.setDescriptionStyle(com.dbs.components.test.R.style.DBSLabel);

        assertEquals(expectedColor, descText.getCurrentTextColor());
    }

    private ViewInteraction getChildView(int child, int parent) {
        return onView(allOf(
                withId(child),
                isDescendantOfA(allOf(withId(-1), isDescendantOfA(withId(parent))))));
    }
}
