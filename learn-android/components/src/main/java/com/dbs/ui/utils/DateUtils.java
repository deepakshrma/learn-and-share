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

import com.dbs.ui.error.EmptyParametersException;
import com.dbs.ui.error.InvalidDateException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * The DateUtils class helps to create custom component/view.
 * It implements basic constructor while extending FrameLayout seamlessly.
 *
 * @author DBS Bank, AppStudio Team
 * @version 1.0.0
 * @since 2018-05-01
 */
public class DateUtils {

    private DateUtils() {
        throw new UnsupportedOperationException(
                "Should not create instance of Util class. Please use as static..");
    }

    /**
     * Parse date.
     *
     * @param dateString date in string format to be parsed.
     * @param format date format in which date should be parsed
     * @return date formatted in given date format
     */
    public static Date parseDate(String dateString, String format) throws EmptyParametersException, ParseException {
        if (dateString == null || dateString.isEmpty()) {
            throw new EmptyParametersException("Date is empty");
        }
        DateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        formatter.setLenient(false);

        return formatter.parse(dateString);
    }

    /**
     * Format the values of the fields year, month, and dayOfMonth with dateFormat.
     *
     * @param year       the value used to set the <code>YEAR</code> calendar field.
     * @param month      the value used to set the <code>MONTH</code> calendar field.
     *                   Month value is 0-based. e.g., 0 for January.
     * @param dayOfMonth the value used to set the <code>DAY_OF_MONTH</code> calendar field.
     * @throws InvalidDateException if day or month is invalid
     */
    public static String getFormattedDate(int year, int month, int dayOfMonth, String dateFormat) throws InvalidDateException {
        Calendar calendar = getCalendar(year, month, dayOfMonth);
        return getFormattedDate(calendar, dateFormat);
    }

    /**
     * Format the date with dateFormat.
     *
     * @param calendar the value to be formatted
     * @param dateFormat format for date
     * @return formatted date in String
     */
    public static String getFormattedDate(Calendar calendar, String dateFormat) {
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.getDefault());
        return format.format(date);
    }

    /**
     * Returns if the parameters passed are for today's date
     * @throws InvalidDateException if day or month is invalid
     */
    public static boolean isToday(int year, int month, int dayOfMonth) throws InvalidDateException {
        Calendar date = getCalendar(year, month, dayOfMonth);
        Calendar today = Calendar.getInstance();
        return today.get(Calendar.YEAR) == date.get(Calendar.YEAR)
                && today.get(Calendar.MONTH) == date.get(Calendar.MONTH)
                && today.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Returns calendar instance for details passed.
     * @throws InvalidDateException if day or month is invalid
     */
    public static Calendar getCalendar(int year, int month, int dayOfMonth) throws InvalidDateException {
        if (month < 0 || month > 11 || dayOfMonth < 0 || dayOfMonth > 31) {
            throw new InvalidDateException("Invalid Date argument");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        return calendar;
    }
}
