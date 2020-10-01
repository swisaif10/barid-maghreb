package com.mobiblanc.baridal_maghrib.views.cart.delivery;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;
import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.views.cart.CartActivity;
import com.mobiblanc.baridal_maghrib.views.cart.payment.PaymentFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AgencySelectFragment extends Fragment {

    private OnMapReadyCallback callback = googleMap -> {
        LatLng location = new LatLng(33.567727, -7.605191);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.marker_view, null);
        IconGenerator generator = new IconGenerator(getContext());
        generator.setContentView(view);
        generator.setBackground(new ColorDrawable(0));
        googleMap.addMarker(new MarkerOptions().position(location).icon(BitmapDescriptorFactory.fromBitmap(generator.makeIcon())));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 18.0f));

    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agency_select, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @OnClick({R.id.backBtn, R.id.nextBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                getActivity().onBackPressed();
                break;
            case R.id.nextBtn:
                ((CartActivity) getActivity()).replaceFragment(new PaymentFragment());
                break;
        }
    }
}