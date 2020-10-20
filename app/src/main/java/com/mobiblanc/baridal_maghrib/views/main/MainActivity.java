package com.mobiblanc.baridal_maghrib.views.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.lifecycle.ViewModelProviders;
import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.baridal_maghrib.models.dashboard.DashboardData;
import com.mobiblanc.baridal_maghrib.models.dashboard.DashboardResponseData;
import com.mobiblanc.baridal_maghrib.utilities.Connectivity;
import com.mobiblanc.baridal_maghrib.utilities.Constants;
import com.mobiblanc.baridal_maghrib.utilities.Utilities;
import com.mobiblanc.baridal_maghrib.viewmodels.MainVM;
import com.mobiblanc.baridal_maghrib.views.account.AccountActivity;
import com.mobiblanc.baridal_maghrib.views.base.BaseActivity;
import com.mobiblanc.baridal_maghrib.views.cart.CartActivity;
import com.mobiblanc.baridal_maghrib.views.main.dashboard.DashboardFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.loader)
    GifImageView loader;
    @BindView(R.id.count)
    TextView count;
    @BindView(R.id.loginBtn)
    RelativeLayout loginBtn;
    @BindView(R.id.backBtn)
    RelativeLayout backBtn;
    private Connectivity connectivity;
    private MainVM mainVM;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mainVM = ViewModelProviders.of(this).get(MainVM.class);
        connectivity = new Connectivity(this, this);
        mainVM.getDashboardLiveData().observe(this, this::handleDashboardData);

        preferenceManager = new PreferenceManager.Builder(this, Context.MODE_PRIVATE)
                .name(Constants.SHARED_PREFS_NAME)
                .build();

        getDashboardDetails();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartCount();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick({R.id.loginBtn, R.id.cartBtn, R.id.backBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loginBtn:
                Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                if (preferenceManager.getValue(Constants.TOKEN, null) != null)
                    intent.putExtra("destination", 1);
                else
                    intent.putExtra("destination", 0);

                startActivity(intent);
                break;
            case R.id.cartBtn:
                startActivity(new Intent(MainActivity.this, CartActivity.class));
                break;
            case R.id.backBtn:
                onBackPressed();
        }
    }

    private void init(DashboardResponseData responseData) {
        replaceFragment(DashboardFragment.newInstance(responseData));
    }

    private void getDashboardDetails() {
        if (connectivity.isConnected()) {
            showHideLoader(View.VISIBLE);
            mainVM.getDashboardDetails(preferenceManager.getValue(Constants.TOKEN, null));
        } else
            Utilities.showErrorPopup(this, getString(R.string.no_internet_msg));
    }

    private void handleDashboardData(DashboardData dashboardData) {

        if (dashboardData == null) {
            showHideLoader(View.GONE);
            Utilities.showErrorPopup(this, getString(R.string.generic_error));
        } else {
            int code = dashboardData.getHeader().getCode();
            if (code == 200) {
                init(dashboardData.getResponse());
            } else if (code == 403) {
                showHideLoader(View.GONE);
                Utilities.showErrorPopupWithClick(this, dashboardData.getHeader().getMessage(), view -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    getDashboardDetails();
                });

            } else {
                showHideLoader(View.GONE);
                Utilities.showErrorPopup(this, dashboardData.getHeader().getMessage());
            }
        }
    }

    public void showHideLoader(int visibility) {
        loader.setVisibility(visibility);
    }

    public void showHideBackBtn(Boolean isVisible) {
        if (isVisible) {
            backBtn.setVisibility(View.VISIBLE);
            loginBtn.setVisibility(View.GONE);
        } else {
            backBtn.setVisibility(View.GONE);
            loginBtn.setVisibility(View.VISIBLE);
        }
    }

    public void updateCartCount() {
        count.setText(String.valueOf(preferenceManager.getValue(Constants.NB_ITEMS_IN_CART, 0)));
        count.setVisibility(preferenceManager.getValue(Constants.NB_ITEMS_IN_CART, 0) > 0 ? View.VISIBLE : View.INVISIBLE);
    }
}