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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.TypedValue;

import com.dbs.components.R;
import com.dbs.ui.error.MissingAttributeException;

import static com.dbs.ui.utils.ThemeUtils.DBSCoreThemes.DEFAULT;
import static com.dbs.ui.utils.ThemeUtils.DBSCoreThemes.INDIA;
import static com.dbs.ui.utils.ThemeUtils.DBSCoreThemes.INDONESIA;
import static com.dbs.ui.utils.ThemeUtils.DBSCoreThemes.IWEALTH;
import static com.dbs.ui.utils.ThemeUtils.DBSCoreThemes.POSB;
import static com.dbs.ui.utils.ThemeUtils.DBSCoreThemes.SINGAPORE;

/**
 * The ThemeUtils class helps in getting different themes and applying theme.
 *
 * @author DBS Bank, AppStudio Team
 */
public final class ThemeUtils {
    static final String DBS_APP_THEME_NAME = "DBS_APP_THEME_NAME";

    private ThemeUtils() {
        throw new UnsupportedOperationException(
                "Should not create instance of Util class. Please use as static..");
    }

    /**
     * Load theme from SharedPreferences
     *
     * @param activity
     */
    public static void loadTheme(Activity activity) {
        int themeId;
        switch (getThemeName(activity)) {
            case "SINGAPORE":
                themeId = SINGAPORE.id;
                break;
            case "INDONESIA":
                themeId = INDONESIA.id;
                break;
            case "INDIA":
                themeId = INDIA.id;
                break;
            case "IWEALTH":
                themeId = IWEALTH.id;
                break;
            case "POSB":
                themeId = POSB.id;
                break;
            default:
                themeId = DEFAULT.id;
                break;
        }
        activity.setTheme(themeId);
    }

    /**
     * Switch theme to newTheme and recreate activity
     *
     * @param activity
     * @param newTheme
     */
    public static void switchTheme(Activity activity, DBSCoreThemes newTheme) {
        String themeName = getThemeName(activity);
        if (!StringUtils.isEmpty(themeName) && themeName.equalsIgnoreCase(newTheme.name)) {
            return;
        }
        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(activity.getApplication());
        pref.edit().putString(DBS_APP_THEME_NAME, newTheme.name).commit();
        recreate(activity);
    }

    /**
     * Util method to create activity with fade-in and fade-out animation
     *
     * @param activity
     */
    public static void recreate(Activity activity) {
        Intent restartIntent = new Intent(activity, activity.getClass());
        Bundle extras = activity.getIntent().getExtras();
        if (extras != null) {
            restartIntent.putExtras(extras);
        }
        ActivityCompat.startActivity(
                activity,
                restartIntent,
                ActivityOptionsCompat
                        .makeCustomAnimation(activity, android.R.anim.fade_in, android.R.anim.fade_out)
                        .toBundle()
        );
        activity.finish();
    }

    public static String getThemeName(Activity activity) {
        if (activity.getApplication() != null) {
            SharedPreferences pref = PreferenceManager
                    .getDefaultSharedPreferences(activity.getApplication());
            return pref.getString(DBS_APP_THEME_NAME, DEFAULT.name);
        }
        return "";
    }

    /**
     * This method returns color based on theme Attribute, Theme must have to define passed attribute.
     *
     * @param context
     * @param colorAttribute
     * @return
     */
    public static int getThemeColor(Context context, int colorAttribute) {
        TypedValue colorValue = new TypedValue();
        if (!context.getTheme().resolveAttribute(colorAttribute, colorValue, true))
            throw new MissingAttributeException("Attribute not found");
        return colorValue.data;
    }

    /**
     * Basic core theme available
     */
    public enum DBSCoreThemes {
        DEFAULT("DBS_DEFAULT", R.style.DBSBase_NoActionBar_Default),
        INDIA("INDIA", R.style.DBSBase_NoActionBar_India),
        IWEALTH("IWEALTH", R.style.DBSBase_NoActionBar_IWealth),
        POSB("POSB", R.style.DBSBase_NoActionBar_POSB),
        INDONESIA("INDONESIA", R.style.DBSBase_NoActionBar_Indonesia),
        SINGAPORE("SINGAPORE", R.style.DBSBase_NoActionBar_Singapore);
        private final String name;
        private final int id;

        DBSCoreThemes(String name, int id) {
            this.id = id;
            this.name = name;

        }

        public int getTheme() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }
    }
}