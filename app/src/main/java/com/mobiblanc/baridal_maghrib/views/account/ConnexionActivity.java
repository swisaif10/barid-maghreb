package com.mobiblanc.baridal_maghrib.views.account;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.views.account.connexion.AuthenticationFragment;
import com.mobiblanc.baridal_maghrib.views.account.connexion.InscriptionFragment;
import com.mobiblanc.baridal_maghrib.views.account.profile.ProfileFragment;
import com.mobiblanc.baridal_maghrib.views.main.help.HelpFragment;

public class ConnexionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        if (getIntent() != null) {
            switch (getIntent().getIntExtra("destination", -1)) {
                case 0:
                    getSupportFragmentManager().beginTransaction().add(R.id.container, new AuthenticationFragment()).commit();
                    break;
                case 1:
                    getSupportFragmentManager().beginTransaction().add(R.id.container, new ProfileFragment()).commit();
                    break;
                case 2:
                    getSupportFragmentManager().beginTransaction().add(R.id.container, new HelpFragment()).commit();

                    break;
                default:
                    getSupportFragmentManager().beginTransaction().add(R.id.container, new InscriptionFragment()).commit();
                    break;

            }
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();
        else
            finish();
    }
}