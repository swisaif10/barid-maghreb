package com.mobiblanc.gbam.views.cart.shipping;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.mobiblanc.gbam.databinding.FragmentAddNewAddressBinding;
import com.mobiblanc.gbam.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.gbam.models.shipping.address.Address;
import com.mobiblanc.gbam.models.shipping.address.AddressData;
import com.mobiblanc.gbam.utilities.Connectivity;
import com.mobiblanc.gbam.utilities.Constants;
import com.mobiblanc.gbam.utilities.NumericKeyBoardTransformationMethod;
import com.mobiblanc.gbam.utilities.Utilities;
import com.mobiblanc.gbam.viewmodels.CartVM;
import com.mobiblanc.gbam.views.cart.CartActivity;
import com.mobiblanc.gbam.views.main.MainActivity;

import java.util.Objects;

public class AddNewAddressFragment extends Fragment {

    private FragmentAddNewAddressBinding fragmentBinding;
    private Connectivity connectivity;
    private CartVM cartVM;
    private PreferenceManager preferenceManager;
    private String addressType = "type business";
    private Address address;
    private Boolean isUpdate = false;

    public AddNewAddressFragment() {
        // Required empty public constructor
    }

    public static AddNewAddressFragment newInstance(Address address) {
        AddNewAddressFragment fragment = new AddNewAddressFragment();
        Bundle args = new Bundle();
        args.putSerializable("address", address);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cartVM = ViewModelProviders.of(this).get(CartVM.class);
        connectivity = new Connectivity(requireContext(), this);
        cartVM.getAddNewAddressLiveData().observe(this, this::handleAddAddressData);
        cartVM.getUpdateAddressLiveData().observe(this, this::handleUpdateAddressData);

        preferenceManager = new PreferenceManager.Builder(requireContext(), Context.MODE_PRIVATE)
                .name(Constants.SHARED_PREFS_NAME)
                .build();

        if (getArguments() != null)
            address = (Address) getArguments().getSerializable("address");

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = FragmentAddNewAddressBinding.inflate(inflater, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((CartActivity) requireActivity()).showUpdateBtn(View.GONE);
        init();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void init() {
        fragmentBinding.phoneNumber.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        fragmentBinding.phoneNumber.setTransformationMethod(new NumericKeyBoardTransformationMethod());

        fragmentBinding.postalCode.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        fragmentBinding.postalCode.setTransformationMethod(new NumericKeyBoardTransformationMethod());

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!String.valueOf(fragmentBinding.phoneNumber.getText()).contains(" ")) {
                    int maxLength = 10;
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

        fragmentBinding.container.setOnClickListener(v -> Utilities.hideSoftKeyboard(getContext(), getView()));

        fragmentBinding.companyChoice.setOnClickListener(v -> {
            fragmentBinding.companyChoice.setBackground(requireContext().getDrawable(R.drawable.selected_payment_item_background));
            fragmentBinding.particularChoice.setBackground(requireContext().getDrawable(R.drawable.unselected_payment_item_background));
            fragmentBinding.cni.setVisibility(View.GONE);
            fragmentBinding.ice.setVisibility(View.VISIBLE);
            fragmentBinding.fiscalId.setVisibility(View.VISIBLE);
            addressType = "type business";
        });

        fragmentBinding.particularChoice.setOnClickListener(v -> {
            fragmentBinding.particularChoice.setBackground(requireContext().getDrawable(R.drawable.selected_payment_item_background));
            fragmentBinding.companyChoice.setBackground(requireContext().getDrawable(R.drawable.unselected_payment_item_background));
            fragmentBinding.cni.setVisibility(View.VISIBLE);
            fragmentBinding.ice.setVisibility(View.GONE);
            fragmentBinding.fiscalId.setVisibility(View.GONE);
            addressType = "particular";
        });

        fragmentBinding.saveBtn.setOnClickListener(v -> {
            if (isUpdate)
                updateAddress();
            else
                addAddress();
        });

        fragmentBinding.addressName.addTextChangedListener(textWatcher);
        fragmentBinding.streetNumber.addTextChangedListener(textWatcher);
        fragmentBinding.city.addTextChangedListener(textWatcher);
        fragmentBinding.postalCode.addTextChangedListener(textWatcher);
        fragmentBinding.phoneNumber.addTextChangedListener(textWatcher);
        fragmentBinding.ice.addTextChangedListener(textWatcher);
        fragmentBinding.fiscalId.addTextChangedListener(textWatcher);
        fragmentBinding.cni.addTextChangedListener(textWatcher);

        if (address != null) {
            isUpdate = true;
            fragmentBinding.title.setText(R.string.update_address_title);
            if (address.getType().equalsIgnoreCase("type business"))
                fragmentBinding.companyChoice.performClick();
            else
                fragmentBinding.particularChoice.performClick();

            fragmentBinding.addressName.setText(address.getName());
            fragmentBinding.streetNumber.setText(address.getStreetNumber());
            fragmentBinding.city.setText(address.getCity());
            fragmentBinding.postalCode.setText(address.getPostcode());
            fragmentBinding.phoneNumber.setText(address.getTelephone());
            fragmentBinding.ice.setText(address.getIce());
            fragmentBinding.fiscalId.setText(address.getTaxIdentification());
            fragmentBinding.cni.setText(address.getCni());
            fragmentBinding.addressComplement.setText(address.getComplementAddress());

        }
    }

    private void checkForm() {
        switch (addressType) {
            case "type business":
                fragmentBinding.saveBtn.setEnabled(!Utilities.isEmpty(fragmentBinding.addressName) && !Utilities.isEmpty(fragmentBinding.streetNumber)
                        && !Utilities.isEmpty(fragmentBinding.city) && !Utilities.isEmpty(fragmentBinding.postalCode)
                        && !Utilities.isEmpty(fragmentBinding.phoneNumber) && !Utilities.isEmpty(fragmentBinding.ice)
                        && !Utilities.isEmpty(fragmentBinding.fiscalId));
                break;
            case "particular":
                fragmentBinding.saveBtn.setEnabled(!Utilities.isEmpty(fragmentBinding.addressName) && !Utilities.isEmpty(fragmentBinding.streetNumber)
                        && !Utilities.isEmpty(fragmentBinding.city) && !Utilities.isEmpty(fragmentBinding.postalCode)
                        && !Utilities.isEmpty(fragmentBinding.phoneNumber) && !Utilities.isEmpty(fragmentBinding.cni));
                break;
        }
    }

    private void addAddress() {
        if (connectivity.isConnected()) {
            fragmentBinding.loader.setVisibility(View.VISIBLE);
            cartVM.addNewAddress(preferenceManager.getValue(Constants.TOKEN, null),
                    fragmentBinding.addressName.getText().toString(),
                    fragmentBinding.streetNumber.getText().toString(),
                    fragmentBinding.addressComplement.getText().toString(),
                    fragmentBinding.city.getText().toString(),
                    fragmentBinding.postalCode.getText().toString(),
                    fragmentBinding.phoneNumber.getText().toString(),
                    fragmentBinding.ice.getText().toString(),
                    fragmentBinding.fiscalId.getText().toString(),
                    fragmentBinding.cni.getText().toString(),
                    addressType
            );
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleAddAddressData(AddressData addressData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (addressData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = addressData.getHeader().getCode();
            if (code == 200) {
                Intent intent = new Intent();
                intent.putExtra("addresses", addressData.getResponse().getAddresses());
                try {
                    Objects.requireNonNull(getTargetFragment()).onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                requireActivity().onBackPressed();
            } else if (code == 403) {
                Utilities.showErrorPopupWithClick(getContext(), addressData.getHeader().getMessage(), view -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    preferenceManager.clearValue(Constants.CART_ID);
                    preferenceManager.clearValue(Constants.NB_ITEMS_IN_CART);
                    requireActivity().finishAffinity();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                });
            } else {
                Utilities.showErrorPopup(getContext(), addressData.getHeader().getMessage());
            }
        }
    }

    private void updateAddress() {
        if (connectivity.isConnected()) {
            fragmentBinding.loader.setVisibility(View.VISIBLE);
            cartVM.updateAddress(preferenceManager.getValue(Constants.TOKEN, null),
                    address.getId(),
                    fragmentBinding.addressName.getText().toString(),
                    fragmentBinding.streetNumber.getText().toString(),
                    fragmentBinding.addressComplement.getText().toString(),
                    fragmentBinding.city.getText().toString(),
                    fragmentBinding.postalCode.getText().toString(),
                    fragmentBinding.phoneNumber.getText().toString(),
                    fragmentBinding.ice.getText().toString(),
                    fragmentBinding.fiscalId.getText().toString(),
                    fragmentBinding.cni.getText().toString(),
                    addressType
            );
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleUpdateAddressData(AddressData addressData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (addressData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = addressData.getHeader().getCode();
            if (code == 200) {
                Intent intent = new Intent();
                intent.putExtra("addresses", addressData.getResponse().getAddresses());
                try {
                    Objects.requireNonNull(getTargetFragment()).onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                requireActivity().onBackPressed();
            } else if (code == 403) {
                Utilities.showErrorPopupWithClick(getContext(), addressData.getHeader().getMessage(), view -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    preferenceManager.clearValue(Constants.CART_ID);
                    preferenceManager.clearValue(Constants.NB_ITEMS_IN_CART);
                    requireActivity().finishAffinity();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                });
            } else {
                Utilities.showErrorPopup(getContext(), addressData.getHeader().getMessage());
            }
        }
    }
}