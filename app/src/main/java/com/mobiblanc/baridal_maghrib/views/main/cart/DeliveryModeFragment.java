package com.mobiblanc.baridal_maghrib.views.main.cart;

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
import com.mobiblanc.baridal_maghrib.models.CartItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeliveryModeFragment extends Fragment {

    @BindView(R.id.deliveryRecycler)
    RecyclerView deliveryRecycler;

    public DeliveryModeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delivery_mode, container, false);
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
        ArrayList<CartItem> items = new ArrayList<CartItem>() {
            {
                add(new CartItem(R.drawable.timbre, "Timbre", "Véhicule circulant au Maroc", "7,50 MAD"));
                add(new CartItem(R.drawable.portrait_1, "Portrait de Sa Majesté le Roi", "20cm*30cm", "500 MAD"));
            }
        };
        deliveryRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        deliveryRecycler.setAdapter(new DeliveryModeAdapter(getContext()));

    }
}