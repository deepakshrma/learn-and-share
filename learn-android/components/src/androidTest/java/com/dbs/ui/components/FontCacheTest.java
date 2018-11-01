package com.dbs.ui.components;

import android.graphics.Typeface;
import android.support.test.rule.ActivityTestRule;

import com.dbs.ui.components.testRule.EmptyActivity;
import com.dbs.ui.utils.FontCache;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

public class FontCacheTest {

    @Rule
    public ActivityTestRule<EmptyActivity> viewTestRule = new ActivityTestRule<>(EmptyActivity.class);

    @Test
    public void shouldReturnNullTypefaceForInvalidFont() {
        FontCache fontCache = FontCache.getInstance();

        Typeface typeface = fontCache.getTypeface(viewTestRule.getActivity(), "invalid");

        assertNull(typeface);
    }

    @Test
    public void shouldReturnValidTypefaceIfFontIsPresent() {
        FontCache fontCache = FontCache.getInstance();

        Typeface typeface = fontCache.getTypeface(viewTestRule.getActivity(), "AvenirNextLTPro-Medium.ttf");

        assertNotNull(typeface);
    }

    @Test
    public void shouldReturnInstanceFromHashMapIfFontAlreadyLoaded() {
        FontCache fontCache = FontCache.getInstance();

        Typeface typefaceOne = fontCache.getTypeface(viewTestRule.getActivity(), "AvenirNextLTPro-Medium.ttf");
        Typeface typefaceTwo = fontCache.getTypeface(viewTestRule.getActivity(), "AvenirNextLTPro-Medium.ttf");

        assertSame(typefaceOne, typefaceTwo);
    }

    @Test
    public void shouldReturnValidTypefaceIfFontIsPresentInDifferentRelativePath() {
        FontCache fontCache = FontCache.getInstance();

        Typeface typeface = fontCache.getTypeface(viewTestRule.getActivity(), "roboto_bold.ttf", "");

        assertNotNull(typeface);
    }
}
