package com.mobiblanc.baridal_maghrib.views.account.history;

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
import com.mobiblanc.baridal_maghrib.models.HistoryItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyHistoryFragment extends Fragment {

    @BindView(R.id.myHistoryRecycler)
    RecyclerView myHistoryRecycler;

    public MyHistoryFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_history, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @OnClick(R.id.backBtn)
    public void onViewClicked() {
        getActivity().onBackPressed();
    }

    private void init() {
        ArrayList<HistoryItem> arrayList = new ArrayList<HistoryItem>(){{
            add(new HistoryItem(R.color.yellow,"Livrée"));
            add(new HistoryItem(R.color.greenStatus,"En cours de traitement"));
            add(new HistoryItem(R.color.yellow,"Livrée"));
            add(new HistoryItem(R.color.redStatus,"Annulée"));
            add(new HistoryItem(R.color.blue,"Confirmée"));
            add(new HistoryItem(R.color.yellow,"Livrée"));
            add(new HistoryItem(R.color.yellow,"Livrée"));
            add(new HistoryItem(R.color.yellow,"Livrée"));
            add(new HistoryItem(R.color.yellow,"Livrée"));
            add(new HistoryItem(R.color.yellow,"Livrée"));
            add(new HistoryItem(R.color.yellow,"Livrée"));
            add(new HistoryItem(R.color.yellow,"Livrée"));
            add(new HistoryItem(R.color.yellow,"Livrée"));
            add(new HistoryItem(R.color.yellow,"Livrée"));
        }};
        myHistoryRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        myHistoryRecycler.setAdapter(new MyHistoryAdapter(getContext(),arrayList));
    }
}