package com.mobiblanc.gbam.views.cart.payment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.databinding.FragmentPaymentBinding;
import com.mobiblanc.gbam.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.gbam.models.payment.PaymentRecapData;
import com.mobiblanc.gbam.utilities.Connectivity;
import com.mobiblanc.gbam.utilities.Constants;
import com.mobiblanc.gbam.utilities.Utilities;
import com.mobiblanc.gbam.viewmodels.CartVM;
import com.mobiblanc.gbam.views.cart.CartActivity;
import com.mobiblanc.gbam.views.main.MainActivity;
import com.mobiblanc.gbam.views.tracking.TrackingActivity;

public class PaymentFragment extends Fragment {

    private FragmentPaymentBinding fragmentBinding;
    private int id;
    private String paymentMethod;
    private Connectivity connectivity;
    private CartVM cartVM;
    private PreferenceManager preferenceManager;

    public static PaymentFragment newInstance(int id, String paymentMethod) {
        PaymentFragment fragment = new PaymentFragment();
        Bundle args = new Bundle();
        args.putInt("id", id);
        args.putString("paymentMethod", paymentMethod);
        fragment.setArguments(args);
        return fragment;
    }

    public PaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((CartActivity) getActivity()).showHideHeader(View.VISIBLE);

        if (getArguments() != null) {
            id = getArguments().getInt("id");
            paymentMethod = getArguments().getString("paymentMethod");
        }

        cartVM = ViewModelProviders.of(this).get(CartVM.class);
        connectivity = new Connectivity(getContext(), this);
        cartVM.getPaymentRecapLiveData().observe(this, this::handlePaymentRecapData);

        preferenceManager = new PreferenceManager.Builder(getContext(), Context.MODE_PRIVATE)
                .name(Constants.SHARED_PREFS_NAME)
                .build();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = FragmentPaymentBinding.inflate(inflater, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPaymentRecap();
    }

    private void init(PaymentRecapData paymentRecapData) {
        fragmentBinding.addComment.setOnClickListener(v -> ((CartActivity) requireActivity()).addFragment(new AddNewCommentFragment()));
        fragmentBinding.bankCardChoice.setOnClickListener(v -> {
            fragmentBinding.bankCardChoice.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.selected_payment_item_background));
            fragmentBinding.cashChoice.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.unselected_payment_item_background));
        });
        fragmentBinding.cashChoice.setOnClickListener(v -> {
            fragmentBinding.cashChoice.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.selected_payment_item_background));
            fragmentBinding.bankCardChoice.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.unselected_payment_item_background));
        });
        fragmentBinding.payBtn.setOnClickListener(v -> Utilities.showConfirmationDialog(getContext(), v1 -> {
            getActivity().finish();
            startActivity(new Intent(getActivity(), TrackingActivity.class));
        }));

        fragmentBinding.cguBtn.setText(Html.fromHtml(getActivity().getResources().getString(R.string.cgu_text)));

        fragmentBinding.shippingMethod.setText(paymentRecapData.getResponse().getShippingMethode());
        fragmentBinding.date.setText(paymentRecapData.getResponse().getCommandeDate());
        fragmentBinding.address.setText(paymentRecapData.getResponse().getAddress());
        fragmentBinding.shippingFee.setText(String.valueOf(paymentRecapData.getResponse().getFees()));
        fragmentBinding.total.setText(String.valueOf(paymentRecapData.getResponse().getTotalPrice()));

        fragmentBinding.itemsRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        fragmentBinding.itemsRecycler.setAdapter(new CartItemsAdapter(getContext(), paymentRecapData.getResponse().getItems()));
        fragmentBinding.itemsRecycler.setNestedScrollingEnabled(false);

        fragmentBinding.cguCheck.setOnCheckedChangeListener((buttonView, isChecked) -> fragmentBinding.payBtn.setEnabled(isChecked));

    }

    private void getPaymentRecap() {
        if (connectivity.isConnected()) {
            fragmentBinding.loader.setVisibility(View.VISIBLE);
            cartVM.getPaymentRecap(preferenceManager.getValue(Constants.TOKEN, null), paymentMethod, id, id);
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handlePaymentRecapData(PaymentRecapData paymentRecapData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (paymentRecapData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = paymentRecapData.getHeader().getCode();
            if (code == 200) {
                init(paymentRecapData);
            } else if (code == 403) {
                Utilities.showErrorPopupWithClick(getContext(), paymentRecapData.getHeader().getMessage(), view -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    getActivity().finishAffinity();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                });
            } else {
                Utilities.showErrorPopup(getContext(), paymentRecapData.getHeader().getMessage());
            }
        }
    }
}