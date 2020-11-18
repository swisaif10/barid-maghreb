package com.mobiblanc.gbam.views.cart.shipping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.utilities.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddNewAddressFragment extends Fragment {

    @BindView(R.id.companyChoice)
    LinearLayout companyChoice;
    @BindView(R.id.particularChoice)
    LinearLayout particularChoice;
    @BindView(R.id.CompanyForm)
    LinearLayout CompanyForm;
    @BindView(R.id.ParticularForm)
    LinearLayout ParticularForm;

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

    @OnClick({R.id.saveBtn, R.id.container, R.id.companyChoice, R.id.particularChoice})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.saveBtn:
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, new Intent());
                getActivity().onBackPressed();
                break;
            case R.id.companyChoice:
                companyChoice.setBackground(getContext().getDrawable(R.drawable.selected_payment_item_background));
                particularChoice.setBackground(getContext().getDrawable(R.drawable.unselected_payment_item_background));
                CompanyForm.setVisibility(View.VISIBLE);
                ParticularForm.setVisibility(View.GONE);
                break;
            case R.id.particularChoice:
                particularChoice.setBackground(getContext().getDrawable(R.drawable.selected_payment_item_background));
                companyChoice.setBackground(getContext().getDrawable(R.drawable.unselected_payment_item_background));
                ParticularForm.setVisibility(View.VISIBLE);
                CompanyForm.setVisibility(View.GONE);
            case R.id.container:
                Utilities.hideSoftKeyboard(getContext(), getView());
                break;
        }
    }
}