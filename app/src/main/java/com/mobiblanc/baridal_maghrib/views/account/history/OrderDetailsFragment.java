package com.mobiblanc.baridal_maghrib.views.account.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.views.cart.payment.CartItemsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailsFragment extends Fragment {

    @BindView(R.id.itemsRecycler)
    RecyclerView itemsRecycler;

    public OrderDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_details, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @OnClick(R.id.backBtn)
    public void onViewClicked() {
        getActivity().onBackPressed();
    }

    private void init() {
        itemsRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        //itemsRecycler.setAdapter(new CartItemsAdapter(getContext()));
        //itemsRecycler.setNestedScrollingEnabled(false);
    }
}