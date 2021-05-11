package com.mobiblanc.gbam.views.account.webview;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.databinding.FragmentWebviewBinding;
import com.mobiblanc.gbam.models.webview.WebViewData;
import com.mobiblanc.gbam.utilities.Connectivity;
import com.mobiblanc.gbam.utilities.Utilities;
import com.mobiblanc.gbam.viewmodels.AccountVM;

public class WebViewFragment extends Fragment {

    private FragmentWebviewBinding fragmentBinding;
    private Connectivity connectivity;
    private AccountVM accountVM;
    private Boolean isFAQ;

    public WebViewFragment() {
        // Required empty public constructor
    }

    public static WebViewFragment newInstance(Boolean isFAQ) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putBoolean("isFAQ", isFAQ);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
            isFAQ = getArguments().getBoolean("isFAQ");

        accountVM = ViewModelProviders.of(this).get(AccountVM.class);
        connectivity = new Connectivity(requireContext(), this);
        accountVM.getPdfLiveData().observe(this, this::handlePDFData);
        accountVM.getFaqPortraitLiveData().observe(this, this::handlePDFData);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = FragmentWebviewBinding.inflate(inflater, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isFAQ)
            getFAQPortrait();
        else
            getPDF();
    }

    private void init(String url) {
        fragmentBinding.backBtn.setOnClickListener(v -> requireActivity().onBackPressed());
        fragmentBinding.webView.getSettings().setJavaScriptEnabled(true);
        fragmentBinding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                fragmentBinding.loader.setVisibility(View.GONE);
            }
        });
        fragmentBinding.webView.loadUrl("https://docs.google.com/viewer?embedded=true&url=" + url);
    }


    private void getPDF() {
        if (connectivity.isConnected()) {
            fragmentBinding.loader.setVisibility(View.VISIBLE);
            accountVM.getPDF();
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handlePDFData(WebViewData webViewData) {
        if (webViewData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = webViewData.getHeader().getCode();
            if (code == 200) {
                init(webViewData.getResponse().getFile());
            } else {
                Utilities.showErrorPopup(getContext(), webViewData.getHeader().getMessage());
            }
        }
    }

    private void getFAQPortrait() {
        if (connectivity.isConnected()) {
            fragmentBinding.loader.setVisibility(View.VISIBLE);
            accountVM.getFAQPortrait();
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleFAQPortraitData(WebViewData webViewData) {
        if (webViewData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = webViewData.getHeader().getCode();
            if (code == 200) {
                init(webViewData.getResponse().getFile());
            } else {
                Utilities.showErrorPopup(getContext(), webViewData.getHeader().getMessage());
            }
        }
    }
}