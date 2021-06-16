package com.example.weather;

import com.example.weather.WeatherClasses.Weather;
import com.example.weather.WeatherClasses.CityName;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class WeatherAPI {
    public static String KEY = "0d310a79b62a70c1101fe08c0773e2c4";
    public static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private static Retrofit retrofit = null;

    public interface ApiInterface {
        @GET("weather")
        Call<CityName> getCity(@Query("lat") Double lat, @Query("lon") Double lon, @Query("units") String units, @Query("lang") String lang, @Query("appid") String appid);

        @GET("onecall")
        Call<Weather> getForecast(@Query("lat") Double lat, @Query("lon") Double lon, @Query("units") String units, @Query("lang") String lang, @Query("appid") String appid);
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
