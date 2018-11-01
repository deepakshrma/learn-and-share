package com.dbs.replsdk.util;

/**
 * Created by deepak on 12/9/18.
 */

import android.content.Context;
import android.content.SharedPreferences;

public class PrefUtils {
    /**
     * Storing API Key in shared preferences to
     * add it in header part of every retrofit request
     */
    public PrefUtils() {
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("REPL_APP_PREF", Context.MODE_PRIVATE);
    }

    public static void store(Context context, String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(key, value).apply();
    }

    public static String find(Context context, String key) {
        return getSharedPreferences(context).getString(key, null);

    }

    public static void storeApiKey(Context context, String apiKey) {
        store(context, "API_KEY", apiKey);
    }

    public static String getApiKey(Context context) {
        return find(context, "API_KEY");
    }
}