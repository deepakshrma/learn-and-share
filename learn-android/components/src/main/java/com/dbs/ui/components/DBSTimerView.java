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
import android.os.CountDownTimer;
import android.text.format.DateUtils;
import android.util.AttributeSet;

import com.dbs.components.R;
import com.dbs.ui.error.MissingAttributeException;

/**
 * The DBSTimerView is a component to create a timer for specified duration (as XML attribute).
 *
 * @author DBS Bank, AppStudio Team
 * @version 1.0.0
 * @since 2018-05-01
 */

public class DBSTimerView extends android.support.v7.widget.AppCompatTextView {
    private static final int IDEAL_INTERVAL = 250;
    private TimerListener listener;
    private CountDownTimer timer;

    public DBSTimerView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.dbs_themeSmallTextStyle);
    }

    public DBSTimerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DBSTimerView);
        if (!typedArray.hasValue(R.styleable.DBSTimerView_dbs_timerDuration)) {
            throw new MissingAttributeException("custom:timerDuration");
        }
        int timerDuration = typedArray.getInt(R.styleable.DBSTimerView_dbs_timerDuration, 0);
        int timerInterval = typedArray.getInt(R.styleable.DBSTimerView_dbs_timerInterval, 1);
        boolean shouldIncrement = typedArray.getBoolean(R.styleable.DBSTimerView_dbs_timerShouldIncrement, false);
        typedArray.recycle();
        initializeCounter(timerDuration, timerInterval, shouldIncrement);
    }

    private void initializeCounter(int timerDuration, int timerInterval, boolean countUpTimer) {
        int timerDurationInMillis = timerDuration * 1000;
        int negateFactor = countUpTimer ? -1 : 1;
        int timerDurationFactor = countUpTimer ? timerDuration : 0;
        DBSTimerView.this.setText(DateUtils.formatElapsedTime(timerDuration));
        timer = new CountDownTimer(timerDurationInMillis, IDEAL_INTERVAL) {

            @Override
            public void onTick(long millisUntilFinished) {
                int secondsRemaining = (int) Math.ceil(millisUntilFinished / 1000d);
                if ((timerDuration - secondsRemaining) % timerInterval == 0) {
                    // left the below line of code a bit unreadable for performance. More number of instructions, more the processing / performance hit
                    int secondsElapsedOrRemaining = timerDurationFactor + secondsRemaining * negateFactor;
                    DBSTimerView.this.setText(DateUtils.formatElapsedTime(secondsElapsedOrRemaining));
                    if (listener != null) {
                        listener.onTick(secondsElapsedOrRemaining);
                    }
                }

            }

            @Override
            public void onFinish() {
                //Android has issue(until oreo) with calling onTick(above method) for last tick and hence updating UI here for last tick
                DBSTimerView.this.setText(DateUtils.formatElapsedTime(timerDurationFactor));
                if (listener != null)
                    listener.onTimeElapsed();
            }
        };
    }

    /**
     * Start timer and notify listener.
     */
    public void start() {
        timer.start();
        if (listener != null) {
            listener.onTimerStarted();
        }
    }

    /**
     * Stop timer and notify listener.
     */
    public void stop() {
        timer.cancel();
        if (listener != null) {
            listener.onTimerStopped();
        }
    }

    /**
     * Sets timer listener.
     */
    public void setTimerListener(TimerListener listener) {
        this.listener = listener;
    }

    /**
     * When an object of a type is attached to DBSTimerView, its methods will
     * be called when timer state changes.
     */
    public interface TimerListener {
        /**
         * This method is called to notify you that,
         * the specified time in timer has elapsed.
         */
        void onTimeElapsed();

        /**
         * This method is called to notify you that,
         * the timer has started.
         */
        default void onTimerStarted() {
            // default implementation not needed
        }

        /**
         * This method is called to notify you that,
         * the timer has stopped.
         */
        default void onTimerStopped() {
            // default implementation not needed
        }

        /**
         * This method is called after every tick,
         * with secondsRemaining in countdown timer and secondsElapsed in countup.
         * */
        default void onTick(int secondsElapsedOrRemaining) {
            // default implementation not needed
        }
    }
}