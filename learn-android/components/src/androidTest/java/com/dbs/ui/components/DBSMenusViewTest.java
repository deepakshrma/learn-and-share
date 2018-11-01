package com.dbs.ui.components;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.dbs.components.test.R;
import com.dbs.ui.components.Matchers.RecyclerViewMatcher;
import com.dbs.ui.components.testRule.ViewTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.dbs.ui.components.Matchers.CustomTestMatcher.withDrawable;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class DBSMenusViewTest {

    @Rule
    public ViewTestRule<DBSMenusView> testRule = new ViewTestRule<DBSMenusView>(R.layout.dbs_menu_view);


    @Test
    public void menuItemViewShouldGiveTheDBSScrollView() throws Throwable {
        DBSMenusView menusView = testRule.getView();
        menusView.setNumberOfVisibleItems(3);
        DBSMenusView.OnMenuItemClickListener itemClickListener = mock(DBSMenusView.OnMenuItemClickListener.class);

        ArrayList<MenuItem> menus = new ArrayList<>();
        menus.add(new MenuItem(1, R.drawable.happy_image, "someText"));
        menus.add(new MenuItem(2, R.drawable.happy_image, "someText"));
        menus.add(new MenuItem(3, R.drawable.happy_image, "someText"));
        menus.add(new MenuItem(4, R.drawable.happy_image, "someText"));
        menus.add(new MenuItem(5, R.drawable.happy_image, "someText"));

        testRule.runOnUiThread(() -> {
            menusView.setOnItemClickListener(itemClickListener);
            menusView.addMenus(menus);
        });

        shouldCallTheListenerWhenClickedOnAnItem(itemClickListener);

        shouldBindMenuItemsWithGivenData();

        visibleNumberOfItemsShouldBeThree(menusView);

        menuShouldHaveFiveItems(menusView);
    }

    private void shouldCallTheListenerWhenClickedOnAnItem(DBSMenusView.OnMenuItemClickListener itemClickListener) {
        onView(withId(com.dbs.components.R.id.dbsRecyclerView))
                .perform(RecyclerViewActions.scrollToPosition(5))
                .perform(RecyclerViewActions.actionOnItemAtPosition(4,
                        click()));

        verify(itemClickListener, times(1)).onItemClick(Mockito.any(View.class), Mockito.any(MenuItem.class));
    }

    private void menuShouldHaveFiveItems(DBSMenusView itemView) {
        int expectedNumberOfItems = 5;
        assertEquals(expectedNumberOfItems, itemView.getMenuAdapter().getItemCount());
    }

    private void visibleNumberOfItemsShouldBeThree(DBSMenusView itemView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) itemView.getDBSRecyclerView().getRecyclerView().getLayoutManager();
        int numberOfItemsVisible = layoutManager.findLastVisibleItemPosition() - layoutManager.findFirstVisibleItemPosition() + 1;
        int expectedVisibleNumberOfItems = 3;
        assertEquals(expectedVisibleNumberOfItems, numberOfItemsVisible);
    }

    private void shouldBindMenuItemsWithGivenData() {
        String expectedLabel = "someText";
        int expectedResourceIcon = R.drawable.happy_image;

        onView((new RecyclerViewMatcher(R.id.dbsRecyclerView)).atPositionOnView(4, R.id.dbsMenuItemTitle))
                .check(matches(withText(expectedLabel)));
        onView((new RecyclerViewMatcher(R.id.dbsRecyclerView)).atPositionOnView(4, R.id.dbsMenuItemIcon))
                .check(matches(withDrawable(expectedResourceIcon)));
    }
}