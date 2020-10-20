package com.mobiblanc.baridal_maghrib.views.cart.shipping;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.views.cart.CartActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShippingMethodFragment extends Fragment {

    public ShippingMethodFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shipping_method, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.standardShipping, R.id.agencyShipping})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.standardShipping:
                ((CartActivity) getActivity()).replaceFragment(new StandardShippingFragment());
                break;
            case R.id.agencyShipping:
                ((CartActivity) getActivity()).replaceFragment(new AgencyShippingFragment());
                break;
        }
    }
}