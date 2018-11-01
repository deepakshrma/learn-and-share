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
import android.support.annotation.NonNull;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

/**
 * The DBSQRDecoder can read QR code from bitmap and return a payload string.
 *
 * @author DBS Bank, AppStudio Team
 */
public class DBSQRDecoder {

    /**
     * Returns QR code payload String read from QR code bitmap
     * @throws InvalidQRException when not able to read the bitmap for QR code.
     */
    public String scanQRFromBitmap(Bitmap qrCodeBitmap) throws InvalidQRException {
        if (qrCodeBitmap == null) {
            return null;
        }

        int width = qrCodeBitmap.getWidth();
        int height = qrCodeBitmap.getHeight();

        BinaryBitmap bBitmap = convertQRAsBinaryBitmap(qrCodeBitmap, width, height);

        try {
            Result result = new MultiFormatReader().decode(bBitmap);
            if (result != null && result.getText() != null) {
                return result.getText();
            }
        } catch (NotFoundException ne) {
            throw new InvalidQRException(ne);
        } finally {
            qrCodeBitmap.recycle();

        }
        return null;
    }

    @NonNull
    private BinaryBitmap convertQRAsBinaryBitmap(Bitmap qrCodeBitmap, int width, int height) {
        RGBLuminanceSource source = new RGBLuminanceSource(width, height, getPixels(qrCodeBitmap, width, height));
        return new BinaryBitmap(new HybridBinarizer(source));
    }

    private int[] getPixels(Bitmap qrCodeBitmap, int width, int height) {
        int[] pixels = new int[width * height];
        qrCodeBitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        return pixels;
    }
}