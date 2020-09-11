package com.mobiblanc.baridal_maghrib.views.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.views.cart.CartActivity;
import com.mobiblanc.baridal_maghrib.views.connexion.ConnexionActivity;
import com.mobiblanc.baridal_maghrib.views.main.divers.DiversFragment;
import com.mobiblanc.baridal_maghrib.views.main.home.HomeFragment;
import com.mobiblanc.baridal_maghrib.views.main.portraits.PortraitsFragment;
import com.mobiblanc.baridal_maghrib.views.main.stamps.StampsFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tabMenu)
    TabLayout tabLayout;
    @BindView(R.id.header)
    ConstraintLayout header;
    private String[] menu = {"Accueil", "Portraits", "Timbres", "Divers", "A propos"};
    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
    }

    @Override
    public void onBackPressed() {

        hideShowHeader(View.VISIBLE);
        if (getCurrentFragment() instanceof HomeFragment)
            finish();
        else {
            super.onBackPressed();
            tabLayout.selectTab(tabLayout.getTabAt(getFragmentIndex(getCurrentFragment())));
        }
    }

    @OnClick({R.id.loginBtn, R.id.cartBtn, R.id.logo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loginBtn:
                startActivity(new Intent(MainActivity.this, ConnexionActivity.class));
                break;
            case R.id.cartBtn:
                startActivity(new Intent(MainActivity.this, CartActivity.class));
                break;
            case R.id.logo:
                selectTab(0);
                break;
        }
    }

    private void init() {
        for (String item : menu) {
            TabLayout.Tab tab = tabLayout.newTab();
            tabLayout.addTab(tab);
        }
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new PortraitsFragment());
        fragments.add(new StampsFragment());
        fragments.add(new DiversFragment());
        fragments.add(new DiversFragment());
        highLightTab(0);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                highLightTab(position);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragments.get(position)).addToBackStack(null).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        getSupportFragmentManager().beginTransaction().add(R.id.container, new HomeFragment()).addToBackStack(null).commit();
    }

    private void highLightTab(int position) {
        if (tabLayout != null) {
            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                assert tab != null;
                getTabView(menu[i], tab, i == position);
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

    public void selectTab(int position) {
        highLightTab(position);
        tabLayout.selectTab(tabLayout.getTabAt(position));

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
}