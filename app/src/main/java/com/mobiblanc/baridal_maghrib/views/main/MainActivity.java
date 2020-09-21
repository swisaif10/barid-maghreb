package com.mobiblanc.baridal_maghrib.views.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.tabs.TabLayout;
import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.models.dashboard.DashboardData;
import com.mobiblanc.baridal_maghrib.models.dashboard.DashboardResponseData;
import com.mobiblanc.baridal_maghrib.models.dashboard.Menu;
import com.mobiblanc.baridal_maghrib.utilities.Connectivity;
import com.mobiblanc.baridal_maghrib.utilities.Utilities;
import com.mobiblanc.baridal_maghrib.viewmodels.MainVM;
import com.mobiblanc.baridal_maghrib.views.account.AccountActivity;
import com.mobiblanc.baridal_maghrib.views.base.BaseActivity;
import com.mobiblanc.baridal_maghrib.views.cart.CartActivity;
import com.mobiblanc.baridal_maghrib.views.main.dashboard.DashboardFragment;
import com.mobiblanc.baridal_maghrib.views.main.products.ProductsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tabMenu)
    TabLayout tabLayout;
    @BindView(R.id.header)
    ConstraintLayout header;
    @BindView(R.id.loader)
    GifImageView loader;
    private ArrayList<Fragment> fragments;
    private Connectivity connectivity;
    private MainVM mainVM;
    private List<Menu> menu;

    private Boolean isMenu = true;

    private Boolean backPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mainVM = ViewModelProviders.of(this).get(MainVM.class);
        connectivity = new Connectivity(this, this);
        mainVM.getDashboardLiveData().observe(this, this::handleDashboardData);

        getDashboardDetails();
    }

    @Override
    public void onBackPressed() {
        hideShowHeader(View.VISIBLE);

        if (getCurrentFragment() instanceof DashboardFragment)
            finish();
        else {
            super.onBackPressed();

            if (isMenu) {
                backPressed = true;
                tabLayout.selectTab(tabLayout.getTabAt(fragments.indexOf(getCurrentFragment())));
            } else
                isMenu = true;
        }
    }

    @OnClick({R.id.loginBtn, R.id.cartBtn, R.id.logo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loginBtn:
                Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                intent.putExtra("destination", 1);
                startActivity(intent);
                break;
            case R.id.cartBtn:
                startActivity(new Intent(MainActivity.this, CartActivity.class));
                break;
            case R.id.logo:
                tabLayout.selectTab(tabLayout.getTabAt(0));
                break;
        }
    }

    private void init(DashboardResponseData responseData) {
        fragments = new ArrayList<>();
        menu = responseData.getMenu();
        for (Menu item : menu) {
            TabLayout.Tab tab = tabLayout.newTab();
            tabLayout.addTab(tab);

            getTabView(item.getTitle(), tab, false);

            switch (item.getType()) {
                case "view":
                    if (item.getValue().equalsIgnoreCase("dashboard"))
                        fragments.add(DashboardFragment.newInstance(responseData));
                    break;
                case "category":
                    fragments.add(ProductsFragment.newInstance(item.getValue(), item.getTitle()));
                    break;
            }
        }

        tabLayout.post(() -> {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int widthS = displayMetrics.widthPixels;
            tabLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            int widthT = tabLayout.getMeasuredWidth();

            if (widthS > widthT) {
                tabLayout.setTabMode(TabLayout.MODE_FIXED);
                tabLayout.setVisibility(View.VISIBLE);
                //tabLayout.setLayoutParams(new ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                highLightTab(position);

                if (!backPressed) {
                    replaceFragment(fragments.get(position));
                } else {
                    backPressed = false;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // to init the tabLayout to 0
        tabLayout.selectTab(tabLayout.getTabAt(1));
        tabLayout.selectTab(tabLayout.getTabAt(0));
    }

    private void getDashboardDetails() {
        if (connectivity.isConnected()) {
            showHideLoader(View.VISIBLE);
            mainVM.getDashboardDetails();
        } else
            Utilities.showErrorPopup(this, getString(R.string.no_internet_msg));
    }

    private void handleDashboardData(DashboardData dashboardData) {

        if (dashboardData == null) {
            Utilities.showErrorPopup(this, getString(R.string.generic_error));
        } else {
            int code = dashboardData.getHeader().getCode();
            if (code == 200) {
                init(dashboardData.getResponse());
            } else {
                Utilities.showErrorPopup(this, dashboardData.getHeader().getMessage());
            }
        }
    }

    private void highLightTab(int position) {
        if (tabLayout != null) {
            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                assert tab != null;
                getTabView(menu.get(i).getTitle(), tab, i == position);
                LinearLayout layout = ((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(i));
                layout.setBackground(null);
            }
        }
    }

    private void getTabView(String title, TabLayout.Tab tab, boolean isSelected) {
        View view = tab.getCustomView() == null ? LayoutInflater.from(tabLayout.getContext()).inflate(R.layout.custom_tabmenu_item, null) : tab.getCustomView();
        if (tab.getCustomView() == null) {
            tab.setCustomView(view);
        }
        TextView tabTextView = view.findViewById(R.id.title);
        View underline = view.findViewById(R.id.underline);
        tabTextView.setTypeface(isSelected ? ResourcesCompat.getFont(this, R.font.montserrat_semibold) : ResourcesCompat.getFont(this, R.font.montserrat_regular));
        tabTextView.setText(title);
        underline.setVisibility(isSelected ? View.VISIBLE : View.INVISIBLE);
    }

    public void selectTab(int id, String title) {
        int position = -1;
        for (Menu item : menu) {
            if (item.getValue().equalsIgnoreCase(String.valueOf(id)))
                position = menu.indexOf(item);
        }
        if (position != -1)
            tabLayout.selectTab(tabLayout.getTabAt(position));
        else {
            highLightTab(-1);
            replaceFragment(ProductsFragment.newInstance(String.valueOf(id), title));
            isMenu = false;
        }
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.container);
    }

    private int getFragmentIndex(Fragment fragment) {
        for (int i = 0; i < fragments.size(); i++) {
            if (fragments.get(i).getClass().equals(fragment.getClass()))
                return i;
        }
        return -1;
    }

    public void hideShowHeader(int visibility) {
        header.setVisibility(visibility);
    }

    public void isMenu(Boolean menu) {
        isMenu = menu;
    }

    public void showHideLoader(int visibility) {
        loader.setVisibility(visibility);
    }
}