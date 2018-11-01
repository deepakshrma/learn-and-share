package com.dbs.ui.components.plugins;

import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dbs.components.R;
import com.dbs.ui.components.testRule.ViewTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DBSPluginsViewTest {

    @Rule
    public ViewTestRule<LinearLayout> rule = new ViewTestRule<>(com.dbs.components.test.R.layout.dbs_plugins_view_test);

    @Test
    public void shouldAddPluginToPluginsView() {
        DBSPluginsView pluginsView = rule.getView().findViewById(com.dbs.components.test.R.id.plugins_view_test);
        TextView textView = new TextView(rule.getActivity());
        String testText = "Some text";
        textView.setText(testText);
        rule.runOnMainSynchronously(view -> {
            DBSPlugin plugin = new DBSPlugin() {
                @Override
                public View onCreatePluginView(ViewGroup parent) {
                    return textView;
                }

                @Override
                public DBSPluginType getPluginType() {
                    return DBSPluginType.SMALL;
                }
            };
            pluginsView.addPlugin(plugin);
        });

        onView(withId(R.id.plugin_container)).check((view, noViewFoundException) -> {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }
            int expectedHeight = rule.getActivity().getResources()
                    .getDimensionPixelOffset(R.dimen.plugin_height_small);
            assertEquals(expectedHeight, view.getHeight());
        });
        onView(withText(testText)).check(matches(isDisplayed()));
    }
}