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

import android.content.Context;
import android.os.Vibrator;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class UtilsTests {
    private static final int DURATION = 100;
    private static final int GAP = 100;
    private static final int INITIAL_GAP = 0;
    private static final long[] successHapticPattern = {INITIAL_GAP, DURATION, GAP, DURATION};
    private static final long[] errorHapticPattern = {INITIAL_GAP, DURATION, GAP, DURATION, GAP, DURATION, GAP, DURATION};
    private static final long[] warningHapticPattern = {INITIAL_GAP, DURATION, GAP, DURATION, GAP, DURATION};
    private static final long[] defaultHapticPattern = {DURATION};

    @Test
    public void shouldAbleToCoverToEMOJI() throws UnsupportedEncodingException {
        assertEquals(Utils.currencyToEmoFlagChar("US"), "\uD83C\uDDFA\uD83C\uDDF8");
        assertEquals(Utils.currencyToEmoFlagChar("INida"), "\uD83C\uDDEE\uD83C\uDDF3");
        assertEquals(Utils.currencyToEmoFlagChar("CNY"), "\uD83C\uDDE8\uD83C\uDDF3");
    }

    @Test
    public void hapticTestDefault() {
        Context mockContext = mock(Context.class);
        Vibrator mockVibrator = mock(Vibrator.class);
        when(mockContext.getSystemService(Context.VIBRATOR_SERVICE)).thenReturn(mockVibrator);
        DBSHapticFeedbackUtils.trigger(mockContext);
        verify(mockVibrator).vibrate(defaultHapticPattern, -1);
    }

    @Test
    public void hapticTestError() {
        Context mockContext = mock(Context.class);
        Vibrator mockVibrator = mock(Vibrator.class);
        when(mockContext.getSystemService(Context.VIBRATOR_SERVICE)).thenReturn(mockVibrator);
        DBSHapticFeedbackUtils.error(mockContext);
        verify(mockVibrator).vibrate(errorHapticPattern, -1);
    }

    @Test
    public void hapticTestSuccess() {
        Context mockContext = mock(Context.class);
        Vibrator mockVibrator = mock(Vibrator.class);
        when(mockContext.getSystemService(Context.VIBRATOR_SERVICE)).thenReturn(mockVibrator);
        DBSHapticFeedbackUtils.success(mockContext);
        verify(mockVibrator).vibrate(successHapticPattern, -1);
    }

    @Test
    public void hapticTestWarning() {
        Context mockContext = mock(Context.class);
        Vibrator mockVibrator = mock(Vibrator.class);
        when(mockContext.getSystemService(Context.VIBRATOR_SERVICE)).thenReturn(mockVibrator);
        DBSHapticFeedbackUtils.warning(mockContext);
        verify(mockVibrator).vibrate(warningHapticPattern, -1);
    }
    @Test
    public void shouldBeSameForDBS() {
        assertEquals(Utils.hasSameDomainAs("https://www.dbs.com", "dbs"), true);
    }
    @Test
    public void shouldBeSameForNotDBS() {
        assertNotEquals(Utils.hasSameDomainAs("https://www.google.com", "dbs"), true);
    }
    @Test
    public void shouldGetLastCharM() {
        assertEquals(StringUtils.getLastCharacter("https://www.google.com"), 'm');
    }
    @Test
    public void shouldGetLastChar0() {
        assertNotEquals(StringUtils.getLastCharacter(""), '0');
    }
}
