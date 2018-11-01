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

package com.dbs.ui.components.qrcode;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

import com.dbs.ui.utils.StringUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.EnumMap;
import java.util.Map;

/**
 * The DBSQREncoder can encode QR code from bitmap and return a string.
 *
 * @author DBS Bank, AppStudio Team
 */
public class DBSQREncoder {
    private static final String TAG = DBSQREncoder.class.getSimpleName();

    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final int DEFAULT_QR_CODE_MARGIN = 2;

    private int qrCodeMargin;
    private int qrBitmapWidth;
    private int qrBitmapHeight;
    private int qrBitmapOverlayResWidth;
    private int qrBitmapOverlayResHeight;
    private int qrColor;
    private DBSQRWatermark watermark;

    private DBSQREncoder(Builder builder) {
        qrBitmapWidth = builder.qrBitmapWidth;
        qrBitmapHeight = builder.qrBitmapHeight;
        qrColor = builder.qrColor == 0 ? Color.BLACK : builder.qrColor;
        qrCodeMargin = builder.qrCodeMargin < 0 ? DEFAULT_QR_CODE_MARGIN : builder.qrCodeMargin;
        watermark = builder.watermark;
        qrBitmapOverlayResWidth = adjustSize(qrBitmapWidth, builder.qrBitmapOverlayResWidth);
        qrBitmapOverlayResHeight = adjustSize(qrBitmapHeight, builder.qrBitmapOverlayResHeight);
    }

    /**
     * Encodes the given payload string to qr bitmap
     * @throws QREncodingException when not able to encode payload into QR code.
     */
    public Bitmap encode(String qrPayload) throws QREncodingException {
        if (StringUtils.isEmpty(qrPayload) || qrBitmapWidth <= 0 || qrBitmapHeight <= 0) {
            return null;
        }
        try {
            if (watermark != null) {
                return createOverlayQrCode(qrPayload);
            } else {
                return createQrCode(qrPayload);
            }
        } catch (WriterException we) {
            throw new QREncodingException(we);
        }
    }

    private Bitmap createQrCode(String payload) throws WriterException {
        Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        hints.put(EncodeHintType.CHARACTER_SET, DEFAULT_ENCODING);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.MARGIN, qrCodeMargin);

        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result = writer.encode(payload, BarcodeFormat.QR_CODE, qrBitmapWidth, qrBitmapHeight, hints);
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? qrColor : Color.WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private Bitmap createOverlayQrCode(String qrPayload) throws WriterException {
        Bitmap overlay = watermark.getWatermark();
        if (overlay == null) {
            Log.w(TAG, "Unable to create watermark from the given input, please provide valid watermark.");
            return null;
        }

        resizeOverlay(overlay);

        Bitmap qrCodeBitmap = createQrCode(qrPayload);
        int qrCodeBitmapHeight = qrCodeBitmap.getHeight();
        int qrCodeBitmapWidth = qrCodeBitmap.getWidth();

        Bitmap combined = Bitmap.createBitmap(qrCodeBitmapWidth, qrCodeBitmapHeight, qrCodeBitmap.getConfig());

        Canvas canvas = new Canvas(combined);
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        canvas.drawBitmap(qrCodeBitmap, new Matrix(), null);

        int centreX = (canvasWidth - qrBitmapOverlayResWidth) / 2;
        int centreY = (canvasHeight - qrBitmapOverlayResHeight) / 2;
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        canvas.drawBitmap(Bitmap.createScaledBitmap(overlay, qrBitmapOverlayResWidth, qrBitmapOverlayResHeight, false), (float) centreX, (float) centreY, paint);

        return combined;

    }

    private int adjustSize(int qrSize, int overlaySize) {
        //40% of QR size is the maximum recommended size for watermark for better reading of QR Code
        int maxSize = (qrSize * 40) / 100;
        return (overlaySize <= 0 || overlaySize > maxSize) ? maxSize : overlaySize;
    }

    private void resizeOverlay(Bitmap overlayBitmap) {
        int overlayOriginalWidth = overlayBitmap.getWidth();
        int overlayOriginalHeight = overlayBitmap.getHeight();
        if (overlayOriginalWidth <= qrBitmapOverlayResWidth && overlayOriginalHeight <= qrBitmapOverlayResHeight) {
            Log.i(TAG, "Given overlay sizes are optimal i.e less than equal to maximum size");
        } else {
            Log.i(TAG, "Adjusting required watermark width and height w.r.t aspect ratio of original watermark width and height");
            if (overlayOriginalWidth > overlayOriginalHeight) {
                qrBitmapOverlayResHeight = Math.round(overlayOriginalHeight * ((float) qrBitmapOverlayResWidth / (float) overlayOriginalWidth));
            } else if (overlayOriginalHeight > overlayOriginalWidth) {
                qrBitmapOverlayResWidth = Math.round(overlayOriginalWidth * ((float) qrBitmapOverlayResHeight / (float) overlayOriginalHeight));
            }
        }
    }

    /**
     * Builder for DBSQREncoder
     */
    public static class Builder {
        private int qrBitmapWidth;
        private int qrBitmapHeight;
        private int qrBitmapOverlayResWidth;
        private int qrBitmapOverlayResHeight;
        private int qrColor;
        private int qrCodeMargin = -1;
        private DBSQRWatermark watermark;

        /**
         * Sets bitmap size for generated QR code
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setQrBitmapSize(int qrBitmapWidth, int qrBitmapHeight) {
            this.qrBitmapWidth = qrBitmapWidth;
            this.qrBitmapHeight = qrBitmapHeight;

            return this;
        }

        /**
         * Sets watermark to overlay in QR code bitmap
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setQrBitmapOverlayRes(DBSQRWatermark watermark) {
            this.watermark = watermark;
            return this;
        }

        /**
         * Sets size for watermark overlay
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setQrBitmapOverlayResSize(int overlayResWidth, int overlayResHeight) {
            qrBitmapOverlayResWidth = overlayResWidth;
            qrBitmapOverlayResHeight = overlayResHeight;
            return this;
        }

        /**
         * Sets QR code color
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setQrColor(int color) {
            qrColor = color;
            return this;
        }

        /**
         * Sets margin
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setQrMargin(int margin) {
            qrCodeMargin = margin;
            return this;
        }

        /**
         * Creates an {@link DBSQREncoder} with the arguments supplied to this
         * builder.
         */
        public DBSQREncoder build() {
            return new DBSQREncoder(this);
        }
    }
}