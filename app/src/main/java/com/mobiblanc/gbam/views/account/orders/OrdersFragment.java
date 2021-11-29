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
import com.mobiblanc.gbam.databinding.FragmentOrdersBinding;
import com.mobiblanc.gbam.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.gbam.listeners.OnItemSelectedListener;
import com.mobiblanc.gbam.models.orders.Order;
import com.mobiblanc.gbam.models.orders.OrdersListData;
import com.mobiblanc.gbam.utilities.Connectivity;
import com.mobiblanc.gbam.utilities.Constants;
import com.mobiblanc.gbam.utilities.Utilities;
import com.mobiblanc.gbam.viewmodels.AccountVM;
import com.mobiblanc.gbam.views.account.AccountActivity;
import com.mobiblanc.gbam.views.main.MainActivity;

import java.util.List;

public class OrdersFragment extends Fragment implements OnItemSelectedListener {

    private FragmentOrdersBinding fragmentBinding;
    private Connectivity connectivity;
    private AccountVM accountVM;
    private PreferenceManager preferenceManager;

    public OrdersFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountVM = ViewModelProviders.of(this).get(AccountVM.class);
        connectivity = new Connectivity(requireContext(), this);
        accountVM.getOrdersListLiveData().observe(this, this::handleOrdersData);

        preferenceManager = new PreferenceManager.Builder(requireContext(), Context.MODE_PRIVATE)
                .name(Constants.SHARED_PREFS_NAME)
                .build();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = FragmentOrdersBinding.inflate(inflater, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentBinding.backBtn.setOnClickListener(v -> requireActivity().onBackPressed());
        getOrders();
    }

    @Override
    public void onItemSelected(int position, Object object) {
        Order order = (Order) object;
        OrderDetailsFragment orderDetailsFragment = new OrderDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("order_id", order.getOrderId());
        bundle.putString("total_amount", order.getTotalAmount());
        orderDetailsFragment.setArguments(bundle);
        //((MainActivity) requireActivity()).replaceFragment(OrderDetailsFragment.newInstance(order.getOrderId(), order.getTotalAmount()));
        ((AccountActivity) requireActivity()).replaceFragment(orderDetailsFragment);
    }

    private void getOrders() {
        if (connectivity.isConnected()) {
            fragmentBinding.loader.setVisibility(View.VISIBLE);
            accountVM.getOrders(preferenceManager.getValue(Constants.TOKEN, ""));
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleOrdersData(OrdersListData ordersListData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (ordersListData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = ordersListData.getHeader().getCode();
            if (code == 200) {
                init(ordersListData.getResponse());
            } else if (code == 403) {
                Utilities.showErrorPopupWithClick(getContext(), ordersListData.getHeader().getMessage(), view -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    preferenceManager.clearValue(Constants.NB_ITEMS_IN_CART);
                    preferenceManager.clearValue(Constants.CART_ID);
                    requireActivity().finishAffinity();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                });
            } else {
                Utilities.showErrorPopup(getContext(), ordersListData.getHeader().getMessage());
            }
        }
    }

    private void init(List<Order> orders) {

        fragmentBinding.ordersRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentBinding.ordersRecycler.setAdapter(new OrdersAdapter(orders, this));
    }
}