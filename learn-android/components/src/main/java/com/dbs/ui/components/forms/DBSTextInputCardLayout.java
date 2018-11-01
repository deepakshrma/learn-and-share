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
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.dbs.components.R;

/**
 * The DBSTextInputCardLayout is custom TextInputLayout with validator and error message.
 *
 * @author DBS Bank, AppStudio Team
 */
public class DBSTextInputCardLayout extends DBSBaseTextInputLayout {
    private TextView errorTextView;
    private TextInputLayout textInputLayout;
    private TextInputEditText textInputEditText;
    private View errorSeparatorLine;

    public DBSTextInputCardLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public DBSTextInputCardLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public DBSTextInputCardLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(context, R.layout.dbs_text_input_card_layout, this);
        textInputLayout = findViewById(R.id.textInputLayout);
        textInputEditText = findViewById(R.id.textInputEditText);
        errorSeparatorLine = findViewById(R.id.errorTextSeparatorLine);
        errorTextView = findViewById(R.id.errorTextView);

        String hintText = null;
        String errorText = null;
        int inputTextColor = -1;
        int errorTextColor = -1;
        Drawable drawable = null;

        if (attrs != null) {
            TypedArray customAttributes = context.obtainStyledAttributes(attrs, R.styleable.DBSTextInputCardLayout, defStyleAttr, 0);

            drawable = customAttributes.getDrawable(R.styleable.DBSTextInputCardLayout_android_src);
            hintText = customAttributes.getString(R.styleable.DBSTextInputCardLayout_android_hint);
            errorText = customAttributes.getString(R.styleable.DBSTextInputCardLayout_dbs_textError);
            inputTextColor = customAttributes.getColor(R.styleable.DBSTextInputCardLayout_dbs_inputTextColor, -1);
            errorTextColor = customAttributes.getColor(R.styleable.DBSTextInputCardLayout_dbs_textErrorColor, -1);

            setFontFromAttributesIfAvailable(context, customAttributes);

            setTextSizeFromAttributesIfAvailable(customAttributes);
            customAttributes.recycle();
        }
        textInputEditText.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        textInputLayout.setHint(hintText);
        errorSeparatorLine.setVisibility(INVISIBLE);
        errorTextView.setVisibility(INVISIBLE);
        errorTextView.setText(errorText);

        setInputTextColor(inputTextColor);
        setErrorTextColor(errorTextColor);
    }

    /**
     * Sets error color
     */
    public void setErrorColor(int color) {
        errorSeparatorLine.setBackgroundColor(color);
        errorTextView.setTextColor(color);
    }

    /**
     * Sets and Shows error message.
     */
    @Override
    public void setError(@Nullable CharSequence error) {
        if (TextUtils.isEmpty(error)) {
            errorSeparatorLine.setVisibility(INVISIBLE);
        } else {
            errorTextView.setVisibility(VISIBLE);
            errorSeparatorLine.setVisibility(VISIBLE);
        }
        errorTextView.setText(error);
    }

    /**
     * Shows error which is already set.
     */
    public void showError() {
        errorTextView.setVisibility(VISIBLE);
        errorSeparatorLine.setVisibility(VISIBLE);
    }

    @Override
    protected void hideError() {
        this.setError(null);
    }

    /**
     * returns TextInputEditTextView.
     */
    public TextInputEditText getTextInputEditTextView() {
        return textInputEditText;
    }

    /**
     * returns errorTextView.
     */
    public TextView getErrorTextView() {
        return errorTextView;
    }

    /**
     * returns TextInputLayout.
     */
    public TextInputLayout getTextInputLayout() {
        return textInputLayout;
    }

    private void setErrorTextColor(int color) {
        if (color != -1) {
            errorSeparatorLine.setBackgroundColor(color);
            errorTextView.setTextColor(color);
        }
    }

    private void setInputTextColor(int color) {
        if (color != -1)
            textInputEditText.setTextColor(color);
    }

    private void setFontFromAttributesIfAvailable(Context context, TypedArray customAttributes) {
        int font = customAttributes.getResourceId(R.styleable.DBSTextInputCardLayout_dbs_font, -1);
        if (font != -1) {
            textInputEditText.setTypeface(ResourcesCompat.getFont(context, font));
            errorTextView.setTypeface(ResourcesCompat.getFont(context, font));
        }
    }

    private void setTextSizeFromAttributesIfAvailable(TypedArray customAttributes) {
        final int inputTextSize = customAttributes.getDimensionPixelSize(R.styleable.DBSTextInputCardLayout_dbs_inputTextSize, -1);
        if (inputTextSize != -1)
            textInputEditText.setTextSize(inputTextSize);

        final int errorTextSize = customAttributes.getDimensionPixelSize(R.styleable.DBSTextInputCardLayout_dbs_textErrorSize, -1);
        if (errorTextSize != -1)
            errorTextView.setTextSize(errorTextSize);
    }
}