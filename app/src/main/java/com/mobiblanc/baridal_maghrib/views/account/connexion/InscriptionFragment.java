package com.mobiblanc.baridal_maghrib.views.account.connexion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.utilities.Utilities;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class InscriptionFragment extends Fragment {

    public InscriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inscription, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.backBtn, R.id.signUpBtn, R.id.container})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                getActivity().onBackPressed();
                break;
            case R.id.signUpBtn:
                getActivity().finish();
                break;
            case R.id.container:
                Utilities.hideSoftKeyboard(getContext(), getView());
                break;
        }
    }
}