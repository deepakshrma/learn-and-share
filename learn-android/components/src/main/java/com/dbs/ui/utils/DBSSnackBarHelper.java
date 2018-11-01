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
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.StyleRes;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.dbs.components.R;

/**
 * The DBSSnackBarHelper class helps to create custom Snackbar providing basic configuration.
 *
 * @author DBS Bank, AppStudio Team
 * @version 1.0.0
 * @since 2018-05-01
 */
public final class DBSSnackBarHelper {
    private DBSSnackBarHelper() {
        throw new UnsupportedOperationException(
                "Should not create instance of Util class. Please use as static..");
    }

    /**
     * Sets the color for background and message text with style.
     */
    public static void applyStyle(Snackbar snackbar, @StyleRes int snackBarStyle) {
        Context context = snackbar.getContext();
        TypedArray attributes = context.obtainStyledAttributes(snackBarStyle, R.styleable.DBSSnackBar);
        final int backgroundColor = attributes.getColor(R.styleable.DBSSnackBar_android_background, Color.BLACK);
        final int messageColor = attributes.getColor(R.styleable.DBSSnackBar_android_textColor, Color.WHITE);
        final int maxLines = attributes.getInt(R.styleable.DBSSnackBar_android_maxLines, -1);
        attributes.recycle();
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(backgroundColor);
        TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(messageColor);
        if (maxLines != -1) {
            textView.setMaxLines(maxLines);
        }
    }

    /**
     * Static Builder, to collect information of the Snackbar being built
     */
    public static class Builder {
        private String actionText;
        private String messageText;
        private int durationLength = Snackbar.LENGTH_LONG;
        private int messageColor;
        private int actionTextColor = Color.WHITE;
        private int backgroundColor;
        private int maxLines = -1;
        private View view;
        private OnClickListener actionListener;

        /**
         * Builder constructor with view and message.
         *
         * @param view        View will be used to create Snackbar.
         * @param messageText String Snackbar message text.
         *                    This method will apply default colors from current theme.
         */
        public Builder(View view, String messageText) {
            this.messageText = messageText;
            this.view = view;
            applyDefaultColors(view.getContext());
        }

        /**
         * Builder constructor with activity message.
         *
         * @param activity    Activity context to create Snackbar.
         * @param messageText String Snackbar message text.
         *                    This method will find root view will be used to create snackbar.
         *                    This method will apply default colors from current theme.
         */
        public Builder(Activity activity, String messageText) {
            this.messageText = messageText;
            view = activity.getWindow().getDecorView().getRootView();
            applyDefaultColors(activity);
        }

        /**
         * Sets the duration length.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder withDuration(int durationLength) {
            this.durationLength = durationLength;
            return this;
        }

        /**
         * Sets the action text and click listener.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder withActionable(String actionText, View.OnClickListener listener) {
            this.actionText = actionText;
            this.actionListener = listener;
            return this;
        }

        /**
         * Sets color for message text.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder withMessageColor(int messageColor) {
            this.messageColor = messageColor;
            return this;
        }

        /**
         * Sets the background color.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder withBackgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        /**
         * Sets the action text color.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder withActionTextColor(int actionTextColor) {
            this.actionTextColor = actionTextColor;
            return this;
        }

        /**
         * Sets the color for background, message text and action text.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder withCustomColor(int backgroundColor, int messageColor, int actionTextColor) {
            this.backgroundColor = backgroundColor;
            this.messageColor = messageColor;
            this.actionTextColor = actionTextColor;
            return this;
        }

        /**
         * Builder constructor with view and message.
         *
         * @param maxLines String Snackbar maxLines.
         *                 This method will change the no of max lines.
         */
        public Builder withMaxLines(int maxLines) {
            this.maxLines = maxLines;
            return this;
        }

        /**
         * This build method will create Snackbar using view.
         *
         * @return Snackbar instance
         */
        public Snackbar build() {
            Snackbar snackbar = Snackbar.make(view, messageText, durationLength);
            if (actionText != null && !actionText.isEmpty()) {
                snackbar.setAction(actionText, actionListener);
                snackbar.setActionTextColor(actionTextColor);
            }
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(backgroundColor);
            TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(messageColor);
            if (maxLines != -1) {
                textView.setMaxLines(maxLines);
            }
            return snackbar;
        }

        private void applyDefaultColors(Context context) {
            backgroundColor = ThemeUtils.getThemeColor(context, R.attr.colorPrimary);
            messageColor = ThemeUtils.getThemeColor(context, R.attr.dbs_themePrimaryTextColorInverse);
        }
    }
}