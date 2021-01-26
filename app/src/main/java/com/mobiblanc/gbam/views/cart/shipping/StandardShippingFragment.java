package com.mobiblanc.gbam.views.cart.shipping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mobiblanc.gbam.databinding.FragmentStandardShippingBinding;
import com.mobiblanc.gbam.listeners.OnItemSelectedListener;
import com.mobiblanc.gbam.listeners.OnUpdateButtonClickListener;
import com.mobiblanc.gbam.models.shipping.address.Address;
import com.mobiblanc.gbam.views.cart.CartActivity;
import com.mobiblanc.gbam.views.cart.payment.RecapPaymentFragment;

import java.util.ArrayList;

public class StandardShippingFragment extends Fragment implements OnItemSelectedListener, OnUpdateButtonClickListener {

    private static final int REQUEST_CODE = 100;

    private FragmentStandardShippingBinding fragmentBinding;

    private AddressAdapter addressAdapter;
    private ArrayList<Address> addressList;
    private Address address;
    private Boolean canPay;
    private static Boolean initialized = false;

    public static StandardShippingFragment newInstance(ArrayList<Address> addressList, Boolean canPay) {
        StandardShippingFragment fragment = new StandardShippingFragment();
        Bundle args = new Bundle();
        args.putSerializable("addresses", addressList);
        args.putBoolean("canPay", canPay);
        fragment.setArguments(args);
        return fragment;
    }

    public StandardShippingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            addressList = (ArrayList<Address>) getArguments().getSerializable("addresses");
            canPay = getArguments().getBoolean("canPay");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = FragmentStandardShippingBinding.inflate(inflater, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (addressList.isEmpty() && !initialized) {
            AddNewAddressFragment addNewAddressFragment = new AddNewAddressFragment();
            addNewAddressFragment.setTargetFragment(StandardShippingFragment.this, REQUEST_CODE);
            ((CartActivity) requireActivity()).replaceFragment(addNewAddressFragment);
            initialized = true;
        }
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((CartActivity) getActivity()).showHideHeader(View.VISIBLE);
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
    }

}