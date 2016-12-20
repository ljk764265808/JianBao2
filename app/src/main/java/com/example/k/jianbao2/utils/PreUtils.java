package com.example.k.jianbao2.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by k on 2016/11/30.
 */

public class PreUtils {
    public static final String PREF_NAME = "config";

    public static boolean getBoolean(Context ctx, String key, boolean defaultValue) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
                 Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
  }

     public static void setBoolean(Context ctx, String key, boolean value) {
         SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
                  Context.MODE_PRIVATE);
         sp.edit().putBoolean(key, value).commit();
   }
}
