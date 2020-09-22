package com.mobiblanc.baridal_maghrib.views.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.views.account.connexion.AuthenticationFragment;
import com.mobiblanc.baridal_maghrib.views.account.connexion.RegistrationFragment;
import com.mobiblanc.baridal_maghrib.views.account.help.HelpFragment;
import com.mobiblanc.baridal_maghrib.views.account.profile.ProfileFragment;
import com.mobiblanc.baridal_maghrib.views.base.BaseActivity;

public class AccountActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        if (getIntent() != null) {
            Fragment fragment;
            switch (getIntent().getIntExtra("destination", -1)) {
                case 0:
                    fragment = new AuthenticationFragment();
                    break;
                case 1:
                    fragment = new ProfileFragment();
                    break;
                case 2:
                    fragment = new HelpFragment();
                    break;
                default:
                    fragment = new RegistrationFragment();
                    break;
            }
            replaceFragment(fragment);
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1)
            getSupportFragmentManager().popBackStack();
        else
            finish();
    }
}