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

import com.google.zxing.WriterException;

/**
 * The QREncodingException is to be thrown when not able to encode payload into QR code.
 *
 * @author DBS Bank, AppStudio Team
 */
public class QREncodingException extends Exception {
    QREncodingException(WriterException we) {
        super(we);
    }
}