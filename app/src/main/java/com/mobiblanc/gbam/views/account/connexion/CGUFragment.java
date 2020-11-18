package com.mobiblanc.gbam.views.account.connexion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.mobiblanc.gbam.databinding.FragmentCguBinding;

public class CGUFragment extends Fragment {

    private FragmentCguBinding fragmentCguBinding;

    public CGUFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentCguBinding = FragmentCguBinding.inflate(inflater, container, false);
        return fragmentCguBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        fragmentCguBinding.backBtn.setOnClickListener(v -> getActivity().onBackPressed());
    }
}