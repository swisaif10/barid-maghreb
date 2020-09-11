package com.mobiblanc.baridal_maghrib.views.main.stamps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.models.Item;
import com.mobiblanc.baridal_maghrib.views.main.portraits.PortraitsAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StampsFragment extends Fragment {

    @BindView(R.id.stampsRecycler)
    RecyclerView stampsRecycler;

    public StampsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stamps, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        ArrayList<Item> portraits = new ArrayList<Item>() {
            {
                add(new Item(R.drawable.timbre_am,"100cm*150cm"));
                add(new Item(R.drawable.timbre_at,"70cm*100cm"));
                add(new Item(R.drawable.timbre_phil,"50cm*70cm"));
                add(new Item(R.drawable.timbre_solidariter,"20cm*30cm"));
                add(new Item(R.drawable.timbre_am,"100cm*150cm"));
                add(new Item(R.drawable.timbre_at,"70cm*100cm"));
                add(new Item(R.drawable.timbre_phil,"50cm*70cm"));
                add(new Item(R.drawable.timbre_solidariter,"20cm*30cm"));

            }
        };
        stampsRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        stampsRecycler.setAdapter(new StampsAdapter(getContext(), portraits));
    }
}