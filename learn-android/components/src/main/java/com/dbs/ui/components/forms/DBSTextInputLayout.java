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

package com.dbs.ui.components.forms;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;

import com.dbs.components.R;

/**
 * The DBSTextInputLayout is custom TextInputLayout with validator and error message.
 *
 * @author DBS Bank, AppStudio Team
 */
public class DBSTextInputLayout extends DBSBaseTextInputLayout {
    private TextInputLayout textInputLayout;
    private TextInputEditText textInputEditText;
    private String errorText;

    public DBSTextInputLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public DBSTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public DBSTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(context, R.layout.dbs_text_input_layout, this);
        textInputLayout = findViewById(R.id.textInputLayout);
        textInputEditText = findViewById(R.id.textInputEditText);

        String hintText = null;
        errorText = null;
        Drawable drawable = null;

        if (attrs != null) {
            TypedArray customAttributes = context.obtainStyledAttributes(attrs, R.styleable.DBSTextInputLayout, defStyleAttr, 0);

            drawable = customAttributes.getDrawable(R.styleable.DBSTextInputLayout_android_src);
            hintText = customAttributes.getString(R.styleable.DBSTextInputLayout_android_hint);
            errorText = customAttributes.getString(R.styleable.DBSTextInputLayout_dbs_textError);

            customAttributes.recycle();
        }
        textInputEditText.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        textInputLayout.setHint(hintText);
    }

    @Override
    public void setError(@Nullable CharSequence error) {
        errorText = String.valueOf(error);
        textInputLayout.setError(error);
    }

    public void showError() {
        textInputLayout.setError(errorText);
    }

    public void hideError() {
        textInputLayout.setErrorEnabled(false);
    }

    public TextInputEditText getTextInputEditTextView() {
        return textInputEditText;
    }
}