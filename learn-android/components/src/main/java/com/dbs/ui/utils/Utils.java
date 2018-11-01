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

package com.dbs.ui.utils;

public class Utils {
    private Utils() {
        throw new UnsupportedOperationException(
                "Should not create instance of Util class. Please use as static..");
    }

    public static String countryCodeToEmoFlagChar(String countryCode) {
        if (StringUtils.isEmpty(countryCode) || countryCode.length() < 2)
            return "";
        String countryCodeCaps = countryCode.toUpperCase();
        int firstLetter = Character.codePointAt(countryCodeCaps, 0) - 0x41 + 0x1F1E6;
        int secondLetter = Character.codePointAt(countryCodeCaps, 1) - 0x41 + 0x1F1E6;
        return new String(Character.toChars(firstLetter)) + new String(Character.toChars(secondLetter));
    }

    public static String currencyToEmoFlagChar(String countryCode) {
        return countryCodeToEmoFlagChar(countryCode);
    }

    public static boolean hasSameDomainAs(String url, String domain) {
        return !(url == null || url.isEmpty()) && url.matches("^https?://[^/@]*\\." + domain + "+\\.com(/.*)?$");
    }
}

