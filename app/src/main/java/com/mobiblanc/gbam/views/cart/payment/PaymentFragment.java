package com.mobiblanc.gbam.views.cart.payment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.gbam.models.payment.PaymentRecapData;
import com.mobiblanc.gbam.utilities.Connectivity;
import com.mobiblanc.gbam.utilities.Constants;
import com.mobiblanc.gbam.utilities.Utilities;
import com.mobiblanc.gbam.viewmodels.CartVM;
import com.mobiblanc.gbam.views.cart.CartActivity;
import com.mobiblanc.gbam.views.main.MainActivity;
import com.mobiblanc.gbam.views.tracking.TrackingActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifImageView;

public class PaymentFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.itemsRecycler)
    RecyclerView itemsRecycler;
    @BindView(R.id.bankCardChoice)
    LinearLayout bankCardChoice;
    @BindView(R.id.cashChoice)
    LinearLayout cashChoice;
    @BindView(R.id.loader)
    GifImageView loader;
    @BindView(R.id.shippingMethod)
    TextView shippingMethod;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.shippingFee)
    TextView shippingFee;
    @BindView(R.id.total)
    TextView total;
    @BindView(R.id.cguBtn)
    TextView cguBtn;
    private int id;
    private Connectivity connectivity;
    private CartVM cartVM;
    private PreferenceManager preferenceManager;

    public static PaymentFragment newInstance(int id) {
        PaymentFragment fragment = new PaymentFragment();
        Bundle args = new Bundle();
        args.putInt("id", id);
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

        if (getArguments() != null)
            id = getArguments().getInt("id");

        cartVM = ViewModelProviders.of(this).get(CartVM.class);
        connectivity = new Connectivity(getContext(), this);
        cartVM.getPaymentRecapLiveData().observe(this, this::handlePaymentRecapData);

        preferenceManager = new PreferenceManager.Builder(getContext(), Context.MODE_PRIVATE)
                .name(Constants.SHARED_PREFS_NAME)
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //getPaymentRecap();
        init(null);
    }

    @OnClick({R.id.addComment, R.id.bankCardChoice, R.id.cashChoice, R.id.payBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addComment:
                ((CartActivity) getActivity()).addFragment(new AddNewCommentFragment());
                break;
            case R.id.bankCardChoice:
                bankCardChoice.setBackground(getContext().getDrawable(R.drawable.selected_payment_item_background));
                cashChoice.setBackground(getContext().getDrawable(R.drawable.unselected_payment_item_background));
                break;
            case R.id.cashChoice:
                cashChoice.setBackground(getContext().getDrawable(R.drawable.selected_payment_item_background));
                bankCardChoice.setBackground(getContext().getDrawable(R.drawable.unselected_payment_item_background));
                break;
            case R.id.payBtn:
                Utilities.showConfirmationDialog(getContext(), this::onClick);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        getActivity().finish();
        startActivity(new Intent(getActivity(), TrackingActivity.class));
    }

    private void init(PaymentRecapData paymentRecapData) {

        cguBtn.setText(Html.fromHtml(getActivity().getResources().getString(R.string.cgu_text)));


/*
        shippingMethod.setText(paymentRecapData.getResponse().getShippingMethode());
        date.setText(paymentRecapData.getResponse().getCommandeDate());
        address.setText(paymentRecapData.getResponse().getAddress());
        shippingFee.setText(String.valueOf(paymentRecapData.getResponse().getFees()));
        total.setText(String.valueOf(paymentRecapData.getResponse().getTotalPrice()));
*/

        itemsRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        itemsRecycler.setAdapter(new CartItemsAdapter(getContext(), new ArrayList<>()));
        itemsRecycler.setNestedScrollingEnabled(false);
    }

    private void getPaymentRecap() {
        if (connectivity.isConnected()) {
            loader.setVisibility(View.VISIBLE);
            cartVM.getPaymentRecap(preferenceManager.getValue(Constants.TOKEN, null), "standard", id, -1);
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handlePaymentRecapData(PaymentRecapData paymentRecapData) {
        loader.setVisibility(View.GONE);
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