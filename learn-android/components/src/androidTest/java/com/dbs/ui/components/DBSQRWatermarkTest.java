package com.dbs.ui.components;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;

import com.dbs.ui.components.qrcode.DBSQRWatermark;
import com.dbs.ui.components.testRule.EmptyActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class DBSQRWatermarkTest {
    @Rule
    public ActivityTestRule<EmptyActivity> rule = new ActivityTestRule<>(EmptyActivity.class);

    @Test
    public void shouldSetWaterMarkThroughResourceId() {
        assertNotNull(new DBSQRWatermark(rule.getActivity().getApplication(), com.dbs.components.test.R.drawable.qr_watermark).getWatermark());
    }

    @Test
    public void shouldSetWaterMarkThroughDrawable() {
        Drawable drawable = ContextCompat.getDrawable(rule.getActivity().getApplicationContext(), com.dbs.components.test.R.drawable.qr_watermark);

        assertNotNull(new DBSQRWatermark(drawable).getWatermark());
    }

    @Test
    public void shouldSetWaterMarkThroughBitmap() {
        Drawable drawable = ContextCompat.getDrawable(rule.getActivity().getApplicationContext(), com.dbs.components.test.R.drawable.qr_watermark);
        Bitmap bitmap = new DBSQRWatermark(drawable).getWatermark();
        assertNotNull(bitmap);
        assertNotNull(new DBSQRWatermark(bitmap).getWatermark());
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNPEForInvalidResourceId1() {
        new DBSQRWatermark(rule.getActivity().getApplicationContext(), 21321312);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNPEForInvalidDrawable() {
        Drawable drawable = null;
        new DBSQRWatermark(drawable);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNPEForInvalidBitmap() {
        Bitmap bitmap = null;
        new DBSQRWatermark(bitmap);
    }
}
