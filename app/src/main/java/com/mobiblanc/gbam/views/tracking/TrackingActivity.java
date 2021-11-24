package com.mobiblanc.gbam.views.tracking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.databinding.ActivityTrackingBinding;
import com.mobiblanc.gbam.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.gbam.models.tracking.TrackingData;
import com.mobiblanc.gbam.utilities.Connectivity;
import com.mobiblanc.gbam.utilities.Constants;
import com.mobiblanc.gbam.utilities.Utilities;
import com.mobiblanc.gbam.viewmodels.TrackingVM;
import com.mobiblanc.gbam.views.main.MainActivity;

public class TrackingActivity extends AppCompatActivity {

    private ActivityTrackingBinding activityBinding;
    private PreferenceManager preferenceManager;
    private Connectivity connectivity;
    private TrackingVM trackingVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_tracking);

        trackingVM = ViewModelProviders.of(this).get(TrackingVM.class);
        connectivity = new Connectivity(this, this);

        trackingVM.getTrackingLiveData().observe(this, this::handleTrackingData);

        preferenceManager = new PreferenceManager.Builder(this, Context.MODE_PRIVATE)
                .name(Constants.SHARED_PREFS_NAME)
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        activityBinding.backBtn.setOnClickListener(v -> onBackPressed());

        activityBinding.container.setOnClickListener(v -> Utilities.hideSoftKeyboard(TrackingActivity.this, v));

        activityBinding.scrollView.setOnClickListener(v -> Utilities.hideSoftKeyboard(TrackingActivity.this, v));

        activityBinding.applyBtn.setOnClickListener(v -> {
            Utilities.hideSoftKeyboard(TrackingActivity.this, v);
            getTracking(activityBinding.code.getText().toString());
        });

        activityBinding.code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityBinding.applyBtn.setEnabled(!s.toString().isEmpty());
            }
        });

        activityBinding.trackingRecycler.setLayoutManager(new LinearLayoutManager(this));
        activityBinding.trackingRecycler.setNestedScrollingEnabled(false);

        if (getIntent().hasExtra("trackingId")) {
            String orderId = getIntent().getStringExtra("orderId");
            String trackingId = getIntent().getStringExtra("trackingId");
            activityBinding.codeLayout.setVisibility(View.GONE);
            //activityBinding.title.setText(String.format("Commande N°%s", orderId));
            activityBinding.title.setVisibility(View.GONE);
            activityBinding.trackingNumber.setText(String.format("Expédition N°%s", trackingId));
            activityBinding.trackingNumber.setVisibility(View.VISIBLE);
            getTracking(trackingId);
        }
    }

    private void getTracking(String id) {
        if (connectivity.isConnected()) {
            activityBinding.loader.setVisibility(View.VISIBLE);
            trackingVM.trackCommand(preferenceManager.getValue(Constants.TOKEN, ""),
                    id);

        } else
            Utilities.showErrorPopup(this, getString(R.string.no_internet_msg));
    }

    private void handleTrackingData(TrackingData trackingData) {
        activityBinding.loader.setVisibility(View.GONE);
        if (trackingData == null) {
            Utilities.showErrorPopup(this, getString(R.string.generic_error));
        } else {
            int code = trackingData.getHeader().getCode();
            if (code == 200) {
                activityBinding.trackingRecycler.setVisibility(View.VISIBLE);
                activityBinding.trackingRecycler.setAdapter(new TrackingAdapter(trackingData.getResponse()));
            } else if (code == 403) {
                Utilities.showErrorPopupWithClick(this, trackingData.getHeader().getMessage(), view -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    preferenceManager.clearValue(Constants.NB_ITEMS_IN_CART);
                    preferenceManager.clearValue(Constants.CART_ID);
                    finishAffinity();
                    startActivity(new Intent(this, MainActivity.class));
                });
            } else {
                activityBinding.trackingRecycler.setVisibility(View.GONE);
                Utilities.showTrackingDialog(this, trackingData.getHeader().getMessage(), view -> {
                }, false);
            }
        }
    }
}