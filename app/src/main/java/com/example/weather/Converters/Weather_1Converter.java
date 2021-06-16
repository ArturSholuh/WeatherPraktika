package com.example.weather.Converters;

import androidx.room.TypeConverter;

import com.example.weather.WeatherClasses.Weather__1;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class Weather_1Converter {
    Gson gson = new Gson();

    @TypeConverter
    public List<Weather__1> stringToWeather__1ObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Weather__1>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public String weather__1ObjectListToString(List<Weather__1> weather__1List) {
        return gson.toJson(weather__1List);
    }
}
