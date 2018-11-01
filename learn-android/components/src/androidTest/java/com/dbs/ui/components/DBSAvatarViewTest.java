package com.dbs.ui.components;

import android.graphics.drawable.GradientDrawable;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import com.dbs.ui.components.testRule.ViewTestRule;
import com.dbs.ui.utils.LayoutUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.dbs.components.test.R.drawable.placeholder;
import static com.dbs.ui.components.Matchers.CustomTestMatcher.hasSampleGradientDrawable;
import static com.dbs.ui.components.Matchers.CustomTestMatcher.withDrawable;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class DBSAvatarViewTest {

    @Rule
    public ViewTestRule<DBSAvatarView> viewTestRule = new ViewTestRule<>(com.dbs.components.test.R.layout.dbs_avatar_view_test);

    DBSAvatarView avatarView;

    @Before
    public void setUp() {
        avatarView = viewTestRule.getView().findViewById(com.dbs.components.test.R.id.roundAvatarView2);
    }


    @Test
    public void withText_shouldGetProperDrawable() {
        String strTest = "An Nam";
        GradientDrawable test = new LayoutUtils.GradientBuilder()
                .withOrientation(GradientDrawable.Orientation.TOP_BOTTOM)
                .withPrimaryColor(0xffFF9DA2)
                .withSecondaryColor(0xff97BEFF)
                .withShape(GradientDrawable.OVAL)
                .build();

        viewTestRule.runOnMainSynchronously(v -> avatarView.setName(strTest));

        // With no user interaction, the ImageView will have a drawable.
        onView(withId(com.dbs.components.test.R.id.roundAvatarView2)).check(matches(hasSampleGradientDrawable(test)));

    }


    @Test
    public void SingleWord_shouldGetSingleColorDrawable() {
        String strTest = "An";
        GradientDrawable test = new LayoutUtils.GradientBuilder()
                .withOrientation(GradientDrawable.Orientation.TOP_BOTTOM)
                .withPrimaryColor(0xffFF9DA2)
                .withShape(GradientDrawable.OVAL)
                .build();

        viewTestRule.runOnMainSynchronously(v -> avatarView.setName(strTest));


        // With no user interaction, the ImageView will have a drawable.
        onView(withId(com.dbs.components.test.R.id.roundAvatarView2)).check(matches(hasSampleGradientDrawable(test)));

    }


    @Test
    public void setImageResource_shouldActLikeNormalImageView() {
        viewTestRule.runOnMainSynchronously(view -> avatarView.setImageResource(placeholder));
        assertEquals(avatarView.getInitials().length, 0);
        onView(withId(com.dbs.components.test.R.id.roundAvatarView2)).check(matches(withDrawable(placeholder)));

    }


}
