package com.example.dima.currentinfo.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Dima on 17.12.2017.
 */

public interface ApiService {
    @GET("weather")
    Call<Weather> getToday(
            @Query("lat") Double lat,
            @Query("lon") Double lon,
            @Query("units") String units,
            @Query("appid") String appid
    );

    @GET("forecast")
    Call<WeatherForecast> getForecast(
            @Query("lat") Double lat,
            @Query("lon") Double lon,
            @Query("units") String units,
            @Query("appid") String appid
    );
}
