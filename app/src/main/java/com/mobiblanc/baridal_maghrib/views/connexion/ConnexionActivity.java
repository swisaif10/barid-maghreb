package com.mobiblanc.baridal_maghrib.views.connexion;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.views.connexion.fragments.AuthenticationFragment;
import com.mobiblanc.baridal_maghrib.views.connexion.fragments.InscriptionFragment;

public class ConnexionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        if (getIntent() != null && getIntent().getBooleanExtra("to_login", true))
            getSupportFragmentManager().beginTransaction().add(R.id.container, new AuthenticationFragment()).commit();
        else
            getSupportFragmentManager().beginTransaction().add(R.id.container, new InscriptionFragment()).commit();

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();
        else
            finish();
    }
}