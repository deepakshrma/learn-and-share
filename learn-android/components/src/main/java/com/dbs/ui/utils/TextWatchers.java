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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static com.dbs.ui.components.DBSEditTextView.LEFT_CURRENCY_SYMBOL;
import static com.dbs.ui.utils.NumberUtils.DECIMAL_POINT;
import static com.dbs.ui.utils.NumberUtils.getFractionLength;

/**
 * The TextWatchers class contains different TextWatcher implementation.
 *
 * @author DBS Bank, AppStudio Team
 * @version 1.0.0
 * @since 2018-05-01
 */
public final class TextWatchers {
    private TextWatchers() {
        throw new UnsupportedOperationException(
                "Should not create instance of Util class. Please use as static..");
    }

    /**
     * The NumberTextWatcher is TextWatcher which formats number input as number with Comma or as currency.
     */
    public static class NumberTextWatcher implements TextWatcher {
        private static final char COMMA = ',';
        private boolean mEditing;
        private String pattern;
        private String beforeEdit;
        private boolean isCurrency;
        private int currencySymbolPosition;
        private String currencySymbol;
        private AppCompatEditText editText;

        public NumberTextWatcher(@NonNull String pattern, AppCompatEditText editText) {
            mEditing = false;
            this.pattern = pattern;
            this.isCurrency = false;
            this.editText = editText;
        }

        public NumberTextWatcher(@NonNull String pattern, String currencySymbol, int currencySymbolPosition, AppCompatEditText editText) {
            mEditing = false;
            this.pattern = pattern;
            this.editText = editText;
            this.currencySymbolPosition = currencySymbolPosition;
            this.currencySymbol = currencySymbol;
            if (currencySymbol != null && !currencySymbol.isEmpty()) {
                this.isCurrency = true;
            }
        }

        /**
         * @inheritDoc Store text state before edit.
         */
        @Override
        public void beforeTextChanged(CharSequence text, int start, int count, int after) {
            beforeEdit = text.toString();
        }

        /**
         * @inheritDoc Update cursor position if at current position of cursor is at comma.
         */
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (isCursorAtComa(count)) {
                editText.setSelection(editText.getSelectionStart() - 1);
            }
        }

        private boolean isCursorAtComa(int count) {
            return (count == 0) && beforeEdit.length() > editText.getSelectionStart() && (beforeEdit.charAt(editText.getSelectionStart()) == COMMA);
        }

        /**
         * @inheritDoc Update text input according to requirement to number or currency.
         */
        @Override
        public synchronized void afterTextChanged(Editable editable) {
            if (!mEditing) {
                mEditing = true;
                String digits = editable.toString();
                digits = digits.replaceAll("[^\\d.]", "");
                if (isInvalidInput(editable, digits)) return;
                String[] numbers = digits.split("\\.");
                String afterDecimal = getStringAfterDecimal(digits, numbers);
                String beforeDecimal = numbers.length > 0 ? numbers[0] : "";
                if (digits.length() > 0) {
                    try {
                        beforeDecimal = NumberUtils.formatAsDecimal(beforeDecimal, pattern);
                    } catch (NumberFormatException nfe) {
                        Log.e("NumberTextWatcher", nfe.toString());
                        editable.clear();
                    }
                }
                String output = beforeDecimal + afterDecimal;
                int changeInCursorPosition = output.length() - editable.length();
                setText(editable, output);
                changeCursorPositionAfterFormating(editable, changeInCursorPosition);
                mEditing = false;
            }
        }

        @NonNull
        private String getStringAfterDecimal(String digits, String[] numbers) {
            String afterDecimal = numbers.length < 2 ? "" : DECIMAL_POINT + numbers[1];
            if (afterDecimal.isEmpty() && digits.contains(DECIMAL_POINT)) {
                afterDecimal = DECIMAL_POINT;
            }
            return afterDecimal;
        }

        private boolean isInvalidInput(Editable editable, String digits) {
            if (hasExtraDecimalPoints(digits) || hasExtraCharactersPresentAfterDecimal(digits, pattern) || digits.equals(DECIMAL_POINT)) {
                editable.replace(0, editable.length(), beforeEdit);
                mEditing = false;
                return true;
            }
            return false;
        }

        private boolean hasExtraCharactersPresentAfterDecimal(String digits, String pattern) {
            int patternFractionLength = getFractionLength(pattern);
            int digitsFractionLength = getFractionLength(digits);
            int extraCharacterLength = digitsFractionLength - patternFractionLength;
            return extraCharacterLength > 0;
        }

        private void changeCursorPositionAfterFormating(Editable editable, int changeInPosition) {
            changeInPosition = isCurrency ? changeInPosition + 1 : changeInPosition;
            if (changeInPosition > 0 && editText.getSelectionStart() != editable.length()) {
                editText.setSelection(editText.getSelectionStart() + 1);
            }
        }

        private void setText(Editable editable, String digits) {
            if (isCurrency) {
                handleCurrencySymbol(editable, digits);
            } else {
                editable.replace(0, editable.length(), digits);
            }
        }

        private void handleCurrencySymbol(Editable editable, String digits) {
            if (currencySymbolPosition == LEFT_CURRENCY_SYMBOL) {
                editable.replace(0, editable.length(), currencySymbol + digits);
            } else {
                editable.replace(0, editable.length(), digits + currencySymbol);
            }
        }

        private boolean hasExtraDecimalPoints(String number) {
            return StringUtils.countOccurrencesOf(number, DECIMAL_POINT) > 1;
        }
    }

    /**
     * The PhoneNumberWatcher is TextWatcher which formats input as phone number.
     */
    public static class PhoneNumberWatcher implements TextWatcher {
        private static final String TAG = PhoneNumberWatcher.class.getSimpleName();
        private boolean isFormatted;
        private boolean isEditing;
        private int cursorPosition;
        private int maxLen;
        private String beforeEdit;
        private String editableText;
        private String countryCode;
        private String pattern;
        private String format;

        public PhoneNumberWatcher(@NonNull String countryCode, String pattern, String format, int maxLen) {
            this.isEditing = false;
            this.countryCode = countryCode;
            this.pattern = pattern;
            this.format = format;
            this.maxLen = maxLen;
        }

        /**
         * Returns if the current input is formatted or not
         */
        public boolean isFormatted() {
            return isFormatted;
        }

        /**
         * @inheritDoc
         * Store text state and cursor position before edit.
         */
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            beforeEdit = s.toString();
            cursorPosition = start;
        }

        /**
         * @inheritDoc
         */
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Do nothing on Text Changed
        }

        /**
         * @inheritDoc
         * Update input with formatted text.
         */
        @Override
        public void afterTextChanged(Editable editable) {
            editableText = editable.toString();
            if (!isEditing && !editableText.trim().equals(countryCode.trim())) {
                isEditing = true;
                editable.replace(0, editable.length(), formatPhoneNumber());
                isEditing = false;
            }
        }

        private String formatPhoneNumber() {
            String numWithoutSplChars = editableText.replaceAll("\\D", "");
            String countryCodeNum = countryCode.replaceAll("\\D", "");
            String numWithoutCountryCode = numWithoutSplChars.replaceFirst(Pattern.quote(countryCodeNum), "");
            if ((beforeEdit.length() <= editableText.length()) || (cursorPosition <= countryCode.length() - 1)) {
                String updatedNumber = handleUpdatedText(numWithoutCountryCode);
                return updatedNumber != null ? updatedNumber : countryCode;
            } else {
                return handleDeleteText(numWithoutCountryCode);
            }
        }

        @Nullable
        private String handleUpdatedText(String number) {
            String numBeforeEdit = beforeEdit.trim().replaceFirst(Pattern.quote(countryCode), "");
            numBeforeEdit = numBeforeEdit.replaceAll("\\D", "");
            try {
                if (pattern != null && format != null) {
                    isFormatted = true;
                    Pattern p = Pattern.compile(pattern);
                    String formattedNum = formatNumber(numBeforeEdit, p);
                    return formattedNum != null ? formattedNum : countryCode + p.matcher(number).replaceAll(format);
                } else {
                    isFormatted = false;
                    String formattedNum = formatNumber(numBeforeEdit, null);
                    return formattedNum != null ? formattedNum : countryCode + number;
                }
            } catch (PatternSyntaxException pse) {
                Log.e(TAG, "Invalid Pattern: " + pse.getMessage());
            }
            return null;
        }

        private String formatNumber(String numBeforeEdit, Pattern p) {
            if (numBeforeEdit.length() == maxLen || editableText.length() < countryCode.length()
                    || (!numBeforeEdit.isEmpty() && cursorPosition <= countryCode.length() - 1)) {
                if (p != null) {
                    return countryCode + p.matcher(numBeforeEdit).replaceAll(format);
                } else {
                    return countryCode + numBeforeEdit;
                }
            }
            return null;
        }

        private String handleDeleteText(String number) {
            isFormatted = false;
            if (beforeEdit.trim().equals(countryCode.trim())) {
                return countryCode;
            }
            return countryCode + number;
        }
    }

    /**
     * AfterChangeTextWatcher class is simplified version of TextWatcher. It will just listen afterTextChanged and return value
     */
    public abstract static class AfterChangeTextWatcher implements TextWatcher {
        /**
         * @inheritDoc
         */
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Do nothing on before Text Changed
        }

        /**
         * @inheritDoc
         */
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Do nothing on Text Changed
        }

        /**
         * @inheritDoc
         */
        @Override
        public void afterTextChanged(Editable editable) {
            String editableText = editable.toString();
            this.onTextChange(editableText);
        }

        /**
         * This method is called after text is changed.
         * @param currentText updated text.
         */
        public abstract void onTextChange(String currentText);
    }
}
