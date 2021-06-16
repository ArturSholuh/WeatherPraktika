package com.example.weather.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather.Icons;
import com.example.weather.R;
import com.example.weather.WeatherClasses.Hourly;

import java.util.Calendar;
import java.util.List;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder> {
    static final class HourlyViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewHourlyTemp;
        private final ImageView imageViewHourly;
        private final TextView textViewHourlyTime;

        public HourlyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewHourlyTemp = itemView.findViewById(R.id.hourlyItemLayoutTemp);
            imageViewHourly = itemView.findViewById(R.id.hourlyItemLayoutImage);
            textViewHourlyTime = itemView.findViewById(R.id.hourlyItemLayoutTime);
        }

        @SuppressLint("SetTextI18n")
        private void bind(@NonNull Hourly hourly) {
            textViewHourlyTemp.setText(hourly.getTempString());

            int idWeather = hourly.getWeather().get(0).getId();
            imageViewHourly.setImageResource(Icons.getIcon(idWeather));


            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(hourly.getDt() * 1000);

            int hours = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);
            textViewHourlyTime.setText(hours + ":00");
        }
    }

    private final List<Hourly> hourlyList;

    public HourlyAdapter(List<Hourly> hourlyList) {
        this.hourlyList = hourlyList;
    }

    @NonNull
    @Override
    public HourlyAdapter.HourlyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hourly_item_layout, viewGroup, false);

        return new HourlyAdapter.HourlyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyAdapter.HourlyViewHolder viewHolder, int i) {
        Hourly hourly = hourlyList.get(i);
        viewHolder.bind(hourly);
        viewHolder.itemView.setTag(hourly);
    }

    @Override
    public int getItemCount() {
        return hourlyList.size();
    }
}