package com.example.weather.WeatherClasses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Weather__1 implements Serializable {

    public Weather__1(){}

    @SerializedName("id")
    private int id;

    @SerializedName("main")
    private String main;

    @SerializedName("description")
    private String description;

    @SerializedName("icon")
    private String icon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

//    public void setMain(String main) {
//        this.main = main;
//    }

    public String getDescription() {
        return description;
    }

//    public void setDescription(String description) {
//        this.description = description;
//    }

    public String getIcon() {
        return icon;
    }

//    public void setIcon(String icon) {
//        this.icon = icon;
//    }

    public void setMain(String main) {
        this.main = main;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
