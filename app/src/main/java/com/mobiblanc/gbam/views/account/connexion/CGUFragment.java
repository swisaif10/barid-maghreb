package com.mobiblanc.gbam.views.account.connexion;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.databinding.FragmentCguBinding;
import com.mobiblanc.gbam.models.html.HtmlData;
import com.mobiblanc.gbam.models.html.HtmlResponse;
import com.mobiblanc.gbam.utilities.Connectivity;
import com.mobiblanc.gbam.utilities.Utilities;
import com.mobiblanc.gbam.viewmodels.AccountVM;

public class CGUFragment extends Fragment {

    private FragmentCguBinding fragmentCguBinding;
    private Connectivity connectivity;
    private AccountVM accountVM;

    public CGUFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountVM = ViewModelProviders.of(this).get(AccountVM.class);
        connectivity = new Connectivity(requireContext(), this);
        accountVM.getCguLiveData().observe(this, this::handleCGUData);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentCguBinding = FragmentCguBinding.inflate(inflater, container, false);
        return fragmentCguBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getCGU();
    }

    private void init(HtmlResponse htmlResponse) {
        fragmentCguBinding.backBtn.setOnClickListener(v -> getActivity().onBackPressed());
        fragmentCguBinding.title.setText(htmlResponse.getTitle());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fragmentCguBinding.body.setText(Html.fromHtml(htmlResponse.getHtml(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            fragmentCguBinding.body.setText(Html.fromHtml(htmlResponse.getHtml()));
        }
    }

    private void getCGU() {
        if (connectivity.isConnected()) {
            fragmentCguBinding.loader.setVisibility(View.VISIBLE);
            accountVM.getCGU();
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleCGUData(HtmlData htmlData) {
        fragmentCguBinding.loader.setVisibility(View.GONE);
        if (htmlData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = htmlData.getHeader().getCode();
            if (code == 200) {
                init(htmlData.getResponse());
            } else {
                Utilities.showErrorPopup(getContext(), htmlData.getHeader().getMessage());
            }
        }
    }
}