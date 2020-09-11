package com.mobiblanc.baridal_maghrib.views.cart.delivery;

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
import com.mobiblanc.baridal_maghrib.listeners.OnItemSelectedListener;
import com.mobiblanc.baridal_maghrib.utilities.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AgencyDeliveryFragment extends Fragment implements OnItemSelectedListener {

    @BindView(R.id.deliveryRecycler)
    RecyclerView deliveryRecycler;

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

    @OnClick({R.id.backBtn, R.id.container})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                getActivity().onBackPressed();
                break;
            case R.id.container:
                Utilities.hideSoftKeyboard(getContext(),getView());
                break;
        }
    }
    @Override
    public void onItemSelected(int mode) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new AgencySelectFragment()).addToBackStack(null).commit();
    }

    private void init() {
        deliveryRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        deliveryRecycler.setAdapter(new AgencyDeliveryAdapter(getContext(), this::onItemSelected));
    }


}