package com.myapp.weatherapp.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 版权: ft626 版权所有(c) 2016
 * 作者: wjh
 * 版本: 1.0
 * 创建日期: 2016/6/21.20:34
 * 描述:
 **/
public class SharePrefUtil {
    public final static String NAME = "weather";
    private static SharedPreferences sharedPreferences;

    public static void saveString(Context context, String key, String value) {
        if (sharedPreferences == null)
            sharedPreferences= context.getSharedPreferences(NAME, 0);
        sharedPreferences.edit().putString(key, value).commit();

    }

    public static String getString(Context context, String key, String defValue) {
        if (sharedPreferences == null)
            sharedPreferences = context.getSharedPreferences(NAME, 0);
        return sharedPreferences.getString(key, defValue);
    }

    public static void saveBoolean(Context context, String key, boolean value) {
        if (sharedPreferences == null)
            sharedPreferences = context.getSharedPreferences(NAME, 0);
        sharedPreferences.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key,
                                     boolean defValue) {
        if (sharedPreferences == null)
            sharedPreferences = context.getSharedPreferences(NAME, 0);
        return sharedPreferences.getBoolean(key, defValue);
    }

}
