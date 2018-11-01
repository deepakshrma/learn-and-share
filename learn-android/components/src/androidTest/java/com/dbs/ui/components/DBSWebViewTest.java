package com.dbs.ui.components;

import android.support.test.runner.AndroidJUnit4;
import android.widget.LinearLayout;

import com.dbs.components.test.R;
import com.dbs.ui.components.testRule.ViewTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
public class DBSWebViewTest {
    @Rule
    public ViewTestRule<LinearLayout> viewTestRule = new ViewTestRule<>(com.dbs.components.test.R.layout.dbs_web_view_test);

    @Test
    public void shouldLoadUrl() {
        DBSWebView dbsWebView = viewTestRule.getActivity().findViewById(R.id.web_view);
        viewTestRule.runOnMainSynchronously(v -> {
            dbsWebView.load(viewTestRule.getActivity().getResources().getString(R.string.google_link));
        });
        assertNotEquals(dbsWebView, null);
    }

}
