package com.mobiblanc.gbam.views.cart.shipping;

import android.annotation.SuppressLint;
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
import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.databinding.FragmentLocateSelectedAgencyBinding;
import com.mobiblanc.gbam.models.shipping.agencies.Agency;
import com.mobiblanc.gbam.views.cart.CartActivity;
import com.mobiblanc.gbam.views.cart.payment.RecapPaymentFragment;

public class LocateSelectedAgencyFragment extends Fragment {

    private FragmentLocateSelectedAgencyBinding fragmentBinding;
    private Agency agency;

    public static LocateSelectedAgencyFragment newInstance(Agency agency) {
        LocateSelectedAgencyFragment fragment = new LocateSelectedAgencyFragment();
        Bundle args = new Bundle();
        args.putSerializable("agency", agency);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((CartActivity) getActivity()).showHideHeader(View.GONE);
        if (getArguments() != null)
            agency = (Agency) getArguments().getSerializable("agency");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentBinding = FragmentLocateSelectedAgencyBinding.inflate(inflater, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
        init();
    }

    private void init() {
        fragmentBinding.backBtn.setOnClickListener(v -> requireActivity().onBackPressed());
        fragmentBinding.nextBtn.setOnClickListener(v -> ((CartActivity) requireActivity()).replaceFragment(RecapPaymentFragment.newInstance(agency.getId(), "en_agence")));
        fragmentBinding.title.setText(agency.getTitle());
        fragmentBinding.address.setText(agency.getAddress());
    }

    @SuppressLint("InflateParams")
    private final OnMapReadyCallback callback = googleMap -> {
        LatLng location = new LatLng(Float.parseFloat(agency.getLatitude()), Float.parseFloat(agency.getLongitude()));
        View view = LayoutInflater.from(getContext()).inflate(R.layout.marker_view, null);
        IconGenerator generator = new IconGenerator(requireContext());
        generator.setContentView(view);
        generator.setBackground(new ColorDrawable(0));
        googleMap.addMarker(new MarkerOptions().position(location).icon(BitmapDescriptorFactory.fromBitmap(generator.makeIcon())));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 18.0f));
    };
}