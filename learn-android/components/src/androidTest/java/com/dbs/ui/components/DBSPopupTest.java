package com.dbs.ui.components;

import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.dbs.components.R;
import com.dbs.ui.BaseView;
import com.dbs.ui.components.testRule.ViewTestRule;
import com.dbs.ui.models.DBSAccount;
import com.dbs.ui.models.DBSCurrency;
import com.dbs.ui.utils.DBSPopup;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.dbs.ui.components.Matchers.RecyclerViewMatcher.nthChildOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class DBSPopupTest {
    DBSPopup dbsPopup;
    DBSPopup dbsPopupWithView;
    View popupView;
    @Rule
    public ViewTestRule<BaseView> viewTestRule = new ViewTestRule<>(com.dbs.components.test.R.layout.dbsbadge_view_test);
    @Before
    public void setUp() {
        dbsPopup = DBSPopup.createCancelableDialog(com.dbs.components.test.R.layout.dialog_enter_password);
        popupView = View.inflate(viewTestRule.getActivity(), com.dbs.components.test.R.layout.dialog_enter_password, null);
        dbsPopupWithView = DBSPopup.createCancelableDialog(popupView);
    }
    @Test
    public void shouldShowDialog() {
        FragmentManager fm = viewTestRule.getActivity().getSupportFragmentManager();
        dbsPopup.show(fm, "DBSPopup");
        onView(withText("Enter digibank password")).inRoot(isDialog()).check(matches(isDisplayed()));
    }
    @Test
    public void shouldShowInflatedViewInDialog() {
        FragmentManager fm = viewTestRule.getActivity().getSupportFragmentManager();
        dbsPopupWithView.show(fm, "DBSPopup");
        onView(withText("Enter digibank password")).inRoot(isDialog()).check(matches(isDisplayed()));
    }
    @Test
    public void shouldAbleToCallOnCloseOKListener() {
        FragmentManager fm = viewTestRule.getActivity().getSupportFragmentManager();
        DBSPopup.OnPopupCloseListener mockListener = mock(DBSPopup.OnPopupCloseListener.class);
        dbsPopup.setOnPopupCloseListener(mockListener);
        dbsPopup.show(fm, "DBSPopup");
        onView(withId(R.id.dbs_popup_button_done)).perform(click());
        verify(mockListener, times(1)).positiveOnclick(Mockito.any(DBSPopup.class));
    }@Test
    public void shouldAbleToCallOnCloseCancelListener() {
        FragmentManager fm = viewTestRule.getActivity().getSupportFragmentManager();
        DBSPopup.OnPopupCloseListener mockListener = mock(DBSPopup.OnPopupCloseListener.class);
        dbsPopupWithView.setOnPopupCloseListener(mockListener);
        dbsPopupWithView.show(fm, "DBSPopup");
        onView(withId(R.id.dbs_popup_button_cancel)).perform(click());
        verify(mockListener, times(1)).negativeOnclick(Mockito.any(DBSPopup.class));
    }
}
