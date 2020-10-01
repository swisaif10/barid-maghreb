package com.mobiblanc.baridal_maghrib.views.cart;

import android.os.Bundle;

import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.views.base.BaseActivity;
import com.mobiblanc.baridal_maghrib.views.cart.cartdetails.CartDetailsFragment;
import com.mobiblanc.baridal_maghrib.views.cart.delivery.StandardDeliveryFragment;

public class CartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        if (getIntent() != null && getIntent().getIntExtra("destination", -1) == 1) {
            replaceFragment(new StandardDeliveryFragment());
        } else {
            replaceFragment(new CartDetailsFragment());
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