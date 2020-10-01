package com.mobiblanc.baridal_maghrib.views.tracking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.baridal_maghrib.utilities.Constants;
import com.mobiblanc.baridal_maghrib.utilities.Utilities;
import com.mobiblanc.baridal_maghrib.views.account.AccountActivity;
import com.mobiblanc.baridal_maghrib.views.cart.CartActivity;
import com.mobiblanc.baridal_maghrib.views.main.MainActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrackingActivity extends AppCompatActivity {
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        ButterKnife.bind(this);

        preferenceManager = new PreferenceManager.Builder(this, Context.MODE_PRIVATE)
                .name(Constants.SHARED_PREFS_NAME)
                .build();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TrackingActivity.this, MainActivity.class));
        finish();
    }

    @OnClick({R.id.loginBtn, R.id.cartBtn, R.id.cancelBtn, R.id.container, R.id.logo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loginBtn:
                Intent intent = new Intent(TrackingActivity.this, AccountActivity.class);
                if (preferenceManager.getValue(Constants.TOKEN, null) != null)
                    intent.putExtra("destination", 1);
                else
                    intent.putExtra("destination", 0);
                startActivity(intent);
                break;
            case R.id.cartBtn:
                startActivity(new Intent(TrackingActivity.this, CartActivity.class));
                break;
            case R.id.cancelBtn:
                break;
            case R.id.container:
                Utilities.hideSoftKeyboard(this, view);
                break;
            case R.id.logo:
                startActivity(new Intent(TrackingActivity.this, MainActivity.class));
                finish();
                break;
        }
    }
}