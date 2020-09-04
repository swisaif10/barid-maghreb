package com.mobiblanc.baridal_maghrib.views.main.portraits;

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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PortraitsFragment extends Fragment {

    @BindView(R.id.portraitsRecycler)
    RecyclerView portraitsRecycler;

    public PortraitsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_portraits, container, false);
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

            }
        };
        portraitsRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        portraitsRecycler.setAdapter(new PortraitsAdapter(getContext(), portraits));
    }
}