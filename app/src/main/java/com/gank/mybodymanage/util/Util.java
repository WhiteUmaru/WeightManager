package com.gank.mybodymanage.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 工具类
 * Created by Ly on 2018/2/22.
 */

public class Util {

    public static void saveString(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
        editor.commit();
    }

}
