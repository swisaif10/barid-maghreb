package com.mobiblanc.baridal_maghrib.views.cart.delivery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.utilities.Utilities;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddNewAddressFragment extends Fragment {

    public AddNewAddressFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_address, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.backBtn, R.id.saveBtn, R.id.container})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                getActivity().onBackPressed();
                break;
            case R.id.saveBtn:
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, new Intent());
                getActivity().onBackPressed();
                break;
            case R.id.container:
                Utilities.hideSoftKeyboard(getContext(),getView());
                break;
        }
    }
}