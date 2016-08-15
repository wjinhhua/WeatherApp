package com.myapp.weatherapp;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by wjh on 2016/6/20.
 */
public class WeatherApplication extends Application {
    private static WeatherApplication application;
    public static RequestQueue queen;
    private static int mainTid;
    private static Handler handler;
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        mainTid = android.os.Process.myTid();
        handler=new Handler();
        queen = Volley.newRequestQueue(getApplicationContext());
    }
    public static RequestQueue getRequestQueue() {
        return queen;
    }
    public static Context getApplication() {
        return application;
    }

    public static int getMainTid() {
        return mainTid;
    }
    public static Handler getHandler() {
        return handler;
    }
}
