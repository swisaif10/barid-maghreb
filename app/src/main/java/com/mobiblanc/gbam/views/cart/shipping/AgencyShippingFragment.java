package com.mobiblanc.gbam.views.cart.shipping;

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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.databinding.FragmentAgencyShippingBinding;
import com.mobiblanc.gbam.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.gbam.listeners.OnItemSelectedListener;
import com.mobiblanc.gbam.models.shipping.agencies.AgenciesData;
import com.mobiblanc.gbam.models.shipping.agencies.Agency;
import com.mobiblanc.gbam.utilities.Connectivity;
import com.mobiblanc.gbam.utilities.Constants;
import com.mobiblanc.gbam.utilities.Utilities;
import com.mobiblanc.gbam.viewmodels.CartVM;
import com.mobiblanc.gbam.views.cart.CartActivity;
import com.mobiblanc.gbam.views.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class AgencyShippingFragment extends Fragment implements OnItemSelectedListener {

    private FragmentAgencyShippingBinding fragmentBinding;
    private Connectivity connectivity;
    private CartVM cartVM;
    private PreferenceManager preferenceManager;
    private List<Agency> agencies;
    private AgenciesAdapter agenciesAdapter;
    private LinearLayoutManager layoutManager;
    private int currentPage = 1;
    private int totalPage = 0;
    private Boolean isLoading = false;
    private Boolean filtered = false;


    public AgencyShippingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cartVM = ViewModelProviders.of(this).get(CartVM.class);
        connectivity = new Connectivity(requireContext(), this);
        cartVM.getAgenciesLiveData().observe(this, this::handleAgenciesData);

        preferenceManager = new PreferenceManager.Builder(requireContext(), Context.MODE_PRIVATE)
                .name(Constants.SHARED_PREFS_NAME)
                .build();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = FragmentAgencyShippingBinding.inflate(inflater, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layoutManager = new LinearLayoutManager(requireContext());
        fragmentBinding.agenciesRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading && currentPage <= totalPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= 25) {
                        isLoading = true;
                        currentPage++;
                        getAgencies();
                    }
                }
            }
        });
        getAgencies();
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((CartActivity) requireActivity()).showHideHeader(View.VISIBLE);
    }

    @Override
    public void onItemSelected(int position, Object object) {
        ((CartActivity) requireActivity()).replaceFragment(LocateSelectedAgencyFragment.newInstance((Agency) object));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        agencies = new ArrayList<>();
        agenciesAdapter = new AgenciesAdapter(requireContext(), agencies, this);

        fragmentBinding.agenciesRecycler.setLayoutManager(layoutManager);
        fragmentBinding.agenciesRecycler.setAdapter(agenciesAdapter);
        //fragmentBinding.agenciesRecycler.setNestedScrollingEnabled(false);

    }

    @SuppressLint("ClickableViewAccessibility")
    private void initFilter(List<String> regions) {
        fragmentBinding.citiesDropDownMenu.setAdapter(new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, regions));

        fragmentBinding.citiesDropDownMenu.setOnTouchListener((v, event) -> {
            fragmentBinding.citiesDropDownMenu.showDropDown();
            return false;
        });

        fragmentBinding.citiesDropDownMenu.setOnItemClickListener((parent, view, position, id) -> {
            filtered = true;
            getAgencies(regions.get(position));
        });
    }

    private void getAgencies() {
        if (connectivity.isConnected()) {
            fragmentBinding.loader.setVisibility(View.VISIBLE);
            cartVM.getAgencies(preferenceManager.getValue(Constants.TOKEN, null), currentPage);
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void getAgencies(String region) {
        if (connectivity.isConnected()) {
            fragmentBinding.loader.setVisibility(View.VISIBLE);
            cartVM.getAgencies(preferenceManager.getValue(Constants.TOKEN, null), currentPage, region);
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleAgenciesData(AgenciesData agenciesData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (agenciesData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = agenciesData.getHeader().getCode();
            if (code == 200) {
                if (filtered)
                    this.agencies.clear();
                this.totalPage = agenciesData.getResponse().getTotalPage();
                this.agencies.addAll(agenciesData.getResponse().getAgencies());
                agenciesAdapter.notifyDataSetChanged();
                if (currentPage == 1)
                    initFilter(agenciesData.getResponse().getRegions());
                isLoading = false;
            } else if (code == 403) {
                Utilities.showErrorPopupWithClick(getContext(), agenciesData.getHeader().getMessage(), view -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    preferenceManager.clearValue(Constants.CART_ID);
                    preferenceManager.clearValue(Constants.NB_ITEMS_IN_CART);
                    requireActivity().finishAffinity();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                });
            } else {
                Utilities.showErrorPopup(getContext(), agenciesData.getHeader().getMessage());
            }
        }
    }
}