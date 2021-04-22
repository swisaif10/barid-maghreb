package com.mobiblanc.gbam.views.account.connexion;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.google.android.material.button.MaterialButton;
import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.listeners.OnDialogButtonsClickListener;
import com.mobiblanc.gbam.utilities.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendOTPDialog extends Dialog {

    private final Activity context;
    private final OnDialogButtonsClickListener onDialogButtonsClickListener;
    @BindView(R.id.code)
    PinEntryEditText code;
    @BindView(R.id.nextBtn)
    MaterialButton nextBtn;

    public SendOTPDialog(Activity context, OnDialogButtonsClickListener onDialogButtonsClickListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.onDialogButtonsClickListener = onDialogButtonsClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(context).inflate(R.layout.send_otp_dialog, null, false);
        ButterKnife.bind(this, view);
        this.setContentView(view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

    @OnClick({R.id.nextBtn, R.id.resendOTPBtn, R.id.container})
    public void onViewClicked(View view) {
        Utilities.hideSoftKeyboard(context, view);
        switch (view.getId()) {
            case R.id.nextBtn:
                onDialogButtonsClickListener.onFirstButtonClick(code.getText().toString().trim());
                dismiss();
                break;
            case R.id.resendOTPBtn:
                code.getEditableText().clear();
                onDialogButtonsClickListener.onSecondButtonClick();
                dismiss();
                break;
        }
    }

    private void init() {
        code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                nextBtn.setEnabled(code.getText().length() == 4);
            }
        });
    }
}
