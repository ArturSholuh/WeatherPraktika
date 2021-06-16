package com.example.weather.Converters;

import androidx.room.TypeConverter;

import com.example.weather.WeatherClasses.Hourly;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class HourlyConverter {
    Gson gson = new Gson();

    @TypeConverter
    public List<Hourly> stringToHourlyObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Hourly>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public String hourlyObjectListToString(List<Hourly> hourlyList) {
        return gson.toJson(hourlyList);
    }
}
