package com.example.weather.WeatherClasses;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.List;

public class CityName {
    @SerializedName("name")
    private String city;

    public String getCity() { return city; }
}
