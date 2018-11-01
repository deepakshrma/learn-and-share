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

package com.dbs.ui.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.widget.TextViewCompat;
import android.util.AttributeSet;

import com.dbs.components.R;
import com.dbs.ui.styleable.StyleableComponent;
import com.dbs.ui.utils.LayoutUtils;

/**
 * The DBSBadge component is to create different types of badges
 *
 * @author DBS Bank, AppStudio Team
 * @version 1.0.0
 * @since 2018-05-01
 */

public class DBSBadge extends android.support.v7.widget.AppCompatTextView implements StyleableComponent {
    public DBSBadge(Context context) {
        super(context);
        init(context, null);
    }

    public DBSBadge(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DBSBadge(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
        applyStyle(defStyleAttr);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.DBSBadge, 0, 0);
            boolean rounded = attributes.getBoolean(R.styleable.DBSBadge_dbs_rounded, false);
            float roundedCorner = attributes.getDimensionPixelSize(R.styleable.DBSBadge_dbs_cornerRadius, 20);
            if (rounded) {
                drawRoundedCircle(roundedCorner, obtainStyleableArray(attrs));
            }
            attributes.recycle();
        }
    }

    /**
     * Change the style of DBS Badge Programmatically
     * Ex.
     * <code>
     * <style name="DBSBadgeCustom">
     * <item name="android:textColor">?themeTertiaryTextColor</item>
     * <item name="android:background">?colorPrimaryDark</item>
     * <item name="rounded">true</item>
     * <item name="dbsRoundedCorner">20</item>
     * </style>
     * </code>
     *
     * @param style
     */
    @Override
    public void applyStyle(@StyleRes int style) {
        Context context = getContext();
        TypedArray attributes = context.obtainStyledAttributes(style, R.styleable.DBSBadge);
        boolean rounded = attributes.getBoolean(R.styleable.DBSBadge_dbs_rounded, false);
        float roundedCorner = attributes.getDimensionPixelSize(R.styleable.DBSBadge_dbs_cornerRadius, 24);
        if (rounded) {
            drawRoundedCircle(roundedCorner, obtainStyleableArray(style));
        }
        TextViewCompat.setTextAppearance(this, style);
        attributes.recycle();
    }

    private TypedArray obtainStyleableArray(@StyleRes int style) {
        int[] attrsArray = {
                android.R.attr.background
        };
        return getContext().obtainStyledAttributes(style, attrsArray);
    }

    private TypedArray obtainStyleableArray(AttributeSet attributeSet) {
        int[] attrsArray = {
                android.R.attr.background
        };
        return getContext().obtainStyledAttributes(attributeSet, attrsArray);
    }

    private void drawRoundedCircle(float roundedCorner, TypedArray ta) {
        int backgroundColor = ta.getColor(0, Color.TRANSPARENT);
        ta.recycle();
        GradientDrawable rounderColoredDrawable = LayoutUtils.GradientHelper.getCircleGradientDrawable(backgroundColor, roundedCorner);
        this.setBackgroundDrawable(rounderColoredDrawable);
    }
}