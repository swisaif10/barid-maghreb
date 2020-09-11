package com.mobiblanc.baridal_maghrib.views.main.home;

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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {


    @BindView(R.id.categoriesRecycler)
    SnappingRecyclerView categoriesRecycler;
    @BindView(R.id.servicesRecycler)
    RecyclerView servicesRecycler;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        categoriesRecycler.enableViewScaling(true);
        ArrayList<Item> categories = new ArrayList<Item>() {
            {
                add(new Item(R.drawable.portrait, "Portraits de Sa Majesté le Roi"));
                add(new Item(R.drawable.timbres, "Timbres"));
                add(new Item(R.drawable.portrait, "Divers"));
            }
        };
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        categoriesRecycler.setAdapter(new CategoriesAdapter(getContext(), categories));

        ArrayList<Item> services = new ArrayList<Item>() {
            {
                add(new Item(R.drawable.ic_reclamation, "Réclamation"));
                add(new Item(R.drawable.ic_tracking, "Suivi de commande"));
                add(new Item(R.drawable.ic_adresse, "Mes adresses"));
                add(new Item(R.drawable.ic_cart_bancaire, "Mes cartes"));
                add(new Item(R.drawable.ic_aide, "Assistance"));
            }
        };
        servicesRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        servicesRecycler.setAdapter(new ServicesAdapter(getContext(), services));

    }
}