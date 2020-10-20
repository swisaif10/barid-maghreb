package com.mobiblanc.baridal_maghrib.views.cart.shipping;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.baridal_maghrib.listeners.OnObjectSelectedListener;
import com.mobiblanc.baridal_maghrib.models.shipping.agencies.AgenciesData;
import com.mobiblanc.baridal_maghrib.models.shipping.agencies.Agency;
import com.mobiblanc.baridal_maghrib.utilities.Connectivity;
import com.mobiblanc.baridal_maghrib.utilities.Constants;
import com.mobiblanc.baridal_maghrib.utilities.Utilities;
import com.mobiblanc.baridal_maghrib.viewmodels.CartVM;
import com.mobiblanc.baridal_maghrib.views.cart.CartActivity;
import com.mobiblanc.baridal_maghrib.views.main.MainActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifImageView;

public class AgencyShippingFragment extends Fragment implements OnObjectSelectedListener {

    @BindView(R.id.deliveryRecycler)
    RecyclerView deliveryRecycler;
    @BindView(R.id.citiesDropDownMenu)
    AppCompatAutoCompleteTextView citiesDropDownMenu;
    @BindView(R.id.provincesDropDownMenu)
    AppCompatAutoCompleteTextView provincesDropDownMenu;
    @BindView(R.id.loader)
    GifImageView loader;
    private Connectivity connectivity;
    private CartVM cartVM;
    private PreferenceManager preferenceManager;

    public AgencyShippingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cartVM = ViewModelProviders.of(this).get(CartVM.class);
        connectivity = new Connectivity(getContext(), this);
        cartVM.getAgenciesLiveData().observe(this, this::handleAgenciesData);

        preferenceManager = new PreferenceManager.Builder(getContext(), Context.MODE_PRIVATE)
                .name(Constants.SHARED_PREFS_NAME)
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agency_shipping, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAgencies();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((CartActivity) getActivity()).showHideHeader(View.VISIBLE);
    }

    @Override
    public void onObjectSelected(Object object) {
        ((CartActivity) getActivity()).replaceFragment(LocateSelectedAgencyFragment.newInstance((Agency) object));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(AgenciesData agenciesData) {
        deliveryRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        deliveryRecycler.setAdapter(new AgenciesAdapter(getContext(), this::onObjectSelected, agenciesData.getResponse().getAgencies()));
        deliveryRecycler.setNestedScrollingEnabled(false);

        ArrayList cities = new ArrayList<String>() {{
            add("Ville 1");
            add("Ville 2");
            add("Ville 3");
            add("Ville 4");
            add("Ville 5");
            add("Ville 6");
            add("Ville 7");
            add("Ville 8");
        }};

        ArrayList provinces = new ArrayList<String>() {{
            add("Province 1");
            add("Province 2");
            add("Province 3");
            add("Province 4");
            add("Province 5");
            add("Province 6");
            add("Province 7");
            add("Province 8");
        }};

        ArrayAdapter citiesAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, cities);
        ArrayAdapter provincesAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, provinces);

        citiesDropDownMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        citiesDropDownMenu.setAdapter(citiesAdapter);
        citiesDropDownMenu.setOnTouchListener((v, event) -> {
            citiesDropDownMenu.showDropDown();
            return false;
        });

        provincesDropDownMenu.setAdapter(provincesAdapter);
        provincesDropDownMenu.setOnTouchListener((v, event) -> {
            provincesDropDownMenu.showDropDown();
            return false;
        });
    }

    private void getAgencies() {
        if (connectivity.isConnected()) {
            loader.setVisibility(View.VISIBLE);
            cartVM.getAgencies(preferenceManager.getValue(Constants.TOKEN, null));
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleAgenciesData(AgenciesData agenciesData) {
        loader.setVisibility(View.GONE);
        if (agenciesData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = agenciesData.getHeader().getCode();
            if (code == 200) {
                init(agenciesData);
            } else if (code == 403) {
                Utilities.showErrorPopupWithClick(getContext(), agenciesData.getHeader().getMessage(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        preferenceManager.clearValue(Constants.TOKEN);
                        getActivity().finishAffinity();
                        startActivity(new Intent(getActivity(), MainActivity.class));
                    }
                });
            } else {
                Utilities.showErrorPopup(getContext(), agenciesData.getHeader().getMessage());
            }
        }
    }
}