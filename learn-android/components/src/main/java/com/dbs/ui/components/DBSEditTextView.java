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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.widget.EditText;

import com.dbs.components.R;
import com.dbs.ui.utils.TextWatchers;

import java.util.regex.Pattern;

import static android.text.InputType.TYPE_TEXT_VARIATION_NORMAL;
import static com.dbs.ui.utils.NumberUtils.DECIMAL_POINT;
import static com.dbs.ui.utils.NumberUtils.getFractionLength;
import static com.dbs.ui.utils.StringUtils.fillZero;

/**
 * The DBSEditTextView component is to create edit text which can format input for numbers, phone number, currency
 * Input type could be specified with xml attribute.
 *
 * @author DBS Bank, AppStudio Team
 */
public class DBSEditTextView extends AppCompatEditText {

    public static final int NUMBER = 1;
    public static final int PHONE = 3;
    public static final int CURRENCY = 4;
    public static final String DEFAULT_KEYS = "0123456789.,";
    public static final int RIGHT_CURRENCY_SYMBOL = 1;
    public static final int LEFT_CURRENCY_SYMBOL = 0;
    private int inputType;
    private String currencySymbol;
    private int currencySymbolPosition;
    private String countryCode;

    public DBSEditTextView(Context context) {
        super(context);
    }

    public DBSEditTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DBSEditTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.DBSEditTextView, 0, 0);
            inputType = attributes.getInt(R.styleable.DBSEditTextView_dbs_inputType, TYPE_TEXT_VARIATION_NORMAL);
            if (inputType == NUMBER) {
                String format = attributes.getString(R.styleable.DBSEditTextView_dbs_editFormat);
                setInputKeys(attributes, DEFAULT_KEYS);
                addNumberTextWatcher(format);
            } else if (inputType == PHONE) {
                addPhoneNumberWatcher(attributes);
            } else if (inputType == CURRENCY) {
                String format = attributes.getString(R.styleable.DBSEditTextView_dbs_editFormat);
                currencySymbol = attributes.getString(R.styleable.DBSEditTextView_dbs_editCurrencySymbol);
                currencySymbolPosition = attributes.getInt(R.styleable.DBSEditTextView_dbs_editCurrencySymbolPosition, TYPE_TEXT_VARIATION_NORMAL);
                setInputKeys(attributes, DEFAULT_KEYS + currencySymbol);
                addNumberTextWatcher(attributes, format);
                addFocusChangeListener(format);
            }

            attributes.recycle();
        }
    }

    private void setInputKeys(TypedArray attributes, String defaultKeys) {
        String keys = attributes.getString(R.styleable.DBSEditTextView_dbs_editDigits);
        this.setInputType(InputType.TYPE_CLASS_NUMBER);
        this.setKeyListener(DigitsKeyListener.getInstance(keys == null ? defaultKeys : keys));
    }


    private void addNumberTextWatcher(TypedArray attributes, String format) {
        String defaultText = attributes.getString(R.styleable.DBSEditTextView_android_text);
        this.addTextChangedListener(new TextWatchers.NumberTextWatcher(format, currencySymbol, currencySymbolPosition, this));
        if (defaultText != null) {
            this.setText(defaultText);
        }
    }

    private void addNumberTextWatcher(String format) {
        this.addTextChangedListener(new TextWatchers.NumberTextWatcher(format, this));
    }

    private void addPhoneNumberWatcher(TypedArray attributes) {
        int maxLen = attributes.getInt(R.styleable.DBSEditTextView_dbs_maxLength, Integer.MAX_VALUE);
        String pattern = attributes.getString(R.styleable.DBSEditTextView_dbs_editPattern);
        String format = attributes.getString(R.styleable.DBSEditTextView_dbs_editFormat);
        String defaultText = attributes.getString(R.styleable.DBSEditTextView_android_text);
        countryCode = attributes.getString(R.styleable.DBSEditTextView_dbs_editCountryPrefix);
        if (countryCode == null) {
            countryCode = "";
        }

        TextWatchers.PhoneNumberWatcher phoneNumberWatcher = new TextWatchers.PhoneNumberWatcher(countryCode, pattern, format, maxLen);
        this.addTextChangedListener(phoneNumberWatcher);
        if (defaultText != null) {
            this.setText(defaultText);
        } else {
            this.setText(countryCode);
        }
        this.setOnFocusChangeListener((v, hasFocus) -> {
            if (!phoneNumberWatcher.isFormatted()) {
                String editableText = String.valueOf(getText());
                String numWithoutCountryCode = editableText;
                if (countryCode != null) {
                    numWithoutCountryCode = editableText.replaceFirst(Pattern.quote(countryCode), "");
                }
                String number = numWithoutCountryCode.replaceAll("\\D", "");
                if (number.length() >= maxLen) {
                    setText(""); // to set beforeEdit equals to countryCode
                    setText(number);
                }
            }
        });

    }


    private void addFocusChangeListener(String format) {
        this.setOnFocusChangeListener((v, hasFocus) -> {
            EditText editText = (EditText) v;
            String digits = editText.getText().toString();
            if (!hasFocus && isDecimalPointPresent(digits, format)) {
                int pattenFractionLength = getFractionLength(format);
                String fraction = getFraction(digits, pattenFractionLength);
                if (currencySymbolPosition == 0) {
                    setText(String.format("%s%s", digits, fraction));
                } else {
                    setText(String.format("%s%s%s", digits.substring(0, digits.length() - 1), fraction, digits.substring(digits.length() - 1)));
                }
            }
        });
    }

    @NonNull
    private String getFraction(String digits, int pattenFractionLength) {
        String fraction = fillZero(pattenFractionLength);
        if (!digits.contains(DECIMAL_POINT)) {
            fraction = DECIMAL_POINT + fraction;
        } else {
            int digitsFractionLength = currencySymbolPosition == LEFT_CURRENCY_SYMBOL ? getFractionLength(digits) : getFractionLength(digits) - 1;
            int fractionSize = pattenFractionLength - digitsFractionLength;
            fraction = fillZero(fractionSize);
        }
        return fraction;
    }

    private boolean isDecimalPointPresent(String digits, String pattern) {
        return pattern.contains(DECIMAL_POINT) && digits.length() > 1;
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        if (inputType == PHONE && !countryCode.isEmpty() && selStart != 0 && selStart == selEnd && selStart <= countryCode.length()) {
            this.setSelection(countryCode.length());
        } else if (inputType == CURRENCY) {
            String text = this.getText().toString();
            if (currencySymbolPosition == RIGHT_CURRENCY_SYMBOL && selEnd == text.length() && text.length() > 0) {
                this.setSelection(selEnd - currencySymbol.length());
            } else if (currencySymbolPosition == LEFT_CURRENCY_SYMBOL && selStart == 0 && text.length() > 0) {
                this.setSelection(selStart + currencySymbol.length());
            }
        }
    }
}