package com.example.nguyentrungkien.drumpadelectronic;

import android.content.Context;
import android.content.SharedPreferences;

public class MySetting {
    public static final String REMOVE_ADS = "remove_ads";

    public static void putRemoveAds(Context context,boolean isRemoveAds){
        SharedPreferences sharedPreferences = context.getSharedPreferences(REMOVE_ADS,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(REMOVE_ADS, isRemoveAds);
        editor.commit();
    }

    public static boolean isRemoveAds(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(REMOVE_ADS,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(REMOVE_ADS, false);
    }
}
