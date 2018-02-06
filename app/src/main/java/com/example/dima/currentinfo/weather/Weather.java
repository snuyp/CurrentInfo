package com.example.dima.currentinfo.weather;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Dima on 17.12.2017.
 */
public class Weather {
    @SerializedName("sys")
    private Sys sys;
    @SerializedName("main")
    private Main main;
    @SerializedName("wind")
    private Wind wind;
    @SerializedName("name")
    private String city;
    @SerializedName("dt")
    private long timestamp;

    @SerializedName("weather")
    private List<WeatherDescription> description;

    public String getDate()
    {
        Date date=new Date(timestamp * 1000);
        SimpleDateFormat df2 = new SimpleDateFormat("E dd.MM.yyyy",new Locale("en","US"));

        return df2.format(date);
    }

    public String getIcon() {
        return "http://openweathermap.org/img/w/" + description.get(0).icon + ".png";
    }

    public String getCity() {
        return city;
    }

    public Sys getSys() {
        return sys;
    }

    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        Log.e("speed", wind.getSpeed());
        return wind;
    }

    public class Main {
        @SerializedName("temp")
        private Double temp;
        @SerializedName("humidity")
        private Integer humidity;
        @SerializedName("pressure")
        private Double pressure;
        @SerializedName("temp_min")
        private Double tempMin;
        @SerializedName("temp_max")
        private Double tempMax;

        public String getTemp() {
            return String.valueOf(temp.intValue()) + "\u00B0";
        }

        public String getTempMax() {
            return String.valueOf(tempMax.intValue()) + "\u00B0";
        }

        public String getHumidity() {
            return String.valueOf(humidity) + " %";
        }

        public String getPressure() {
            return String.valueOf(pressure) + " hPa";
        }

        public String getTempMin() {
            return String.valueOf(tempMin.intValue()) + "\u00B0";
        }
    }

    public class Wind {
        private Double speed;
        private Double deg;

        public String getSpeed() {

            return String.valueOf(speed) + " m/s";
        }

        public Double getDeg() {
            return deg;
        }
    }

    public class Sys {
        private String country;
        private long sunrise ;
        private long sunset;

        public String getCountry() {
            return country;
        }
        public String getSunset()
        {
            return "↓ "+ dateText(sunset);
        }
        public String getSunrise()
        {
            return "↑ " + dateText(sunrise);
        }
        private String dateText(long dateText)
        {
            Date date=new Date(dateText * 1000);
            SimpleDateFormat df2 = new SimpleDateFormat("HH:mm ",new Locale("en","US"));
            return  df2.format(date);
        }

    }

    public class WeatherDescription {
        String icon;
    }

}
