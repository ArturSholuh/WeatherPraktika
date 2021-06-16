package com.example.weather;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.WorkerParameters;

import com.example.weather.Activity.MainActivity;
import com.example.weather.WeatherClasses.Weather;
import com.example.weather.WeatherClasses.CityName;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Worker extends androidx.work.Worker {
    private final String TAG = "Worker";
    private static final int NOTIFY_ID = 7;
    private static final String CHANNEL_ID = "Channel_1";

    String units = "metric";
    String lang = "RU";
    String key = WeatherAPI.KEY;

    private final Context context;
    private WeatherAPI.ApiInterface apiWeather;
    private Weather weather;
    private String cityName;

    public Worker(@NonNull @NotNull Context context, @NonNull @NotNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        //Получаем данные
        double lat = getInputData().getDouble("lat", 0);
        double lon = getInputData().getDouble("lon", 0);

        apiWeather = WeatherAPI.getClient().create(WeatherAPI.ApiInterface.class);

        getCityName(lat, lon);
        getWeather(lat, lon);

        return Result.success();
    }

    private void getCityName(double lat, double lon){
        Call<CityName> callCityName = apiWeather.getCity(lat, lon, units, lang, key);
        callCityName.enqueue(new Callback<CityName>() {
            @Override
            public void onResponse(Call<CityName> call, Response<CityName> response) {
                if (response.isSuccessful()) {
                    CityName cityData = response.body(); //Помещаем полученные с Json данные
                    cityName = cityData.getCity();
                }
            }
            @Override
            public void onFailure(Call<CityName> call, Throwable t) {
                Log.e(TAG, "onFailure");
                Log.e(TAG, t.toString());
            }
        });
    }

    private void getWeather(double lat, double lon){
        Call<Weather> callForecast = apiWeather.getForecast(lat, lon, units, lang, key);
        callForecast.enqueue(new Callback<Weather>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Log.d(TAG, "onResponse");

                if (response.isSuccessful()) {
                    weather = response.body();  //Помещаем полученные с Json данные
                    weather.setCityName(cityName);
                    weather.setId(1);
                    showNotification();
                }
            }
            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.e(TAG, "onFailure");
                Log.e(TAG, t.toString());
            }
        });
    }

    private void showNotification(){
        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.notification_layout);

        //Получаем отдельные данные с объекта weather
        String city = weather.getCityName();
        String temp = weather.getCurrent().getTempString();
        String feels_like = weather.getCurrent().getFeelsLikeString();
        int weatherId = weather.getCurrent().getWeather().get(0).getId();
        int icon = Icons.getIcon(weatherId);
        String description = weather.getCurrent().getWeather().get(0).getDescription();

        //Установка значений в layout уведомления
        if (city.isEmpty()) {
            remoteViews.setTextViewText(R.id.city_notification, context.getString(R.string.unknown_place));
        } else {
            remoteViews.setTextViewText(R.id.city_notification, city);
        }
        remoteViews.setTextViewText(R.id.temp_notification, temp + "C");
        remoteViews.setTextViewText(R.id.feels_like_notification, context.getString(R.string.feels_like_temp) + " " + feels_like + "C");
        remoteViews.setImageViewResource(R.id.image_notification, icon);
        remoteViews.setTextViewText(R.id.description_notification, description);

        //Intent для открытия MainActivity по нажатию на уведомление
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(icon)
                .setCustomContentView(remoteViews)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setOngoing(true)
                .setContentIntent(contentIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Weather", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Weather channel");
            channel.enableLights(true);
            channel.enableVibration(false);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(NOTIFY_ID, builder.build());
    }
}