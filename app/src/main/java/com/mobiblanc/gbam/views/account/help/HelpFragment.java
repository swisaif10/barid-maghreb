package com.mobiblanc.gbam.views.account.help;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.databinding.FragmentHelpBinding;
import com.mobiblanc.gbam.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.gbam.models.account.otp.OTPData;
import com.mobiblanc.gbam.utilities.Connectivity;
import com.mobiblanc.gbam.utilities.Constants;
import com.mobiblanc.gbam.utilities.NumericKeyBoardTransformationMethod;
import com.mobiblanc.gbam.utilities.Utilities;
import com.mobiblanc.gbam.viewmodels.AccountVM;
import com.mobiblanc.gbam.views.main.MainActivity;

import java.util.Objects;

public class HelpFragment extends Fragment {

    private FragmentHelpBinding fragmentBinding;
    private Connectivity connectivity;
    private AccountVM accountVM;
    private PreferenceManager preferenceManager;
    private int phoneNumberLength = 11;

    public HelpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountVM = ViewModelProviders.of(this).get(AccountVM.class);

        connectivity = new Connectivity(getContext(), this);

        accountVM.getContactLiveData().observe(this, this::handleContactData);

        preferenceManager = new PreferenceManager.Builder(getContext(), Context.MODE_PRIVATE)
                .name(Constants.SHARED_PREFS_NAME)
                .build();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = FragmentHelpBinding.inflate(inflater, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        fragmentBinding.backBtn.setOnClickListener(v -> getActivity().onBackPressed());
        fragmentBinding.container.setOnClickListener(v -> Utilities.hideSoftKeyboard(getContext(), getView()));
        fragmentBinding.sendBtn.setOnClickListener(v -> sendContact());

        fragmentBinding.phoneNumber.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        fragmentBinding.phoneNumber.setTransformationMethod(new NumericKeyBoardTransformationMethod());

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

        fragmentBinding.name.addTextChangedListener(textWatcher);
        fragmentBinding.phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!String.valueOf(fragmentBinding.phoneNumber.getText()).contains(" ")) {
                    int maxLength = 10;
                    phoneNumberLength = maxLength;
                    InputFilter[] fArray = new InputFilter[1];
                    fArray[0] = new InputFilter.LengthFilter(maxLength);
                    fragmentBinding.phoneNumber.setFilters(fArray);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkForm();
                if (!Utilities.isEmailValid(s.toString().trim()) && !s.toString().equalsIgnoreCase("")) {
                    fragmentBinding.phoneError.setVisibility(View.VISIBLE);
                    fragmentBinding.phoneError.setText(R.string.invalid_format_error);
                } else {
                    fragmentBinding.phoneError.setVisibility(View.GONE);
                }
            }
        });
        fragmentBinding.object.addTextChangedListener(textWatcher);
        fragmentBinding.message.addTextChangedListener(textWatcher);
    }

    private void checkForm() {
        fragmentBinding.sendBtn.setEnabled(!Utilities.isEmpty(fragmentBinding.name) && !Utilities.isEmpty(fragmentBinding.email)
                && Utilities.isEmailValid(fragmentBinding.email.getText().toString().trim())
                && Objects.requireNonNull(fragmentBinding.phoneNumber.getText()).toString().length() == phoneNumberLength
                && !Utilities.isEmpty(fragmentBinding.phoneNumber) && !Utilities.isEmpty(fragmentBinding.object) && !Utilities.isEmpty(fragmentBinding.message));
    }

    private void sendContact() {
        if (connectivity.isConnected()) {
            fragmentBinding.loader.setVisibility(View.VISIBLE);
            accountVM.contact(preferenceManager.getValue(Constants.TOKEN, ""),
                    fragmentBinding.name.getText().toString(),
                    fragmentBinding.email.getText().toString(),
                    fragmentBinding.phoneNumber.getText().toString(),
                    fragmentBinding.object.getText().toString(),
                    fragmentBinding.message.getText().toString()
            );
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleContactData(OTPData otpData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (otpData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = otpData.getHeader().getCode();
            if (code == 200) {
                Utilities.showErrorPopupWithClick(getContext(), otpData.getHeader().getMessage(), view -> getActivity().onBackPressed());
            } else if (code == 403) {
                Utilities.showErrorPopupWithClick(getContext(), otpData.getHeader().getMessage(), view -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    getActivity().finishAffinity();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                });
            } else {
                Utilities.showErrorPopup(getContext(), otpData.getHeader().getMessage());
            }
        }
    }

}