package com.mobiblanc.baridal_maghrib.views.cart;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.views.cart.cartdetails.CartDetailsFragment;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getSupportFragmentManager().beginTransaction().add(R.id.container, new CartDetailsFragment()).addToBackStack("").commit();
    }
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1)
            getSupportFragmentManager().popBackStack();
        else
            finish();
    }
}