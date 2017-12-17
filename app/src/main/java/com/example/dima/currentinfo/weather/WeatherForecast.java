package com.example.dima.currentinfo.weather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherForecast {
    @SerializedName("list")
    private List<Weather> items;

    public WeatherForecast(List<Weather> items) {
        this.items = items;
    }

    public List<Weather> getItems() {
        return items;
    }
}