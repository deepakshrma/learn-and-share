package com.dbs.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;

import com.dbs.components.R;
import com.dbs.ui.components.testRule.ViewTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.dbs.ui.ViewAssertions.assertAbove;
import static com.dbs.ui.ViewAssertions.assertRight;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.not;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class DBSPageHeaderViewTest {
    @Rule
    public ViewTestRule<LinearLayout> rule = new ViewTestRule<>(com.dbs.components.test.R.layout.dbs_page_header_view_test1);
    private Context context;

    @Before
    public void setUp(){
        context = rule.getActivity();
    }


    @Test
    public void shouldGetRespectiveFields(){
        LinearLayout allHeaders = rule.getView();
        DBSPageHeaderView titleSubtitleAndImageHeader = allHeaders.findViewById(com.dbs.components.test.R.id.dbs_page_header_with_image_top_with_title_and_subtitle);
        DBSPageHeaderView progressHeaderBar = allHeaders.findViewById(com.dbs.components.test.R.id.dbs_page_header_with_progressbar);

        assertSame(titleSubtitleAndImageHeader.findViewById(R.id.title), titleSubtitleAndImageHeader.getTitle());
        assertSame(titleSubtitleAndImageHeader.findViewById(R.id.subtitle), titleSubtitleAndImageHeader.getSubTitle());
        assertSame(titleSubtitleAndImageHeader.findViewById(R.id.image), titleSubtitleAndImageHeader.getHeaderImage());

        assertSame(progressHeaderBar.findViewById(R.id.progressbar), progressHeaderBar.getProgressBar());

        rule.updateActivityToNewLayout(com.dbs.components.test.R.layout.dbs_page_header_view_test2);
        allHeaders = rule.getView();

        DBSPageHeaderView HeaderWithCta = allHeaders.findViewById(com.dbs.components.test.R.id.dbs_page_header_with_cta);
        assertSame(HeaderWithCta.findViewById(R.id.cta), HeaderWithCta.getCta());


    }

    @Test
    public void whenTitleIsTheOnlyThingConfigured_shouldDisplayTitleOnly() {
        DBSPageHeaderView pageHeaderView = rule.getView().findViewById(com.dbs.components.test.R.id.dbs_page_header_with_title_only);
        AppCompatTextView title = pageHeaderView.findViewById(R.id.title);

        TypedArray typedArray = context.obtainStyledAttributes(R.style.DBSHeader_H3, new int[]{android.R.attr.textSize, android.R.attr.textColor});
        float expectedTextSize = typedArray.getDimension(0, 0);
        int expectedTextColor = typedArray.getColor(1, -1);
        //could not test font face
        onView(allOf(isDescendantOfA(withId(com.dbs.components.test.R.id.dbs_page_header_with_title_only)), withId(R.id.title))).check((matches(allOf((isDisplayed()), withText("testTitle")))));
        assertEquals(expectedTextSize , title.getTextSize());
        assertEquals(expectedTextColor , title.getCurrentTextColor());

        onView(allOf(isDescendantOfA(withId(com.dbs.components.test.R.id.dbs_page_header_with_title_only)), withId(R.id.subtitle))).check((matches(not(isDisplayed()))));
        onView(allOf(isDescendantOfA(withId(com.dbs.components.test.R.id.dbs_page_header_with_title_only)), withId(R.id.progressbar))).check((matches(not(isDisplayed()))));
        onView(allOf(isDescendantOfA(withId(com.dbs.components.test.R.id.dbs_page_header_with_title_only)), withId(R.id.image))).check((matches(not(isDisplayed()))));
    }

    @Test
    public void whenSubTitleIsTheOnlyThingConfigured_shouldDisplaySubTitleOnly() {
        rule.updateActivityToNewLayout(com.dbs.components.test.R.layout.dbs_page_header_view_test2);
        DBSPageHeaderView pageHeaderView = rule.getView().findViewById(com.dbs.components.test.R.id.dbs_page_header_with_subtitle_only);

        TypedArray typedArray = context.obtainStyledAttributes(R.style.DBSHeader_H5, new int[]{android.R.attr.textSize, android.R.attr.textColor});
        float expectedTextSize = typedArray.getDimension(0, 0);
        int expectedTextColor = typedArray.getColor(1, -1);
        //could not test font face
        AppCompatTextView subTitle = pageHeaderView.findViewById(R.id.subtitle);

        onView(allOf(isDescendantOfA(withId(com.dbs.components.test.R.id.dbs_page_header_with_subtitle_only)), withId(R.id.subtitle))).check((matches(allOf((isDisplayed()), withText("testSubTitle")))));
        // TODO: need to fix
        // assertEquals(expectedTextSize , subTitle.getTextSize());
        assertEquals(expectedTextColor , subTitle.getCurrentTextColor());

        onView(allOf(isDescendantOfA(withId(com.dbs.components.test.R.id.dbs_page_header_with_subtitle_only)), withId(R.id.title))).check((matches(not(isDisplayed()))));
        onView(allOf(isDescendantOfA(withId(com.dbs.components.test.R.id.dbs_page_header_with_subtitle_only)), withId(R.id.progressbar))).check((matches(not(isDisplayed()))));
        onView(allOf(isDescendantOfA(withId(com.dbs.components.test.R.id.dbs_page_header_with_subtitle_only)), withId(R.id.image))).check((matches(not(isDisplayed()))));
    }

    @Test
    public void whenSubTitleAndTitleArePresent_titleShouldBeAboveSubtitle() {
        onView(allOf(isDescendantOfA(withId(com.dbs.components.test.R.id.dbs_page_header_with_title_and_subtitle_only)), withId(R.id.subtitle))).check((matches(allOf((isDisplayed()), withText("testSubTitle")))));
        onView(allOf(isDescendantOfA(withId(com.dbs.components.test.R.id.dbs_page_header_with_title_and_subtitle_only)), withId(R.id.title))).check((matches(allOf((isDisplayed()), withText("testTitle")))));

        onView(allOf(isDescendantOfA(withId(com.dbs.components.test.R.id.dbs_page_header_with_title_and_subtitle_only)), withId(R.id.progressbar))).check((matches(not(isDisplayed()))));
        onView(allOf(isDescendantOfA(withId(com.dbs.components.test.R.id.dbs_page_header_with_title_and_subtitle_only)), withId(R.id.image))).check((matches(not(isDisplayed()))));

        DBSPageHeaderView pageHeaderView = rule.getView().findViewById(com.dbs.components.test.R.id.dbs_page_header_with_title_and_subtitle_only);
        assertAbove(pageHeaderView.findViewById(R.id.title), pageHeaderView.findViewById(R.id.subtitle));
    }

    @Test
    public void whenProgressBarIsConfigured_itShouldBeDisplayed() {
        DBSPageHeaderView pageHeaderView = rule.getView().findViewById(com.dbs.components.test.R.id.dbs_page_header_with_progressbar);
        onView(allOf(isDescendantOfA(withId(com.dbs.components.test.R.id.dbs_page_header_with_progressbar)), withId(R.id.progressbar))).check(matches(isDisplayed()));
        assertEquals(12, pageHeaderView.getProgress());
    }

    @Test
    public void whenImageIsConfiguredTopWithTitleAndSubTitle_itShouldBeDisplayedAboveTitle() {
        DBSPageHeaderView pageHeaderView = rule.getView().findViewById(com.dbs.components.test.R.id.dbs_page_header_with_image_top_with_title_and_subtitle);
        onView(allOf(isDescendantOfA(withId(com.dbs.components.test.R.id.dbs_page_header_with_image_top_with_title_and_subtitle)), withId(R.id.image))).check(matches(isDisplayed()));

        assertAbove(pageHeaderView.findViewById(R.id.image), pageHeaderView.findViewById(R.id.title));
        assertAbove(pageHeaderView.findViewById(R.id.title), pageHeaderView.findViewById(R.id.subtitle));

        onView(allOf(isDescendantOfA(withId(com.dbs.components.test.R.id.dbs_page_header_with_image_top_with_title_and_subtitle)), withId(R.id.title))).check((matches(allOf(isDisplayed(), withText("testTitleWithImage")))));
        onView(allOf(isDescendantOfA(withId(com.dbs.components.test.R.id.dbs_page_header_with_image_top_with_title_and_subtitle)), withId(R.id.subtitle))).check((matches(allOf(isDisplayed(), withText("testSubTitleWithImage")))));
    }


    @Test
    public void whenImageIsConfiguredBottom_itShouldBeDisplayedBelowTitle() {
        rule.updateActivityToNewLayout(com.dbs.components.test.R.layout.dbs_page_header_view_test2);
        DBSPageHeaderView pageHeaderView = rule.getView().findViewById(com.dbs.components.test.R.id.dbs_page_header_with_image_bottom);
        onView(allOf(isDescendantOfA(withId(com.dbs.components.test.R.id.dbs_page_header_with_image_bottom)), withId(R.id.image))).check(matches(isDisplayed()));

        assertAbove(pageHeaderView.findViewById(R.id.subtitle), pageHeaderView.findViewById(R.id.image));
        assertAbove(pageHeaderView.findViewById(R.id.title), pageHeaderView.findViewById(R.id.subtitle));

        onView(allOf(isDescendantOfA(withId(com.dbs.components.test.R.id.dbs_page_header_with_image_bottom)), withId(R.id.title))).check((matches(allOf((isDisplayed()), withText("testTitleWithImage")))));
        onView(allOf(isDescendantOfA(withId(com.dbs.components.test.R.id.dbs_page_header_with_image_bottom)), withId(R.id.subtitle))).check((matches(allOf((isDisplayed()), withText("testSubtitleWithImage")))));
    }

    @Test
    public void whenImageIsConfiguredRight_itShouldBeShownBottomRight() {
        rule.updateActivityToNewLayout(com.dbs.components.test.R.layout.dbs_page_header_view_test2);

        DBSPageHeaderView pageHeaderView = rule.getView().findViewById(com.dbs.components.test.R.id.dbs_page_header_with_image_right);
        onView(allOf(isDescendantOfA(withId(com.dbs.components.test.R.id.dbs_page_header_with_image_right)), withId(R.id.image))).check(matches(isDisplayed()));

        assertAbove(pageHeaderView.findViewById(R.id.title), pageHeaderView.findViewById(R.id.image));
        assertAbove(pageHeaderView.findViewById(R.id.title), pageHeaderView.findViewById(R.id.subtitle));
        assertRight(pageHeaderView.findViewById(R.id.image), pageHeaderView.findViewById(R.id.subtitle));

        onView(allOf(isDescendantOfA(withId(com.dbs.components.test.R.id.dbs_page_header_with_image_right)), withId(R.id.title))).check((matches(allOf((isDisplayed()), withText("testTitleWithImage")))));
        onView(allOf(isDescendantOfA(withId(com.dbs.components.test.R.id.dbs_page_header_with_image_right)), withId(R.id.subtitle))).check((matches(allOf((isDisplayed()), withText("testSubtitleWithImage")))));
    }

    @Test
    public void whenCTAIsConfigured_CTAShouldBePresentAtEnd() {
        rule.updateActivityToNewLayout(com.dbs.components.test.R.layout.dbs_page_header_view_test2);

        DBSPageHeaderView pageHeaderView = rule.getView().findViewById(com.dbs.components.test.R.id.dbs_page_header_with_cta);
        onView(allOf(isDescendantOfA(withId(com.dbs.components.test.R.id.dbs_page_header_with_cta)), withId(R.id.cta))).check(matches(allOf(isDisplayed(), withText("Yolo"))));

        assertAbove(pageHeaderView.findViewById(R.id.image), pageHeaderView.findViewById(R.id.title));
        assertAbove(pageHeaderView.findViewById(R.id.title), pageHeaderView.findViewById(R.id.cta));

        onView(allOf(isDescendantOfA(withId(com.dbs.components.test.R.id.dbs_page_header_with_cta)), withId(R.id.title))).check((matches(allOf((isDisplayed()), withText("testTitleWithCta")))));
    }

    @Test
    public void whenCTAIsConfigured_shouldBeAbleToSetCta() {
        rule.updateActivityToNewLayout(com.dbs.components.test.R.layout.dbs_page_header_view_test2);

        DBSPageHeaderView pageHeaderView = rule.getView().findViewById(com.dbs.components.test.R.id.dbs_page_header_with_cta);
        View.OnClickListener mockOnCta = Mockito.mock(View.OnClickListener.class);
        pageHeaderView.setOnCtaClicked(mockOnCta);

        onView(allOf(isDescendantOfA(withId(com.dbs.components.test.R.id.dbs_page_header_with_cta)), withId(R.id.cta))).perform(click());
        verify(mockOnCta, times(1)).onClick(any(View.class));
    }
}