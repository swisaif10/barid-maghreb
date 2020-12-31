package com.mobiblanc.gbam.views.cart.payment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.databinding.FragmentAddNewCommentBinding;
import com.mobiblanc.gbam.databinding.FragmentRecapPaymentBinding;
import com.mobiblanc.gbam.views.cart.CartActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddNewCommentFragment extends Fragment {

    private FragmentAddNewCommentBinding fragmentBinding;

    public AddNewCommentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = FragmentAddNewCommentBinding.inflate(inflater, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init(){
        fragmentBinding.container.setOnClickListener(v -> requireActivity().onBackPressed());
        fragmentBinding.addBtn.setOnClickListener(v -> requireActivity().onBackPressed());
    }
}