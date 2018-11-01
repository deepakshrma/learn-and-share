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

package com.dbs.ui.components.dbsradiobutton;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;

import com.dbs.components.R;

import java.util.ArrayList;
import java.util.List;

/**
 * The DBSRadioButton component is to create custom radio button
 *
 * @author DBS Bank, AppStudio Team
 */
public class DBSRadioButton extends AppCompatRadioButton implements DBSRadioCheckable {

    public static final String VIEW_TAG = "DBSRadioButton";
    private int checkedColor;
    private int unCheckedColor;

    private boolean isChecked;
    private List<DBSRadioCheckable.OnCheckedChangeListener> onCheckedChangeListeners = new ArrayList<>();

    public DBSRadioButton(Context context) {
        super(context);
        init(null);
    }

    public DBSRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public DBSRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            parseAttributes(attrs);
        }
        setTag();
        setCustomTint();
    }

    private void parseAttributes(AttributeSet attrs) {
        TypedArray attrsArr = getContext().obtainStyledAttributes(attrs,
                R.styleable.DBSRadioButton, 0, 0);
        try {
            checkedColor = attrsArr.getColor(R.styleable.DBSRadioButton_dbs_radioColorChecked, Color.rgb(199, 199, 204));
            unCheckedColor = attrsArr.getColor(R.styleable.DBSRadioButton_dbs_radioColorUnChecked, Color.rgb(19, 201, 152));
        } catch (Exception ex) {
            checkedColor = Color.rgb(199, 199, 204);
            unCheckedColor = Color.rgb(19, 201, 152);
        } finally {
            attrsArr.recycle();
        }
    }

    private void setTag() {
        this.setTag(VIEW_TAG);
    }

    private void setCustomTint() {
        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked},
                        new int[]{android.R.attr.state_checked}
                },
                new int[]{
                        unCheckedColor,
                        checkedColor,
                }
        );
        CompoundButtonCompat.setButtonTintList(this, colorStateList);
    }

    /**
     * @inheritDoc
     * Notifies listeners about changed state of radio button.
     */
    @Override
    public void setChecked(boolean checked) {
        if (this.isChecked != checked) {
            this.isChecked = checked;
            super.setChecked(checked);
            notifyCheckChangeListeners();
        }
    }

    /**
     * @inheritDoc
     * Add listener to list
     */
    @Override
    public void addOnCheckChangeListener(DBSRadioCheckable.OnCheckedChangeListener onCheckedChangeListener) {
        onCheckedChangeListeners.add(onCheckedChangeListener);
    }

    /**
     * @inheritDoc
     * Remove listener from list
     */
    @Override
    public void removeOnCheckChangeListener(DBSRadioCheckable.OnCheckedChangeListener onCheckedChangeListener) {
        onCheckedChangeListeners.remove(onCheckedChangeListener);
    }

    private void notifyCheckChangeListeners() {
        if (onCheckedChangeListeners != null) {  // To handle android:checked = true set in xml file
            for (DBSRadioCheckable.OnCheckedChangeListener onCheckedChangeListener : onCheckedChangeListeners) {
                onCheckedChangeListener.onCheckedChanged(this, isChecked);
            }
        }
    }
}