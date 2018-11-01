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
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;

import com.dbs.components.R;

/**
 * The DBSMultiSpanTextView is a component which supports adding clickable span on text in text view.
 *
 * @author DBS Bank, AppStudio Team
 */
public class DBSMultiSpanTextView extends AppCompatTextView {

    SpannableString spannableString;

    public DBSMultiSpanTextView(Context context) {
        super(context, null, R.attr.dbs_themeSmallTextStyle);
    }

    public DBSMultiSpanTextView(Context context, AttributeSet attrs) {
        super(context, attrs, R.attr.dbs_themeSmallTextStyle);
    }

    public DBSMultiSpanTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * This method will setClickableSpan at given indices and the clickListener
     *
     * @param start start index of span
     * @param end end index of span
     * @param onClickListener clickListener to attach
     */
    public void setClickSpannable(int start, int end, OnClickListener onClickListener) {
        if (spannableString == null)
            spannableString = new SpannableString(getText());
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                onClickListener.onClick(widget);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(true);
            }
        }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setMovementMethod(LinkMovementMethod.getInstance());
        setHighlightColor(Color.TRANSPARENT);
        this.setText(spannableString);
    }

    /**
     * This method will setClickableSpan to text matching spanText
     *
     * @param spanText substring to be treated as span
     * @param onClickListener clickListener to attach
     */
    public void setClickSpannable(String spanText, OnClickListener onClickListener) {
        int startIndex = getText().toString().indexOf(spanText);
        setClickSpannable(startIndex, startIndex + spanText.length(), onClickListener);
    }
}