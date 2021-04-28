package com.mobiblanc.gbam.views.cart.payment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.databinding.FragmentRecapPaymentBinding;
import com.mobiblanc.gbam.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.gbam.models.payment.operation.PaymentOperationData;
import com.mobiblanc.gbam.models.payment.recap.PaymentRecapData;
import com.mobiblanc.gbam.models.payment.recap.info.RecapInfoData;
import com.mobiblanc.gbam.utilities.Connectivity;
import com.mobiblanc.gbam.utilities.Constants;
import com.mobiblanc.gbam.utilities.Utilities;
import com.mobiblanc.gbam.viewmodels.CartVM;
import com.mobiblanc.gbam.views.account.connexion.CGUFragment;
import com.mobiblanc.gbam.views.cart.CartActivity;
import com.mobiblanc.gbam.views.main.MainActivity;

public class RecapPaymentFragment extends Fragment {

    private static final int REQUEST_CODE = 200;

    private FragmentRecapPaymentBinding fragmentBinding;
    private int id;
    private String shippingMethod;
    private Connectivity connectivity;
    private CartVM cartVM;
    private PreferenceManager preferenceManager;
    private Boolean payCash = false;
    private Boolean deleteCart = false;
    private String comment = "";

    public RecapPaymentFragment() {
        // Required empty public constructor
    }

    public static RecapPaymentFragment newInstance(int id, String shippingMethod) {
        RecapPaymentFragment fragment = new RecapPaymentFragment();
        Bundle args = new Bundle();
        args.putInt("id", id);
        args.putString("shippingMethod", shippingMethod);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((CartActivity) requireActivity()).showHideHeader(View.VISIBLE);

        if (getArguments() != null) {
            id = getArguments().getInt("id");
            shippingMethod = getArguments().getString("shippingMethod");
        }

        cartVM = ViewModelProviders.of(this).get(CartVM.class);
        connectivity = new Connectivity(requireContext(), this);
        cartVM.getPaymentRecapLiveData().observe(this, this::handlePaymentRecapData);
        cartVM.getPaymentOperationLiveData().observe(this, this::handlePaymentData);
        cartVM.getRecapInfoLiveData().observe(this, this::handleRecapInfoData);

        preferenceManager = new PreferenceManager.Builder(requireContext(), Context.MODE_PRIVATE)
                .name(Constants.SHARED_PREFS_NAME)
                .build();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = FragmentRecapPaymentBinding.inflate(inflater, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentBinding.cguBtn.setText(Html.fromHtml(requireActivity().getResources().getString(R.string.cgu_text)));
        getPaymentRecap();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (deleteCart) {
            preferenceManager.clearValue(Constants.CART_ID);
            preferenceManager.clearValue(Constants.NB_ITEMS_IN_CART);
            startActivity(new Intent(requireActivity(), MainActivity.class));
            requireActivity().finish();
        }
    }

    private void init(PaymentRecapData paymentRecapData) {
        fragmentBinding.infoBtn.setOnClickListener(v -> getRecapInfo());
        fragmentBinding.addComment.setOnClickListener(v -> Utilities.showCommentDialog(requireContext(), comment -> RecapPaymentFragment.this.comment = comment));
        fragmentBinding.bankCardChoice.setOnClickListener(v -> {
            fragmentBinding.bankCardChoice.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.selected_payment_item_background));
            fragmentBinding.cashChoice.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.unselected_payment_item_background));
            payCash = false;
        });
        fragmentBinding.cashChoice.setOnClickListener(v -> {
            fragmentBinding.cashChoice.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.selected_payment_item_background));
            fragmentBinding.bankCardChoice.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.unselected_payment_item_background));
            payCash = true;
        });
        fragmentBinding.payBtn.setOnClickListener(v -> {
            deleteCart = true;
            payment();
        });

        fragmentBinding.cguBtn.setOnClickListener(v -> ((CartActivity) requireActivity()).addFragment(CGUFragment.newInstance(false)));

        fragmentBinding.shippingMethod.setText(paymentRecapData.getResponse().getShippingMethode());
        fragmentBinding.date.setText(paymentRecapData.getResponse().getCommandeDate());
        fragmentBinding.address.setText(paymentRecapData.getResponse().getAddress());
        fragmentBinding.shippingFee.setText(String.valueOf(paymentRecapData.getResponse().getFees()));
        fragmentBinding.total.setText(String.valueOf(paymentRecapData.getResponse().getTotalPrice()));

        fragmentBinding.itemsRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        fragmentBinding.itemsRecycler.setAdapter(new CartItemsAdapter(getContext(), paymentRecapData.getResponse().getItems()));
        fragmentBinding.itemsRecycler.setNestedScrollingEnabled(false);

        fragmentBinding.cguCheck.setOnCheckedChangeListener((buttonView, isChecked) -> fragmentBinding.payBtn.setEnabled(isChecked));

        fragmentBinding.payBtn.setEnabled(fragmentBinding.cguCheck.isChecked());

    }

    private void getPaymentRecap() {
        if (connectivity.isConnected()) {
            fragmentBinding.loader.setVisibility(View.VISIBLE);
            if (shippingMethod.equalsIgnoreCase("standard"))
                cartVM.getStandardPaymentRecap(preferenceManager.getValue(Constants.TOKEN, null), shippingMethod, id);
            else
                cartVM.getAgencyPaymentRecap(preferenceManager.getValue(Constants.TOKEN, null), shippingMethod, id);
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
                    preferenceManager.clearValue(Constants.CART_ID);
                    preferenceManager.clearValue(Constants.NB_ITEMS_IN_CART);
                    requireActivity().finishAffinity();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                });
            } else {
                Utilities.showErrorPopup(getContext(), paymentRecapData.getHeader().getMessage());
            }
        }
    }

    private void payment() {
        if (connectivity.isConnected()) {
            fragmentBinding.loader.setVisibility(View.VISIBLE);
            if (payCash && shippingMethod.equalsIgnoreCase("standard"))
                cartVM.payment(preferenceManager.getValue(Constants.TOKEN, null), shippingMethod, "cashondelivery", String.valueOf(id), "", comment);
            else if (!payCash && shippingMethod.equalsIgnoreCase("standard"))
                cartVM.payment(preferenceManager.getValue(Constants.TOKEN, null), shippingMethod, "banktransfer", String.valueOf(id), "", comment);
            else if (payCash)
                cartVM.payment(preferenceManager.getValue(Constants.TOKEN, null), shippingMethod, "cashondelivery", "", String.valueOf(id), comment);
            else
                cartVM.payment(preferenceManager.getValue(Constants.TOKEN, null), shippingMethod, "banktransfer", "", String.valueOf(id), comment);
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handlePaymentData(PaymentOperationData paymentOperationData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (paymentOperationData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = paymentOperationData.getHeader().getCode();
            if (code == 200) {
                if (!paymentOperationData.getResponse().getUrl().equalsIgnoreCase("")) {
                    Uri uri = Uri.parse(paymentOperationData.getResponse().getUrl());
                    CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
                    //intentBuilder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
                    //intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
                    intentBuilder.setStartAnimations(requireContext(), android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    intentBuilder.setExitAnimations(requireContext(), android.R.anim.slide_in_left,
                            android.R.anim.slide_out_right);
                    CustomTabsIntent customTabsIntent = intentBuilder.build();
                    customTabsIntent.launchUrl(requireActivity(), uri);

                    fragmentBinding.scrollView.setVisibility(View.GONE);
                }
            } else if (code == 403) {
                Utilities.showErrorPopupWithClick(getContext(), paymentOperationData.getHeader().getMessage(), view -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    preferenceManager.clearValue(Constants.CART_ID);
                    preferenceManager.clearValue(Constants.NB_ITEMS_IN_CART);
                    requireActivity().finishAffinity();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                });
            } else if (code == 409) {
                Utilities.showErrorPopupWithClick(requireContext(), paymentOperationData.getHeader().getMessage(), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        preferenceManager.clearValue(Constants.CART_ID);
                        startActivity(new Intent(requireActivity(), MainActivity.class));
                        requireActivity().finish();
                    }
                });
            } else {
                Utilities.showErrorPopup(getContext(), paymentOperationData.getHeader().getMessage());
            }
        }
    }

    private void getRecapInfo() {
        if (connectivity.isConnected()) {
            fragmentBinding.loader.setVisibility(View.VISIBLE);
            cartVM.getRecapInfo("recap_discount");
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleRecapInfoData(RecapInfoData recapInfoData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (recapInfoData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = recapInfoData.getHeader().getCode();
            if (code == 200) {
                Utilities.showInfoPopup(getContext(), "Information", recapInfoData.getResponse().getContent());
            } else if (code == 403) {
                Utilities.showErrorPopupWithClick(getContext(), recapInfoData.getHeader().getMessage(), view -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    preferenceManager.clearValue(Constants.CART_ID);
                    preferenceManager.clearValue(Constants.NB_ITEMS_IN_CART);
                    requireActivity().finishAffinity();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                });
            } else {
                Utilities.showErrorPopup(getContext(), recapInfoData.getHeader().getMessage());
            }
        }
    }
}