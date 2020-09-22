package com.mobiblanc.baridal_maghrib.views.account.connexion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.utilities.Utilities;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthenticationFragment extends Fragment {

    public AuthenticationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_authentication, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick({R.id.backBtn, R.id.siginBtn, R.id.loginBtn, R.id.container})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                getActivity().onBackPressed();
                break;
            case R.id.siginBtn:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new RegistrationFragment()).addToBackStack(null).commit();
                break;
            case R.id.loginBtn:
                getActivity().finish();
            case R.id.container:
                Utilities.hideSoftKeyboard(getContext(),getView());
                break;
        }
    }
}