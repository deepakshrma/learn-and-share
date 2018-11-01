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

import java.util.Arrays;

/**
 * The StringUtils class contains string utilities methods.
 *
 * @author DBS Bank, AppStudio Team
 * @version 1.0.0
 * @since 2018-05-01
 */
public final class StringUtils {
    private StringUtils() {
        throw new UnsupportedOperationException(
                "Should not create instance of Util class. Please use as static..");
    }

    /**
     * This method is to fill string with given character
     *
     * @param length { Length of the string }
     * @param ch     { Character to append/fill }
     * @return
     */
    public static String fill(int length, char ch) {
        final char[] array = new char[length];
        Arrays.fill(array, ch);
        return new String(array);
    }

    /**
     * This method is to fill string with '0'
     *
     * @param length { Length of the string }
     * @return
     */
    public static String fillZero(int length) {
        return fill(length, '0');
    }

    /**
     * This method is to find the occurrences count of the string
     *
     * @param text
     * @param pattern
     * @return
     */
    public static int countOccurrencesOf(String text, String pattern) {
        return text.length() - text.replace(pattern, "").length();
    }

    /**
     * Returns last character of String
     *
     * Returns '\0' if null or empty.
     */
    public static char getLastCharacter(String text) {
        if (!isEmpty(text)) {
            return text.charAt(text.length() - 1);
        }
        return '\0';
    }

    /**
     * Returns true if the string is null
     * Returns true if the string is empty
     * Returns true if the trimmed string is empty
     * Returns false otherwise
     */
    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0 || s.trim().isEmpty();
    }
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }
}