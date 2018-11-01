package com.dbs.ui.components;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;

import com.dbs.ui.components.qrcode.DBSQRDecoder;
import com.dbs.ui.components.qrcode.DBSQREncoder;
import com.dbs.ui.components.qrcode.DBSQRWatermark;
import com.dbs.ui.components.qrcode.InvalidQRException;
import com.dbs.ui.components.qrcode.QREncodingException;
import com.dbs.ui.components.testRule.EmptyActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(AndroidJUnit4.class)
public class DBSQRGeneratorTest {
    private DBSQREncoder qrEncoder;
    private int height = 600;
    private int width = 600;

    @Rule
    public ActivityTestRule<EmptyActivity> rule = new ActivityTestRule<>(EmptyActivity.class);

    @Before
    public void setUp() {
        Drawable drawable = ContextCompat.getDrawable(rule.getActivity().getApplicationContext(), com.dbs.components.test.R.drawable.qr_watermark);
        qrEncoder = new DBSQREncoder.Builder()
                .setQrBitmapSize(width, height)
                .setQrBitmapOverlayRes(new DBSQRWatermark(drawable))
                .setQrBitmapOverlayResSize(200, 200)
                .setQrColor(Color.parseColor("#931B7C"))
                .setQrMargin(2)
                .build();
    }

    @Test
    public void shouldNotReturnQrCodeForEmptyOrNullString() throws QREncodingException {

        Bitmap bitmap = qrEncoder.encode(null);

        assertNull(bitmap);

        bitmap = qrEncoder.encode("");

        assertNull(bitmap);
    }

    @Test
    public void shouldReturnQrCodeForGivenStringAndOverlay() throws QREncodingException {

        Bitmap bitmap = qrEncoder.encode("http://dbs.com");

        assertNotNull(bitmap);
    }

    @Test
    public void shouldQrCodeDimensionsMatch() throws QREncodingException {
        Bitmap bitmap = qrEncoder.encode("http://dbs.com");
        assertNotNull(bitmap);
        assertEquals(width, bitmap.getWidth());
        assertEquals(height, bitmap.getHeight());
    }

    @Test
    public void shouldReturnQrCodeWithoutOverlay() throws QREncodingException {
        qrEncoder = new DBSQREncoder.Builder()
                .setQrBitmapSize(width, height)
                .setQrColor(Color.parseColor("#931B7C"))
                .build();
        Bitmap bitmap = qrEncoder.encode("http://dbs.com");
        assertNotNull(bitmap);
    }

    @Test
    public void shouldQrCodeMatchWithDecodedValue() throws QREncodingException, InvalidQRException {
        String qrCode = "Sample QR Code of supported length";
        Bitmap bitmap = qrEncoder.encode(qrCode);

        assertNotNull(bitmap);
        assertEquals(qrCode, new DBSQRDecoder().scanQRFromBitmap(bitmap));
    }
}
