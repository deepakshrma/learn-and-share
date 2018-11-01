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

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.dbs.components.R;
import com.dbs.ui.BaseView;
import com.dbs.ui.error.PinLengthException;
import com.dbs.ui.styleable.StyleableComponent;

import java.util.ArrayList;
import java.util.List;

import static com.dbs.ui.utils.ThemeUtils.getThemeColor;

/**
 * The DBSOTPEditText component is to create edit text where you can enter OTP.
 * (It is shown as pins)
 *
 * @author DBS Bank, AppStudio Team
 */
public class DBSOTPEditText extends BaseView implements TextWatcher, View.OnClickListener, StyleableComponent {

    public static final int DEFAULT_OTP_LENGTH = 6;
    public static final String OTP_LENGTH_EXCEPTION_MESSAGE = "Please check your otp Length";
    public static final int HIDE_KEYBOARD_FLAG_0 = 0;
    private Context mContext;
    private LinearLayout pinsView;
    private EditText hiddenEditText;
    private OtpEnteredHandler completionHandler;
    private View statusLine;
    private AppCompatTextView statusTextView;
    private List<AppCompatTextView> pins;
    private int filledUntil = 0;
    private int mEmptyPinColor;
    private int mFilledPinColor;
    private int hintStatusColor;
    private int errorStatusColor;
    private float pinFontSize;
    private String pinCharacter;

    public DBSOTPEditText(Context context) {
        this(context, null);
    }

    public DBSOTPEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DBSOTPEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.mContext = context;
        initializeView(attrs);
    }

    @Override
    protected void viewInflated(View view, String type, AttributeSet attrs) {
        //nothing to do here yet.
    }

    @Override
    protected int provideLayoutId(String type) {
        return R.layout.otp_edittext_layout;
    }

    /**
     * Sets error message and red color status line
     * @param errorMessage message to be shown
     */
    public void showErrorMessage(String errorMessage) {
        statusLine.setBackgroundColor(errorStatusColor);
        statusTextView.setText(errorMessage);
        statusTextView.setTextColor(getThemeColor(mContext, R.attr.dbs_themeFailColor));
    }

    /**
     * Sets status message to empty string and transparent color to status line
     */
    public void resetStatus() {
        statusLine.setBackgroundColor(Color.TRANSPARENT);
        statusTextView.setText(null);
    }

    /**
     * Sets hint message and sets status line color
     * @param hint message to be shown
     */
    public void showHint(String hint) {
        statusLine.setBackgroundColor(hintStatusColor);
        statusTextView.setText(hint);
        statusTextView.setTextColor(getThemeColor(mContext, R.attr.dbs_themeSecondaryTextColor));
    }

    /**
     * @inheritDoc
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Do nothing on beforeTextChanged
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // Do nothing on onTextChanged
    }

    /**
     * @inheritDoc
     * Pins are refreshed to show current input.
     * If the length has reached to max length of the OTP then completion handler will be called if available.
     * @see OtpEnteredHandler
     */
    @Override
    public void afterTextChanged(Editable s) {
        int currentLength = s.length();
        setFilledUntil(currentLength);
        if (filledUntil == pins.size()) {
            hideKeyboard();
            if (completionHandler != null)
                completionHandler.otpEntered(s.subSequence(0, filledUntil).toString());
        }
    }

    /**
     * @inheritDoc
     * Keyboard is shown to enter OTP.
     */
    @Override
    public void onClick(View v) {
        hiddenEditText.requestFocus();
        showKeyboard();
    }

    /**
     * @inheritDoc
     * @param style Applied to pinsView
     */
    @Override
    public void applyStyle(@StyleRes int style) {
        TypedArray typedArray = mContext.obtainStyledAttributes(style, R.styleable.DBSOTPEditText);
        applyStyle(typedArray);
        typedArray.recycle();
    }

    /**
     * Set otp entered callback to do some action when user is done entering otp
     * Ex.
     * <code>dbsOtpEditText.setOtpEnteredCallback(otp -> {
     *      System.out.println("OTP entered");
     *  });</code>
     * @param completionHandler callback handler to be called
     */
    public void setOtpEnteredCallback(OtpEnteredHandler completionHandler) {
        this.completionHandler = completionHandler;
    }

    /**
     * Get pin that was entered/set
     */
    public String getOtp() {
        return hiddenEditText.getText().toString();
    }

    /**
     * Set otp.
     * This could be used when the otp is read from SMS and is to be set programmatically.
     * Ex.
     * <code>dbsOtpEditText.setOtp("SecretPin");</code>
     *
     * @param otp otp to be set
     */
    public void setOtp(String otp) {
        if (otp.length() != pins.size()) {
            throw new PinLengthException(OTP_LENGTH_EXCEPTION_MESSAGE);
        }
        hiddenEditText.setText(otp);
        hiddenEditText.setSelection(otp.length());
    }

    public int getNumberOfPins() {
        return pins.size();
    }

    public void setNumberOfPins(int targetNumberOfPins) {
        int totalNumberOfPinsExisting = pins.size();
        if (targetNumberOfPins == totalNumberOfPinsExisting)
            return;
        if (targetNumberOfPins > totalNumberOfPinsExisting) {
            addPins(targetNumberOfPins - totalNumberOfPinsExisting);
        } else {
            reducePinsTo(targetNumberOfPins);
        }
        filledUntil = Math.min(filledUntil, targetNumberOfPins);
        hiddenEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(targetNumberOfPins)});
    }

    /**
     * Reset otp to empty text.
     * This could be used when the otp is wrong and erase otp so that user can type new otp than erasing it manually and then typing it.
     * Ex.
     * <code>dbsOtpEditText.resetOtp();</code>
     */
    public void resetOtp() {
        hiddenEditText.setText("");
        setFilledUntil(0);
    }

    private void initializeView(AttributeSet attrs) {
        hiddenEditText = view.findViewById(R.id.hidden_edit_text);
        pinsView = view.findViewById(R.id.pins);
        statusLine = view.findViewById(R.id.status_line);
        statusTextView = view.findViewById(R.id.status_text_view);

        hintStatusColor = getThemeColor(mContext, R.attr.dbs_themeDividerColor);
        errorStatusColor = getThemeColor(mContext, R.attr.dbs_themeFailColor);
        pinCharacter = getResources().getString(R.string.pin);
        pins = new ArrayList<>();

        TypedArray viewCustomAttributes = mContext.obtainStyledAttributes(attrs, R.styleable.DBSOTPEditText);
        applyStyle(viewCustomAttributes);
        viewCustomAttributes.recycle();

        hiddenEditText.addTextChangedListener(this);
        hiddenEditText.setCursorVisible(false);
        setOnClickListener(this);
    }

    private void applyStyle(TypedArray typedArray) {
        mEmptyPinColor = typedArray.getColor(R.styleable.DBSOTPEditText_dbs_otpEmptyPinColor, getThemeColor(mContext, R.attr.dbs_themeDividerColor));
        mFilledPinColor = typedArray.getColor(R.styleable.DBSOTPEditText_dbs_otpFilledPinColor, getThemeColor(mContext, R.attr.dbs_themePrimaryTextColor));
        pinFontSize = typedArray.getDimension(R.styleable.DBSOTPEditText_dbs_fontSize, getResources().getDimension(R.dimen.default_otp_pin_size));
        redrawPins(typedArray);
    }

    private void redrawPins(TypedArray typedArray) {
        reducePinsTo(0);
        setNumberOfPins(typedArray.getInt(R.styleable.DBSOTPEditText_dbs_otpNumberOfPins, DEFAULT_OTP_LENGTH));
    }

    private void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.showSoftInput(hiddenEditText, InputMethodManager.SHOW_IMPLICIT);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindowToken(), HIDE_KEYBOARD_FLAG_0);
    }

    private void setFilledUntil(int filledPinsCount) {
        if (filledPinsCount == filledUntil)
            return;
        this.filledUntil = Math.min(filledPinsCount, getNumberOfPins());
        updatePinsBackground();
    }

    private void updatePinsBackground() {
        for (int i = 0; i < filledUntil; i++)
            pins.get(i).setTextColor(mFilledPinColor);
        for (int i = filledUntil, totalPins = getNumberOfPins(); i < totalPins; i++)
            pins.get(i).setTextColor(mEmptyPinColor);
    }

    private void reducePinsTo(int targetNumberOfPins) {
        for (int i = pins.size() - 1; i >= targetNumberOfPins; i--) {
            AppCompatTextView view = pins.get(i);
            pinsView.removeView(view);
            pins.remove(view);
        }
    }

    private void addPins(int numberOfPins) {
        for (int i = 0; i < numberOfPins; i++) {
            AppCompatTextView textView = createPin();
            pinsView.addView(textView);
            pins.add(textView);
        }
    }

    @NonNull
    private AppCompatTextView createPin() {
        AppCompatTextView textView = new AppCompatTextView(mContext, null, R.attr.dbs_themeSmallTextStyle);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = getResources().getDimensionPixelOffset(R.dimen.x_small_margin);
        layoutParams.setMargins(margin, margin, margin, margin);
        textView.setLayoutParams(layoutParams);
        textView.setTextColor(mEmptyPinColor);
        textView.setText(pinCharacter);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, pinFontSize);
        return textView;
    }

    /**
     * When OtpEnteredHandler is set on DBSOTPEditText, its methods will
     * be called when otp is entered.
     */
    public interface OtpEnteredHandler {
        /**
         * This method is called to notify that, the pin was entered.
         *
         * @param pin Entered pin text.
         */
        void otpEntered(String pin);
    }

}