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


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

/**
 * The NumberUtils class contains helper methods and static classes for numbers/formatter.
 *
 * @author DBS Bank, AppStudio Team
 * @version 1.0.0
 * @since 2018-05-01
 */
public final class NumberUtils {
    public static final String DECIMAL_POINT = ".";

    private NumberUtils() {
        throw new UnsupportedOperationException(
                "Should not create instance of Util class. Please use as static..");
    }

    /**
     * This method convert string as Decimal string
     *
     * @param number
     * @param pattern { ex: "0.###E0" formats the number 1234 as "1.234E3"}
     * @return
     */
    public static String formatAsDecimal(String number, String pattern) {
        DecimalFormat decimalFormatter = new DecimalFormat(pattern);
        BigDecimal decimal = new BigDecimal(number);
        decimalFormatter.setRoundingMode(RoundingMode.UNNECESSARY);
        return decimalFormatter.format(decimal);
    }

    /**
     * Returns the number of digits after decimal point
     */
    public static int getFractionLength(String digits) {
        String[] numbers = digits.split("\\.");
        if (numbers.length < 2)
            return 0;
        if (numbers.length > 2)
            throw new NumberFormatException("Too many dots found" + digits);
        return numbers[1].length();
    }

    /**
     * Am I reinventing the wheel?
     * <p>
     * Format big decimal value base on the currency
     * Eg, (USD) 1000 ->USD 1,000.00
     *
     * @param bigDecimal
     * @param currency
     * @return
     */
    public static String formatDecimalWithCurrency(BigDecimal bigDecimal, String currency) {
        DecimalFormat n = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.getDefault());
        n.setRoundingMode(RoundingMode.UNNECESSARY);
        n.setCurrency(Currency.getInstance(currency));
        DecimalFormatSymbols symbols = n.getDecimalFormatSymbols();
        symbols.setCurrencySymbol(String.format("%s ", currency)); // Don't use null.
        n.setDecimalFormatSymbols(symbols);
        double val = bigDecimal.doubleValue();
        return n.format(val);
    }


}