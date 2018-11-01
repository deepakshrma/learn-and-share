package com.dbs.ui.utils;

import com.dbs.components.R;

import java.util.Calendar;

public class GreetingUtil {
    private GreetingUtil() {
        throw new UnsupportedOperationException("Should not create instance of Util class. Please use as static..");
    }

    public static int getGreetingMessageResource() {
        Calendar calendar = Calendar.getInstance();
        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12) {
            return R.string.good_morning;
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            return R.string.good_afternoon;
        } else if (timeOfDay >= 16 && timeOfDay < 24) {
            return R.string.good_evening;
        }
        return -1;
    }
}