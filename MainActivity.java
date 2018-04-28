package com.example.zhenying.weather;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
        RecyclerView recyclerView;
        ProgressBar mProgressBar;
        List<HoursWeather> data = new ArrayList<HoursWeather>();
        HoursWeatherAdapter hoursWeatherAdapter = new HoursWeatherAdapter(data);
        Button btnDetermine;
        EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //

        //
        btnDetermine = findViewById(R.id.btn_ok);
        editText = findViewById(R.id.ett_name);
        btnDetermine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hoursWeatherAdapter.clear();
                String url = "https://free-api.heweather.com/s6/weather/forecast?location="+editText.getText().toString().trim()+"&key=eddc14bbba8d4b238238eeb0698f46d0";
                new MyAsycTask().execute(url);

            }
        });

    }
    class MyAsycTask extends AsyncTask<String,Void,List<HoursWeather>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mProgressBar!=null)
                mProgressBar.setVisibility(View.GONE);
            createProgressBar();

        }

        @Override
        protected void onPostExecute(List<HoursWeather> hoursWeathers) {
            super.onPostExecute(hoursWeathers);
            data = hoursWeathers;
            if (data != null){
                mProgressBar.setVisibility(View.GONE);
                recyclerView = findViewById(R.id.recycler_view_hours_weather);
                LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                hoursWeatherAdapter = new HoursWeatherAdapter(data);
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(hoursWeatherAdapter);


                data = null;
                Toast.makeText(MainActivity.this,"天气信息已更新",Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(MainActivity.this,"未找到该城市",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected List<HoursWeather> doInBackground(String... strings) {
            List<HoursWeather> data = new ArrayList<HoursWeather>();
            //String url = "https://free-api.heweather.com/s6/weather/forecast?location=芗城区&key=eddc14bbba8d4b238238eeb0698f46d0";
            String url;
            url = strings[0];
            try {
                HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
                conn.setDoInput(true);
                conn.setConnectTimeout(10000);
                InputStream is = conn.getInputStream();
                StringBuilder stringBuilder = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                String str = null;
                while ((str = bufferedReader.readLine()) != null)
                    stringBuilder.append(str);
                str = stringBuilder.toString();
                is.close();
                //-----------------------------
                JSONObject obj;
                JSONArray array;
                obj = new JSONObject(str);
                array = obj.getJSONArray("HeWeather6");
                JSONObject object = array.getJSONObject(0);
                String status = object.getString("status");
                if (status.equals("ok")){
                    JSONArray forcast = object.getJSONArray("daily_forecast");
                    for (int i = 0; i < forcast.length(); i++) {
                        JSONObject js = forcast.getJSONObject(i);
                        HoursWeather hw = new HoursWeather();
                        hw.setDate_d(js.getString("date"));
                        hw.setImg_d(getResId("w"+js.getString("cond_code_d"),MainActivity.this));
                        hw.setWeather_d(js.getString("cond_txt_d"));
                        hw.setTemperature_d("最高气温："+js.getString("tmp_max")+"\n最低气温："+js.getString("tmp_min"));
                        //---
                        hw.setDate_n(js.getString("date"));
                        hw.setImg_n(getResId("w"+js.getString("cond_code_n"),MainActivity.this));
                        hw.setWeather_n(js.getString("cond_txt_n"));
                        hw.setTemperature_n("最高气温："+js.getString("tmp_max")+"\n最低气温："+js.getString("tmp_min"));
                        data.add(hw);
                    }
                }else {
                    return null;
                }
                //-----------------------------
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return data;
        }
    }
    public int getResId(String name, Context context) {
        Resources r = context.getResources();

        int id = r.getIdentifier(name, "drawable", getBaseContext().getPackageName());
        return id;
    }
    private void createProgressBar(){
        //整个Activity布局的最终父布局,参见参考资料
        FrameLayout rootFrameLayout=(FrameLayout) findViewById(android.R.id.content);
        FrameLayout.LayoutParams layoutParams=
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity= Gravity.CENTER;
        mProgressBar=new ProgressBar(this);
        mProgressBar.setLayoutParams(layoutParams);
        mProgressBar.setVisibility(View.VISIBLE);
        rootFrameLayout.addView(mProgressBar);
    }




}
