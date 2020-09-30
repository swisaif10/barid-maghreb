package com.mobiblanc.baridal_maghrib.views.cart.delivery;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.listeners.OnItemSelectedListener;
import com.mobiblanc.baridal_maghrib.views.cart.CartActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AgencyDeliveryFragment extends Fragment implements OnItemSelectedListener {

    @BindView(R.id.deliveryRecycler)
    RecyclerView deliveryRecycler;
    @BindView(R.id.citiesDropDownMenu)
    AppCompatAutoCompleteTextView citiesDropDownMenu;
    @BindView(R.id.provincesDropDownMenu)
    AppCompatAutoCompleteTextView provincesDropDownMenu;

    public AgencyDeliveryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agency_delivery, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @OnClick({R.id.backBtn})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.backBtn) {
            getActivity().onBackPressed();
        }
    }

    @Override
    public void onItemSelected(int mode) {
        ((CartActivity) getActivity()).replaceFragment(new AgencySelectFragment());
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        deliveryRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        deliveryRecycler.setAdapter(new AgencyDeliveryAdapter(getContext(), this::onItemSelected));
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


}