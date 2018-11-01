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

/**
 * When an object of a type is attached to {@link DBSQRScannerFragment}, its methods will
 * be called while trying to scan QR code.
 *
 * @author DBS Bank, AppStudio Team
 */
public interface DBSScanQRListener {

    /**
     * This method is called to notify you that,
     * QR code was scanned successfully.
     * @param code decoded message from QR code.
     */
    void onScanned(String code);

    /**
     * This method is called to notify you that,
     * Camera Permission was denied.
     */
    void onCameraPermissionDenied();
}