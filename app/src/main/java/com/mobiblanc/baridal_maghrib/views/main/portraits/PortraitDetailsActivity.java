package com.mobiblanc.baridal_maghrib.views.main.portraits;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.views.main.cart.CartActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PortraitDetailsActivity extends AppCompatActivity {

    @BindView(R.id.quantity)
    TextView quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portrait_details);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.backBtn, R.id.decreaseBtn, R.id.increaseBtn, R.id.cartBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                onBackPressed();
                break;
            case R.id.decreaseBtn:
                quantity.setText(String.valueOf(Integer.valueOf(quantity.getText().toString()) - 1));
                break;
            case R.id.increaseBtn:
                quantity.setText(String.valueOf(Integer.valueOf(quantity.getText().toString()) + 1));
                break;
            case R.id.cartBtn:
                startActivity(new Intent(PortraitDetailsActivity.this, CartActivity.class));
                break;
        }
    }
}