package com.mobiblanc.gbam.views.account.connexion;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.databinding.FragmentRegisterationBinding;
import com.mobiblanc.gbam.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.gbam.listeners.OnDialogButtonsClickListener;
import com.mobiblanc.gbam.models.account.checkotp.CheckOTPData;
import com.mobiblanc.gbam.models.account.otp.OTPData;
import com.mobiblanc.gbam.utilities.Connectivity;
import com.mobiblanc.gbam.utilities.Constants;
import com.mobiblanc.gbam.utilities.Utilities;
import com.mobiblanc.gbam.viewmodels.AccountVM;
import com.mobiblanc.gbam.views.account.AccountActivity;

public class RegistrationFragment extends Fragment implements OnDialogButtonsClickListener {

    private FragmentRegisterationBinding fragmentBinding;
    private Connectivity connectivity;
    private AccountVM accountVM;
    private PreferenceManager preferenceManager;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountVM = ViewModelProviders.of(this).get(AccountVM.class);
        connectivity = new Connectivity(getContext(), this);
        accountVM.getRegistrationLiveData().observe(this, this::handleRegistrationData);
        accountVM.getConfirmRegistrationLiveData().observe(this, this::handleConfirmRegistrationData);

        preferenceManager = new PreferenceManager.Builder(getContext(), Context.MODE_PRIVATE)
                .name(Constants.SHARED_PREFS_NAME)
                .build();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = FragmentRegisterationBinding.inflate(inflater, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void onFirstButtonClick(String code) {
        confirmRegistration(code);
    }

    @Override
    public void onSecondButtonClick() {
        //resend otp
    }

    private void init() {
        fragmentBinding.backBtn.setOnClickListener(v -> getActivity().onBackPressed());
        fragmentBinding.container.setOnClickListener(v -> Utilities.hideSoftKeyboard(getContext(), getView()));
        fragmentBinding.cguBtn.setOnClickListener(v -> ((AccountActivity) getActivity()).replaceFragment(new CGUFragment()));
        fragmentBinding.registerBtn.setOnClickListener(v -> register());

        fragmentBinding.cguBtn.setText(Html.fromHtml(getActivity().getResources().getString(R.string.cgu_text)));
        fragmentBinding.rulesBtn.setText(Html.fromHtml(getActivity().getResources().getString(R.string.rules_text)));

        TextWatcher textWatcher = new TextWatcher() {
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
        };

        fragmentBinding.lastName.addTextChangedListener(textWatcher);
        fragmentBinding.firstName.addTextChangedListener(textWatcher);

        fragmentBinding.email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkForm();
                if (!Utilities.isEmailValid(s.toString().trim()) && !s.toString().equalsIgnoreCase("")) {
                    fragmentBinding.emailError.setVisibility(View.VISIBLE);
                    fragmentBinding.emailError.setText(R.string.invalid_email_error);
                } else {
                    fragmentBinding.emailError.setVisibility(View.GONE);
                }
            }
        });


        fragmentBinding.cguCheck.setOnCheckedChangeListener((buttonView, isChecked) -> checkForm());
        fragmentBinding.rulesCheck.setOnCheckedChangeListener((buttonView, isChecked) -> checkForm());
    }

    private void checkForm() {
        fragmentBinding.registerBtn.setEnabled(!Utilities.isEmpty(fragmentBinding.firstName) && !Utilities.isEmpty(fragmentBinding.lastName)
                && !Utilities.isEmpty(fragmentBinding.email) && Utilities.isEmailValid(fragmentBinding.email.getText().toString().trim())
                && !Utilities.isEmpty(fragmentBinding.phoneNumber) && fragmentBinding.cguCheck.isChecked() && fragmentBinding.rulesCheck.isChecked());
    }

    private void register() {
        if (connectivity.isConnected()) {
            fragmentBinding.loader.setVisibility(View.VISIBLE);
            accountVM.register(fragmentBinding.email.getText().toString().trim(),
                    fragmentBinding.firstName.getText().toString().trim(),
                    fragmentBinding.lastName.getText().toString().trim(),
                    fragmentBinding.phoneNumber.getText().toString().trim());
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleRegistrationData(OTPData otpData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (otpData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = otpData.getHeader().getCode();
            if (code == 200) {
                new SendOTPDialog(getActivity(), this).show();
            } else {
                Utilities.showErrorPopup(getContext(), otpData.getHeader().getMessage());
            }
        }
    }

    private void confirmRegistration(String otp) {
        if (connectivity.isConnected()) {
            fragmentBinding.loader.setVisibility(View.VISIBLE);
            accountVM.confirmRegistration(fragmentBinding.phoneNumber.getText().toString().trim(),
                    otp, preferenceManager.getValue(Constants.CART_ID, ""));
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleConfirmRegistrationData(CheckOTPData checkOTPData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (checkOTPData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = checkOTPData.getHeader().getCode();
            if (code == 200) {
                getActivity().finish();
            } else {
                Utilities.showErrorPopup(getContext(), checkOTPData.getHeader().getMessage());
            }
        }
    }
}