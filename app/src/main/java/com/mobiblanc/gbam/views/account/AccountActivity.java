package com.mobiblanc.gbam.views.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.views.account.connexion.AuthenticationFragment;
import com.mobiblanc.gbam.views.account.connexion.RegistrationFragment;
import com.mobiblanc.gbam.views.account.profile.ContactFragment;
import com.mobiblanc.gbam.views.account.profile.ProfileFragment;
import com.mobiblanc.gbam.views.base.BaseActivity;

public class AccountActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        if (getIntent() != null) {
            Fragment fragment;
            switch (getIntent().getIntExtra("destination", -1)) {
                case 0:
                    fragment = AuthenticationFragment.newInstance(getIntent().getStringExtra("next"));
                    break;
                case 1:
                    fragment = new ProfileFragment();
                    break;
                case 2:
                    fragment = new ContactFragment();
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
            super.onBackPressed();
            //getSupportFragmentManager().popBackStack();
        else
            finish();
    }
}