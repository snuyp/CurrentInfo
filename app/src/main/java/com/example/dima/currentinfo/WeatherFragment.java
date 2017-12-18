package com.example.dima.currentinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.dima.currentinfo.weather.ApiService;
import com.example.dima.currentinfo.weather.Weather;
import com.example.dima.currentinfo.weather.WeatherApi;
import com.example.dima.currentinfo.weather.WeatherForecast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dima on 04.12.2017.
 */

public class WeatherFragment extends Fragment{
    public final String TAG = "WEATHER";
    private ApiService mApiService;
    private TextView mWeatherTemp;
    private TextView mWeatherCity;
    private Button mGetWeatherButton;
    public static WeatherFragment newInstance()
    {
        return new WeatherFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = WeatherApi.getClient().create(ApiService.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weather,container,false);
        mWeatherTemp = (TextView) v.findViewById(R.id.weather_temp);
        mGetWeatherButton = (Button) v.findViewById(R.id.get_weather);
        callWeather();
        return v;
    }
    private void callWeather()
    {
        /*
          TODO     Geo coords  for Minsk [53.9023, 27.5619].
         */
        Double lat = 53.9023;
        Double lng = 27.5619;
        String units = "metric";
        Call<Weather> callWeather = mApiService.getToday(lat,lng,units,WeatherApi.KEY);

        callWeather.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Log.e(TAG, "onResponse");
                Weather weather = response.body();
                if(response.isSuccessful())
                {
                    mWeatherTemp.setText(weather.getMain().getTemp());
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.e(TAG, "onFailure " + t.toString());
            }
        });

    }
}
