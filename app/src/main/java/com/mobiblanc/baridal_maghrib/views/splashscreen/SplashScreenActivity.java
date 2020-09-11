package com.mobiblanc.baridal_maghrib.views.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.listeners.OnDialogButtonsClickListener;
import com.mobiblanc.baridal_maghrib.utilities.Utilities;
import com.mobiblanc.baridal_maghrib.views.main.MainActivity;

public class SplashScreenActivity extends AppCompatActivity implements OnDialogButtonsClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(() -> {
            Utilities.showUpdateDialog(this,this);

        }, 3000);
    }

    @Override
    public void onFirstButtonClick() {
        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onSecondButtonClick() {
        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
        finish();
    }
}