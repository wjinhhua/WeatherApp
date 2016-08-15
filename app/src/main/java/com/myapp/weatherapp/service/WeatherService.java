package com.myapp.weatherapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.myapp.weatherapp.WeatherApplication;
import com.myapp.weatherapp.domain.Weather;
import com.myapp.weatherapp.util.ConstantsUtil;
import com.myapp.weatherapp.util.GsonUtil;
import com.myapp.weatherapp.util.SharePrefUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by wjh on 2015/10/29.
 */
public class WeatherService extends Service {
    private WeatherServiceBinder ibinder = new WeatherServiceBinder();
    private WeatherCallBack callBack;
    private Weather weather;
    private VolleyError error;
    private static final int REGET = 0;
    private static final int ONSUCCEES = 1;
    private static final int ONFAILURE = 2;
    private boolean isOnRun = false;
    private String cityId;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return ibinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        cityId =  SharePrefUtil.getString(getApplicationContext(), "id",null);
        if (cityId == null)
            cityId = "CN101280101";
        handler.sendEmptyMessage(REGET);
    }

    public class WeatherServiceBinder extends Binder {
        public WeatherService getService() {
            return WeatherService.this;
        }

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REGET:
                    getWeather();
                    sendEmptyMessageDelayed(REGET, 60 * 60 * 1000);
                    break;
                case ONSUCCEES:
                    if (callBack != null)
                        callBack.onSuccess(weather);
                    isOnRun = false;
                    break;
                case ONFAILURE:
                    if (callBack != null)
                        callBack.onFailure(error.getMessage());
                    break;
                default:
                    break;
            }
        }
    };

    public interface WeatherCallBack {
         void onSuccess(Weather weather);
         void onFailure(String failure);
    }

    public void setCallBack(WeatherCallBack callBack) {
        this.callBack = callBack;
    }

    public void cancleCallBack() {
        if (callBack != null) {
            callBack = null;
        }
    }
    public void getWeather(String cityId) {
        this.cityId = cityId;
        getWeather();
    }
    public void getWeather() {
        if (isOnRun) return;
        isOnRun = true;
        String url = ConstantsUtil.URL+cityId+"&key="+ConstantsUtil.KEY;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                JSONArray array = new JSONArray();
                try {
                    JSONObject object = new JSONObject(s);
                    JSONArray arry = object.getJSONArray("HeWeather data service 3.0");
                    s = arry.get(0).toString();
                  //  SharePrefUtil.saveString(getApplicationContext(), "weather", s);
                    weather =  GsonUtil.gson(s, Weather.class);
                 //   System.out.println("aaaaaaaaaaaaaaaaa"+weather.aqi.city);
                    handler.sendEmptyMessage(ONSUCCEES);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                handler.sendEmptyMessage(ONFAILURE);
                error = volleyError;
            }
        });
        request.setTag("GET");
        WeatherApplication.getRequestQueue().add(request);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        WeatherApplication.getRequestQueue().cancelAll("GET");
    }
}
