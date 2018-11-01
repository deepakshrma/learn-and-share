package com.dbs.ui.models;

public class WeatherDetails {
    private final String temperature;
    private final WeatherType weatherType;
    private final String message;

    public WeatherDetails(String temperature, WeatherType weatherType, String message) {
        this.temperature = temperature;
        this.weatherType = weatherType;
        this.message = message;
    }

    public String getTemperature() {
        return temperature;
    }

    public int getWeatherInfo() {
        return weatherType.getInfo();
    }

    public String getMessage() {
        return message;
    }

    public int getWeatherIcon() {
        return weatherType.getIconDrawable();
    }
}