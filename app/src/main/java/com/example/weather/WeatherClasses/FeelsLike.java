package com.example.weather.WeatherClasses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FeelsLike implements Serializable {

    public FeelsLike(){}

//    @SerializedName("day")
//    private double day;
//
//    @SerializedName("night")
//    private double night;

    @SerializedName("eve")
    private double eve;

    @SerializedName("morn")
    private double morn;

    public void setEve(double eve) {
        this.eve = eve;
    }

    public void setMorn(double morn) {
        this.morn = morn;
    }

    public FeelsLike(double eve, double morn) {
        this.eve = eve;
        this.morn = morn;
    }

    //    public double getDay() {
//        return day;
//    }
//
//    public void setDay(double day) {
//        this.day = day;
//    }
//
//    public double getNight() {
//        return night;
//    }
//
//    public void setNight(double night) {
//        this.night = night;
//    }

    public double getEve() {
        return eve;
    }

//    public void setEve(double eve) {
//        this.eve = eve;
//    }

    public double getMorn() {
        return morn;
    }

//    public void setMorn(double morn) {
//        this.morn = morn;
//    }

}
