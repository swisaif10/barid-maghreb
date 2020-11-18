package com.mobiblanc.gbam.views.tracking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.databinding.ActivityTrackingBinding;
import com.mobiblanc.gbam.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.gbam.utilities.Constants;
import com.mobiblanc.gbam.utilities.Utilities;
import com.mobiblanc.gbam.views.main.MainActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrackingActivity extends AppCompatActivity {

    private ActivityTrackingBinding activityBinding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        preferenceManager = new PreferenceManager.Builder(this, Context.MODE_PRIVATE)
                .name(Constants.SHARED_PREFS_NAME)
                .build();
        init();
    }

    private void init() {
        activityBinding.backBtn.setOnClickListener(v -> onBackPressed());

        activityBinding.container.setOnClickListener(v -> Utilities.hideSoftKeyboard(TrackingActivity.this, v));
    }
}