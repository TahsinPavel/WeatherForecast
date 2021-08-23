package com.tahsin_nactar.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final String  TAG = MainActivity.class.getSimpleName();

    TextView tCity,tCountry,tPressure,tHumidity,tSunset,tSunrise,
            tTemperature,tTime,tFeeltemp;
    ImageView tIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tCity = (TextView) findViewById(R.id.city);
        tCountry = (TextView) findViewById(R.id.country);
        tPressure = (TextView) findViewById(R.id.pressure);
        tHumidity = (TextView) findViewById(R.id.humidity);
        tSunset = (TextView) findViewById(R.id.sunset);
        tSunrise = (TextView) findViewById(R.id.sunrise);
        tTemperature = (TextView) findViewById(R.id.temp);
        tTime = (TextView) findViewById(R.id.time_date);
        tFeeltemp = (TextView) findViewById(R.id.feel_like);
        tIcon = (ImageView)  findViewById(R.id.image);

        WeatherInfo();
    }

    private void WeatherInfo(){
        String tag_json_obj = "json_obj_req";

        String url = "http://api.openweathermap.org/data/2.5/weather?q=dhaka&appid=562406c28890590ef9052b5445e8f1d3";

        

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.e(TAG, response.toString());
                        try {
                            String name = response.getString("name");

                            JSONObject main = response.getJSONObject("main");
                            double temp = main.getDouble("temp");
                            double feels_like = main.getDouble("feels_like");
                            int pressure = main.getInt("pressure");
                            int humidity = main.getInt("humidity");
                            long timezone = main.getLong("timezone");

                            JSONArray weatherArray = response.getJSONArray("weather");
                            JSONObject item = weatherArray.getJSONObject(0);
                            String description = item.getString("description");
                            String weather = item.getString("main");
                            String weather_icon = item.getString("icon");

                            JSONObject wind = response.getJSONObject("wind");
                            double speed = wind.getDouble("speed");

                            JSONObject sys = response.getJSONObject("sys");
                            String country = sys.getString("country");
                            long sunrise = sys.getLong("sunrise");
                            long sunset = sys.getLong("sunset");

                            tCity.setText(name);






                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());

            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }
    public static String getDate(long milliSeconds, String dateFormat) {
        SimpleDateFormat formatter =
                new SimpleDateFormat(dateFormat, Locale.getDefault());

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}