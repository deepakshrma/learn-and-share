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

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.jakewharton.rxbinding.internal.Preconditions;

/**
 * The DBSQRWatermark is watermark to be used while encoding payload into QR code.
 * Watermark could be created from Resource/Drawable/Bitmap
 *
 * @author DBS Bank, AppStudio Team
 */
public class DBSQRWatermark {
    private Bitmap watermark;

    public DBSQRWatermark(Context context, int resourceId) {
        this.watermark = BitmapFactory.decodeResource(context.getResources(), resourceId);
        Preconditions.checkNotNull(watermark, "Couldn't create bitmap from resourceId");
    }

    public DBSQRWatermark(Drawable watermark) {
        Preconditions.checkNotNull(watermark, "Drawable cannot be null");
        this.watermark = drawableToBitmap(watermark);
    }

    public DBSQRWatermark(Bitmap watermark) {
        Preconditions.checkNotNull(watermark, "Bitmap cannot be null");
        this.watermark = watermark;
    }

    /**
     * Returns watermark bitmap created
     */
    public Bitmap getWatermark() {
        return watermark;
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}