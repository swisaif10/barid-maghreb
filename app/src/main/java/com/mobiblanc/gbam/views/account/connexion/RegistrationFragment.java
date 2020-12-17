package com.mobiblanc.gbam.views.account.connexion;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
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
import com.mobiblanc.gbam.databinding.FragmentRegisterationBinding;
import com.mobiblanc.gbam.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.gbam.listeners.OnDialogButtonsClickListener;
import com.mobiblanc.gbam.models.account.checkotp.CheckOTPData;
import com.mobiblanc.gbam.models.account.otp.OTPData;
import com.mobiblanc.gbam.utilities.Connectivity;
import com.mobiblanc.gbam.utilities.Constants;
import com.mobiblanc.gbam.utilities.NumericKeyBoardTransformationMethod;
import com.mobiblanc.gbam.utilities.Utilities;
import com.mobiblanc.gbam.viewmodels.AccountVM;
import com.mobiblanc.gbam.views.account.AccountActivity;

import java.util.Objects;

public class RegistrationFragment extends Fragment implements OnDialogButtonsClickListener {

    private FragmentRegisterationBinding fragmentBinding;
    private Connectivity connectivity;
    private AccountVM accountVM;
    private PreferenceManager preferenceManager;
    private int phoneNumberLength = 11;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountVM = ViewModelProviders.of(this).get(AccountVM.class);
        connectivity = new Connectivity(requireContext(), this);
        accountVM.getRegistrationLiveData().observe(this, this::handleRegistrationData);
        accountVM.getConfirmRegistrationLiveData().observe(this, this::handleConfirmRegistrationData);

        preferenceManager = new PreferenceManager.Builder(requireContext(), Context.MODE_PRIVATE)
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
        register();
    }

    private void init() {
        fragmentBinding.backBtn.setOnClickListener(v -> requireActivity().onBackPressed());
        fragmentBinding.container.setOnClickListener(v -> Utilities.hideSoftKeyboard(getContext(), getView()));
        fragmentBinding.cguBtn.setOnClickListener(v -> ((AccountActivity) requireActivity()).replaceFragment(CGUFragment.newInstance(true)));
        fragmentBinding.rulesBtn.setOnClickListener(v -> ((AccountActivity) requireActivity()).replaceFragment(new PDFFragment()));
        fragmentBinding.registerBtn.setOnClickListener(v -> register());

        fragmentBinding.phoneNumber.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        fragmentBinding.phoneNumber.setTransformationMethod(new NumericKeyBoardTransformationMethod());

        fragmentBinding.cguBtn.setText(Html.fromHtml(requireActivity().getResources().getString(R.string.cgu_text)));
        fragmentBinding.rulesBtn.setText(Html.fromHtml(requireActivity().getResources().getString(R.string.rules_text)));

        TextWatcher textWatcher = new TextWatcher() {
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
            }
        };

        fragmentBinding.lastName.addTextChangedListener(textWatcher);
        fragmentBinding.firstName.addTextChangedListener(textWatcher);
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
                && !Utilities.isEmpty(fragmentBinding.email) && Utilities.isEmailValid(String.valueOf(fragmentBinding.email.getText()).trim())
                && !Utilities.isEmpty(fragmentBinding.phoneNumber)
                && Objects.requireNonNull(fragmentBinding.phoneNumber.getText()).toString().length() == phoneNumberLength
                && fragmentBinding.cguCheck.isChecked() && fragmentBinding.rulesCheck.isChecked());
    }

    private void register() {
        if (connectivity.isConnected()) {
            fragmentBinding.loader.setVisibility(View.VISIBLE);
            accountVM.register(String.valueOf(fragmentBinding.email.getText()).trim(),
                    String.valueOf(fragmentBinding.firstName.getText()).trim(),
                    String.valueOf(fragmentBinding.lastName.getText()).trim(),
                    String.valueOf(fragmentBinding.phoneNumber.getText()).trim());
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
                new SendOTPDialog(requireActivity(), this).show();
            } else {
                Utilities.showErrorPopup(getContext(), otpData.getHeader().getMessage());
            }
        }
    }

    private void confirmRegistration(String otp) {
        if (connectivity.isConnected()) {
            fragmentBinding.loader.setVisibility(View.VISIBLE);
            accountVM.confirmRegistration(String.valueOf(fragmentBinding.phoneNumber.getText()).trim(),
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
                requireActivity().finish();
            } else {
                Utilities.showErrorPopup(getContext(), checkOTPData.getHeader().getMessage());
            }
        }
    }
}