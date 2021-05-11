package com.mobiblanc.gbam.views.account.profile;

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
import com.mobiblanc.gbam.databinding.FragmentFaqStampsBinding;
import com.mobiblanc.gbam.models.faqstamp.FAQItem;
import com.mobiblanc.gbam.models.faqstamp.FAQStampData;
import com.mobiblanc.gbam.utilities.Connectivity;
import com.mobiblanc.gbam.utilities.Utilities;
import com.mobiblanc.gbam.viewmodels.AccountVM;
import com.mobiblanc.gbam.views.cart.CartActivity;

import java.util.List;

public class FAQStampsFragment extends Fragment {

    private FragmentFaqStampsBinding fragmentBinding;
    private Connectivity connectivity;
    private AccountVM accountVM;

    public FAQStampsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountVM = ViewModelProviders.of(this).get(AccountVM.class);

        accountVM.getFaqStampLiveData().observe(this, this::handleFAQStampData);

        connectivity = new Connectivity(requireContext(), this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = FragmentFaqStampsBinding.inflate(inflater, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getFAQStamps();
    }

    private void getFAQStamps() {
        if (connectivity.isConnected()) {
            fragmentBinding.loader.setVisibility(View.VISIBLE);
            accountVM.getFAQStamp();
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleFAQStampData(FAQStampData faqStampData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (faqStampData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = faqStampData.getHeader().getCode();
            if (code == 200) {
                init(faqStampData.getResponse());
            } else {
                Utilities.showErrorPopup(getContext(), faqStampData.getHeader().getMessage());
            }
        }
    }

    private void init(List<FAQItem> items) {
        fragmentBinding.backBtn.setOnClickListener(v -> requireActivity().onBackPressed());
        fragmentBinding.cartBtn.setOnClickListener(v -> startActivity(new Intent(getActivity(), CartActivity.class)));

        fragmentBinding.faqRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentBinding.faqRecycler.setAdapter(new FAQStampsAdapter(items));
    }

}