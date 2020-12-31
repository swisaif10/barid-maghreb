package com.mobiblanc.gbam.views.account.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.databinding.FragmentUpdatePersonalInformationBinding;
import com.mobiblanc.gbam.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.gbam.models.account.profile.ProfileData;
import com.mobiblanc.gbam.models.account.profile.UserInfo;
import com.mobiblanc.gbam.utilities.Connectivity;
import com.mobiblanc.gbam.utilities.Constants;
import com.mobiblanc.gbam.utilities.Utilities;
import com.mobiblanc.gbam.viewmodels.AccountVM;
import com.mobiblanc.gbam.views.main.MainActivity;

public class UpdatePersonalInformationFragment extends Fragment {

    private FragmentUpdatePersonalInformationBinding fragmentBinding;
    private Connectivity connectivity;
    private AccountVM accountVM;
    private PreferenceManager preferenceManager;
    private Boolean editEnabled = false;

    public UpdatePersonalInformationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountVM = ViewModelProviders.of(this).get(AccountVM.class);

        connectivity = new Connectivity(requireContext(), this);

        accountVM.getProfileLiveData().observe(this, this::handleProfileData);
        accountVM.getUpdateProfileLiveData().observe(this, this::handleUpdateProfilesData);

        preferenceManager = new PreferenceManager.Builder(requireContext(), Context.MODE_PRIVATE)
                .name(Constants.SHARED_PREFS_NAME)
                .build();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = FragmentUpdatePersonalInformationBinding.inflate(inflater, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getProfile();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (editEnabled)
                    disableUpdate();
                else
                    requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    private void init(UserInfo userInfo) {
        fragmentBinding.backBtn.setOnClickListener(v -> {
            if (editEnabled) {
                Utilities.hideSoftKeyboard(getContext(), getView());
                disableUpdate();
            } else
                requireActivity().getSupportFragmentManager().popBackStack();
        });
        fragmentBinding.container.setOnClickListener(v -> Utilities.hideSoftKeyboard(getContext(), getView()));
        fragmentBinding.updateBtn.setOnClickListener(v -> enableUpdate());
        fragmentBinding.nextBtn.setOnClickListener(v -> updateProfile());

        fragmentBinding.inputsLayout.setVisibility(View.VISIBLE);
        fragmentBinding.lastName.setText(userInfo.getLastName());
        fragmentBinding.firstName.setText(userInfo.getFirstName());
        fragmentBinding.email.setText(userInfo.getEmail());
        fragmentBinding.phoneNumber.setText(userInfo.getUsername());

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
        fragmentBinding.email.addTextChangedListener(textWatcher);

    }

    private void enableUpdate() {
        editEnabled = true;
        fragmentBinding.lastName.setFocusable(true);
        fragmentBinding.lastName.setFocusableInTouchMode(true);
        fragmentBinding.firstName.setFocusable(true);
        fragmentBinding.firstName.setFocusableInTouchMode(true);
        fragmentBinding.email.setFocusable(true);
        fragmentBinding.email.setFocusableInTouchMode(true);
        fragmentBinding.nextBtn.setVisibility(View.VISIBLE);
        fragmentBinding.updateBtn.setVisibility(View.GONE);
    }

    private void disableUpdate() {
        editEnabled = false;
        fragmentBinding.lastName.setFocusable(false);
        fragmentBinding.lastName.setFocusableInTouchMode(false);
        fragmentBinding.firstName.setFocusable(false);
        fragmentBinding.firstName.setFocusableInTouchMode(false);
        fragmentBinding.email.setFocusable(false);
        fragmentBinding.email.setFocusableInTouchMode(false);
        fragmentBinding.nextBtn.setEnabled(false);
        fragmentBinding.nextBtn.setVisibility(View.GONE);
        fragmentBinding.updateBtn.setVisibility(View.VISIBLE);
    }

    private void checkForm() {
        fragmentBinding.nextBtn.setEnabled(!Utilities.isEmpty(fragmentBinding.lastName) && !Utilities.isEmpty(fragmentBinding.lastName) && !Utilities.isEmpty(fragmentBinding.email)
                && !Utilities.isEmpty(fragmentBinding.phoneNumber));
    }

    private void getProfile() {
        if (connectivity.isConnected()) {
            fragmentBinding.loader.setVisibility(View.VISIBLE);
            accountVM.getProfile(preferenceManager.getValue(Constants.TOKEN, null));
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleProfileData(ProfileData profileData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (profileData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = profileData.getHeader().getCode();
            if (code == 200) {
                init(profileData.getResponse().getUserInfo());
            } else if (code == 403) {
                Utilities.showErrorPopupWithClick(getContext(), profileData.getHeader().getMessage(), view -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    preferenceManager.clearValue(Constants.NB_ITEMS_IN_CART);
                    preferenceManager.clearValue(Constants.CART_ID);
                    requireActivity().finishAffinity();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                });
            } else {
                Utilities.showErrorPopup(getContext(), profileData.getHeader().getMessage());
            }
        }
    }

    private void updateProfile() {
        if (connectivity.isConnected()) {
            fragmentBinding.loader.setVisibility(View.VISIBLE);
            accountVM.updateProfile(preferenceManager.getValue(Constants.TOKEN, null)
                    , fragmentBinding.email.getText().toString(),
                    fragmentBinding.firstName.getText().toString(),
                    fragmentBinding.lastName.getText().toString());
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleUpdateProfilesData(ProfileData profileData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (profileData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = profileData.getHeader().getCode();
            if (code == 200) {
                preferenceManager.putValue(Constants.NAME, fragmentBinding.firstName.getText().toString() +
                        " " + fragmentBinding.lastName.getText().toString());
                disableUpdate();
            } else if (code == 403) {
                Utilities.showErrorPopupWithClick(getContext(), profileData.getHeader().getMessage(), view -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    preferenceManager.clearValue(Constants.NB_ITEMS_IN_CART);
                    preferenceManager.clearValue(Constants.CART_ID);
                    requireActivity().finishAffinity();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                });
            } else {
                Utilities.showErrorPopup(getContext(), profileData.getHeader().getMessage());
            }
        }
    }
}