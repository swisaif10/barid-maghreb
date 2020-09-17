package com.mobiblanc.baridal_maghrib.views.tracking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.utilities.Utilities;
import com.mobiblanc.baridal_maghrib.views.cart.CartActivity;
import com.mobiblanc.baridal_maghrib.views.account.ConnexionActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrackingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.loginBtn, R.id.cartBtn, R.id.cancelBtn, R.id.container})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loginBtn:
                Intent intent = new Intent(TrackingActivity.this, ConnexionActivity.class);
                intent.putExtra("destination", 1);
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
        }
    }
}