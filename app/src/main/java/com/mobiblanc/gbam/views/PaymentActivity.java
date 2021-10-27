package com.mobiblanc.gbam.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.databinding.ActivityPaimentActivityBinding;

public class PaymentActivity extends AppCompatActivity {
    private ActivityPaimentActivityBinding binding;
    public String url = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            url= null;
        } else {
            url= extras.getString("url");
        }
        setContentView(R.layout.activity_paiment_activity);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_paiment_activity);
        binding.paymentWebView.getSettings().setJavaScriptEnabled(true);
        binding.paymentWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                binding.loader.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                binding.loader.setVisibility(View.GONE);
            }

        });
        binding.paymentWebView.loadUrl(url);
    }
}