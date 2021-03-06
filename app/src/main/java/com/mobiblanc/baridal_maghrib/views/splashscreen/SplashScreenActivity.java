package com.mobiblanc.baridal_maghrib.views.splashscreen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.listeners.OnDialogButtonsClickListener;
import com.mobiblanc.baridal_maghrib.models.controlversion.ControlVersionData;
import com.mobiblanc.baridal_maghrib.utilities.Connectivity;
import com.mobiblanc.baridal_maghrib.utilities.Utilities;
import com.mobiblanc.baridal_maghrib.viewmodels.SplashVM;
import com.mobiblanc.baridal_maghrib.views.main.MainActivity;

public class SplashScreenActivity extends AppCompatActivity implements OnDialogButtonsClickListener {

    private SplashVM splashVM;
    private Connectivity connectivity;
    private String link;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splashVM = ViewModelProviders.of(this).get(SplashVM.class);
        connectivity = new Connectivity(this, this);

        splashVM.getControlVersionLiveData().observe(this, this::handleVersionCheckResponse);

        new Handler().postDelayed(this::controlVersion, 3000);
    }

    @Override
    public void onFirstButtonClick() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
    }

    @Override
    public void onSecondButtonClick() {
        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
        finish();
    }

    private void controlVersion() {
        if (connectivity.isConnected()) {
            splashVM.controlVersion();
        } else
            Utilities.showErrorPopup(this, getString(R.string.no_internet_msg));
    }

    private void handleVersionCheckResponse(ControlVersionData controlVersionData) {
        if (controlVersionData == null) {
            Utilities.showErrorPopup(this, getString(R.string.generic_error));
        } else {
            int code = controlVersionData.getHeader().getCode();
            if (code == 200) {
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                finish();
            } else if (code == 204) {
                link = controlVersionData.getResponse().getLink();
                Utilities.showUpdateDialog(this, controlVersionData.getResponse().getStatus(), this);
            } else
                Utilities.showErrorPopup(this, controlVersionData.getHeader().getMessage());
        }
    }
}