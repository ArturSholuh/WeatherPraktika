package com.example.weather.WeatherClasses;

import androidx.room.Embedded;
import androidx.room.TypeConverters;

import com.example.weather.Converters.Weather_1Converter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Daily implements Serializable {

    public Daily(){}

    @SerializedName("dt")
    private long dt;

//    @SerializedName("sunrise")
//    private int sunrise;
//
//    @SerializedName("sunset")
//    private int sunset;
//
//    @SerializedName("moonrise")
//    private int moonrise;
//
//    @SerializedName("moonset")
//    private int moonset;
//
//    @SerializedName("moon_phase")
//    private double moonPhase;

    public Daily(long dt, Temp temp, List<Weather__1> weather, double rain) {
        this.dt = dt;
        this.temp = temp;
        this.weather = weather;
        this.rain = rain;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    @Embedded
    @SerializedName("temp")
    private Temp temp;

//    @SerializedName("feels_like")
//    private FeelsLike feelsLike;
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
//    @SerializedName("wind_speed")
//    private double windSpeed;
//
//    @SerializedName("wind_deg")
//    private int windDeg;
//
//    @SerializedName("wind_gust")
//    private double windGust;

    @TypeConverters({Weather_1Converter.class})
    @SerializedName("weather")
    private List<Weather__1> weather = null;

//    @SerializedName("clouds")
//    private int clouds;
//
//    @SerializedName("pop")
//    private double pop;
//
    @SerializedName("rain")
    private double rain;
//
//    @SerializedName("uvi")
//    private double uvi;

    public long getDt() {
        return dt;
    }

//    public void setDt(int dt) {
//        this.dt = dt;
//    }
//
//    public int getSunrise() {
//        return sunrise;
//    }
//
//    public void setSunrise(int sunrise) {
//        this.sunrise = sunrise;
//    }
//
//    public int getSunset() {
//        return sunset;
//    }
//
//    public void setSunset(int sunset) {
//        this.sunset = sunset;
//    }
//
//    public int getMoonrise() {
//        return moonrise;
//    }
//
//    public void setMoonrise(int moonrise) {
//        this.moonrise = moonrise;
//    }
//
//    public int getMoonset() {
//        return moonset;
//    }
//
//    public void setMoonset(int moonset) {
//        this.moonset = moonset;
//    }
//
//    public double getMoonPhase() {
//        return moonPhase;
//    }
//
//    public void setMoonPhase(double moonPhase) {
//        this.moonPhase = moonPhase;
//    }

    public Temp getTemp() {
        return temp;
    }

//    public void setTemp(Temp temp) {
//        this.temp = temp;
//    }
//
//    public FeelsLike getFeelsLike() {
//        return feelsLike;
//    }
//
//    public void setFeelsLike(FeelsLike feelsLike) {
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

//    public int getClouds() {
//        return clouds;
//    }
//
//    public void setClouds(Integer clouds) {
//        this.clouds = clouds;
//    }
//
//    public double getPop() {
//        return pop;
//    }
//
//    public void setPop(double pop) {
//        this.pop = pop;
//    }

    public double getRain() {
        return rain;
    }

//    public void setRain(double rain) {
//        this.rain = rain;
//    }
//
//    public double getUvi() {
//        return uvi;
//    }
//
//    public void setUvi(double uvi) {
//        this.uvi = uvi;
//    }

}
