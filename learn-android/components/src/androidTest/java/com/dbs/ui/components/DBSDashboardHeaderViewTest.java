package com.dbs.ui.components;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dbs.components.R;
import com.dbs.ui.components.Matchers.CustomTestMatcher;
import com.dbs.ui.components.testRule.ViewTestRule;
import com.dbs.ui.models.WeatherDetails;
import com.dbs.ui.models.WeatherType;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.dbs.ui.components.Matchers.CustomTestMatcher.withDrawable;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class DBSDashboardHeaderViewTest {
    private static final int HEADER_WITH_BUILDER_ID_SG = 4763274;
    @Rule
    public ViewTestRule<LinearLayout> rule = new ViewTestRule<>(com.dbs.components.test.R.layout.dbs_dashboard_header_test);
    private LinearLayout view;

    @Before
    public void setUp() {
        view = rule.getView();
    }

    @Test
    public void shouldShowHeaderWithAttributes() {
        int headerId = com.dbs.components.test.R.id.headerWithAttributes;
        DBSDashboardHeaderView headerView = view.findViewById(headerId);
        Matcher<View> showMoreButtonMatcher = CustomTestMatcher.getViewMather(R.id.dashboard_header_show_more,
                R.id.header_view, R.id.header_view_container, headerId);
        onView(showMoreButtonMatcher).check(matches(isDisplayed()));
        String temperature = "26º";
        DBSDashboardHeaderView.IListener mockListener = mock(DBSDashboardHeaderView.IListener.class);
        headerView.setHeaderListener(mockListener);

        onView(showMoreButtonMatcher).perform(click());
        verify(mockListener, times(1)).showMore();
        List<WeatherDetails> weatherDetails = new ArrayList<>();
        weatherDetails.add(new WeatherDetails(temperature, WeatherType.BROKEN_CLOUDY, "Hello"));
        headerView.subscribeToWeatherDetails(Observable.fromIterable(weatherDetails));

        onView(CustomTestMatcher.getViewMather(R.id.dashboard_weather, R.id.header_view, R.id.header_view_container, headerId)).check(matches(isDisplayed()));
        int[] weatherViewAncestors = {-1, R.id.dashboard_weather, R.id.header_view, R.id.header_view_container, headerId};
        onView(CustomTestMatcher.getViewMather(R.id.dashboard_weather_icon, weatherViewAncestors)).check(matches(withDrawable(R.drawable.ic_cloudy)));
        onView(CustomTestMatcher.getViewMather(R.id.dashboard_temperature, weatherViewAncestors)).check(matches(withText(temperature)));
        onView(CustomTestMatcher.getViewMather(R.id.dashboard_weather_info, weatherViewAncestors)).check(matches(withText("Broken Cloudy")));
        onView(CustomTestMatcher.getViewMather(R.id.dashboard_greeting_message, R.id.dashboard_weather, R.id.header_view, R.id.header_view_container, headerId)).check(matches(withText(containsString("HI !"))));

        String customMessage = "Custom message";
        rule.runOnMainSynchronously(headerView1 -> {
            headerView.setCustomGreetingMessage(customMessage);
        });
        onView(CustomTestMatcher.getViewMather(R.id.dashboard_greeting_message, R.id.dashboard_weather, R.id.header_view, R.id.header_view_container, headerId)).check(matches(withText(customMessage)));
    }

    @Test
    public void shouldShowHeaderCreatedWithBuilder() {
        String customGreetingMessage = "Custom message";
        DBSDashboardToolbarView.ILeftItemClickListener mockLeftClickListener = mock(DBSDashboardToolbarView.ILeftItemClickListener.class);
        DBSDashboardToolbarView.IRightItemClickListener mockRightClickListener = mock(DBSDashboardToolbarView.IRightItemClickListener.class);

        rule.runOnMainSynchronously(view -> {
            DBSDashboardHeaderView headerView =
                    new DBSDashboardHeaderView.Builder(rule.getActivity(), DBSDashboardHeaderView.Config.SINGAPORE)
                            .withToolbarLeftIconClickListener(mockLeftClickListener)
                            .withToolbarRightIconClickListener(mockRightClickListener)
                            .build();
            headerView.setId(HEADER_WITH_BUILDER_ID_SG);
            headerView.enableShowMoreDetails();
            headerView.setCustomGreetingMessage(customGreetingMessage);
            headerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.addView(headerView);
            List<WeatherDetails> weatherDetails = new ArrayList<>();
            weatherDetails.add(new WeatherDetails("22º", WeatherType.BROKEN_CLOUDY, "Hello"));
            headerView.subscribeToWeatherDetails(Observable.fromIterable(weatherDetails));
        });

        Matcher<View> showMoreButtonMatcher = CustomTestMatcher.getViewMather(R.id.dashboard_header_show_more,
                R.id.header_view, R.id.header_view_container, HEADER_WITH_BUILDER_ID_SG);
        onView(showMoreButtonMatcher).check(matches(isDisplayed()));
        Matcher<View> greetingMessageMatcher = CustomTestMatcher.getViewMather(R.id.dashboard_greeting_message,
                R.id.dashboard_weather, R.id.header_view, R.id.header_view_container, HEADER_WITH_BUILDER_ID_SG);
        onView(greetingMessageMatcher)
                .check(matches(withText(customGreetingMessage)));
        Matcher<View> backgroundImageViewMatcherSg = CustomTestMatcher.getViewMather(R.id.dashboard_header_background_image_view,
                R.id.header_view, R.id.header_view_container, HEADER_WITH_BUILDER_ID_SG);
        onView(backgroundImageViewMatcherSg).check(matches(withDrawable(R.drawable.dashboard_header_background_sg)));

        onView(withId(R.id.leftNavigationItem))
                .check(matches(withDrawable(R.drawable.ic_bell)))
                .perform(click())
                .check((view, noViewFoundException) -> verify(mockLeftClickListener).onLeftItemClick());
        onView(withId(R.id.rightNavigationItem))
                .check(matches(withDrawable(R.drawable.ic_chatbot)))
                .perform(click())
                .check((view, noViewFoundException) -> verify(mockRightClickListener).onRightItemClick());
    }
}