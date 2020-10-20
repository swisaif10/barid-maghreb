package com.mobiblanc.baridal_maghrib.views.main.dashboard;

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
import com.mobiblanc.baridal_maghrib.listeners.OnDashboardItemSelectedListener;
import com.mobiblanc.baridal_maghrib.models.dashboard.DashboardResponseData;
import com.mobiblanc.baridal_maghrib.utilities.Constants;
import com.mobiblanc.baridal_maghrib.utilities.Utilities;
import com.mobiblanc.baridal_maghrib.views.account.AccountActivity;
import com.mobiblanc.baridal_maghrib.views.cart.CartActivity;
import com.mobiblanc.baridal_maghrib.views.main.MainActivity;
import com.mobiblanc.baridal_maghrib.views.main.products.ProductsFragment;
import com.mobiblanc.baridal_maghrib.views.tracking.TrackingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardFragment extends Fragment implements OnDashboardItemSelectedListener {

    @BindView(R.id.servicesRecycler)
    RecyclerView servicesRecycler;
    DashboardResponseData responseData;

    public static DashboardFragment newInstance(DashboardResponseData responseData) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putSerializable("response", responseData);
        fragment.setArguments(args);
        return fragment;
    }

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
            responseData = (DashboardResponseData) getArguments().getSerializable("response");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).showHideLoader(View.GONE);
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).showHideBackBtn(false);
    }

    @OnClick({R.id.discoverBtn, R.id.portraitBtn, R.id.stampBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.discoverBtn:
                Utilities.showDiscoverDialog(getContext(), v -> ((MainActivity) getActivity()).replaceFragment(ProductsFragment.newInstance("0", "")));
            case R.id.portraitBtn:
                ((MainActivity) getActivity()).replaceFragment(ProductsFragment.newInstance("0", ""));
                break;
            case R.id.stampBtn:
                ((MainActivity) getActivity()).replaceFragment(ProductsFragment.newInstance("0", ""));
                break;
        }
    }

    @Override
    public void onDashboardItemSelected(int index) {
        Intent intent;
        switch (responseData.getServices().get(index).getView()) {
            case "assistance":
                intent = new Intent(getActivity(), AccountActivity.class);
                intent.putExtra("destination", 2);
                startActivity(intent);
                break;
            case "tracking":
                startActivity(new Intent(getActivity(), TrackingActivity.class));
                getActivity().finish();
                break;
            case "adresse":
                intent = new Intent(getActivity(), CartActivity.class);
                intent.putExtra("destination", 1);
                startActivity(intent);
                break;
        }
    }

    private void init() {
        servicesRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        servicesRecycler.setAdapter(new ServicesAdapter(getContext(), responseData.getServices(), this::onDashboardItemSelected));
        servicesRecycler.setNestedScrollingEnabled(false);
    }

}