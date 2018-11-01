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

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * The FontCache class helps to load and cache Font from assets.
 *
 * @author DBS Bank, AppStudio Team
 * @version 1.0.0
 * @since 2018-05-01
 */
public class FontCache {

    private static final String LOG_TAG = FontCache.class.getSimpleName();
    private static final String RELATIVE_FONTS_FOLDER_PATH = "fonts/";
    private static FontCache fontCache;
    private final Map<String, Typeface> fontTypefaceMap = new HashMap<>();

    private FontCache() {
    }

    /**
     * Returns singleton instance
     */
    public static synchronized FontCache getInstance() {
        if (fontCache == null) {
            fontCache = new FontCache();
        }
        return fontCache;
    }

    /**
     * This method is to load Typeface form "fonts" from resources
     *
     * @param context
     * @param fontName
     * @return
     */
    public Typeface getTypeface(Context context, String fontName) {
        return getTypeface(context, fontName, RELATIVE_FONTS_FOLDER_PATH);
    }

    /**
     * This method is to load Typeface form relative path given to the method
     *
     * @param context
     * @param fontName
     * @param relativeFontFolderPath {relative path to find fonts}
     * @return
     */
    public Typeface getTypeface(Context context, String fontName, String relativeFontFolderPath) {
        Typeface typeface = null;
        if (fontTypefaceMap.containsKey(fontName)) {
            typeface = fontTypefaceMap.get(fontName);
        } else {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), relativeFontFolderPath + fontName);
                fontTypefaceMap.put(fontName, typeface);
            } catch (RuntimeException e) {
                Log.e(LOG_TAG, String.format("Font %s not found in %s of assets directory", fontName, relativeFontFolderPath));
            }
        }
        return typeface;
    }
}