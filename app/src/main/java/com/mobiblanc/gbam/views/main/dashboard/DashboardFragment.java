package com.mobiblanc.gbam.views.main.dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.databinding.FragmentDashboardBinding;
import com.mobiblanc.gbam.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.gbam.listeners.OnDashboardItemSelectedListener;
import com.mobiblanc.gbam.models.cart.guest.GuestCartData;
import com.mobiblanc.gbam.models.dashboard.Category;
import com.mobiblanc.gbam.models.dashboard.DashboardData;
import com.mobiblanc.gbam.models.shipping.address.AddressData;
import com.mobiblanc.gbam.utilities.Connectivity;
import com.mobiblanc.gbam.utilities.Constants;
import com.mobiblanc.gbam.utilities.Utilities;
import com.mobiblanc.gbam.viewmodels.CartVM;
import com.mobiblanc.gbam.viewmodels.MainVM;
import com.mobiblanc.gbam.views.account.AccountActivity;
import com.mobiblanc.gbam.views.account.orders.OrdersFragment;
import com.mobiblanc.gbam.views.account.profile.ContactFragment;
import com.mobiblanc.gbam.views.cart.CartActivity;
import com.mobiblanc.gbam.views.main.MainActivity;
import com.mobiblanc.gbam.views.main.adapters.CategoriesAdapter;
import com.mobiblanc.gbam.views.main.adapters.ServicesAdapter;
import com.mobiblanc.gbam.views.main.discover.DiscoverFragment;
import com.mobiblanc.gbam.views.main.product.PortraitFragment;
import com.mobiblanc.gbam.views.main.product.StampsFragment;
import com.mobiblanc.gbam.views.tracking.TrackingActivity;

public class DashboardFragment extends Fragment implements OnDashboardItemSelectedListener {

    private FragmentDashboardBinding fragmentDashboardBinding;
    private Connectivity connectivity;
    private MainVM mainVM;
    private PreferenceManager preferenceManager;
    private DashboardData dashboardData;
    private CartVM cartVM;


    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainVM = ViewModelProviders.of(this).get(MainVM.class);
        connectivity = new Connectivity(requireContext(), this);
        mainVM.getDashboardLiveData().observe(this, this::handleDashboardData);
        mainVM.getGuestCartLiveData().observe(this, this::handleCreateGuestCartData);

        cartVM = ViewModelProviders.of(this).get(CartVM.class);
        cartVM.getAddressLiveData().observe(this, this::handleAddressData);

        preferenceManager = new PreferenceManager.Builder(requireContext(), Context.MODE_PRIVATE)
                .name(Constants.SHARED_PREFS_NAME)
                .build();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentDashboardBinding = FragmentDashboardBinding.inflate(inflater, container, false);
        return fragmentDashboardBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDashboardDetails();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (preferenceManager.getValue(Constants.CART_ID, null) == null)
            createCart();
        if (preferenceManager.getValue(Constants.NB_ITEMS_IN_CART, 0) > 0) {
            fragmentDashboardBinding.count.setVisibility(View.VISIBLE);
            fragmentDashboardBinding.count.setText(String.valueOf(preferenceManager.getValue(Constants.NB_ITEMS_IN_CART, 0)));
        } else fragmentDashboardBinding.count.setVisibility(View.GONE);

    }

    @Override
    public void onServiceItemSelected(int index) {
        String targetVIew = dashboardData.getDashboardResponseData().getServices().get(index).getView();
        Intent intent;
        switch (targetVIew) {
            case "assistance":
                if (preferenceManager.getValue(Constants.TOKEN, null) != null) {
                    ((MainActivity) requireActivity()).replaceFragment(new ContactFragment());
                } else {
                    intent = new Intent(getActivity(), AccountActivity.class);
                    intent.putExtra("destination", 2);
                    intent.putExtra("next", "contact");
                    startActivity(intent);
                }
                break;
            case "tracking":
                startActivity(new Intent(getActivity(), TrackingActivity.class));
                break;
            case "adresse":
                if (preferenceManager.getValue(Constants.TOKEN, null) != null) {
                    getAddress();
                } else {
                    intent = new Intent(getActivity(), AccountActivity.class);
                    intent.putExtra("destination", 0);
                    intent.putExtra("next", "new_address");
                    startActivity(intent);
                }
                break;
            case "commandes":
                if (preferenceManager.getValue(Constants.TOKEN, null) != null) {
                    ((MainActivity) requireActivity()).replaceFragment(new OrdersFragment());
                } else {
                    intent = new Intent(getActivity(), AccountActivity.class);
                    intent.putExtra("destination", 0);
                    intent.putExtra("next", "history");
                    startActivity(intent);
                }
        }
    }

    @Override
    public void onCategoryItemSelected(int index) {
        Category category = dashboardData.getDashboardResponseData().getInfos().getCategories().get(index);
        if (category.getViewCategory().equalsIgnoreCase("portraits"))
            Utilities.showDashboardDialog(getContext(), "Portrait Officiel de Sa Majesté", category.getDescriptionGlobal(),
                    v -> ((MainActivity) requireActivity()).replaceFragment(PortraitFragment.newInstance(category.getId(), category.getImage(), category.getDescription())));
        else
            ((MainActivity) requireActivity()).replaceFragment(StampsFragment.newInstance(category.getId()));
    }

    private void init() {
        fragmentDashboardBinding.container.setVisibility(View.VISIBLE);

        fragmentDashboardBinding.loginBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AccountActivity.class);
            if (preferenceManager.getValue(Constants.TOKEN, null) != null)
                intent.putExtra("destination", 1);
            else
                intent.putExtra("destination", 0);
            startActivity(intent);
        });
        fragmentDashboardBinding.cartBtn.setOnClickListener(v -> startActivity(new Intent(getActivity(), CartActivity.class)));
        fragmentDashboardBinding.discoverBtn.setOnClickListener(v -> ((MainActivity) requireActivity()).replaceFragment(new DiscoverFragment()));

        fragmentDashboardBinding.title.setText(dashboardData.getDashboardResponseData().getInfos().getPresentation().getTitle());
        fragmentDashboardBinding.description.setText(dashboardData.getDashboardResponseData().getInfos().getPresentation().getDescription());
        fragmentDashboardBinding.discoverBtn.setText(dashboardData.getDashboardResponseData().getInfos().getPresentation().getLabelButton());

        fragmentDashboardBinding.categoriesRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        fragmentDashboardBinding.categoriesRecycler.setAdapter(new CategoriesAdapter(dashboardData.getDashboardResponseData().getInfos().getCategories(), this));

        fragmentDashboardBinding.servicesRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        fragmentDashboardBinding.servicesRecycler.setAdapter(new ServicesAdapter(getContext(), dashboardData.getDashboardResponseData().getServices(), this));

    }

    private void getDashboardDetails() {
        if (connectivity.isConnected()) {
            fragmentDashboardBinding.loader.setVisibility(View.VISIBLE);
            mainVM.getDashboardDetails(preferenceManager.getValue(Constants.TOKEN, ""));
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleDashboardData(DashboardData dashboardData) {
        fragmentDashboardBinding.loader.setVisibility(View.GONE);
        if (dashboardData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = dashboardData.getHeader().getCode();
            if (code == 200) {
                this.dashboardData = dashboardData;
                init();
            } else if (code == 403) {
                Utilities.showErrorPopupWithClick(getContext(), dashboardData.getHeader().getMessage(), view -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    preferenceManager.clearValue(Constants.NB_ITEMS_IN_CART);
                    preferenceManager.clearValue(Constants.CART_ID);
                    requireActivity().finishAffinity();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                });
            } else {
                Utilities.showErrorPopup(getContext(), dashboardData.getHeader().getMessage());
            }
        }
    }

    private void createCart() {
        if (connectivity.isConnected())
            mainVM.createCart(preferenceManager.getValue(Constants.TOKEN, ""));
        else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleCreateGuestCartData(GuestCartData guestCartData) {
        if (guestCartData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = guestCartData.getHeader().getCode();
            if (code == 200)
                preferenceManager.putValue(Constants.CART_ID, guestCartData.getResponse().getQuoteId());
            else if (code == 403)
                Utilities.showErrorPopupWithClick(getContext(), guestCartData.getHeader().getMessage(), view -> preferenceManager.clearValue(Constants.TOKEN));
            else {
                Utilities.showErrorPopup(getContext(), guestCartData.getHeader().getMessage());
            }
        }
    }

    private void getAddress() {
        if (connectivity.isConnected()) {
            fragmentDashboardBinding.loader.setVisibility(View.VISIBLE);
            cartVM.getAddress(preferenceManager.getValue(Constants.TOKEN, null));
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleAddressData(AddressData addressData) {
        fragmentDashboardBinding.loader.setVisibility(View.GONE);
        if (addressData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = addressData.getHeader().getCode();
            if (code == 200) {
                Intent intent = new Intent(getActivity(), CartActivity.class);
                intent.putExtra("destination", 1);
                intent.putExtra("addresses", addressData.getResponse().getAddresses());
                intent.putExtra("canPay", false);
                startActivity(intent);
            } else if (code == 403) {
                Utilities.showErrorPopupWithClick(getContext(), addressData.getHeader().getMessage(), view -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    preferenceManager.clearValue(Constants.NB_ITEMS_IN_CART);
                    preferenceManager.clearValue(Constants.CART_ID);
                    requireActivity().finishAffinity();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                });
            } else {
                Utilities.showErrorPopup(getContext(), addressData.getHeader().getMessage());
            }
        }
    }


}