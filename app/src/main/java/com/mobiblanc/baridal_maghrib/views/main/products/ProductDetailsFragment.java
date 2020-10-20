package com.mobiblanc.baridal_maghrib.views.main.products;

import android.animation.Animator;
import android.content.Context;
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
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.baridal_maghrib.models.cart.add.AddItemData;
import com.mobiblanc.baridal_maghrib.models.cart.guest.GuestCartData;
import com.mobiblanc.baridal_maghrib.models.products.Product;
import com.mobiblanc.baridal_maghrib.utilities.Connectivity;
import com.mobiblanc.baridal_maghrib.utilities.Constants;
import com.mobiblanc.baridal_maghrib.utilities.Utilities;
import com.mobiblanc.baridal_maghrib.viewmodels.CartVM;
import com.mobiblanc.baridal_maghrib.views.cart.CartActivity;
import com.mobiblanc.baridal_maghrib.views.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductDetailsFragment extends Fragment {

    @BindView(R.id.count)
    TextView count;
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
    private Connectivity connectivity;
    private CartVM cartVM;
    private PreferenceManager preferenceManager;

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

        cartVM = ViewModelProviders.of(this).get(CartVM.class);
        connectivity = new Connectivity(getContext(), this);
        cartVM.getAddItemLiveData().observe(this, this::handleAddItemToCartData);
        cartVM.getGuestCartLiveData().observe(this, this::handleCreateGuestCartData);

        preferenceManager = new PreferenceManager.Builder(getContext(), Context.MODE_PRIVATE)
                .name(Constants.SHARED_PREFS_NAME)
                .build();

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

    @Override
    public void onResume() {
        super.onResume();
        preview.setVisibility(View.GONE);
        count.setText(String.valueOf(preferenceManager.getValue(Constants.NB_ITEMS_IN_CART, 0)));
        count.setVisibility(preferenceManager.getValue(Constants.NB_ITEMS_IN_CART, 0) > 0 ? View.VISIBLE : View.INVISIBLE);
    }

    @OnClick({R.id.backBtn, R.id.decreaseBtn, R.id.increaseBtn, R.id.cartBtn, R.id.addBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                getActivity().onBackPressed();
                break;
            case R.id.decreaseBtn:
                if (Integer.valueOf(quantity.getText().toString()) > 1)
                    quantity.setText(String.valueOf(Integer.valueOf(quantity.getText().toString()) - 1));
                break;
            case R.id.increaseBtn:
                if (Integer.valueOf(quantity.getText().toString()) < 9)
                    quantity.setText(String.valueOf(Integer.valueOf(quantity.getText().toString()) + 1));
                break;
            case R.id.cartBtn:
                startActivity(new Intent(getActivity(), CartActivity.class));
                break;
            case R.id.addBtn:
                String id = preferenceManager.getValue(Constants.CART_ID, null);
                if (id == null)
                    createCart();
                else
                    addItemToCart(id);
                break;
        }
    }

    private void init() {

        Glide.with(getContext()).load(product.getImage().get(0)).into(image);
        Glide.with(getContext()).load(product.getImage().get(0)).into(copy);
        Glide.with(getContext()).load(product.getImage().get(0)).into(preview);
        title.setText(product.getName());
        shortDescription.setText(product.getShortDescription());
        description.setText(Html.fromHtml(product.getDescription()).toString());
        description.setMovementMethod(new ScrollingMovementMethod());
        total.setText(product.getPrice());
    }

    private void addItemToCart(String id) {
        if (connectivity.isConnected()) {
            cartVM.addItem(preferenceManager.getValue(Constants.TOKEN, null), id, product.getSku(), Integer.valueOf(quantity.getText().toString()));
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleAddItemToCartData(AddItemData addItemData) {

        if (addItemData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = addItemData.getHeader().getCode();
            if (code == 200) {
                makeFlyAnimation();
                preferenceManager.putValue(Constants.NB_ITEMS_IN_CART, preferenceManager.getValue(Constants.NB_ITEMS_IN_CART, 0) + Integer.valueOf(quantity.getText().toString()));
            } else if (code == 403) {
                Utilities.showErrorPopupWithClick(getContext(), addItemData.getHeader().getMessage(), view -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    //((MainActivity) getActivity()).selectTab(0, null);
                });
            } else
                Utilities.showErrorPopup(getContext(), addItemData.getHeader().getMessage());
        }
    }

    private void createCart() {
        if (connectivity.isConnected()) {
            cartVM.createCart(preferenceManager.getValue(Constants.TOKEN, null));
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleCreateGuestCartData(GuestCartData guestCartData) {

        if (guestCartData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = guestCartData.getHeader().getCode();
            if (code == 200) {
                preferenceManager.putValue(Constants.CART_ID, guestCartData.getResponse().getQuoteId());
                addItemToCart(guestCartData.getResponse().getQuoteId());
            } else if (code == 403) {
                Utilities.showErrorPopupWithClick(getContext(), guestCartData.getHeader().getMessage(), view -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    //((MainActivity) getActivity()). selectDashboard();
                });
            } else {
                Utilities.showErrorPopup(getContext(), guestCartData.getHeader().getMessage());
            }
        }
    }

    private void makeFlyAnimation() {
        new AnimationUtil().attachActivity(getActivity()).setTargetView(image).setMoveDuration(1000).setDestView(cartBtn).setAnimationListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                preview.setVisibility(View.VISIBLE);
                count.setVisibility(View.VISIBLE);
                count.setText(String.valueOf(Integer.valueOf(count.getText().toString()) + Integer.valueOf(quantity.getText().toString())));
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).startAnimation();

    }

}