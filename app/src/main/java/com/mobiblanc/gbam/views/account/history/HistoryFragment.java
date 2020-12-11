package com.mobiblanc.gbam.views.account.history;

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
import com.mobiblanc.gbam.databinding.FragmentHistoryBinding;
import com.mobiblanc.gbam.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.gbam.listeners.OnItemSelectedListener;
import com.mobiblanc.gbam.models.history.History;
import com.mobiblanc.gbam.models.history.HistoryData;
import com.mobiblanc.gbam.utilities.Connectivity;
import com.mobiblanc.gbam.utilities.Constants;
import com.mobiblanc.gbam.utilities.Utilities;
import com.mobiblanc.gbam.viewmodels.AccountVM;
import com.mobiblanc.gbam.views.account.AccountActivity;
import com.mobiblanc.gbam.views.main.MainActivity;

import java.util.List;

public class HistoryFragment extends Fragment implements OnItemSelectedListener {

    private FragmentHistoryBinding fragmentBinding;
    private Connectivity connectivity;
    private AccountVM accountVM;
    private PreferenceManager preferenceManager;

    public HistoryFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountVM = ViewModelProviders.of(this).get(AccountVM.class);
        connectivity = new Connectivity(requireContext(), this);
        accountVM.getHistoryLivData().observe(this, this::handleHistoryData);

        preferenceManager = new PreferenceManager.Builder(requireContext(), Context.MODE_PRIVATE)
                .name(Constants.SHARED_PREFS_NAME)
                .build();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = FragmentHistoryBinding.inflate(inflater, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getHistory();
    }

    @Override
    public void onItemSelected(int position, Object object) {
        ((AccountActivity) requireActivity()).replaceFragment(new HistoryDetailsFragment());
    }

    private void init(List<History> historyList) {
        fragmentBinding.backBtn.setOnClickListener(v -> requireActivity().onBackPressed());

        fragmentBinding.historyRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentBinding.historyRecycler.setAdapter(new HistoryAdapter(historyList, this));
    }

    private void getHistory() {
        if (connectivity.isConnected()) {
            fragmentBinding.loader.setVisibility(View.VISIBLE);
            accountVM.getHistory();
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleHistoryData(HistoryData historyData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (historyData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = historyData.getHeader().getCode();
            if (code == 200) {
                init(historyData.getResponse());
            } else if (code == 403) {
                Utilities.showErrorPopupWithClick(getContext(), historyData.getHeader().getMessage(), view -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    requireActivity().finishAffinity();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                });
            } else {
                Utilities.showErrorPopup(getContext(), historyData.getHeader().getMessage());
            }
        }
    }


}