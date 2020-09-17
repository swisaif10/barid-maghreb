package com.mobiblanc.baridal_maghrib.views.main.products;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.models.products.Product;
import com.mobiblanc.baridal_maghrib.views.cart.CartActivity;
import com.mobiblanc.baridal_maghrib.views.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductDetailsFragment extends Fragment {

    @BindView(R.id.notif)
    TextView notif;
    @BindView(R.id.preview)
    ImageView preview;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.quantity)
    TextView quantity;
    @BindView(R.id.cartBtn)
    ConstraintLayout cartBtn;
    @BindView(R.id.total)
    TextView total;
    @BindView(R.id.copy)
    ImageView copy;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.shortDescription)
    TextView shortDescription;
    @BindView(R.id.description)
    TextView description;
    private Product product;

    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    public static ProductDetailsFragment newInstance(Product product) {
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("product", product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).hideShowHeader(View.GONE);
        if (getArguments() != null) {
            product = (Product) getArguments().getSerializable("product");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_details, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @OnClick({R.id.backBtn, R.id.decreaseBtn, R.id.increaseBtn, R.id.cartBtn, R.id.addBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                getActivity().onBackPressed();
                break;
            case R.id.decreaseBtn:
                if (Integer.valueOf(quantity.getText().toString()) > 1)
                    updateTotal(-1);
                break;
            case R.id.increaseBtn:
                updateTotal(1);
                break;
            case R.id.cartBtn:
                startActivity(new Intent(getActivity(), CartActivity.class));
                break;
            case R.id.addBtn:
                if (Integer.valueOf(quantity.getText().toString()) > 0)
                    makeFlyAnimation();
                break;
        }
    }

    private void init() {
        Glide.with(getContext()).load(product.getImage().get(0)).into(image);
        Glide.with(getContext()).load(product.getImage().get(0)).into(copy);
        title.setText(product.getName());
        shortDescription.setText(product.getShortDescription());
        description.setText(Html.fromHtml(Html.fromHtml(product.getDescription()).toString()));
        description.setMovementMethod(new ScrollingMovementMethod());
        total.setText(product.getPrice());
    }

    private void makeFlyAnimation() {


        new AnimationUtil().attachActivity(getActivity()).setTargetView(image).setMoveDuration(1000).setDestView(cartBtn).setAnimationListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                preview.setVisibility(View.VISIBLE);
                notif.setVisibility(View.VISIBLE);
                notif.setText(String.valueOf(Integer.valueOf(notif.getText().toString()) + Integer.valueOf(quantity.getText().toString())));
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).startAnimation();

    }

    private void updateTotal(int x) {
        quantity.setText(String.valueOf(Integer.valueOf(quantity.getText().toString()) + x));
        int qte = Integer.valueOf(quantity.getText().toString());
        float fee = Integer.valueOf(product.getPrice()) * qte;
        total.setText(String.format("%.2f", fee).replace(".", ","));
    }

}