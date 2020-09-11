package com.mobiblanc.baridal_maghrib.views.main.portraits;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.views.cart.CartActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PortraitDetailsActivity extends AppCompatActivity {

    @BindView(R.id.quantity)
    TextView quantity;
    @BindView(R.id.cartBtn)
    ConstraintLayout cartBtn;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.notif)
    TextView notif;
    @BindView(R.id.preview)
    ImageView preview;
    @BindView(R.id.total)
    TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portrait_details);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.backBtn, R.id.decreaseBtn, R.id.increaseBtn, R.id.cartBtn, R.id.addBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                onBackPressed();
                break;
            case R.id.decreaseBtn:
                if (Integer.valueOf(quantity.getText().toString()) >= 0)
                    updateTotal(-1);
                break;
            case R.id.increaseBtn:
                updateTotal(1);
                break;
            case R.id.cartBtn:
                startActivity(new Intent(PortraitDetailsActivity.this, CartActivity.class));
                break;
            case R.id.addBtn:
                makeFlyAnimation();
                break;
        }
    }

    private void makeFlyAnimation() {


        new AnimationUtil().attachActivity(this).setTargetView(image).setMoveDuration(1000).setDestView(cartBtn).setAnimationListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                preview.setVisibility(View.VISIBLE);
                notif.setVisibility(View.VISIBLE);
                notif.setText(String.valueOf(Integer.valueOf(notif.getText().toString()) + Integer.valueOf(quantity.getText().toString())));
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).startAnimation();

    }

    private void updateTotal(int x) {
        quantity.setText(String.valueOf(Integer.valueOf(quantity.getText().toString()) + x));
        int qte = Integer.valueOf(quantity.getText().toString());
        float fee = 500 * qte;
        total.setText(String.format("%.2f", fee).replace(".", ",") + " MAD");
    }

}