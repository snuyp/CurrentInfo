package com.example.dima.currentinfo.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dima on 17.12.2017.
 */
public class Weather {

    @SerializedName("dt")
    @Expose
    private int dt;
    @SerializedName("main")
    @Expose
    private Main main;
    @SerializedName("weather")
    @Expose
    private List<Weather> weather = null;
    @SerializedName("clouds")
    @Expose
    private Clouds clouds;
    @SerializedName("wind")
    @Expose
    private Wind wind;
    @SerializedName("snow")
    @Expose
    private Snow snow;
    @SerializedName("sys")
    @Expose
    private String dtTxt;

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public String getDtTxt() {
        return dtTxt;
    }

    public void setDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
    }
    public class Main {

        @SerializedName("temp")
        @Expose
        private double temp;
        @SerializedName("temp_min")
        @Expose
        private double tempMin;
        @SerializedName("temp_max")
        @Expose
        private double tempMax;
        @SerializedName("pressure")
        @Expose
        private int pressure;
        @SerializedName("sea_level")
        @Expose
        private double seaLevel;
        @SerializedName("grnd_level")
        @Expose
        private int grndLevel;
        @SerializedName("humidity")
        @Expose
        private int humidity;
        @SerializedName("temp_kf")
        @Expose
        private int tempKf;

        public String getTemp() {
            return String.valueOf(temp);
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }

        public String getTempMin() {
            return String.valueOf(tempMin);
        }

        public void setTempMin(double tempMin) {
            this.tempMin = tempMin;
        }

        public String getTempMax() {
            return String.valueOf(tempMax);
        }

        public void setTempMax(double tempMax) {
            this.tempMax = tempMax;
        }

        public int getPressure() {
            return pressure;
        }

        public void setPressure(int pressure) {
            this.pressure = pressure;
        }

        public double getSeaLevel() {
            return seaLevel;
        }

        public void setSeaLevel(double seaLevel) {
            this.seaLevel = seaLevel;
        }

        public int getGrndLevel() {
            return grndLevel;
        }

        public void setGrndLevel(int grndLevel) {
            this.grndLevel = grndLevel;
        }

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }

        public int getTempKf() {
            return tempKf;
        }

        public void setTempKf(int tempKf) {
            this.tempKf = tempKf;
        }

    }
    public class Wind {
        @SerializedName("speed")
        @Expose
        private double speed;
        @SerializedName("deg")
        @Expose
        private double deg;

        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }

        public double getDeg() {
            return deg;
        }

        public void setDeg(double deg) {
            this.deg = deg;
        }
    }

    public class Clouds {

        @SerializedName("all")
        @Expose
        private int all;

        public int getAll() {
            return all;
        }

        public void setAll(int all) {
            this.all = all;
        }
    }
    public class Snow {

        @SerializedName("3h")
        @Expose
        private double _3h;

        public double get3h() {
            return _3h;
        }

        public void set3h(double _3h) {
            this._3h = _3h;
        }
    }

}
