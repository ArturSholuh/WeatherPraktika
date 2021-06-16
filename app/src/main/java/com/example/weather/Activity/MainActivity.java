package com.example.weather.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;
import androidx.room.Room;
import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather.AppDatabase;
import com.example.weather.Fragments.ProgressFragment;
import com.example.weather.Fragments.WeatherFragment;
import com.example.weather.Icons;
import com.example.weather.R;
import com.example.weather.WeatherAPI;
import com.example.weather.WeatherClasses.Weather;
import com.example.weather.WeatherClasses.CityName;
import com.example.weather.WeatherDao;
import com.example.weather.Worker;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SharedPreferences.OnSharedPreferenceChangeListener {
    private final String TAG = "MainActivity";

    private String cityName;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    private final String units = "metric", lang = "RU", key = WeatherAPI.KEY;
    private WeatherAPI.ApiInterface apiWeather;

    private final int SEARCH_ACTIVITY = 301;

    FrameLayout frameLayout_map;

    private WeatherDao weatherDao;
    private Weather weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        frameLayout_map = findViewById(R.id.frameLayout_map);

        apiWeather = WeatherAPI.getClient().create(WeatherAPI.ApiInterface.class);

        Places.initialize(this, "AIzaSyAJ0JK5pbGzZZWWD2oiuCmNGOl3uHwzjh8");

        getDataInDatabase();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);

        setUserInfo();
    }

    private void getDataInDatabase(){
        AppDatabase db =  Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database")
                .allowMainThreadQueries()
                .build();

        weatherDao = db.weatherDao();
        weather = weatherDao.getAll();

        if(weather != null){ //если запись в бд есть(не первый запуск) то запускаем фрагмент с сохраненным городом
            Bundle bundle = new Bundle();
            bundle.putSerializable("weather", weather);

            replaceFragment(new WeatherFragment(), bundle); // Установка фрагмента с прогнозом погоды
        }
    }

    private void createMapView() {
        frameLayout_map.setVisibility(View.VISIBLE); //делаем видимым layout с фрагментов карты

        try {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            if (mapFragment != null) {
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                            @Override
                            public void onMapClick(LatLng latLng) {
                                displayWeatherFragment(latLng.latitude, latLng.longitude);
                                MainActivity.this.setTitle(R.string.main_activity_title);
                            }
                        });
                    }
                });
            }
        } catch (Exception ex) {
            Toast.makeText(this, R.string.error_open_map, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        getMenuInflater().inflate( R.menu.menu, menu);
        return true;
    }

    private void displaySearch(){
        List<Place.Field> fieldList = Collections.singletonList(Place.Field.LAT_LNG);

        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(MainActivity.this);
        startActivityForResult(intent, SEARCH_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SEARCH_ACTIVITY && resultCode == RESULT_OK){
            if(data != null) {
                Place place = Autocomplete.getPlaceFromIntent(data);

                LatLng latLng = place.getLatLng();

                if(latLng != null) {
                    double lat = latLng.latitude;
                    double lon = latLng.longitude;

                    displayWeatherFragment(lat, lon);
                }
                else {
                    Toast.makeText(MainActivity.this, R.string.place_not_found, Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(MainActivity.this, R.string.place_not_found, Toast.LENGTH_SHORT).show();
            }
        }
        else if(resultCode == AutocompleteActivity.RESULT_ERROR){
            if(data != null) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Toast.makeText(MainActivity.this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.map_search:
                createMapView();
                this.setTitle(R.string.map);
                break;
            case R.id.action_search:
                displaySearch();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayWeatherFragment(double lat, double lon){
        frameLayout_map.setVisibility(View.INVISIBLE); // скрыть layout с картой если он открыт
        replaceFragment(new ProgressFragment(), null); // Установка фрагмента с progress bar

        getCityName(lat, lon);
        getWeather(lat, lon);
    }

    public boolean updateWeather(double lat, double lon){
        if(isOnline()) {
            getCityName(lat, lon);
            getWeather(lat, lon);
            Toast.makeText(this, R.string.updated, Toast.LENGTH_SHORT).show();
            return true;
        }else{
            Toast.makeText(this, R.string.no_connection, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void getCityName(double lat, double lon){
        Call<CityName> callCityName = apiWeather.getCity(lat, lon, units, lang, key);
        callCityName.enqueue(new Callback<CityName>() {
            @Override
            public void onResponse(@NotNull Call<CityName> call, @NotNull Response<CityName> response) {
                if (response.isSuccessful()) {
                    CityName cityData = response.body(); //Помещаем полученные с Json данные

                    if(cityData != null) {
                        cityName = cityData.getCity();
                    }
                } else if (response.code() == 404 && response.message().equals("Not Found")) {
                    Toast.makeText(MainActivity.this, R.string.city_not_found, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<CityName> call, @NotNull Throwable t) {
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
            public void onResponse(@NotNull Call<Weather> call, @NotNull Response<Weather> response) {
                Log.d(TAG, "onResponse");

                if (response.isSuccessful()) {
                    weather = response.body();  //Помещаем полученные с Json данные
                    if(weather != null) {
                        weather.setCityName(cityName);
                        weather.setId(1);

                        weatherDao.insert(weather); // Обновляем данные в бд

                        //Установка данных для передачи во фрагмент
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("weather", weather);
                        // Установка фрагмента с прогнозом погоды
                        replaceFragment(new WeatherFragment(), bundle);
                    }
                } else if (response.code() == 404 && response.message().equals("Not Found")) {
                    Toast.makeText(MainActivity.this, R.string.city_not_found, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<Weather> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure");
                Log.e(TAG, t.toString());
            }
        });
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void replaceFragment(Fragment fragment, Bundle bundle){
        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_container, fragment).commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    public void setColors(int id){
        try {
            Window window = this.getWindow();
            window.setStatusBarColor(getResources().getColor(Icons.getStatusBarColor(id)));

            Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getColor(Icons.getToolbarColor(id))));
        }
        catch (Exception ex){
            Log.e(TAG, "set color error", ex.getCause());
        }
    }

    private void setUserInfo(){
        View headerView = navigationView.getHeaderView(0);

        CircleImageView imageView_avatar = headerView.findViewById(R.id.imageView_avatar);
        TextView textView_user_name = headerView.findViewById(R.id.textView_user_name);
        TextView textView_user_email = headerView.findViewById(R.id.textView_user_email);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser(); //Получаем текущего пользователя

        try {
            if (currentUser != null) {
                //Установка аватара пользователя
                UserInfo profile = currentUser.getProviderData().get(0);
                Uri photoUrl = profile.getPhotoUrl();
                Picasso.with(this).load(photoUrl).into(imageView_avatar);

                //Установка имени пользователя
                String user_name = currentUser.getProviderData().get(0).getDisplayName();
                textView_user_name.setText(user_name);

                //Установка email пользователя
                String user_email = currentUser.getProviderData().get(0).getEmail();
                textView_user_email.setText(user_email);
            }
        }
        catch (Exception ex){
            Log.e(TAG, "setUserInfo:" + ex.getMessage());
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.drawerItem_main_activity:
                break;

            case R.id.drawerItem_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                finish();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        int NOTIFY_ID = 7;

        //когда выключаем уведомления
        if(key.equals("notification_key")){
            if(!sharedPreferences.getBoolean("notification_key", false)){
                WorkManager.getInstance().cancelAllWorkByTag("tag"); //Отменяем фоновую задачу

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                notificationManager.cancel(NOTIFY_ID);//отменяем уведомление

                Toast.makeText(this, R.string.notification_off, Toast.LENGTH_SHORT).show();
            }
        }

        //когда устанавливаем период обновления напоминания
        if(key.equals("notification_period_key") && sharedPreferences.getBoolean("notification_key", false)){
            String period = sharedPreferences.getString("notification_period_key", null);
            if(period != null){
                setNotification(Integer.parseInt(period));
            }
        }
    }

    private void setNotification(int period){
        if(weather != null){
            Data data = new Data.Builder() //данные для передачи в фоновую задачу для напоминания
                    .putDouble("lat", weather.getLat())
                    .putDouble("lon", weather.getLon())
                    .build();

            PeriodicWorkRequest myWorkRequest = new PeriodicWorkRequest.Builder(Worker.class, period, TimeUnit.HOURS)
                    .addTag("tag")
                    .setInputData(data)
                    .build();

            WorkManager.getInstance().enqueue(myWorkRequest);

            Toast.makeText(this, R.string.notification_on, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, R.string.weather_not_set, Toast.LENGTH_SHORT).show();
        }
    }
}