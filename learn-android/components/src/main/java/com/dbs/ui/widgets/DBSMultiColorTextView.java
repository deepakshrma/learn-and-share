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

package com.dbs.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;

import com.dbs.components.R;
import com.dbs.ui.error.TextNotProvidedException;

/**
 * The DBSMultiColorTextView component is to create multi-colored textview
 *
 * @author DBS Bank, AppStudio Team
 */
public class DBSMultiColorTextView extends android.support.v7.widget.AppCompatTextView {

    public DBSMultiColorTextView(Context context) {
        super(context, null, R.attr.dbs_themeMultiColorTextStyle);
    }

    public DBSMultiColorTextView(Context context, AttributeSet attrs) {
        super(context, attrs, R.attr.dbs_themeMultiColorTextStyle);
        configure(context, attrs);
    }

    public DBSMultiColorTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, R.attr.dbs_themeMultiColorTextStyle);
        configure(context, attrs);
    }

    private void configure(Context context, AttributeSet viewAttrs) {
        TypedArray viewCustomAttributes = context.obtainStyledAttributes(viewAttrs, R.styleable.DBSMultiColorTextView);
        boolean textInputNotProvided = getText().equals("");
        if (textInputNotProvided) {
            throw new TextNotProvidedException();
        }
        String highlightedTextRanges = viewCustomAttributes.getString(R.styleable.DBSMultiColorTextView_dbs_mTextHighlightTextRanges);

        int primaryTextColor = getTextColor(viewCustomAttributes, R.attr.dbs_themePrimaryTextColor, R.styleable.DBSMultiColorTextView_dbs_textColor);
        int highlightedTextColor = getTextColor(viewCustomAttributes, R.attr.dbs_themeTertiaryTextColor, R.styleable.DBSMultiColorTextView_dbs_textHighlightedColor);
        viewCustomAttributes.recycle();

        Spannable text = getFormattedText((String) getText(), highlightedTextRanges, primaryTextColor, highlightedTextColor);
        setText(text);
    }

    private int getTextColor(TypedArray viewCustomAttributes, int themeAttr, int styleableColorAttr) {
        TypedArray themeAttributes = getContext().obtainStyledAttributes(new int[]{themeAttr});

        int defaultTextColor = themeAttributes.getColor(0, Color.BLACK);
        int textColor = viewCustomAttributes.getColor(styleableColorAttr, defaultTextColor);

        themeAttributes.recycle();
        return textColor;
    }

    @NonNull
    private Spannable getFormattedText(String fullText,
                                       String commaSeparatedHighlightedTextRanges,
                                       int primaryTextColor,
                                       int highlightedTextColor) {
        Spannable word = new SpannableString(fullText);
        word.setSpan(new ForegroundColorSpan(primaryTextColor), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        boolean isRangeProvided = commaSeparatedHighlightedTextRanges != null;
        if (isRangeProvided) {
            String[] highlightedTextRanges = commaSeparatedHighlightedTextRanges.split(",");
            for (String highlightedTextRange : highlightedTextRanges) {
                String[] indices = highlightedTextRange.split("-");
                int startIndex = Integer.parseInt(indices[0]);
                int endIndex = Integer.parseInt(indices[1]);

                boolean isValidIndicesProvided = fullText.length() > startIndex && fullText.length() >= endIndex;
                if (isValidIndicesProvided) {
                    word.setSpan(new ForegroundColorSpan(highlightedTextColor), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }

        return word;
    }
}