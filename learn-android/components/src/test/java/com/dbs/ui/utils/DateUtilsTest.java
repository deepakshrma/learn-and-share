package com.dbs.ui.utils;

import com.dbs.ui.error.EmptyParametersException;
import com.dbs.ui.error.InvalidDateException;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DateUtilsTest {

    @Test(expected = EmptyParametersException.class)
    public void shouldThrowExceptionIfDateIsNullWhenParsing() throws ParseException, EmptyParametersException {
        DateUtils.parseDate(null, "someFormat");
    }

    @Test(expected = EmptyParametersException.class)
    public void shouldThrowExceptionIfDateIsEmptyWhenParsing() throws ParseException, EmptyParametersException {
        DateUtils.parseDate("", "someFormat");
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionIfDateFormatIsNull() throws ParseException, EmptyParametersException {
        DateUtils.parseDate("02/02/1999", null);
    }

    @Test(expected = ParseException.class)
    public void shouldThrowExceptionIfDateFormatIsEmpty() throws ParseException, EmptyParametersException {
        DateUtils.parseDate("02/02/1999", "");
    }

    @Test(expected = ParseException.class)
    public void shouldThrowExceptionIfDateIsNotInFormat() throws ParseException, EmptyParametersException {
        DateUtils.parseDate("02/02/1999", "yyyy/dd/mm");
    }

    @Test
    public void shouldParseDate() throws ParseException, EmptyParametersException {
        String format = "dd/MM/yyyy";
        String dateString = "02/02/1999";
        Date expectedDate = new SimpleDateFormat(format).parse(dateString);
        Date parsedDate = DateUtils.parseDate(dateString, format);
        assertEquals(expectedDate, parsedDate);
    }

    @Test
    public void shouldReturnFormattedDate() throws InvalidDateException {
        String formattedDateString = DateUtils.getFormattedDate(1999, 1, 2, "dd/MM/yyyy");
        assertEquals("02/02/1999", formattedDateString);
    }

    @Test(expected = InvalidDateException.class)
    public void shouldThrowExceptionWhenMonthIsNegative() throws InvalidDateException {
        DateUtils.getFormattedDate(1999, -1, 100, "dd/MM/yyyy");
    }

    @Test(expected = InvalidDateException.class)
    public void shouldThrowExceptionWhenMonthIsGreaterThan11() throws InvalidDateException {
        DateUtils.getFormattedDate(1999, 12, 100, "dd/MM/yyyy");
    }

    @Test(expected = InvalidDateException.class)
    public void shouldThrowExceptionWhenDayOfMonthIsNegative() throws InvalidDateException {
        DateUtils.getFormattedDate(1999, 1, -2, "dd/MM/yyyy");
    }

    @Test(expected = InvalidDateException.class)
    public void shouldThrowExceptionWhenDayOfMonthIsGreaterThan11() throws InvalidDateException {
        DateUtils.getFormattedDate(1999, 1, 36, "dd/MM/yyyy");
    }

    @Test
    public void shouldReturnTrueIfToday() throws InvalidDateException {
        Calendar today = Calendar.getInstance();
        assertTrue(DateUtils.isToday(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH)));
    }

    @Test
    public void shouldReturnFalseIfYearNotSame() throws InvalidDateException {
        Calendar today = Calendar.getInstance();
        today.add(Calendar.YEAR, 1);
        assertFalse(DateUtils.isToday(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH)));
    }

    @Test
    public void shouldReturnFalseIfMonthNotSame() throws InvalidDateException {
        Calendar today = Calendar.getInstance();
        today.add(Calendar.MONTH, 1);
        assertFalse(DateUtils.isToday(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH)));
    }

    @Test
    public void shouldReturnFalseIfDayNotSame() throws InvalidDateException {
        Calendar today = Calendar.getInstance();
        today.add(Calendar.DAY_OF_MONTH, 1);
        assertFalse(DateUtils.isToday(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH)));
    }

    @Test(expected = InvalidDateException.class)
    public void shouldThrowExceptionWhenDayOfMonthGreaterThanThirtyOne() throws InvalidDateException {
        DateUtils.getCalendar(1999, 2, 32);
    }

    @Test(expected = InvalidDateException.class)
    public void shouldThrowExceptionWhenDayOfMonthLessThanZero() throws InvalidDateException {
        DateUtils.getCalendar(1999, 2, -1);
    }

    @Test(expected = InvalidDateException.class)
    public void shouldThrowExceptionWhenMonthGreaterThanEleven() throws InvalidDateException {
        DateUtils.getCalendar(1999, 12, 1);
    }

    @Test(expected = InvalidDateException.class)
    public void shouldThrowExceptionWhenMonthLessThanZero() throws InvalidDateException {
        DateUtils.getCalendar(1999, -2, 1);
    }

    @Test
    public void shouldReturnValidCalendarInstance() throws InvalidDateException {
        Calendar calendar = DateUtils.getCalendar(1999, 1, 1);

        assertEquals(calendar.get(Calendar.DAY_OF_MONTH), 1);
        assertEquals(calendar.get(Calendar.MONTH), 1);
        assertEquals(calendar.get(Calendar.YEAR), 1999);
    }
}