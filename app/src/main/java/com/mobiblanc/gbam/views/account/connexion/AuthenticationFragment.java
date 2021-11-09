package com.mobiblanc.gbam.views.account.connexion;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
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
import com.mobiblanc.gbam.models.cart.items.CartItemsData;
import com.mobiblanc.gbam.models.common.Item;
import com.mobiblanc.gbam.utilities.Connectivity;
import com.mobiblanc.gbam.utilities.Constants;
import com.mobiblanc.gbam.utilities.NumericKeyBoardTransformationMethod;
import com.mobiblanc.gbam.utilities.Utilities;
import com.mobiblanc.gbam.viewmodels.AccountVM;
import com.mobiblanc.gbam.viewmodels.CartVM;
import com.mobiblanc.gbam.views.account.AccountActivity;
import com.mobiblanc.gbam.views.account.orders.OrdersFragment;
import com.mobiblanc.gbam.views.account.profile.ContactFragment;
import com.mobiblanc.gbam.views.cart.CartActivity;
import com.mobiblanc.gbam.views.main.MainActivity;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class AuthenticationFragment extends Fragment implements OnDialogButtonsClickListener {

    private static final String TAG = "TAG";
    private FragmentAuthenticationBinding fragmentBinding;
    private Connectivity connectivity;
    private AccountVM accountVM;
    private CartVM cartVM;
    private PreferenceManager preferenceManager;
    private String destination = "";
    private int phoneNumberLength = 11;
    private List<Item> items;

    public AuthenticationFragment() {
        // Required empty public constructor
    }

    public static AuthenticationFragment newInstance(String destination) {
        AuthenticationFragment fragment = new AuthenticationFragment();
        Bundle args = new Bundle();
        args.putString("destination", destination);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartVM = ViewModelProviders.of(this).get(CartVM.class);
        cartVM.getCartItemsLiveData().observe(this, this::handleNumberOfItemsData);
        accountVM = ViewModelProviders.of(this).get(AccountVM.class);
        connectivity = new Connectivity(requireContext(), this);
        accountVM.getSendOTPLiveData().observe(this, this::handleSendOTPData);
        accountVM.getLoginLiveData().observe(this, this::handleLoginData);

        preferenceManager = new PreferenceManager.Builder(requireContext(), Context.MODE_PRIVATE)
                .name(Constants.SHARED_PREFS_NAME)
                .build();

        if (getArguments() != null) {
            destination = getArguments().getString("destination", "");
        }
    }

    private void handleNumberOfItemsData(CartItemsData cartItemsData) {
        Log.d(TAG, "handleNumberOfItemsData: ");
        fragmentBinding.loader.setVisibility(View.GONE);
        if (cartItemsData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = cartItemsData.getHeader().getCode();
            if (code == 200) {
                items = cartItemsData.getResponse().getItems();
                preferenceManager.putValue(Constants.NB_ITEMS_IN_CART,cartItemsData.getResponse().getCountCart());
                if (destination.equalsIgnoreCase("new_address")) {
                    Intent intent = new Intent(getActivity(), CartActivity.class);
                    intent.putExtra("destination", 1);
                    startActivity(intent);
                    requireActivity().finish();
                } else if (destination.equalsIgnoreCase("history")) {
                    ((AccountActivity) requireActivity()).replaceFragmentWithoutBAckStack(new OrdersFragment());
                } else if (destination.equalsIgnoreCase("contact")) {
                    ((AccountActivity) requireActivity()).replaceFragmentWithoutBAckStack(new ContactFragment());
                }

                else
                    requireActivity().finish();

            }else {
                Log.d("TAG", "handleNumberOfItemsData: error ");
            }
        }

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
        sendOtp();
    }

    private void init() {
        fragmentBinding.backBtn.setOnClickListener(v -> requireActivity().onBackPressed());
        fragmentBinding.loginBtn.setOnClickListener(v -> sendOtp());
        fragmentBinding.container.setOnClickListener(v -> Utilities.hideSoftKeyboard(getContext(), getView()));
        fragmentBinding.registerBtn.setOnClickListener(v -> ((AccountActivity) requireActivity()).replaceFragment(new RegistrationFragment()));

        fragmentBinding.phoneNumber.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        fragmentBinding.phoneNumber.setTransformationMethod(new NumericKeyBoardTransformationMethod());

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
            }
        });
    }

    private void checkForm() {
        fragmentBinding.error.setVisibility(View.INVISIBLE);
        fragmentBinding.loginBtn.setEnabled(!Utilities.isEmpty(fragmentBinding.phoneNumber)
                && Objects.requireNonNull(fragmentBinding.phoneNumber.getText()).toString().length() == phoneNumberLength);
    }

    private void sendOtp() {
        if (connectivity.isConnected()) {
            fragmentBinding.loader.setVisibility(View.VISIBLE);
            accountVM.sendOtp(String.valueOf(fragmentBinding.phoneNumber.getText()).trim(), Utilities.getUUID(requireContext()));
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
                Utilities.showSendOtpPopup(requireContext(), this);
            } else {
                fragmentBinding.error.setText(otpData.getHeader().getMessage());
                fragmentBinding.error.setVisibility(View.VISIBLE);
            }
        }
    }

    private void login(String otp) {
        if (connectivity.isConnected()) {
            fragmentBinding.loader.setVisibility(View.VISIBLE);
            accountVM.login(String.valueOf(fragmentBinding.phoneNumber.getText()).trim(), otp, preferenceManager.getValue(Constants.CART_ID, ""));
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleLoginData(CheckOTPData checkOTPData) {
        //fragmentBinding.loader.setVisibility(View.GONE);
        fragmentBinding.loader.setVisibility(View.GONE);
        if (checkOTPData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = checkOTPData.getHeader().getCode();
            if (code == 200) {
                preferenceManager.putValue(Constants.TOKEN, checkOTPData.getResponse().getToken());
                preferenceManager.putValue(Constants.CART_ID, checkOTPData.getResponse().getQuoteId());
                preferenceManager.putValue(Constants.NAME, checkOTPData.getResponse().getName());
                preferenceManager.putValue(Constants.PHONE_NUMBER, String.valueOf(fragmentBinding.phoneNumber.getText()));
                //preferenceManager.putValue(Constants.NB_ITEMS_IN_CART, preferenceManager.getValue(String.valueOf(fragmentBinding.phoneNumber.getText()), 0));
                preferenceManager.putValue(Constants.NB_ITEMS_IN_CART,0);

                cartVM.getCartItems(preferenceManager.getValue(Constants.TOKEN, ""), preferenceManager.getValue(Constants.CART_ID, null));

            } else {
                fragmentBinding.error.setText(checkOTPData.getHeader().getMessage());
                fragmentBinding.error.setVisibility(View.VISIBLE);
            }
        }
    }
}