package com.example.weather.WeatherClasses;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.weather.Converters.DailyConverter;
import com.example.weather.Converters.HourlyConverter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Entity
public class Weather implements Serializable {

    public Weather(){}

    @PrimaryKey
    public long id;

    private String cityName;

    @SerializedName("lat")
    private double lat;

    @SerializedName("lon")
    private double lon;
//
//    @SerializedName("timezone")
//    private String timezone;
//
//    @SerializedName("timezone_offset")
//    private int timezoneOffset;

    @Embedded
    @SerializedName("current")
    private Current current;

    @TypeConverters({HourlyConverter.class})
    @SerializedName("hourly")
    private List<Hourly> hourly = null;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public void setHourly(List<Hourly> hourly) {
        this.hourly = hourly;
    }

    public void setDaily(List<Daily> daily) {
        this.daily = daily;
    }

    @TypeConverters({DailyConverter.class})
    @SerializedName("daily")
    private List<Daily> daily = null;

//    public String getCityName() {
//        return cityName;
//    }
//
//    public void setCityName(String cityName) {
//        this.cityName = cityName;
//    }
//
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
//
//    public String getTimezone() {
//        return timezone;
//    }
//
//    public void setTimezone(String timezone) {
//        this.timezone = timezone;
//    }
//
//    public int getTimezoneOffset() {
//        return timezoneOffset;
//    }
//
//    public void setTimezoneOffset(int timezoneOffset) {
//        this.timezoneOffset = timezoneOffset;
//    }

    public Current getCurrent() {
        return current;
    }

//    public void setCurrent(Current current) {
//        this.current = current;
//    }

    public List<Hourly> getHourly() {
        return hourly;
    }

//    public void setHourly(List<Hourly> hourly) {
//        this.hourly = hourly;
//    }

    public List<Daily> getDaily() {
        return daily;
    }

//    public void setDaily(List<Daily> daily) {
//        this.daily = daily;
//    }

}
