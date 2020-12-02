package com.mobiblanc.gbam.views.cart.shipping;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.databinding.FragmentProfileBinding;
import com.mobiblanc.gbam.databinding.FragmentStandardShippingBinding;
import com.mobiblanc.gbam.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.gbam.listeners.OnItemSelectedListener;
import com.mobiblanc.gbam.listeners.OnObjectSelectedListener;
import com.mobiblanc.gbam.models.shipping.address.Address;
import com.mobiblanc.gbam.models.shipping.address.AddressData;
import com.mobiblanc.gbam.utilities.Connectivity;
import com.mobiblanc.gbam.utilities.Constants;
import com.mobiblanc.gbam.utilities.Utilities;
import com.mobiblanc.gbam.viewmodels.CartVM;
import com.mobiblanc.gbam.views.cart.CartActivity;
import com.mobiblanc.gbam.views.cart.payment.PaymentFragment;
import com.mobiblanc.gbam.views.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifImageView;

public class StandardShippingFragment extends Fragment implements OnItemSelectedListener {

    private static final int REQUEST_CODE = 100;

    private FragmentStandardShippingBinding fragmentBinding;

    private AddressAdapter addressAdapter;
    private List<Address> addressList;
    private Address address;

    public static StandardShippingFragment newInstance(AddressData addressData) {
        StandardShippingFragment fragment = new StandardShippingFragment();
        Bundle args = new Bundle();
        args.putSerializable("addressData", addressData);
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
            addressList = ((AddressData) getArguments().getSerializable("addressData")).getResponse().getAddresses();
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
        if (addressList.isEmpty()) {
            AddNewAddressFragment addNewAddressFragment = new AddNewAddressFragment();
            addNewAddressFragment.setTargetFragment(StandardShippingFragment.this, REQUEST_CODE);
            ((CartActivity) getActivity()).replaceFragment(addNewAddressFragment);
        }
        init();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            this.addressList = ((AddressData) data.getSerializableExtra("addresses")).getResponse().getAddresses();
            addressAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemSelected(int position, Object object) {
        boolean selected = (Boolean) object;
        if (selected)
            address = addressList.get(position);
        fragmentBinding.nextBtn.setEnabled(selected);
    }

    private void init() {
        fragmentBinding.addNewAddressBtn.setOnClickListener(v -> {
            AddNewAddressFragment addNewAddressFragment = new AddNewAddressFragment();
            addNewAddressFragment.setTargetFragment(StandardShippingFragment.this, REQUEST_CODE);
            ((CartActivity) getActivity()).replaceFragment(addNewAddressFragment);
        });
        fragmentBinding.nextBtn.setOnClickListener(v -> ((CartActivity) getActivity()).replaceFragment(PaymentFragment.newInstance(address.getId(), "standard")));

        fragmentBinding.addressRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        addressAdapter = new AddressAdapter(addressList, this);
        fragmentBinding.addressRecycler.setAdapter(addressAdapter);
    }
}