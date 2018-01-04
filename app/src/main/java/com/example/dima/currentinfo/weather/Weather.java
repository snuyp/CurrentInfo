package com.example.dima.currentinfo.weather;

import android.content.Intent;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Dima on 17.12.2017.
 */
public class Weather {
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
        private Integer sunrise;
        private Integer sunset;

        public String getCountry() {
            return country;
        }
        public String getSunrise() {
            return String.valueOf(sunrise);
        }
        public String getSunset() {
            return String.valueOf(sunset);
        }
    }

    public class Rain {
        private Integer _3h;

        public Integer get3h() {
            return _3h;
        }

        public void set3h(Integer _3h) {
            this._3h = _3h;
        }
    }


    public class Clouds {
        private Integer all;

        public String getAll() {
            return all + " %";
        }

    }

    @SerializedName("sys")
    private Sys sys;
    @SerializedName("main")
    private Main main;
    @SerializedName("wind")
    private Wind wind;
    @SerializedName("rain")
    private Rain rain;
    @SerializedName("clouds")
    private Clouds clouds;
    @SerializedName("name")
    private String city;
    @SerializedName("dt")
    private String timestamp;
    @SerializedName("description")
    private String description;

    public String getDescription() {
        return description;
    }

    public String getCity() {
        return city;
    }

    public Sys getSys() {
        return sys;
    }

//    public void setSys(Sys sys) {
//        this.sys = sys;
//    }

    //    public List<Weather> getWeather() {
//        return weather;
//    }
//
    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public Rain getRain() {
        return rain;
    }

    public Clouds getClouds() {
        return clouds;
    }


}
