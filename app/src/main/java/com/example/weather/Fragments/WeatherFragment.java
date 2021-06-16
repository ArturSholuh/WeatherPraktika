package com.example.weather.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather.Adapters.DailyAdapter;
import com.example.weather.Adapters.HourlyAdapter;
import com.example.weather.Icons;
import com.example.weather.Activity.MainActivity;
import com.example.weather.R;
import com.example.weather.WeatherClasses.Daily;
import com.example.weather.WeatherClasses.Hourly;
import com.example.weather.WeatherClasses.Weather;

import java.util.List;


public class WeatherFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    String TAG = "WeatherFragment";

    public SwipeRefreshLayout swipeRefreshLayout;
    private ScrollView scrollView;
    private TextView textView_temp;
    private TextView textView_description;
    private TextView textView_feels_like_temp;
    private TextView textView_city_name;
    private ImageView imageView_weather;

    private RecyclerView recyclerViewHourly;
    private HourlyAdapter hourlyAdapter;

    private RecyclerView recyclerViewDaily;
    private DailyAdapter dailyAdapter;

    private Weather data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        //Получаем данных для этого фрагмента
        Bundle bundle = getArguments();
        data = (Weather) bundle.getSerializable("weather");

        initializationView(view); //Инициализация view
        setView(); // Установка значений для view
        setAdapters(); //Устанавливаем адаптеры

        return view;
    }

    private void initializationView(View view){
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        scrollView = view.findViewById(R.id.scroll_view__weather_background);
        textView_city_name = view.findViewById(R.id.textView_city_name);
        textView_temp = view.findViewById(R.id.textView_temp);
        textView_feels_like_temp = view.findViewById(R.id.textView_feels_like_temp);
        imageView_weather = view.findViewById(R.id.imageView_weather);
        textView_description = view.findViewById(R.id.textView_description);
        recyclerViewHourly = view.findViewById(R.id.recyclerViewHourly);
        recyclerViewDaily = view.findViewById(R.id.recyclerViewDaily);
    }

    @SuppressLint("SetTextI18n")
    private void setView(){
        int idWeather = data.getCurrent().getWeather().get(0).getId();

        ((MainActivity)getActivity()).setColors(idWeather);

        scrollView.setBackgroundResource(Icons.getBackground(idWeather));

        if (data.getCityName().isEmpty()) {
            textView_city_name.setText(getString(R.string.unknown_place));
        } else {
            textView_city_name.setText(data.getCityName());
        }

        textView_temp.setText(data.getCurrent().getTempString() + "C");
        textView_feels_like_temp.setText(getString(R.string.feels_like_temp) + " " + data.getCurrent().getFeelsLikeString() + "C");

        imageView_weather.setImageResource(Icons.getIcon(idWeather));

        textView_description.setText(data.getCurrent().getWeather().get(0).getDescription());
    }

    private void setAdapters(){
        //Адаптер для списка почасового прогноза
        List<Hourly> hourlyList = data.getHourly();
        hourlyList.remove(0);
        hourlyList.removeAll(hourlyList.subList(24, hourlyList.size()));
        hourlyAdapter = new HourlyAdapter(hourlyList);
        recyclerViewHourly.setAdapter(hourlyAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewHourly.setLayoutManager(linearLayoutManager);

        //Адаптер для списка ежедневного прогноза
        List<Daily> dailyList = data.getDaily();
        dailyList.remove(0);
        dailyAdapter = new DailyAdapter(dailyList);
        recyclerViewDaily.setAdapter(dailyAdapter);
        recyclerViewDaily.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);

        double lat = data.getLat();
        double lon = data.getLon();

        boolean result = ((MainActivity)getActivity()).updateWeather(lat, lon);

        if(!result){
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}