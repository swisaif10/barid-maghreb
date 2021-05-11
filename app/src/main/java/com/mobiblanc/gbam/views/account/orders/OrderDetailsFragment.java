package com.mobiblanc.gbam.views.account.orders;

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

import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.databinding.FragmentOrderDetailsBinding;
import com.mobiblanc.gbam.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.gbam.listeners.OnItemSelectedListener;
import com.mobiblanc.gbam.models.orders.details.OrderDetail;
import com.mobiblanc.gbam.models.orders.details.OrderDetailsData;
import com.mobiblanc.gbam.utilities.Connectivity;
import com.mobiblanc.gbam.utilities.Constants;
import com.mobiblanc.gbam.utilities.Utilities;
import com.mobiblanc.gbam.viewmodels.AccountVM;
import com.mobiblanc.gbam.views.main.MainActivity;
import com.mobiblanc.gbam.views.tracking.TrackingActivity;

import java.util.List;

public class OrderDetailsFragment extends Fragment implements OnItemSelectedListener {

    private FragmentOrderDetailsBinding fragmentBinding;
    private PreferenceManager preferenceManager;
    private Connectivity connectivity;
    private AccountVM accountVM;
    private String id;
    private String amount;

    public OrderDetailsFragment() {
        // Required empty public constructor
    }

    public static OrderDetailsFragment newInstance(String id, String amount) {
        OrderDetailsFragment fragment = new OrderDetailsFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("amount", amount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString("id");
            amount = getArguments().getString("amount");
        }

        accountVM = ViewModelProviders.of(this).get(AccountVM.class);
        connectivity = new Connectivity(requireContext(), this);
        accountVM.getOrderDetailsLiveData().observe(this, this::handleOrderDetailsData);

        preferenceManager = new PreferenceManager.Builder(requireContext(), Context.MODE_PRIVATE)
                .name(Constants.SHARED_PREFS_NAME)
                .build();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = FragmentOrderDetailsBinding.inflate(inflater, container, false);
        return fragmentBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getOrderDetails();
    }

    @Override
    public void onItemSelected(int position, Object object) {
        Intent intent = new Intent(requireActivity(), TrackingActivity.class);
        intent.putExtra("orderId", id);
        intent.putExtra("trackingId", ((OrderDetail) object).getTrackingNumber());
        startActivity(intent);
    }

    private void getOrderDetails() {
        if (connectivity.isConnected()) {
            fragmentBinding.loader.setVisibility(View.VISIBLE);
            accountVM.getOrderDetails(preferenceManager.getValue(Constants.TOKEN, ""), id);
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleOrderDetailsData(OrderDetailsData orderDetailsData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (orderDetailsData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = orderDetailsData.getHeader().getCode();
            if (code == 200) {
                init(orderDetailsData.getResponse().getOrderDetails());
            } else if (code == 403) {
                Utilities.showErrorPopupWithClick(getContext(), orderDetailsData.getHeader().getMessage(), view -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    preferenceManager.clearValue(Constants.NB_ITEMS_IN_CART);
                    preferenceManager.clearValue(Constants.CART_ID);
                    requireActivity().finishAffinity();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                });
            } else {
                Utilities.showErrorPopup(getContext(), orderDetailsData.getHeader().getMessage());
            }
        }
    }

    private void init(List<OrderDetail> orderDetails) {
        fragmentBinding.backBtn.setOnClickListener(v -> requireActivity().onBackPressed());
        fragmentBinding.orderNumber.setText(String.format("Commande N°%s", id));
        fragmentBinding.amount.setText(String.format("%s MAD", amount));

        fragmentBinding.orderDetailsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentBinding.orderDetailsRecycler.setAdapter(new OrdersDetailsAdapter(orderDetails, this));
    }
}