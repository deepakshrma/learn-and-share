package com.dbs.ui.models;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.dbs.components.R;

public enum WeatherType {
    RAINY(R.drawable.ic_rainy, R.string.rain),
    FEW_CLOUDY(R.drawable.ic_sun, R.string.few_cloudy),
    SCATTER_CLOUDY(R.drawable.ic_cloudy, R.string.scatter_cloudy),
    BROKEN_CLOUDY(R.drawable.ic_cloudy, R.string.broken_cloudy),
    MIST(R.drawable.ic_mist, R.string.mist),
    SNOW(R.drawable.ic_snow, R.string.snow),
    SUNNY(R.drawable.ic_sun, R.string.sun),
    THUNDERSTORM(R.drawable.ic_thunderstorm, R.string.thunderstorm);

    @DrawableRes
    private int iconDrawable;
    @StringRes
    private int info;


    WeatherType(@DrawableRes int iconDrawable, @StringRes int info) {
        this.iconDrawable = iconDrawable;
        this.info = info;
    }

    public int getIconDrawable() {
        return iconDrawable;
    }

    public int getInfo() {
        return info;
    }
}