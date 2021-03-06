package com.mobiblanc.baridal_maghrib.views.account.help;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.utilities.Utilities;
import com.mobiblanc.baridal_maghrib.views.account.KeyboardManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HelpFragment extends Fragment {

    public HelpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @OnClick({R.id.backBtn, R.id.container})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                getActivity().onBackPressed();
                break;
            case R.id.container:
                Utilities.hideSoftKeyboard(getContext(), getView());
                break;
        }
    }
}