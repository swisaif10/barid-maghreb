package com.mobiblanc.gbam.views.account.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.databinding.FragmentProfileBinding;
import com.mobiblanc.gbam.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.gbam.listeners.OnItemSelectedListener;
import com.mobiblanc.gbam.models.Item;
import com.mobiblanc.gbam.models.account.otp.OTPData;
import com.mobiblanc.gbam.utilities.Connectivity;
import com.mobiblanc.gbam.utilities.Constants;
import com.mobiblanc.gbam.utilities.Utilities;
import com.mobiblanc.gbam.viewmodels.AccountVM;
import com.mobiblanc.gbam.views.account.AccountActivity;
import com.mobiblanc.gbam.views.account.help.HelpFragment;
import com.mobiblanc.gbam.views.account.history.MyHistoryFragment;
import com.mobiblanc.gbam.views.cart.CartActivity;
import com.mobiblanc.gbam.views.main.MainActivity;
import com.mobiblanc.gbam.views.tracking.TrackingActivity;

import java.util.ArrayList;

public class ProfileFragment extends Fragment implements OnItemSelectedListener {

    private FragmentProfileBinding fragmentBinding;
    private Connectivity connectivity;
    private AccountVM accountVM;
    private PreferenceManager preferenceManager;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountVM = ViewModelProviders.of(this).get(AccountVM.class);

        connectivity = new Connectivity(getContext(), this);

        accountVM.getLogoutLiveData().observe(this, this::handleLogoutData);

        preferenceManager = new PreferenceManager.Builder(getContext(), Context.MODE_PRIVATE)
                .name(Constants.SHARED_PREFS_NAME)
                .build();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = FragmentProfileBinding.inflate(inflater, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    @Override
    public void onItemSelected(int position, Object object) {
        switch (position) {
            case 0:
                ((AccountActivity) requireActivity()).replaceFragment(new UpdatePersonalInformationFragment());
                break;
            case 1:
                ((AccountActivity) requireActivity()).replaceFragment(new MyHistoryFragment());
                break;
            case 3:
                ((AccountActivity) requireActivity()).replaceFragment(new HelpFragment());
                break;
            case 2:
                startActivity(new Intent(requireActivity(), TrackingActivity.class));
                break;
        }
    }

    private void init() {
        fragmentBinding.backBtn.setOnClickListener(v -> getActivity().onBackPressed());
        fragmentBinding.cartBtn.setOnClickListener(v -> startActivity(new Intent(getActivity(), CartActivity.class)));
        fragmentBinding.logoutBtn.setOnClickListener(v -> logout());

        fragmentBinding.name.setText(preferenceManager.getValue(Constants.NAME, ""));

        ArrayList<Item> items = new ArrayList<Item>() {{
            add(new Item(R.drawable.ic_user, getString(R.string.personal_info_btn)));
            add(new Item(R.drawable.ic_bag_profile, getString(R.string.history_btn)));
            add(new Item(R.drawable.ic_tracking, getString(R.string.tracking_btn)));
            add(new Item(R.drawable.ic_aide, getString(R.string.contact_btn)));
        }};

        fragmentBinding.profileRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentBinding.profileRecycler.setAdapter(new ProfileAdapter(items, this));
    }

    private void logout() {
        if (connectivity.isConnected()) {
            fragmentBinding.loader.setVisibility(View.VISIBLE);
            accountVM.logout(preferenceManager.getValue(Constants.TOKEN, null));
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleLogoutData(OTPData otpData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (otpData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = otpData.getHeader().getCode();
            if (code == 200) {
                preferenceManager.clearValue(Constants.TOKEN);
                preferenceManager.clearValue(Constants.CART_ID);
                preferenceManager.clearValue(Constants.NB_ITEMS_IN_CART);
                getActivity().finish();
                Intent intent = new Intent(getActivity(), AccountActivity.class);
                intent.putExtra("destination", 0);
                startActivity(intent);
                getActivity().finish();
            } else if (code == 403) {
                Utilities.showErrorPopupWithClick(getContext(), otpData.getHeader().getMessage(), view -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    getActivity().finishAffinity();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                });
            } else {
                Utilities.showErrorPopup(getContext(), otpData.getHeader().getMessage());
            }
        }
    }

}