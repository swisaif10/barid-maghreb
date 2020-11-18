package com.mobiblanc.gbam;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

public class BaridAlMaghrib extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

    }
}
