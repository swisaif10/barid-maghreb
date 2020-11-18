package com.mobiblanc.gbam.views.cart;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.views.base.BaseActivity;
import com.mobiblanc.gbam.views.cart.cartdetails.CartDetailsFragment;
import com.mobiblanc.gbam.views.cart.shipping.StandardShippingFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CartActivity extends BaseActivity {

    @BindView(R.id.backBtn)
    RelativeLayout backBtn;
    @BindView(R.id.logo)
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        if (getIntent() != null && getIntent().getIntExtra("destination", -1) == 1) {
            replaceFragment(new StandardShippingFragment());
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

    @OnClick({R.id.backBtn})
    public void onViewClicked(View view) {
        onBackPressed();
    }

    public void showHideHeader(int visibility){
        backBtn.setVisibility(visibility);
        logo.setVisibility(visibility);
    }
}