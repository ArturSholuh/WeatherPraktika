package com.example.weather.WeatherClasses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Temp implements Serializable {

    public Temp(){}

    @SerializedName("day")
    private double day;

//    @SerializedName("min")
//    private double min;
//

    public double getDay() {
        return day;
    }

    public void setDay(double day) {
        this.day = day;
    }

    public double getNight() {
        return night;
    }

    public void setNight(double night) {
        this.night = night;
    }

    public void setEve(double eve) {
        this.eve = eve;
    }

    public void setMorn(double morn) {
        this.morn = morn;
    }

    public Temp(double day, double night, double eve, double morn) {
        this.day = day;
        this.night = night;
        this.eve = eve;
        this.morn = morn;
    }
//    @SerializedName("max")
//    private double max;

    @SerializedName("night")
    private double night;

    @SerializedName("eve")
    private double eve;

    @SerializedName("morn")
    private double morn;

//    public double getDay() {
//        return day;
//    }

    public String getDayString() {
        return (int)day + "\u00B0";
    }

//    public void setDay(double day) {
//        this.day = day;
//    }
//
//    public double getMin() {
//        return min;
//    }
//
//    public void setMin(double min) {
//        this.min = min;
//    }
//
//    public double getMax() {
//        return max;
//    }
//
//    public void setMax(double max) {
//        this.max = max;
//    }

    public String getNightString() {
        return (int)night + "\u00B0";
    }

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
