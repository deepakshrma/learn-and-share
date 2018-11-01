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

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

/**
 * The DBSRadioButtonHolder component is a parent class of Custom radio button
 *
 * @author DBS Bank, AppStudio Team
 * @see DBSRadioButton
 */
public class DBSRadioButtonHolder extends RelativeLayout {
    private OnTouchListener onTouchListener;

    public DBSRadioButtonHolder(Context context) {
        super(context);
    }

    public DBSRadioButtonHolder(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DBSRadioButtonHolder(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setCustomTouchListener();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setCustomTouchListener() {
        super.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                setChecked();
            }
            if (onTouchListener != null) {
                onTouchListener.onTouch(v, event);
            }
            return true;
        });
    }

    /**
     * Sets touch listener.
     */
    @Override
    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }

    /**
     * Sets radio button state to checked.
     */
    public void setChecked() {
        RadioButton btn = this.findViewWithTag(DBSRadioButton.VIEW_TAG);
        if (btn != null && !btn.isChecked()) {
            btn.setChecked(true);
        }
    }
}