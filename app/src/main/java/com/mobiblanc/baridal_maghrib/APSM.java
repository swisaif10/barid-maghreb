package com.mobiblanc.baridal_maghrib;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

public class APSM extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

    }
}
