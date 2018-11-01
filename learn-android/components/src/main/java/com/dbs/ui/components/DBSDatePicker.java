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

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dbs.components.R;
import com.dbs.ui.components.forms.DBSTextInputCardLayout;
import com.dbs.ui.error.EmptyParametersException;
import com.dbs.ui.error.InvalidDateException;
import com.dbs.ui.utils.DateUtils;
import com.dbs.ui.utils.FontCache;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * The DBSDatePicker is a component to create a simple date picker view
 *
 * @author DBS Bank, AppStudio Team
 */
public class DBSDatePicker extends RelativeLayout implements View.OnTouchListener {
    private static final String LOG_TAG = DBSDatePicker.class.getSimpleName();
    private static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
    private Calendar initialDateForPicker;
    private Calendar minDateForPicker;
    private Calendar maxDateForPicker;
    private TextInputEditText calendarTextInputEditText;
    private TextInputLayout calendarTextInputLayout;
    private DBSTextInputCardLayout textInputLayout;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private String dateFormat = DEFAULT_DATE_FORMAT;
    private boolean includeToday = false;
    private Calendar selectedCalendar;
    private TextView calendarErrorTextView;

    public DBSDatePicker(Context context) {
        super(context);
        init(context, null, 0);
    }

    public DBSDatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public DBSDatePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(context, R.layout.layout_date_picker_view, this);
        textInputLayout = findViewById(R.id.calendarTextInputLayout);
        calendarTextInputLayout = textInputLayout.getTextInputLayout();
        calendarTextInputEditText = textInputLayout.getTextInputEditTextView();
        calendarErrorTextView = textInputLayout.getErrorTextView();
        String hintText = null;
        String calendarInitialDateString = null;
        String minDateString = null;
        String maxDateString = null;
        Drawable drawable = null;
        int textColor = -1;
        int errorTextColor = -1;

        if (attrs != null) {
            TypedArray viewCustomAttributes = context.obtainStyledAttributes(attrs, R.styleable.DBSDatePicker, defStyleAttr, 0);

            hintText = viewCustomAttributes.getString(R.styleable.DBSDatePicker_android_hint);
            calendarInitialDateString = viewCustomAttributes.getString(R.styleable.DBSDatePicker_dbs_dateInitialDate);
            drawable = viewCustomAttributes.getDrawable(R.styleable.DBSDatePicker_android_src);
            textColor = viewCustomAttributes.getColor(R.styleable.DBSDatePicker_android_textColor, -1);
            errorTextColor = viewCustomAttributes.getColor(R.styleable.DBSDatePicker_dbs_textErrorColor, -1);
            minDateString = viewCustomAttributes.getString(R.styleable.DBSDatePicker_dbs_dateMinDate);
            maxDateString = viewCustomAttributes.getString(R.styleable.DBSDatePicker_dbs_dateMaxDate);
            if (viewCustomAttributes.hasValue(R.styleable.DBSDatePicker_dbs_dateFormat)) {
                setDateFormat(viewCustomAttributes.getString(R.styleable.DBSDatePicker_dbs_dateFormat));
            }

            final int textSize = viewCustomAttributes.getDimensionPixelSize(R.styleable.DBSDatePicker_android_textSize, -1);
            if (textSize != -1) {
                setTextSize(textSize);
            }

            final int errorTextSize = viewCustomAttributes.getDimensionPixelSize(R.styleable.DBSDatePicker_dbs_textErrorSize, -1);
            if (textSize != -1) {
                setErrorTextSize(errorTextSize);
            }

            setTypeFaceFromAttributesIfAvailable(context, viewCustomAttributes);
            setIncludeToday(viewCustomAttributes.getBoolean(R.styleable.DBSDatePicker_dbs_dateIncludeToday, false));
            viewCustomAttributes.recycle();
        }
        setHint(hintText);
        setFocusAndInputModeForEditText();
        setInitialDateForPicker(calendarInitialDateString);
        setMinDateForPicker(minDateString);
        setMaxDateForPicker(maxDateString);
        if (drawable != null) {
            setCalendarImageSrc(drawable);
        }
        setTextColor(textColor);
        setErrorTextColor(errorTextColor);
        setUpActions();
    }

    private void setFocusAndInputModeForEditText() {
        calendarTextInputEditText.setFocusable(false);
        calendarTextInputEditText.setFocusableInTouchMode(true);
        calendarTextInputEditText.setCursorVisible(false);
        calendarTextInputEditText.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
    }

    private void setTypeFaceFromAttributesIfAvailable(Context context, TypedArray typedArray) {
        int font = typedArray.getResourceId(R.styleable.DBSDatePicker_dbs_font, -1);
        if (font != -1) {
            setTypeFace(ResourcesCompat.getFont(context, font));
            return;
        }

        String fontFileName = typedArray.getString(R.styleable.DBSDatePicker_dbs_font);
        if (fontFileName != null) {
            Typeface typeface = FontCache.getInstance().getTypeface(context, fontFileName);
            setTypeFace(typeface);
        }
    }

    /**
     * Sets the date format.
     * Supports all java date formats.
     */
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * Sets if today word should be prefixed when returning formatted selected date.
     */
    public void setIncludeToday(boolean includeToday) {
        this.includeToday = includeToday;
    }

    private void setTextSize(int textSize) {
        calendarTextInputEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    private void setErrorTextSize(int textSize) {
        calendarErrorTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    /**
     * @inheritDoc
     * Request focus on date edit text when motion event is up.
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            showDatePicker();
            calendarTextInputEditText.requestFocus();
            return true;
        }
        return false;
    }

    /**
     * Returns selected date (Formatted).
     */
    public String getDate() {
        String formattedDate = "";
        formattedDate = selectedCalendar == null ? "" : DateUtils.getFormattedDate(selectedCalendar, dateFormat);
        return formattedDate;
    }

    /**
     * Returns date displayed in edit text.
     */
    public String getDateDisplayed() {
        return calendarTextInputEditText.getText().toString();
    }

    /**
     * Sets date set listener.
     */
    public void setOnDateSetListener(DatePickerDialog.OnDateSetListener onDateSetListener) {
        this.onDateSetListener = onDateSetListener;
    }

    /**
     * Sets typeface for date.
     */
    public void setTypeFace(Typeface typeface) {
        calendarTextInputLayout.setTypeface(typeface);
        calendarTextInputEditText.setTypeface(typeface);
        calendarErrorTextView.setTypeface(typeface);
    }

    /**
     * Sets hint for date picker.
     */
    public void setHint(String hintText) {
        calendarTextInputLayout.setHint(hintText);
    }

    private void setTextColor(int color) {
        if (color != -1) {
            calendarTextInputEditText.setTextColor(color);
        }
    }

    private void setErrorTextColor(int color) {
        if (color != -1) {
            textInputLayout.setErrorColor(color);
        }
    }

    /**
     * Sets minimum date (String) for picker.
     *
     * @param minDateForPicker date in String format
     */
    public void setMinDateForPicker(String minDateForPicker) {
        try {
            this.minDateForPicker = parseDate(minDateForPicker);
        } catch (ParseException e) {
            Log.e(LOG_TAG, "setMinDateForPicker() doesn't support initial date in format " + this.dateFormat, e);
        } catch (EmptyParametersException e) {
            //Nothing to be done.
        }
    }

    /**
     * Sets minimum date (Calendar) for picker.
     *
     * @param minDateForPicker date in Calendar format
     */
    public void setMinDateForPicker(Calendar minDateForPicker) {
        this.minDateForPicker = minDateForPicker;
    }

    /**
     * Sets maximum date (Calendar) for picker.
     *
     * @param maxDateForPicker date in Calendar format
     */
    public void setMaxDateForPicker(Calendar maxDateForPicker) {
        this.maxDateForPicker = maxDateForPicker;
    }

    /**
     * Sets maximum date (String) for picker.
     *
     * @param maxDateForPicker date in String format
     */
    public void setMaxDateForPicker(String maxDateForPicker) {
        try {
            this.maxDateForPicker = parseDate(maxDateForPicker);
        } catch (ParseException e) {
            Log.e(LOG_TAG, "setMaxDateForPicker() doesn't support initial date in format " + this.dateFormat, e);
        } catch (EmptyParametersException e) {
            //Nothing to be done.
        }
    }

    /**
     * Sets error on edit text
     *
     * @param error error message to be displayed.
     */
    public void setError(String error) {
        textInputLayout.setError(error);
    }

    private Calendar getInitialDateForPicker() {
        Calendar calendar = initialDateForPicker;
        if (initialDateForPicker == null) {
            calendar = Calendar.getInstance();
        }
        return calendar;
    }

    /**
     * Sets initial date (String)
     */
    public void setInitialDateForPicker(String initialDateCalendarString) {
        try {
            initialDateForPicker = parseDate(initialDateCalendarString);
        } catch (ParseException e) {
            Log.e(LOG_TAG, "setInitialDateForPicker() doesn't support initial date in format " + this.dateFormat, e);
        } catch (EmptyParametersException e) {
            //Nothing to be done.
        }
    }

    private void showDatePicker() {
        Calendar currentSelectedDate = this.selectedCalendar == null ? getInitialDateForPicker() : this.selectedCalendar;
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                this::onDateSelected,
                currentSelectedDate.get(Calendar.YEAR),
                currentSelectedDate.get(Calendar.MONTH),
                currentSelectedDate.get(Calendar.DATE)
        );
        DatePicker datePicker = datePickerDialog.getDatePicker();
        if (minDateForPicker != null) {
            datePicker.setMinDate(minDateForPicker.getTimeInMillis());
        }
        if (maxDateForPicker != null) {
            datePicker.setMaxDate(maxDateForPicker.getTimeInMillis());
        }
        datePickerDialog.show();
    }

    private void onDateSelected(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
        try {
            updateEditText(selectedYear, selectedMonth, selectedDayOfMonth);
            this.selectedCalendar = DateUtils.getCalendar(selectedYear, selectedMonth, selectedDayOfMonth);
            notifyDateListener(view, selectedYear, selectedMonth, selectedDayOfMonth);
        } catch (InvalidDateException e) {
            Log.e(LOG_TAG, "onDateSelected() returned invalid date", e);
        }
    }

    private void notifyDateListener(DatePicker view, int selectedYear, int selectedMonth,
                                    int selectedDayOfMonth) {
        if (onDateSetListener != null) {
            onDateSetListener.onDateSet(view, selectedYear, selectedMonth, selectedDayOfMonth);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setUpActions() {
        setOnClickListener(v -> showDatePicker());
        calendarTextInputEditText.setOnTouchListener(this);
    }

    private void setCalendarImageSrc(Drawable drawable) {
        calendarTextInputEditText.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
    }

    private void updateEditText(int year, int month, int dayOfMonth) throws InvalidDateException {
        String formattedDateString = DateUtils.getFormattedDate(year, month, dayOfMonth, dateFormat);
        if (includeToday && DateUtils.isToday(year, month, dayOfMonth)) {
            formattedDateString = getResources().getString(R.string.date_with_today_prefix, formattedDateString);
        }
        calendarTextInputEditText.setText(formattedDateString);
    }

    private Calendar parseDate(String dateString) throws ParseException, EmptyParametersException {
        Date date = DateUtils.parseDate(dateString, dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}