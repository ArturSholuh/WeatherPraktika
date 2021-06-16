package com.example.weather.Converters;

import androidx.room.TypeConverter;

import com.example.weather.WeatherClasses.Daily;
import com.example.weather.WeatherClasses.Hourly;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class DailyConverter {
    Gson gson = new Gson();

    @TypeConverter
    public List<Daily> stringToDailyObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Daily>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public String dailyObjectListToString(List<Daily> dailyList) {
        return gson.toJson(dailyList);
    }
}
