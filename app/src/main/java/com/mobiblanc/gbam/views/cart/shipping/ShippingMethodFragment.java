package com.mobiblanc.gbam.views.cart.shipping;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.databinding.FragmentShippingMethodBinding;
import com.mobiblanc.gbam.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.gbam.models.shipping.address.AddressData;
import com.mobiblanc.gbam.utilities.Connectivity;
import com.mobiblanc.gbam.utilities.Constants;
import com.mobiblanc.gbam.utilities.Utilities;
import com.mobiblanc.gbam.viewmodels.CartVM;
import com.mobiblanc.gbam.views.cart.CartActivity;
import com.mobiblanc.gbam.views.main.MainActivity;

public class ShippingMethodFragment extends Fragment {

    private FragmentShippingMethodBinding fragmentBinding;
    private Connectivity connectivity;
    private CartVM cartVM;
    private PreferenceManager preferenceManager;

    public ShippingMethodFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cartVM = ViewModelProviders.of(this).get(CartVM.class);
        connectivity = new Connectivity(getContext(), this);
        cartVM.getAddressLiveData().observe(this, this::handleAddressData);


        preferenceManager = new PreferenceManager.Builder(getContext(), Context.MODE_PRIVATE)
                .name(Constants.SHARED_PREFS_NAME)
                .build();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = FragmentShippingMethodBinding.inflate(inflater, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        fragmentBinding.standardShipping.setOnClickListener(v -> getAddress());
        fragmentBinding.agencyShipping.setOnClickListener(v -> ((CartActivity) getActivity()).replaceFragment(new AgencyShippingFragment()));
    }

    private void getAddress() {
        if (connectivity.isConnected()) {
            fragmentBinding.loader.setVisibility(View.VISIBLE);
            cartVM.getAddress(preferenceManager.getValue(Constants.TOKEN, null));
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleAddressData(AddressData addressData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (addressData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = addressData.getHeader().getCode();
            if (code == 200) {
                ((CartActivity) getActivity()).replaceFragment(StandardShippingFragment.newInstance(addressData));
            } else if (code == 403) {
                Utilities.showErrorPopupWithClick(getContext(), addressData.getHeader().getMessage(), view -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    getActivity().finishAffinity();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                });
            } else {
                Utilities.showErrorPopup(getContext(), addressData.getHeader().getMessage());
            }
        }
    }
}