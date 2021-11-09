package com.mobiblanc.gbam.views.cart.shipping;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.StaticData;
import com.mobiblanc.gbam.databinding.FragmentStandardShippingBinding;
import com.mobiblanc.gbam.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.gbam.listeners.OnItemSelectedListener;
import com.mobiblanc.gbam.listeners.OnUpdateButtonClickListener;
import com.mobiblanc.gbam.models.shipping.address.Address;
import com.mobiblanc.gbam.models.shipping.address.AddressData;
import com.mobiblanc.gbam.utilities.Constants;
import com.mobiblanc.gbam.utilities.SwipeHelper;
import com.mobiblanc.gbam.utilities.Utilities;
import com.mobiblanc.gbam.viewmodels.CartVM;
import com.mobiblanc.gbam.views.account.connexion.AuthenticationFragment;
import com.mobiblanc.gbam.views.account.profile.ProfileFragment;
import com.mobiblanc.gbam.views.cart.CartActivity;
import com.mobiblanc.gbam.views.cart.payment.RecapPaymentFragment;
import com.mobiblanc.gbam.views.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class StandardShippingFragment extends Fragment implements OnItemSelectedListener, OnUpdateButtonClickListener {

    private static final int REQUEST_CODE = 100;
    private static Boolean initialized = false;
    private FragmentStandardShippingBinding fragmentBinding;
    private AddressAdapter addressAdapter;
    private ArrayList<Address> addressList;
    private Address address;
    private Boolean canPay;
    private CartVM cartVM;
    private PreferenceManager preferenceManager;
    private int deletedItemId;
    String resource = "";

    public StandardShippingFragment() {
        // Required empty public constructor
    }

    public static StandardShippingFragment newInstance(ArrayList<Address> addressList, Boolean canPay) {
        StandardShippingFragment fragment = new StandardShippingFragment();
        Bundle args = new Bundle();
        args.putSerializable("addresses", addressList);
        args.putBoolean("canPay", canPay);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            addressList = (ArrayList<Address>) getArguments().getSerializable("addresses");
            canPay = getArguments().getBoolean("canPay");
        }

        preferenceManager = new PreferenceManager.Builder(requireContext(), Context.MODE_PRIVATE)
                .name(Constants.SHARED_PREFS_NAME)
                .build();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = FragmentStandardShippingBinding.inflate(inflater, container, false);
        cartVM = ViewModelProviders.of(this).get(CartVM.class);
        cartVM.getAddressLiveData().observe(getViewLifecycleOwner(), this::handleAddressDeleteData);
        return fragmentBinding.getRoot();
    }

    private void handleAddressDeleteData(AddressData addressData) {
        if (addressData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = addressData.getHeader().getCode();
            if (code == 200) {
                Log.d("TAG-->", "item deleted ");
            } else if (code == 403) {
                //Utilities.showErrorPopupWithClick(getContext(), deleteItemData.getHeader().getMessage(), view -> {});

            Log.d("TAG-->", "problem de suppression ");
        } else
        Log.d("TAG-->", "problem de suppression " + addressData.getHeader().getMessage());
        //Utilities.showErrorPopup(getContext(), deleteItemData.getHeader().getMessage());
    }

}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        enableSwipeToDelete();
        if (addressList.isEmpty() && !initialized) {
            initialized = true;
            AddNewAddressFragment addNewAddressFragment = new AddNewAddressFragment();
            addNewAddressFragment.setTargetFragment(StandardShippingFragment.this, REQUEST_CODE);
            ((CartActivity) requireActivity()).replaceFragment(addNewAddressFragment);
        }
        init();
    }

    private void enableSwipeToDelete() {

        new SwipeHelper(requireContext(), fragmentBinding.addressRecycler) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        ContextCompat.getDrawable(requireContext(), R.drawable.supp),
                        pos -> {
                            deletedItemId = addressList.get(pos).getId();
                            addressAdapter.removeItem(pos);
                            cartVM.deleteAddress(preferenceManager.getValue(Constants.TOKEN, ""),String.valueOf(deletedItemId));
                            Log.d("TAG-->", "deletedItemId: "+deletedItemId);

                        }
                ));
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        ((CartActivity) requireActivity()).showHideHeader(View.VISIBLE);
        initialized = false;

        if (!StaticData.RESOURCE.equals("") && addressList.size() == 0) {
           requireActivity().finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            assert data != null;
            this.addressList = (ArrayList<Address>) data.getSerializableExtra("addresses");
            addressAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemSelected(int position, Object object) {
        boolean selected = (Boolean) object;
        if (selected)
            address = addressList.get(position);
        fragmentBinding.nextBtn.setEnabled(selected);
        if (!canPay) {
            ((CartActivity) requireActivity()).showUpdateBtn(View.VISIBLE);
            ((CartActivity) requireActivity()).setOnUpdateButtonClickListener(this);
        }
    }

    @Override
    public void onUpdateButtonClick() {
        if (address != null) {
            AddNewAddressFragment addNewAddressFragment = AddNewAddressFragment.newInstance(address);
            addNewAddressFragment.setTargetFragment(StandardShippingFragment.this, REQUEST_CODE);
            ((CartActivity) requireActivity()).replaceFragment(addNewAddressFragment);
        }
    }

    private void init() {
        fragmentBinding.addNewAddressBtn.setOnClickListener(v -> {
            AddNewAddressFragment addNewAddressFragment = new AddNewAddressFragment();
            addNewAddressFragment.setTargetFragment(StandardShippingFragment.this, REQUEST_CODE);
            ((CartActivity) requireActivity()).replaceFragment(addNewAddressFragment);
        });

        if (canPay)
            fragmentBinding.nextBtn.setVisibility(View.VISIBLE);

        fragmentBinding.nextBtn.setOnClickListener(v -> ((CartActivity) requireActivity()).addFragment(RecapPaymentFragment.newInstance(address.getId(), "standard")));

        fragmentBinding.addressRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        addressAdapter = new AddressAdapter(addressList, this);
        fragmentBinding.addressRecycler.setAdapter(addressAdapter);
        if (this.addressList.size() == 1 && canPay) {
            address = addressList.get(0);
            fragmentBinding.nextBtn.setEnabled(true);
            address.setSelected(true);
        }
    }

}