package com.mobiblanc.gbam;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import com.flurry.android.FlurryAgent;

public class BaridAlMaghrib extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        new FlurryAgent.Builder()
                .withLogEnabled(true)
                .build(this, "WDMMP7FXCD5JZDM8WKT4");
    }
}
