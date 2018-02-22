package com.gank.mybodymanage.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 工具类
 *
 * @author Ly
 * @date 2018/2/22
 */

public class Util {

    public static final String USER_NAME = "userName";
    public static final String USER_HEIGHT = "userHeight";

    public static void saveString(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
        editor.commit();
    }

    public static void saveInt(Context context, String key, int value) {
        SharedPreferences preferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
        editor.commit();
    }

    public static String getString(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return preferences.getString(key, value);
    }

    public static int getInt(Context context, String key, int value) {
        SharedPreferences preferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return preferences.getInt(key, value);
    }

    public static String getDate(long timestamp) {
        Date date = new Date(timestamp * 1000);
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        return format.format(date);
    }
 public static String getDateForYear(long timestamp) {
        Date date = new Date(timestamp * 1000);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

}
