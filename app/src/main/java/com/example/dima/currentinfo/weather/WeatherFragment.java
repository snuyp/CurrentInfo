package com.example.dima.currentinfo.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dima.currentinfo.R;
import com.example.dima.currentinfo.common.Common;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dima on 04.12.2017.
 */

public class WeatherFragment extends Fragment {
    public final String TAG = "WEATHER";
    private ApiService mApiService;

    private TextView mWeatherTemp;
    private TextView mWeatherTempMin;
    private TextView mWeatherTempMax;

    private ImageView mWeatherImage;
    private TextView mWeatherSunset;
    private TextView mWeatherSunrise;

    private TextView mWeatherCity;

    private TextView mWeatherPressure;
    private TextView mWeatherHumidity;

    private TextView mWeatherWindSpeed;
    private TextView mWeatherWindDirection;
    private ProgressBar mProgressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mApiService = WeatherApi.getClient().create(ApiService.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.weather_fragment, container, false);
        mProgressBar = v.findViewById(R.id.weatherProgressBar);
        mProgressBar.setVisibility(View.VISIBLE);
        mWeatherTemp = v.findViewById(R.id.weather_temp);
        mWeatherTempMin = v.findViewById(R.id.weather_t_min);
        mWeatherTempMax = v.findViewById(R.id.weather_t_max);

        mWeatherPressure = v.findViewById(R.id.weather_pressure);
        mWeatherHumidity = v.findViewById(R.id.weather_humidity);
        mWeatherCity = v.findViewById(R.id.weather_city);
        mWeatherSunrise = v.findViewById(R.id.weather_sunrise);
        mWeatherSunset = v.findViewById(R.id.weather_sunset);
        mWeatherWindDirection = v.findViewById(R.id.weather_wind_direction);
        mWeatherWindSpeed = v.findViewById(R.id.weather_wind_speed);


        mWeatherImage =  v.findViewById(R.id.weather_image);

        Button getWeatherButton = v.findViewById(R.id.button_get_weather);
        getWeatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = WebPageActivity.newIntent(getActivity());
                startActivity(i);
            }
        });
        callWeather();
        return v;
    }

    private void updateSubtitle(String subtitle) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    private void callWeather() {
        /*
           Geo coords  for Minsk [53.9023, 27.5619].
         */

//        Double lat = 53.9023;
//        Double lng = 27.5619;
        Double lat = Common.lastLocation.getLatitude();
        Double lng = Common.lastLocation.getLongitude();
        String units = "metric";
        Call<Weather> callWeather = mApiService.getToday(lat, lng, units, WeatherApi.KEY);
        callWeather.request();
        callWeather.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Log.e(TAG, "onResponse");
                Weather weather = response.body();
                if (response.isSuccessful()) {
                    Log.e(TAG, "onResponse");
                    mWeatherTemp.setText(weather.getMain().getTemp());
                    mWeatherTempMax.setText(weather.getMain().getTempMax());
                    mWeatherTempMin.setText(weather.getMain().getTempMin());
                  //  mWeatherCity.setText(weather.getCity().split(" ")[1]);
                    mWeatherCity.setText(weather.getCity());
                    Glide.with(getContext()).load(weather.getIcon()).into(mWeatherImage);
                    updateSubtitle(weather.getDate() + " [" + weather.getSys().getCountry() + "] ");

                    mWeatherHumidity.setText(getResources().getString(R.string.humidity) + " " + weather.getMain().getHumidity());
                    mWeatherPressure.setText(getResources().getString(R.string.pressure) + " " + weather.getMain().getPressure());
                    mWeatherWindDirection.setText(getResources().getString(R.string.wind_direction) + " " + weather.getWind().getDeg());
                    mWeatherWindSpeed.setText(getResources().getString(R.string.wind_speed) + " " + weather.getWind().getSpeed());

                    mWeatherSunset.setText(weather.getSys().getSunset());
                    mWeatherSunrise.setText(weather.getSys().getSunrise());


                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.e(TAG, "onFailure " + t.toString());
            }
        });

        Call<WeatherForecast> weatherForecastCall = mApiService.getForecast(lat, lng, units, WeatherApi.KEY);
        weatherForecastCall.enqueue(new Callback<WeatherForecast>() {
            @Override
            public void onResponse(Call<WeatherForecast> call, Response<WeatherForecast> response) {

//           TODO: Do it later
//                  WeatherForecast forecast = response.body();
//
//                if (response.isSuccessful()) {
//                    Log.e(TAG, "onResponse forecast");
//                    for(Weather weather : forecast.getItems())
//                    {
//
//                    }
//
//               }
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<WeatherForecast> call, Throwable t) {
                Log.e(TAG, "onFailure forecast" + t.toString());
            }

        });

    }
}
