package com.example.weather;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.weather.WeatherClasses.Weather;

@Dao
public interface WeatherDao {
    @Query("SELECT * FROM weather")
    Weather getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Weather weather);
}
