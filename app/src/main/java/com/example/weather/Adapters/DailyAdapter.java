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
import com.example.weather.WeatherClasses.Daily;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.DailyViewHolder> {
    static final class DailyViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView_daily_date;
        private final TextView textView_daily_description;
        private final ImageView imageView_daily;
        private final TextView textView_daily_temp_day;
        private final TextView textView_daily_temp_night;


        public DailyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_daily_date = itemView.findViewById(R.id.textView_daily_date);
            textView_daily_description = itemView.findViewById(R.id.textView_daily_description);
            imageView_daily = itemView.findViewById(R.id.imageView_daily);
            textView_daily_temp_day = itemView.findViewById(R.id.textView_daily_temp_day);
            textView_daily_temp_night = itemView.findViewById(R.id.textView_daily_temp_night);
        }

        @SuppressLint("SetTextI18n")
        private void bind(@NonNull Daily daily) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTimeInMillis(daily.getDt() * 1000);
            String day = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("RU"));
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("RU"));

            textView_daily_date.setText(day + ", " + dayOfMonth + " " + month);

            textView_daily_description.setText(daily.getWeather().get(0).getDescription());

            int idWeather = daily.getWeather().get(0).getId();
            imageView_daily.setImageResource(Icons.getIcon(idWeather));

            textView_daily_temp_day.setText(daily.getTemp().getDayString());

            textView_daily_temp_night.setText(daily.getTemp().getNightString());
        }
    }

    private final List<Daily> dailyList;

    public DailyAdapter(List<Daily> dailyList) {
        this.dailyList = dailyList;
    }

    @NonNull
    @Override
    public DailyAdapter.DailyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.daily_item_layout, viewGroup, false);

        return new DailyAdapter.DailyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyAdapter.DailyViewHolder viewHolder, int i) {
        Daily daily = dailyList.get(i);
        viewHolder.bind(daily);
        viewHolder.itemView.setTag(daily);
    }

    @Override
    public int getItemCount() {
        return dailyList.size();
    }
}