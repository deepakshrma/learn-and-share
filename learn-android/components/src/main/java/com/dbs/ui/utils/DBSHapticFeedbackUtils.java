package com.dbs.ui.utils;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

/**
 * This class provides the utility to provide haptic feedback to user using Vibrator Service
 * Note: App needs to request for VIBRATE permission for this to work.
 */
public final class DBSHapticFeedbackUtils {
    private static final int DURATION = 100;
    private static final int GAP = 100;
    private static final int INITIAL_GAP = 0;
    private static final long[] successHapticPattern = {INITIAL_GAP, DURATION, GAP, DURATION};
    private static final long[] errorHapticPattern = {INITIAL_GAP, DURATION, GAP, DURATION, GAP, DURATION, GAP, DURATION};
    private static final long[] warningHapticPattern = {INITIAL_GAP, DURATION, GAP, DURATION, GAP, DURATION};
    private static final long[] defaultHapticPattern = {DURATION};

    private DBSHapticFeedbackUtils() {
        throw new UnsupportedOperationException(
                "Should not create instance of Util class. Please use as static..");
    }
    /**
     * Call this method to trigger success haptic feedback on device for given type
     *
     * @param context Context instance to get the System Service
     */
    public static void success(Context context) {
        trigger(context, successHapticPattern);
    }

    /**
     * Call this method to trigger error haptic feedback on device for given type
     *
     * @param context Context instance to get the System Service
     */
    public static void error(Context context) {
        trigger(context, errorHapticPattern);
    }

    /**
     * Call this method to trigger warning haptic feedback on device for given type
     *
     * @param context Context instance to get the System Service
     */
    public static void warning(Context context) {
        trigger(context, warningHapticPattern);
    }

    /**
     * Call this method to trigger default haptic feedback on device for given type
     *
     * @param context Context instance to get the System Service
     */
    public static void trigger(Context context) {
        trigger(context, defaultHapticPattern);
    }

    public static void trigger(Context context, final long[] pattern) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            if (Build.VERSION.SDK_INT >= 26)
                vibrator.vibrate(VibrationEffect.createWaveform(pattern, -1));
            else vibrator.vibrate(pattern, -1);
        }
    }
}
