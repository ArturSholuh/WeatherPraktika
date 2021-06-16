package com.example.weather.WeatherClasses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rain implements Serializable {

    public Rain(){}

    @SerializedName("1h")
    private double _1h;

    public double get_1h() {
        return _1h;
    }

    public void set_1h(double _1h) {
        this._1h = _1h;
    }

    public Rain(double _1h) {
        this._1h = _1h;
    }

//    public double get1h() {
//        return _1h;
//    }
//
//    public void set1h(double _1h) {
//        this._1h = _1h;
//    }

}
