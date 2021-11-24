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
import com.mobiblanc.gbam.listeners.OnDialogButtonsClickListener;
import com.mobiblanc.gbam.listeners.OnItemSelectedListener;
import com.mobiblanc.gbam.models.account.ProfileMenuItem;
import com.mobiblanc.gbam.models.account.otp.OTPData;
import com.mobiblanc.gbam.models.shipping.address.AddressData;
import com.mobiblanc.gbam.utilities.Connectivity;
import com.mobiblanc.gbam.utilities.Constants;
import com.mobiblanc.gbam.utilities.Utilities;
import com.mobiblanc.gbam.viewmodels.AccountVM;
import com.mobiblanc.gbam.viewmodels.CartVM;
import com.mobiblanc.gbam.views.account.AccountActivity;
import com.mobiblanc.gbam.views.account.webview.WebViewFragment;
import com.mobiblanc.gbam.views.cart.CartActivity;
import com.mobiblanc.gbam.views.main.MainActivity;
import com.mobiblanc.gbam.views.tracking.TrackingActivity;

import java.util.ArrayList;

public class ProfileFragment extends Fragment implements OnItemSelectedListener {

    private FragmentProfileBinding fragmentBinding;
    private Connectivity connectivity;
    private AccountVM accountVM;
    private PreferenceManager preferenceManager;
    private CartVM cartVM;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountVM = ViewModelProviders.of(this).get(AccountVM.class);
        accountVM.getLogoutLiveData().observe(this, this::handleLogoutData);

        cartVM = ViewModelProviders.of(this).get(CartVM.class);
        cartVM.getAddressLiveData().observe(this, this::handleAddressData);

        connectivity = new Connectivity(requireContext(), this);

        preferenceManager = new PreferenceManager.Builder(requireContext(), Context.MODE_PRIVATE)
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
        if (preferenceManager.getValue(Constants.NB_ITEMS_IN_CART, 0) > 0) {
            fragmentBinding.count.setVisibility(View.VISIBLE);
            fragmentBinding.count.setText(String.valueOf(preferenceManager.getValue(Constants.NB_ITEMS_IN_CART, 0)));
        } else fragmentBinding.count.setVisibility(View.GONE);
        init();
    }

    @Override
    public void onItemSelected(int position, Object object) {
        switch (position) {
            case 0:
                ((AccountActivity) requireActivity()).replaceFragment(new UpdatePersonalInformationFragment());
                break;
            case 1:
                getAddress();
                break;
            case 2:
                startActivity(new Intent(requireActivity(), TrackingActivity.class));
                break;
            case 3:
                ((AccountActivity) requireActivity()).replaceFragment(new ContactFragment());
                break;
            case 4:
                FAQStampsFragment faqStampsFragment = new FAQStampsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("FAQ","FAQ");
                bundle.putString("title","FAQ Portrait Officiel");
                faqStampsFragment.setArguments(bundle);
                ((AccountActivity) requireActivity()).replaceFragment(faqStampsFragment);
                //((AccountActivity) requireActivity()).replaceFragment(WebViewFragment.newInstance(true));
                break;
            case 5:
                FAQStampsFragment fragment = new FAQStampsFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString("title","FAQ Timbres poste");
                fragment.setArguments(bundle1);
                ((AccountActivity) requireActivity()).replaceFragment(fragment);
                break;
        }
    }

    private void init() {
        fragmentBinding.backBtn.setOnClickListener(v -> requireActivity().onBackPressed());
        fragmentBinding.cartBtn.setOnClickListener(v -> startActivity(new Intent(getActivity(), CartActivity.class)));
        fragmentBinding.logoutBtn.setOnClickListener(v -> logout());

        fragmentBinding.name.setText(preferenceManager.getValue(Constants.NAME, ""));

        ArrayList<ProfileMenuItem> profileMenuItems = new ArrayList<ProfileMenuItem>() {{
            add(new ProfileMenuItem(R.drawable.ic_user, getString(R.string.personal_info_btn)));
            add(new ProfileMenuItem(R.drawable.mes_adresses, getString(R.string.my_addresses_btn)));
            add(new ProfileMenuItem(R.drawable.ic_tracking, getString(R.string.tracking_btn)));
            add(new ProfileMenuItem(R.drawable.ic_aide, getString(R.string.contact_btn)));
            add(new ProfileMenuItem(R.drawable.faq, getString(R.string.faq_portrait_btn)));
            add(new ProfileMenuItem(R.drawable.faq, getString(R.string.faq_stamp_btn)));
        }};

        fragmentBinding.profileRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentBinding.profileRecycler.setAdapter(new ProfileAdapter(profileMenuItems, this));
    }

    private void logout() {

        Utilities.signOutDialog(requireContext(), new OnDialogButtonsClickListener() {
            @Override
            public void onFirstButtonClick(String code) {
                if (connectivity.isConnected()) {
                    fragmentBinding.loader.setVisibility(View.VISIBLE);
                    accountVM.logout(preferenceManager.getValue(Constants.TOKEN, null));
                    preferenceManager.clearValue(Constants.NB_ITEMS_IN_CART);
                } else
                    Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
            }

            @Override
            public void onSecondButtonClick() {

            }
        });


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
                preferenceManager.putValue(preferenceManager.getValue(Constants.PHONE_NUMBER, ""),
                        preferenceManager.getValue(Constants.NB_ITEMS_IN_CART, 0));
                preferenceManager.clearValue(Constants.NB_ITEMS_IN_CART);
                Intent intent = new Intent(getActivity(), AccountActivity.class);
                intent.putExtra("destination", 0);
                startActivity(intent);
                requireActivity().finish();
            } else if (code == 403) {
                Utilities.showErrorPopupWithClick(getContext(), otpData.getHeader().getMessage(), view -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    //preferenceManager.clearValue(Constants.NB_ITEMS_IN_CART);
                    preferenceManager.clearValue(Constants.CART_ID);
                    requireActivity().finishAffinity();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                });
            } else {
                Utilities.showErrorPopup(getContext(), otpData.getHeader().getMessage());
            }
        }
    }

    private void getAddress() {
        if (connectivity.isConnected()) {
            fragmentBinding.loader.setVisibility(View.VISIBLE);
            cartVM.getAddress(preferenceManager.getValue(Constants.TOKEN, null));
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleAddressData(AddressData addressData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (addressData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = addressData.getHeader().getCode();
            if (code == 200) {
                Intent intent = new Intent(getActivity(), CartActivity.class);
                intent.putExtra("destination", 1);
                intent.putExtra("addresses", addressData.getResponse().getAddresses());
                intent.putExtra("canPay", false);
                intent.putExtra("fromPF",true);
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