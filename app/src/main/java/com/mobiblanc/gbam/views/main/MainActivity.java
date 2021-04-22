package com.mobiblanc.gbam.views.main;

import android.content.Context;
import android.os.Bundle;

import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.gbam.utilities.Constants;
import com.mobiblanc.gbam.views.base.BaseActivity;
import com.mobiblanc.gbam.views.main.dashboard.DashboardFragment;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        preferenceManager = new PreferenceManager.Builder(this, Context.MODE_PRIVATE)
                .name(Constants.SHARED_PREFS_NAME)
                .build();

        replaceFragment(new DashboardFragment());
    }

    @Override
    protected void onResume() {
        super.onResume();
        //updateCartCount();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1)
            getSupportFragmentManager().popBackStack();
        else
            finish();
    }

/*    @OnClick({R.id.loginBtn, R.id.cartBtn, R.id.backBtn})
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
    }*/

/*    public void updateCartCount() {
        count.setText(String.valueOf(preferenceManager.getValue(Constants.NB_ITEMS_IN_CART, 0)));
        count.setVisibility(preferenceManager.getValue(Constants.NB_ITEMS_IN_CART, 0) > 0 ? View.VISIBLE : View.INVISIBLE);
    }*/
}