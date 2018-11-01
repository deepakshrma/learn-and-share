package com.dbs.ui.components;

import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.BaseTransientBottomBar.BaseCallback;
import android.support.design.widget.Snackbar;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;

import com.dbs.components.test.R;
import com.dbs.ui.Await;
import com.dbs.ui.components.testRule.ViewTestRule;
import com.dbs.ui.utils.DBSSnackBarHelper;
import com.dbs.ui.utils.DBSSnackBarHelper.Builder;
import com.dbs.ui.utils.ThemeUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasTextColor;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class DBSSnackBarHelperTest {
    private Builder snackBarBuilder;
    private String textMessage = "Some has been done, need to be undone...x";
    private String actionText = "Ok";
    private boolean snackbarShown = false;
    private int expectedBackgroundThemeColor;

    @Rule
    public ViewTestRule<View> viewTestRule =
            new ViewTestRule<>(R.layout.snack_bar_test);

    private BaseCallback<Snackbar> snackbarBaseCallback = new BaseTransientBottomBar.BaseCallback<Snackbar>() {
        @Override
        public void onShown(Snackbar transientBottomBar) {
            snackbarShown = true;
        }
    };

    @Before
    public void createSnackbarBuilder() {
        snackbarShown = false;
        snackBarBuilder = new Builder(viewTestRule.getView(), textMessage);
        expectedBackgroundThemeColor = ThemeUtils.getThemeColor(viewTestRule.getActivity(), R.attr.colorPrimary);
    }

    @Test
    public void shouldShowSnackBarWithGivenTextMessage() throws Exception {
        Snackbar snackbar = snackBarBuilder.build();
        snackbar.addCallback(snackbarBaseCallback);
        snackbar.show();

        Await.await(() -> snackbarShown, 1000);

        onView(withText(textMessage)).check(matches(isDisplayed()));
    }

    @Test
    public void createdSnackBarShouldHaveDefaultDurationAsLENGTH_LONG() {
        Snackbar defaultSnackbar = snackBarBuilder.build();

        assertEquals(Snackbar.LENGTH_LONG, defaultSnackbar.getDuration());
    }

    @Test
    public void shouldBeAbleToCreateSnackbarWithCustomDuration() {
        Snackbar snackbar = snackBarBuilder.withDuration(Snackbar.LENGTH_SHORT).build();

        assertEquals(Snackbar.LENGTH_SHORT, snackbar.getDuration());
    }

    @Test
    public void shouldBeAbleToCreateSnackbarWithCustomBackgroundColor() {
        Snackbar snackbar = snackBarBuilder.withBackgroundColor(R.color.india_color_black_4).build();

        int actualBackgroundColor = ((ColorDrawable) snackbar.getView().getBackground()).getColor();
        assertEquals(R.color.india_color_black_4, actualBackgroundColor);
    }

    @Test
    public void shouldShowSnackbarWithCustomMessageColor() throws Exception {
        Snackbar snackbar = snackBarBuilder.withMessageColor(getColor(R.color.india_color_black_4)).build();
        snackbar.addCallback(snackbarBaseCallback);
        snackbar.show();

        Await.await(() -> snackbarShown, 1000);

        onView(withText(textMessage)).check(matches(hasTextColor(R.color.india_color_black_4)));
    }

    @Test
    public void shouldShowActionTextOnSnackbar() throws Exception {
        Snackbar snackbar = snackBarBuilder.withActionable(actionText, view -> {}).build();
        snackbar.addCallback(snackbarBaseCallback);
        snackbar.show();

        Await.await(() -> snackbarShown, 1000);

        onView(withText(actionText)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldShowActionTextOnSnackbarWithCustomActionTextColor() throws Exception {
        Snackbar snackbar = snackBarBuilder.withActionable(actionText, view -> {})
                .withActionTextColor(getColor(R.color.india_color_primary))
                .build();
        snackbar.addCallback(snackbarBaseCallback);
        snackbar.show();

        Await.await(() -> snackbarShown, 1000);

        onView(withText(actionText)).check(matches(hasTextColor(R.color.india_color_primary)));
    }

    @Test
    public void OnClickingActionText_shouldCallActionListener() throws Exception {
        OnClickListener mockedOnclickListener = Mockito.mock(OnClickListener.class);

        Snackbar snackbar = snackBarBuilder.withActionable(actionText, mockedOnclickListener).build();
        snackbar.addCallback(snackbarBaseCallback);
        snackbar.show();

        Await.await(() -> snackbarShown, 1000);

        onView(withText(actionText)).perform(click());
        verify(mockedOnclickListener, times(1)).onClick(any(View.class));
    }

    @Test
    public void shouldBuildSnackbarWithViewAndShowWithDefaultBackgroundAndActionTextColors() throws Exception {
        Snackbar defaultSnackbar = new Builder(viewTestRule.getView(), textMessage)
                .withActionable(actionText, view -> {})
                .build();
        defaultSnackbar.addCallback(snackbarBaseCallback);
        defaultSnackbar.show();

        Await.await(() -> snackbarShown, 1000);

        int actualColor = ((ColorDrawable) defaultSnackbar.getView().getBackground()).getColor();

        assertEquals(expectedBackgroundThemeColor, actualColor);
        onView(withText(actionText)).check(matches(hasTextColor(R.color.white)));
    }

    @Test
    public void shouldBuildSnackbarWithViewAndShowWithDefaultMessageTextColor() throws Exception {
        viewTestRule.getActivity().setTheme(R.style.DBSBase_NoActionBar_India);
        Snackbar defaultSnackbar = new DBSSnackBarHelper.Builder(viewTestRule.getView(), textMessage).build();
        defaultSnackbar.addCallback(snackbarBaseCallback);
        defaultSnackbar.show();

        Await.await(() -> DBSSnackBarHelperTest.this.snackbarShown, 1000);

        onView(withText(textMessage)).check(matches(hasTextColor(R.color.india_color_white)));
    }

    @Test
    public void shouldBuildSanckbarWithViewAndShowWithCustomColors() throws Exception {
        Snackbar snackbar = new Builder(viewTestRule.getView(), textMessage)
                .withActionable(actionText, view -> {})
                .withCustomColor(R.color.india_color_black_4, getColor(R.color.india_color_secondary_text), getColor(R.color.india_color_primary))
                .build();
        snackbar.addCallback(snackbarBaseCallback);
        snackbar.show();

        Await.await(() -> snackbarShown, 1000);

        int actualBackgroundColor = ((ColorDrawable) snackbar.getView().getBackground()).getColor();

        assertEquals(R.color.india_color_black_4, actualBackgroundColor);
        onView(withText(textMessage)).check(matches(hasTextColor(R.color.india_color_secondary_text)));
        onView(withText(actionText)).check(matches(hasTextColor(R.color.india_color_primary)));
    }

    @Test
    public void shouldBuildSnackbarWithActivityAndShowWithDefaultBackgroundAndActionTextColors() throws Exception {
        Snackbar defaultSnackbar = new Builder(viewTestRule.getActivity(), textMessage)
                .withActionable(actionText, view -> {})
                .build();
        defaultSnackbar.addCallback(snackbarBaseCallback);
        defaultSnackbar.show();

        Await.await(() -> snackbarShown, 1000);

        int actualColor = ((ColorDrawable) defaultSnackbar.getView().getBackground()).getColor();

        assertEquals(expectedBackgroundThemeColor, actualColor);
        onView(withText(actionText)).check(matches(hasTextColor(R.color.white)));
    }

    @Test
    public void shouldBuildSnackbarWithActivityAndShowWithDefaultMessageTextColor() throws Exception {
        viewTestRule.getActivity().setTheme(R.style.DBSBase_NoActionBar_India);
        Snackbar defaultSnackbar = new DBSSnackBarHelper.Builder(viewTestRule.getActivity(), textMessage)
                .withActionable(actionText, view -> {})
                .build();
        defaultSnackbar.addCallback(snackbarBaseCallback);
        defaultSnackbar.show();

        Await.await(() -> DBSSnackBarHelperTest.this.snackbarShown, 1000);

        onView(withText(textMessage)).check(matches(hasTextColor(R.color.india_color_white)));
    }

    @Test
    public void shouldBuildSanckbarWithActivityAndShowWithCustomColors() throws Exception {
        Snackbar snackbar = new Builder(viewTestRule.getActivity(), textMessage)
                .withActionable(actionText, view -> {})
                .withCustomColor(R.color.india_color_black_4, getColor(R.color.india_color_secondary_text), getColor(R.color.india_color_primary))
                .build();
        snackbar.addCallback(snackbarBaseCallback);
        snackbar.show();

        Await.await(() -> snackbarShown, 1000);

        int actualBackgroundColor = ((ColorDrawable) snackbar.getView().getBackground()).getColor();

        assertEquals(R.color.india_color_black_4, actualBackgroundColor);
        onView(withText(textMessage)).check(matches(hasTextColor(R.color.india_color_secondary_text)));
        onView(withText(actionText)).check(matches(hasTextColor(R.color.india_color_primary)));
    }

    @Test
    public void shouldBuildSanckBarWithCustomStyle() throws Exception {
        Snackbar snackbar = new Builder(viewTestRule.getActivity(), textMessage)
                .build();
        DBSSnackBarHelper.applyStyle(snackbar, R.style.DBSSnackBarCustomStyle);
        snackbar.addCallback(snackbarBaseCallback);
        snackbar.show();
        Await.await(() -> snackbarShown, 1000);
        onView(withText(textMessage)).check(matches(hasTextColor(R.color.red)));
        viewTestRule.runOnMainSynchronously(view -> DBSSnackBarHelper.applyStyle(snackbar, R.style.DBSSnackBarCustomStyleGreen));
        snackbar.addCallback(snackbarBaseCallback);
        snackbar.show();
        Await.await(() -> snackbarShown, 1000);
        onView(withText(textMessage)).check(matches(hasTextColor(R.color.green)));
    }

    private int getColor(int colorId) {
        return ContextCompat.getColor(viewTestRule.getActivity(), colorId);
    }
}

