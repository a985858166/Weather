package com.example.zhenying.weather;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class HoursWeatherAdapter extends RecyclerView.Adapter<HoursWeatherAdapter.ViewHolder> {
    private List<HoursWeather> mHoursWeatherList;
    public HoursWeatherAdapter(List<HoursWeather> mHoursWeatherList){
        this.mHoursWeatherList = mHoursWeatherList;
    }

    public void clear(){
        mHoursWeatherList.clear();
        notifyDataSetChanged();

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_hours_weather,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HoursWeather hoursWeather = mHoursWeatherList.get(position);
        holder.date_d.setText(hoursWeather.getDate_d()+"-白");
        holder.weather_d.setText(hoursWeather.getWeather_d());
        holder.img_d.setImageResource(hoursWeather.getImg_d());
        holder.temperature_d.setText(hoursWeather.getTemperature_d());
        holder.date_n.setText(hoursWeather.getDate_n()+"-晚");
        holder.weather_n.setText(hoursWeather.getWeather_n());
        holder.img_n.setImageResource(hoursWeather.getImg_n());
        holder.temperature_n.setText(hoursWeather.getTemperature_n());

    }

    @Override
    public int getItemCount() {
        return mHoursWeatherList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView date_d;
        private TextView weather_d;
        private ImageView img_d;
        private TextView temperature_d;
        private TextView date_n;
        private TextView weather_n;
        private ImageView img_n;
        private TextView temperature_n;

        public ViewHolder(View itemView) {
            super(itemView);
            date_d = itemView.findViewById(R.id.tvw_date_d);
            weather_d = itemView.findViewById(R.id.tvw_weather_d);
            img_d = itemView.findViewById(R.id.img_d);
            temperature_d = itemView.findViewById(R.id.tvw_temperature_d);

            date_n = itemView.findViewById(R.id.tvw_date_n);
            weather_n = itemView.findViewById(R.id.tvw_weather_n);
            img_n = itemView.findViewById(R.id.img_n);
            temperature_n = itemView.findViewById(R.id.tvw_temperature_n);
        }
    }
}
