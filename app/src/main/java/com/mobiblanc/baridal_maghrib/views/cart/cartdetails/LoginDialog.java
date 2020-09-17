package com.mobiblanc.baridal_maghrib.views.cart.cartdetails;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.listeners.OnDialogButtonsClickListener;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginDialog extends Dialog {

    private Activity context;
    private OnDialogButtonsClickListener onDialogButtonsClickListener;


    public LoginDialog(Activity context, OnDialogButtonsClickListener onDialogButtonsClickListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.onDialogButtonsClickListener = onDialogButtonsClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(context).inflate(R.layout.login_dialog, null, false);
        ButterKnife.bind(this, view);
        this.setContentView(view);
    }


    @OnClick({R.id.loginBtn, R.id.signUpBtn, R.id.cancelBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loginBtn:
                onDialogButtonsClickListener.onFirstButtonClick();
                break;
            case R.id.signUpBtn:
                onDialogButtonsClickListener.onSecondButtonClick();
                break;
        }
        dismiss();
    }
}
