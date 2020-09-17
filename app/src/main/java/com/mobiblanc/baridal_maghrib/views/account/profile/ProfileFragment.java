package com.mobiblanc.baridal_maghrib.views.account.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.models.Item;
import com.mobiblanc.baridal_maghrib.views.cart.CartActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileFragment extends Fragment {


    @BindView(R.id.profileRecycler)
    RecyclerView profileRecycler;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @OnClick({R.id.loginBtn, R.id.logo, R.id.cartBtn, R.id.logoutBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loginBtn:
                break;
            case R.id.logo:
                break;
            case R.id.cartBtn:
                startActivity(new Intent(getActivity(), CartActivity.class));
                break;
            case R.id.logoutBtn:
                break;
        }
    }

    private void init() {

        ArrayList<Item> items = new ArrayList<Item>() {{
            add(new Item(R.drawable.ic_user, "Mes informations personnelles"));
            add(new Item(R.drawable.ic_bag_profile, "Mon historique"));
            add(new Item(R.drawable.ic_tracking, "Suivi de commande"));
            add(new Item(R.drawable.ic_aide, "Assistance"));
        }};

        profileRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        profileRecycler.setAdapter(new ProfileAdapter(getContext(), items));
    }
}