package com.dbs.ui.components;

import android.graphics.drawable.Drawable;
import android.support.test.runner.AndroidJUnit4;

import com.dbs.components.R;
import com.dbs.ui.components.DBSTransferView.OnClickListener;
import com.dbs.ui.components.testRule.ViewTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.dbs.components.test.R.drawable.placeholder;
import static com.dbs.components.test.R.id.down_arrow1;
import static com.dbs.components.test.R.id.down_arrow2;
import static com.dbs.components.test.R.id.down_arrow3;
import static com.dbs.components.test.R.layout.dbs_transfer_view_layout;
import static com.dbs.ui.components.Matchers.CustomTestMatcher.withDrawable;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class DBSTransferViewTest {
    @Rule
    public ViewTestRule<DBSTransferView> rule = new ViewTestRule<>(dbs_transfer_view_layout);

    @Test
    public void shouldGiveCallbackForSourceAccountWhenSourceAccountViewClicked() {
        OnClickListener onClickListener = mock(OnClickListener.class);
        rule.runOnMainSynchronously(view -> rule.getView().setOnTransferViewClickListener(onClickListener));

        onView(withId(R.id.source_account_details)).perform(click());

        verify(onClickListener, times(1)).onSourceAccountViewClicked(any());
    }

    @Test
    public void shouldGiveCallbackForDestinationAccountWhenDestinationAccountViewClicked() {
        OnClickListener onClickListener = mock(OnClickListener.class);
        rule.runOnMainSynchronously(view -> view.setOnTransferViewClickListener(onClickListener));

        onView(withId(R.id.destination_account_details)).perform(click());

        verify(onClickListener, times(1)).onDestinationAccountViewClicked(any());
    }

    @Test
    public void shouldSetGivenAnimationImage() {
        Drawable drawable = rule.getView().getResources().getDrawable(placeholder);

        rule.runOnMainSynchronously(view -> rule.getView().setAnimationImage(drawable));

        onView(withId(down_arrow1)).check(matches(withDrawable(placeholder)));
        onView(withId(down_arrow2)).check(matches(withDrawable(placeholder)));
        onView(withId(down_arrow3)).check(matches(withDrawable(placeholder)));
    }
}