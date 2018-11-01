package com.dbs.ui.components;

import android.support.test.runner.AndroidJUnit4;
import android.view.LayoutInflater;
import android.view.View;

import com.dbs.components.R;
import com.dbs.ui.components.testRule.ViewTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class DBSExpandableCardTest {

    @Rule
    public ViewTestRule<DBSExpandableCard> rule = new ViewTestRule<>(com.dbs.components.test.R.layout.expandable_card_view);

    private DBSExpandableCard expandableCard;
    private View headerView;
    private View containerView;
    private View footerView;

    @Before
    public void setUp() {
        LayoutInflater inflater = LayoutInflater.from(rule.getActivity());
        expandableCard = rule.getView();
        headerView = inflater.inflate(com.dbs.components.test.R.layout.expandable_card_row, null);
        containerView = inflater.inflate(com.dbs.components.test.R.layout.expandable_card_row, null);
        footerView = inflater.inflate(com.dbs.components.test.R.layout.expandable_card_footer, null);
    }

    @Test
    public void shouldReturnLayoutId() {
        int layoutId = expandableCard.provideLayoutId("");

        assertEquals(R.layout.expandable_card, layoutId);
    }

    @Test
    public void shouldSetHeader() {
        assertNull(expandableCard.getHeader());

        rule.runOnMainSynchronously(view -> expandableCard.setHeader(headerView));

        assertEquals(headerView, expandableCard.getHeader());
    }

    @Test
    public void shouldSetContainer() {
        assertNull(expandableCard.getContainer());

        rule.runOnMainSynchronously(view -> expandableCard.setContainer(containerView));

        assertEquals(containerView, expandableCard.getContainer());
    }

    @Test
    public void shouldSetFooter() {
        assertNull(expandableCard.getFooter());

        rule.runOnMainSynchronously(view -> expandableCard.setFooter(footerView));

        assertEquals(footerView, expandableCard.getFooter());
    }

    @Test
    public void shouldHaveDefaultToggleBehaviorOfTogglingContainerVisibility() {
        rule.runOnMainSynchronously(view -> {
            expandableCard.setContainer(containerView);
            expandableCard.setFooter(footerView);
            expandableCard.setTogglerPane(DBSExpandableCard.TogglerPane.FOOTER);
            expandableCard.setTogglerOn(com.dbs.components.test.R.id.expandable_footer_test_text);
        });

        onView(withId(com.dbs.components.test.R.id.expandable_card_container))
                .check(matches(not(isDisplayed())));

        onView(withId(com.dbs.components.test.R.id.expandable_footer_test_text))
                .perform(click());

        onView(withId(com.dbs.components.test.R.id.expandable_card_container))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldBeAbleToCustomizeToggleBehavior() {
        DBSExpandableCard.ExpandableCardToggleListener mockListener = mock(DBSExpandableCard.ExpandableCardToggleListener.class);
        rule.runOnMainSynchronously(view -> {
            expandableCard.setFooter(footerView);
            expandableCard.setTogglerPane(DBSExpandableCard.TogglerPane.FOOTER);
            expandableCard.setToggler(com.dbs.components.test.R.id.expandable_footer_test_text, mockListener);
        });

        onView(withId(com.dbs.components.test.R.id.expandable_footer_test_text)).perform(click());

        verify(mockListener, times(1)).onToggle(true);
    }

    @Test
    public void shouldBeAbleToExpandCollapseCard() {
        rule.runOnMainSynchronously(view -> {
            expandableCard.setContainer(containerView);
            expandableCard.setHeader(headerView);
            expandableCard.setTogglerOn(R.id.expandable_card_header);
        });

        onView(withId(com.dbs.components.test.R.id.expandable_card_container)).check(matches(not(isDisplayed())));
        rule.runOnMainSynchronously(view -> expandableCard.setExpanded(true));
        onView(withId(com.dbs.components.test.R.id.expandable_card_container)).check(matches(isDisplayed()));
    }
}