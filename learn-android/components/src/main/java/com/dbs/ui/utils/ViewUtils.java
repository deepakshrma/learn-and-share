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
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.dbs.components.R;

import static android.view.View.VISIBLE;

/**
 * ViewUtils class contains utilities for views
 *
 * @author DBS Bank, AppStudio Team
 * @version 1.0.0
 * @since 2018-05-01
 */

public final class ViewUtils {

    private ViewUtils() {
        throw new UnsupportedOperationException(
                "Should not create instance of Util class. Please use as static..");
    }

    /**
     * Get the screen dimensions
     *
     * @param activity the activity
     * @return the int [ ]
     */
    public static int[] getScreenSize(Activity activity) {
        Point size = new Point();
        WindowManager w = activity.getWindowManager();

        w.getDefaultDisplay().getSize(size);
        return new int[]{size.x, size.y};
    }

    /**
     * Get the screen dimensions
     *
     * @return the int [ ]
     */
    public static int[] getScreenSize() {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return new int[]{metrics.widthPixels, metrics.widthPixels};
    }

    /**
     * Hide On Screen Keyboard for EditText
     *
     * @param context  the famous android context
     * @param editText the edit text
     */
    public static void hideOnScreenKeyboardForEditText(Context context, EditText editText) {
        ((InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * Convert dp to px
     *
     * @param dp      the amount of dp to convert
     * @param context the famous android context
     */
    public static int dpToPixels(Context context, int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    /**
     * Convert resIdInDimen dp to px
     *
     * @param resIdInDimen the amount of resIdInDimen dp to convert
     * @param context      the famous android context
     */
    public static int dpToPixelsWithRes(Context context, int resIdInDimen) {
        int dimensionInDp = (int) context.getResources().getDimension(resIdInDimen);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, dimensionInDp, displayMetrics);
    }

    /**
     * Set orientation change lock
     *
     * @param activity the activity
     * @param status   the status
     */
    public static void setOrientation(Activity activity, boolean status) {
        if (status) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
            }
        } else {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
        }
    }

    /**
     * This method is to set text and make it visible
     *
     * @param textView
     * @param text
     */
    public static void setTextIfNotNull(TextView textView, String text) {
        if (!TextUtils.isEmpty(text)) {
            textView.setText(text);
            textView.setVisibility(VISIBLE);
        }
    }

    /**
     * Convert color int to hex
     *
     * @param color
     */
    public static String colorToHex(int color) {
        return String.format("#%06X", (0xFFFFFF & color));
    }

    /**
     * Remove view from current parent
     *
     * @param child : view to be removed from its parent
     */
    public static void removeFromParent(View child) {
        ViewParent currentParent = child.getParent();
        if (currentParent != null) {
            ((ViewGroup) currentParent).removeView(child);
        }
    }

    /**
     * Create rect shape drawable with bgColor and corner in Px
     *
     * @param bgColor  : background color
     * @param cornerPx : corner in pixels
     */
    public static GradientDrawable getRectShape(int bgColor, int cornerPx) {
        GradientDrawable enabled = new GradientDrawable();
        enabled.setShape(GradientDrawable.RECTANGLE);
        enabled.setCornerRadii(new float[]{cornerPx, cornerPx, cornerPx, cornerPx, cornerPx, cornerPx, cornerPx, cornerPx});
        enabled.setColor(bgColor);
        return enabled;
    }

    /**
     * Toggle view show and height
     *
     * @param view
     * @param isExpend
     */
    public static void toggle(final View view, boolean isExpend) {
        if (isExpend) {
            show(view);
        } else {
            hide(view);
        }
    }

    /**
     * Show view with animation
     *
     * @param view
     */
    public static void show(View view) {
        TranslateAnimation anim = new TranslateAnimation(0.0f, 0.0f, -view.getHeight(), view.getHeight() / 2);
        view.setVisibility(View.VISIBLE);
        anim.setDuration(400);
        anim.setInterpolator(new AccelerateInterpolator(0.8f));
        view.startAnimation(anim);
    }

    /**
     * Hide view with animation
     *
     * @param view
     */
    public static void hide(View view) {
        TranslateAnimation anim = new TranslateAnimation(0.0f, 0.0f, view.getHeight() / 2, -view.getHeight());
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Does not need to be handled
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Does not need to be handled
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }
        });
        anim.setInterpolator(new AccelerateInterpolator(0.8f));
        view.startAnimation(anim);
    }

    /**
     * Returns Rotate Animation 180degree
     *
     * @param cx
     * @param up
     * @return Animation
     */
    public static Animation rotate180(Context cx, boolean up) {
        Animation anim = AnimationUtils.loadAnimation(cx, up ? R.anim.rotate_up : R.anim.rotate_down);
        anim.setInterpolator(new LinearInterpolator()); // for smooth animation
        return anim;
    }
    public static boolean isTransparent(int color) {
        String hexColor = String.format("%08X", (0xFFFFFFFF & color)).substring(0, 2);
        return hexColor.equalsIgnoreCase("00");
    }
}