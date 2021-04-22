package com.mobiblanc.gbam.views.cart;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.databinding.ActivityCartBinding;
import com.mobiblanc.gbam.listeners.OnUpdateButtonClickListener;
import com.mobiblanc.gbam.models.shipping.address.Address;
import com.mobiblanc.gbam.views.base.BaseActivity;
import com.mobiblanc.gbam.views.cart.cartdetails.CartDetailsFragment;
import com.mobiblanc.gbam.views.cart.shipping.StandardShippingFragment;

import java.util.ArrayList;

public class CartActivity extends BaseActivity {

    private ActivityCartBinding activityBinding;
    private OnUpdateButtonClickListener onUpdateButtonClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_cart);

        if (getIntent() != null && getIntent().getIntExtra("destination", -1) == 1) {
            replaceFragment(StandardShippingFragment.newInstance((ArrayList<Address>) getIntent().getSerializableExtra("addresses"),
                    getIntent().getBooleanExtra("canPay", false)));
        } else {
            replaceFragment(new CartDetailsFragment());
        }

        activityBinding.backBtn.setOnClickListener(v -> onBackPressed());

        activityBinding.updateBtn.setOnClickListener(v -> onUpdateButtonClickListener.onUpdateButtonClick());

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1)
            getSupportFragmentManager().popBackStack();
        else
            finish();
    }

    public void showHideHeader(int visibility) {
        activityBinding.backBtn.setVisibility(visibility);
        activityBinding.logo.setVisibility(visibility);
    }

    public void showUpdateBtn(int visibility) {
        activityBinding.updateBtn.setVisibility(visibility);
    }

    public void setOnUpdateButtonClickListener(OnUpdateButtonClickListener onUpdateButtonClickListener) {
        this.onUpdateButtonClickListener = onUpdateButtonClickListener;
    }
}