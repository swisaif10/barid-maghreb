package com.mobiblanc.baridal_maghrib.views.cart.delivery;

import android.app.Activity;
import android.content.Intent;
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
import com.mobiblanc.baridal_maghrib.views.cart.CartActivity;
import com.mobiblanc.baridal_maghrib.views.cart.payment.PaymentFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StandardDeliveryFragment extends Fragment {

    private static final int REQUEST_CODE = 100;

    @BindView(R.id.deliveryRecycler)
    RecyclerView deliveryRecycler;
    private StandardDeliveryAdapter standardDeliveryAdapter;
    private ArrayList<String> arrayList;

    public StandardDeliveryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayList = new ArrayList<>();
        arrayList.add("test");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_standard_delivery, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            arrayList.add("test");
            //standardDeliveryAdapter.notifyItemInserted(arrayList.size() - 1);
            standardDeliveryAdapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.backBtn, R.id.updateBtn, R.id.addNewAddressBtn, R.id.nextBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                getActivity().onBackPressed();
                break;
            case R.id.updateBtn:
                break;
            case R.id.addNewAddressBtn:
                AddNewAddressFragment addNewAddressFragment = new AddNewAddressFragment();
                addNewAddressFragment.setTargetFragment(this, REQUEST_CODE);
                ((CartActivity) getActivity()).replaceFragment(addNewAddressFragment);
                break;
            case R.id.nextBtn:
                ((CartActivity) getActivity()).replaceFragment(new PaymentFragment());
                break;
        }
    }

    private void init() {

        deliveryRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        standardDeliveryAdapter = new StandardDeliveryAdapter(getContext(), arrayList);
        deliveryRecycler.setAdapter(standardDeliveryAdapter);

    }
}