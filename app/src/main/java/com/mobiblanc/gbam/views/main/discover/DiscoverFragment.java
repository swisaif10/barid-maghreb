package com.mobiblanc.gbam.views.main.discover;

import android.content.Intent;
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
import com.mobiblanc.gbam.databinding.FragmentDiscoverBinding;
import com.mobiblanc.gbam.models.html.HtmlData;
import com.mobiblanc.gbam.models.html.HtmlResponse;
import com.mobiblanc.gbam.utilities.Connectivity;
import com.mobiblanc.gbam.utilities.Utilities;
import com.mobiblanc.gbam.viewmodels.MainVM;
import com.mobiblanc.gbam.views.cart.CartActivity;

public class DiscoverFragment extends Fragment {

    private FragmentDiscoverBinding fragmentBinding;
    private Connectivity connectivity;
    private MainVM mainVM;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainVM = ViewModelProviders.of(this).get(MainVM.class);
        connectivity = new Connectivity(requireContext(), this);
        mainVM.getDescriptionLiveData().observe(this, this::handleDescriptionDAta);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = FragmentDiscoverBinding.inflate(inflater, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDescription();
    }

    private void init(HtmlResponse htmlResponse) {
        fragmentBinding.backBtn.setOnClickListener(v -> getActivity().onBackPressed());
        fragmentBinding.title.setText(htmlResponse.getTitle());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fragmentBinding.body.setText(Html.fromHtml(htmlResponse.getHtml(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            fragmentBinding.body.setText(Html.fromHtml(htmlResponse.getHtml()));
        }
    }


    private void getDescription() {
        if (connectivity.isConnected()) {
            fragmentBinding.loader.setVisibility(View.VISIBLE);
            mainVM.getDescription();
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleDescriptionDAta(HtmlData htmlData) {
        fragmentBinding.loader.setVisibility(View.GONE);
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