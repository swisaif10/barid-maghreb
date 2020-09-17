package com.mobiblanc.baridal_maghrib.views.cart.payment;

import android.content.Intent;
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
import com.mobiblanc.baridal_maghrib.utilities.Utilities;
import com.mobiblanc.baridal_maghrib.views.tracking.TrackingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfirmationFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.itemsRecycler)
    RecyclerView itemsRecycler;

    public ConfirmationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirmation, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @OnClick({R.id.backBtn, R.id.payBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                break;
            case R.id.payBtn:
                Utilities.showConfirmationDialog(getContext(), this::onClick);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(getActivity(), TrackingActivity.class));
        getActivity().finish();
    }

    private void init() {
        itemsRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        itemsRecycler.setAdapter(new CartItemsAdapter(getContext()));
        itemsRecycler.setNestedScrollingEnabled(false);
    }
}