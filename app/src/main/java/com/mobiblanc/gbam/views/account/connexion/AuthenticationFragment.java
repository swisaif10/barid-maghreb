package com.mobiblanc.gbam.views.account.connexion;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.databinding.FragmentAuthenticationBinding;
import com.mobiblanc.gbam.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.gbam.listeners.OnDialogButtonsClickListener;
import com.mobiblanc.gbam.models.account.checkotp.CheckOTPData;
import com.mobiblanc.gbam.models.account.otp.OTPData;
import com.mobiblanc.gbam.utilities.Connectivity;
import com.mobiblanc.gbam.utilities.Constants;
import com.mobiblanc.gbam.utilities.Utilities;
import com.mobiblanc.gbam.viewmodels.AccountVM;
import com.mobiblanc.gbam.views.account.AccountActivity;

public class AuthenticationFragment extends Fragment implements OnDialogButtonsClickListener {

    private FragmentAuthenticationBinding fragmentBinding;
    private Connectivity connectivity;
    private AccountVM accountVM;
    private PreferenceManager preferenceManager;

    public AuthenticationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountVM = ViewModelProviders.of(this).get(AccountVM.class);
        connectivity = new Connectivity(getContext(), this);
        accountVM.getSendOTPLiveData().observe(this, this::handleSendOTPData);
        accountVM.getLoginLiveData().observe(this, this::handleLoginData);

        preferenceManager = new PreferenceManager.Builder(getContext(), Context.MODE_PRIVATE)
                .name(Constants.SHARED_PREFS_NAME)
                .build();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = FragmentAuthenticationBinding.inflate(inflater, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void onFirstButtonClick(String code) {
        login(code);
    }

    @Override
    public void onSecondButtonClick() {
        //resend otp
    }

    private void init() {
        fragmentBinding.backBtn.setOnClickListener(v -> getActivity().onBackPressed());
        fragmentBinding.loginBtn.setOnClickListener(v -> sendOtp());
        fragmentBinding.container.setOnClickListener(v -> Utilities.hideSoftKeyboard(getContext(), getView()));
        fragmentBinding.registerBtn.setOnClickListener(v -> ((AccountActivity) getActivity()).replaceFragment(new RegistrationFragment()));

        fragmentBinding.phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkForm();
            }
        });
    }

    private void checkForm() {
        fragmentBinding.error.setVisibility(View.INVISIBLE);
        fragmentBinding.loginBtn.setEnabled(!Utilities.isEmpty(fragmentBinding.phoneNumber));
    }

    private void sendOtp() {
        if (connectivity.isConnected()) {
            fragmentBinding.loader.setVisibility(View.VISIBLE);
            accountVM.sendOtp(fragmentBinding.phoneNumber.getText().toString().trim(), Utilities.getUUID(getContext()));
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleSendOTPData(OTPData otpData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (otpData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = otpData.getHeader().getCode();
            if (code == 200) {
                new SendOTPDialog(getActivity(), this).show();
            } else {
                fragmentBinding.error.setText(otpData.getHeader().getMessage());
                fragmentBinding.error.setVisibility(View.VISIBLE);
            }
        }
    }

    private void login(String otp) {
        if (connectivity.isConnected()) {
            fragmentBinding.loader.setVisibility(View.VISIBLE);
            accountVM.login(fragmentBinding.phoneNumber.getText().toString().trim(), otp, preferenceManager.getValue(Constants.CART_ID, ""));
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleLoginData(CheckOTPData checkOTPData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (checkOTPData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = checkOTPData.getHeader().getCode();
            if (code == 200) {
                preferenceManager.putValue(Constants.TOKEN, checkOTPData.getResponse().getToken());
                preferenceManager.putValue(Constants.CART_ID, checkOTPData.getResponse().getQuoteId());
                getActivity().finish();
            } else {
                fragmentBinding.error.setText(checkOTPData.getHeader().getMessage());
                fragmentBinding.error.setVisibility(View.VISIBLE);
            }
        }
    }
}