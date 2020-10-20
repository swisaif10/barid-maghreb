package com.mobiblanc.baridal_maghrib.views.cart.shipping;

import android.app.Activity;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.baridal_maghrib.listeners.OnObjectSelectedListener;
import com.mobiblanc.baridal_maghrib.models.shipping.address.Address;
import com.mobiblanc.baridal_maghrib.models.shipping.address.AddressData;
import com.mobiblanc.baridal_maghrib.utilities.Connectivity;
import com.mobiblanc.baridal_maghrib.utilities.Constants;
import com.mobiblanc.baridal_maghrib.utilities.Utilities;
import com.mobiblanc.baridal_maghrib.viewmodels.CartVM;
import com.mobiblanc.baridal_maghrib.views.cart.CartActivity;
import com.mobiblanc.baridal_maghrib.views.cart.payment.PaymentFragment;
import com.mobiblanc.baridal_maghrib.views.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifImageView;

public class StandardShippingFragment extends Fragment implements OnObjectSelectedListener {

    private static final int REQUEST_CODE = 100;

    @BindView(R.id.addressRecycler)
    RecyclerView addressRecycler;
    @BindView(R.id.loader)
    GifImageView loader;
    @BindView(R.id.nextBtn)
    MaterialButton nextBtn;
    private Connectivity connectivity;
    private CartVM cartVM;
    private PreferenceManager preferenceManager;
    private AddressAdapter addressAdapter;
    private Address address;

    public StandardShippingFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_standard_shipping, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAddress();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            //standardDeliveryAdapter.notifyItemInserted(arrayList.size() - 1);
            addressAdapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.addNewAddressBtn, R.id.nextBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addNewAddressBtn:
                AddNewAddressFragment addNewAddressFragment = new AddNewAddressFragment();
                addNewAddressFragment.setTargetFragment(this, REQUEST_CODE);
                ((CartActivity) getActivity()).replaceFragment(addNewAddressFragment);
                break;
            case R.id.nextBtn:
                ((CartActivity) getActivity()).replaceFragment(PaymentFragment.newInstance(address.getId()));
                break;
        }
    }

    @Override
    public void onObjectSelected(Object object) {
        if (object == null)
            nextBtn.setEnabled(false);
        else {
            address = (Address) object;
            nextBtn.setEnabled(true);
        }

    }

    private void init(AddressData addressData) {

        addressRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        addressAdapter = new AddressAdapter(getContext(), addressData.getResponse(), this::onObjectSelected);
        addressRecycler.setAdapter(addressAdapter);

    }

    private void getAddress() {
        if (connectivity.isConnected()) {
            loader.setVisibility(View.VISIBLE);
            cartVM.getAddress(preferenceManager.getValue(Constants.TOKEN, null));
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleAddressData(AddressData addressData) {
        loader.setVisibility(View.GONE);
        if (addressData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = addressData.getHeader().getCode();
            if (code == 200) {
                init(addressData);
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