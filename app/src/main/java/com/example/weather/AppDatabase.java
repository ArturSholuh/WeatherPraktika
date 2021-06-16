package com.example.weather;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.weather.WeatherClasses.Weather;

@Database(entities = {Weather.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WeatherDao weatherDao();
}