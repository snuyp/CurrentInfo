package com.example.dima.currentinfo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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
    private TextView mWeatherTempMin;
    private TextView mWeatherTempMax;
//    private TextView mWeatherSunset;
//    private TextView mWeatherSunrise;

    private TextView mWeatherCity;
    private TextView mWeatherCountry;

    private TextView mWeatherPressure;
    private TextView mWeatherHumidity;

    private TextView mWeatherWindSpeed;
    private TextView mWeatherWindDirection;

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
        View v = inflater.inflate(R.layout.weather_fragment,container,false);

        mWeatherTemp = (TextView) v.findViewById(R.id.weather_temp);
        mWeatherTempMin = (TextView) v.findViewById(R.id.weather_t_min);
        mWeatherTempMax = (TextView) v.findViewById(R.id.weather_t_max);

        mWeatherPressure = (TextView) v.findViewById(R.id.weather_pressure);
        mWeatherHumidity = (TextView) v.findViewById(R.id.weather_humidity);
        mWeatherCity = (TextView) v.findViewById(R.id.weather_city);
//      mWeatherSunrise = (TextView) v.findViewById(R.id.weather_sunrise);
//      mWeatherSunset = (TextView) v.findViewById(R.id.weather_sunset);
        mWeatherWindDirection = (TextView) v.findViewById(R.id.weather_wind_direction);
        mWeatherWindSpeed = (TextView) v.findViewById(R.id.weather_wind_speed);

        callWeather();
        return v;
    }
    private void updateSubtitle(String subtitle) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }
    private void callWeather()
    {
        /*
          TODO     Geo coords  for Minsk [53.9023, 27.5619].
         */
        Double lat = 53.9023;
        Double lng = 27.5619;
        String units = "metric";
        Call<Weather> callWeather = mApiService.getToday(lat,lng,units, WeatherApi.KEY);

        callWeather.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Log.e(TAG, "onResponse");
                Weather weather = response.body();
                if(response.isSuccessful())
                {
                    Log.e(TAG, "onResponse" );
                    mWeatherTemp.setText(weather.getMain().getTemp());
                    mWeatherTempMax.setText(weather.getMain().getTempMax());
                    mWeatherTempMin.setText(weather.getMain().getTempMin());
                    mWeatherCity.setText(weather.getCity().split(" ")[1]);

                    updateSubtitle(" ["+weather.getSys().getCountry()+"]");
                }
            }
            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.e(TAG, "onFailure " + t.toString());
            }
        });

        Call<WeatherForecast> weatherForecastCall = mApiService.getForecast(lat,lng,units, WeatherApi.KEY);
        weatherForecastCall.enqueue(new Callback<WeatherForecast>() {
            @Override
            public void onResponse(Call<WeatherForecast> call, Response<WeatherForecast> response) {

                WeatherForecast forecast = response.body();

                if(response.isSuccessful())
                {
                    Log.e(TAG, "onResponse forecast" );
                        for(Weather weather : forecast.getItems()) {
                            SimpleDateFormat formatDayOfWeek = new SimpleDateFormat("E",new Locale("en", "US"));
//                            if (weather.getDate().get(Calendar.HOUR_OF_DAY) == 15) {
//                                String date = String.format(new Locale("en", "US"),"%d.%d.%d %d:%d",
//                                        weather.getDate().get(Calendar.DAY_OF_MONTH),
//                                        weather.getDate().get(Calendar.WEEK_OF_MONTH),
//                                        weather.getDate().get(Calendar.YEAR),
//                                        weather.getDate().get(Calendar.HOUR_OF_DAY),
//                                        weather.getDate().get(Calendar.MINUTE)
//                                );
//                                Log.d(TAG, date);
//                                Log.d(TAG, "---");
                           // mWeatherTemp.setText(weather.getMain().getTemp());

                            mWeatherHumidity.setText(getResources().getString(R.string.humidity)+" "+ weather.getMain().getHumidity());
                            mWeatherPressure.setText(getResources().getString(R.string.pressure)+" "+ weather.getMain().getPressure());
                            mWeatherWindDirection.setText(getResources().getString(R.string.wind_direction)+" "+ weather.getWind().getDeg());
                            mWeatherWindSpeed.setText(getResources().getString(R.string.wind_speed)+" " + weather.getWind().getSpeed());
                        }
                }
            }

            @Override
            public void onFailure(Call<WeatherForecast> call, Throwable t) {
                Log.e(TAG, "onFailure forecast" + t.toString());
            }
        });

    }
}
