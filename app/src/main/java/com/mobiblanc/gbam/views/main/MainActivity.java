package com.mobiblanc.gbam.views.main;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.databinding.ActivityMainBinding;
import com.mobiblanc.gbam.views.base.BaseActivity;
import com.mobiblanc.gbam.views.main.dashboard.DashboardFragment;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        replaceFragment(new DashboardFragment());
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1)
            getSupportFragmentManager().popBackStack();
        else
            finish();
    }
}