/*
 * - Licensed Materials - Property of DBS Bank SG
 * - "Restricted Materials of DBS Bank"
 *
 * APP Studio SDK, Copyright (c) 2018. DBS Bank SG
 *
 * Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *    - Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *
 *    - Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 */

package com.dbs.ui.components;

import android.support.test.runner.AndroidJUnit4;
import android.support.text.emoji.EmojiCompat;
import android.support.text.emoji.bundled.BundledEmojiCompatConfig;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.dbs.components.R;
import com.dbs.ui.components.ac.DBSAccountListView;
import com.dbs.ui.components.testRule.ViewTestRule;
import com.dbs.ui.models.DBSAccount;
import com.dbs.ui.models.DBSCurrency;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.v7.widget.RecyclerView.NO_POSITION;
import static com.dbs.ui.components.Matchers.RecyclerViewMatcher.nthChildOf;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by deepak on 1/10/18.
 */

@RunWith(AndroidJUnit4.class)
public class DBSAccountListViewTest {
    @Rule
    public ViewTestRule<LinearLayout> viewTestRule = new ViewTestRule<>(com.dbs.components.test.R.layout.account_list_view_test);
    private DBSAccountListView accountListView;
    List<DBSAccount> accounts;

    @Before
    public void setUp() {
        accountListView = viewTestRule.getView().findViewById(com.dbs.components.test.R.id.acc_list_view);
        EmojiCompat.Config config = new BundledEmojiCompatConfig(viewTestRule.getActivity()).setEmojiSpanIndicatorEnabled(true);
        EmojiCompat.init(config);

        accounts = new ArrayList<>();
        accounts.add(new DBSAccount("DBS Multi Curr Saving", "1-999999-111", "$23.33", "SGD"));
        DBSAccount account = new DBSAccount("Multi Curry Saving", "1-121212-111", "$2332.33", "HKD");
        account.setCurrencies(new DBSCurrency("HKD", "2323.02", true), new DBSCurrency("INR", "2323"));
        accounts.add(account);
        accounts.add(new DBSAccount("Saving Account", "1-21121-111", "$23sss.33", "SGD"));

    }

    @Test
    public void shouldHaveRecycleView() {
        onView(withId(com.dbs.components.R.id.recyclerView)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldAbleToAddAccount() {
        RecyclerView recyclerView = viewTestRule.getView().findViewById(com.dbs.components.R.id.recyclerView);
        LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
        int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
        assertEquals(firstVisiblePosition, NO_POSITION);
        assertNotNull(recyclerView);
        accountListView.updateAccounts(accounts);
        assertEquals(recyclerView.getAdapter().getItemCount(), 3);
        onView(nthChildOf(withId(R.id.recyclerView), 0)).check(matches(hasDescendant(withText("DBS Multi Curr Saving"))));
        onView(nthChildOf(withId(R.id.recyclerView), 1)).check(matches(hasDescendant(withText("Multi Curry Saving"))));
        onView(nthChildOf(withId(R.id.recyclerView), 2)).check(matches(hasDescendant(withText("Saving Account"))));
    }

    @Test
    public void shouldAbleToClickAndExpend() {
        RecyclerView recyclerView = viewTestRule.getView().findViewById(com.dbs.components.R.id.recyclerView);
        viewTestRule.runOnMainSynchronously(v -> {
            accountListView.updateAccounts(accounts);
        });
        assertEquals(recyclerView.getAdapter().getItemCount(), 3);
        onView(nthChildOf(withId(R.id.recyclerView), 1)).perform(click());
        assertEquals(recyclerView.getAdapter().getItemCount(), 5);
//        onView(nthChildOf(withId(R.id.recyclerView), 3)).check(matches(withText(containsString("HKD"))));

    }

    @Test
    public void shouldAbleOnSelectCheckCallbackCalled() {
        RecyclerView recyclerView = viewTestRule.getView().findViewById(com.dbs.components.R.id.recyclerView);
        viewTestRule.runOnMainSynchronously(v -> {
            accountListView.updateAccounts(accounts);
        });
        DBSAccountListView.OnAccountSelectListener mockListener = mock(DBSAccountListView.OnAccountSelectListener.class);
        accountListView.setOnAccountSelectLister(mockListener);
        assertEquals(recyclerView.getAdapter().getItemCount(), 3);
        onView(nthChildOf(withId(R.id.recyclerView), 1)).perform(click());
        onView(nthChildOf(withId(R.id.recyclerView), 3))
                .perform(click())
                .check((view, noViewFoundException) -> verify(mockListener, times(1)).onAccountSelect(Mockito.any(DBSAccount.class), Mockito.any(DBSCurrency.class)));
        assertEquals(recyclerView.getAdapter().getItemCount(), 5);
    }
}
