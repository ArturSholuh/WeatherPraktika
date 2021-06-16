package com.example.weather.WeatherClasses;

import androidx.room.Embedded;
import androidx.room.TypeConverters;

import com.example.weather.Converters.DailyConverter;
import com.example.weather.Converters.Weather_1Converter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Hourly implements Serializable {

    public Hourly(){}

    @SerializedName("dt")
    private long dt;

    @SerializedName("temp")
    private double temp;

//    @SerializedName("feels_like")
//    private double feelsLike;
//
//    @SerializedName("pressure")
//    private int pressure;
//
//    @SerializedName("humidity")
//    private int humidity;
//
//    @SerializedName("dew_point")
//    private double dewPoint;
//
//    @SerializedName("uvi")
//    private double uvi;
//
//    @SerializedName("clouds")
//    private int clouds;
//
//    @SerializedName("visibility")
//    private int visibility;
//
//    @SerializedName("wind_speed")
//    private double windSpeed;
//
//    @SerializedName("wind_deg")
//    private int windDeg;
//
//    @SerializedName("wind_gust")
//    private double windGust;

    public Hourly(long dt, double temp, List<Weather__1> weather, Rain rain) {
        this.dt = dt;
        this.temp = temp;
        this.weather = weather;
        this.rain = rain;
    }

    @TypeConverters({Weather_1Converter.class})
    @SerializedName("weather")
    private List<Weather__1> weather = null;

//    @SerializedName("pop")
//    private double pop;

    public void setDt(long dt) {
        this.dt = dt;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    @Embedded
    @SerializedName("rain")
    private Rain rain;

    public long getDt() {
        return dt;
    }

//    public void setDt(int dt) {
//        this.dt = dt;
//    }

    public String getTempString() {
        return (int)temp + "\u00B0";
    }

//    public double getTemp() {
//        return temp;
//    }
//
//    public void setTemp(double temp) {
//        this.temp = temp;
//    }
//
//    public double getFeelsLike() {
//        return feelsLike;
//    }
//
//    public void setFeelsLike(double feelsLike) {
//        this.feelsLike = feelsLike;
//    }
//
//    public int getPressure() {
//        return pressure;
//    }
//
//    public void setPressure(int pressure) {
//        this.pressure = pressure;
//    }
//
//    public int getHumidity() {
//        return humidity;
//    }
//
//    public void setHumidity(int humidity) {
//        this.humidity = humidity;
//    }
//
//    public double getDewPoint() {
//        return dewPoint;
//    }
//
//    public void setDewPoint(double dewPoint) {
//        this.dewPoint = dewPoint;
//    }
//
//    public double getUvi() {
//        return uvi;
//    }
//
//    public void setUvi(double uvi) {
//        this.uvi = uvi;
//    }
//
//    public int getClouds() {
//        return clouds;
//    }
//
//    public void setClouds(int clouds) {
//        this.clouds = clouds;
//    }
//
//    public int getVisibility() {
//        return visibility;
//    }

//    public void setVisibility(int visibility) {
//        this.visibility = visibility;
//    }
//
//    public double getWindSpeed() {
//        return windSpeed;
//    }
//
//    public void setWindSpeed(double windSpeed) {
//        this.windSpeed = windSpeed;
//    }
//
//    public int getWindDeg() {
//        return windDeg;
//    }
//
//    public void setWindDeg(int windDeg) {
//        this.windDeg = windDeg;
//    }
//
//    public double getWindGust() {
//        return windGust;
//    }
//
//    public void setWindGust(double windGust) {
//        this.windGust = windGust;
//    }

    public List<Weather__1> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather__1> weather) {
        this.weather = weather;
    }

//    public double getPop() {
//        return pop;
//    }
//
//    public void setPop(double pop) {
//        this.pop = pop;
//    }

    public Rain getRain() {
        return rain;
    }
//
//    public void setRain(Rain rain) {
//        this.rain = rain;
//    }

}
