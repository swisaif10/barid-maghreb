package com.mobiblanc.baridal_maghrib.views.cart.payment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.mobiblanc.baridal_maghrib.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddNewCommentFragment extends Fragment {

    public AddNewCommentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_comment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.addBtn, R.id.container})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.addBtn) {
        }
        getActivity().onBackPressed();
    }
}