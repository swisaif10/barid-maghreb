package com.mobiblanc.gbam.views.main.product;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.databinding.FragmentStampsBinding;
import com.mobiblanc.gbam.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.gbam.listeners.OnItemQuantityChangedListener;
import com.mobiblanc.gbam.listeners.OnItemSelectedListener;
import com.mobiblanc.gbam.models.cart.add.AddItemData;
import com.mobiblanc.gbam.models.products.Product;
import com.mobiblanc.gbam.models.products.ProductsData;
import com.mobiblanc.gbam.utilities.AnimationUtil;
import com.mobiblanc.gbam.utilities.Connectivity;
import com.mobiblanc.gbam.utilities.Constants;
import com.mobiblanc.gbam.utilities.Utilities;
import com.mobiblanc.gbam.viewmodels.CartVM;
import com.mobiblanc.gbam.viewmodels.MainVM;
import com.mobiblanc.gbam.views.cart.CartActivity;
import com.mobiblanc.gbam.views.main.adapters.ProductsAdapter;

import java.util.List;

public class StampsFragment extends Fragment implements OnItemSelectedListener, OnItemQuantityChangedListener {

    private FragmentStampsBinding fragmentStampsBinding;
    private int id;
    private Connectivity connectivity;
    private MainVM mainVM;
    private CartVM cartVM;
    private PreferenceManager preferenceManager;
    private List<Product> products;
    private int selectedProductPosition;
    private ImageView imageView;

    public StampsFragment() {
    }

    public static StampsFragment newInstance(int id) {
        StampsFragment fragment = new StampsFragment();
        Bundle args = new Bundle();
        args.putInt("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainVM = ViewModelProviders.of(this).get(MainVM.class);
        cartVM = ViewModelProviders.of(this).get(CartVM.class);

        connectivity = new Connectivity(requireContext(), this);

        mainVM.getProductsLiveData().observe(this, this::handleStampsData);
        cartVM.getAddItemLiveData().observe(this, this::handleAddItemToCartData);

        preferenceManager = new PreferenceManager.Builder(requireContext(), Context.MODE_PRIVATE)
                .name(Constants.SHARED_PREFS_NAME)
                .build();
        if (getArguments() != null) {
            id = getArguments().getInt("id");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentStampsBinding = FragmentStampsBinding.inflate(inflater, container, false);
        return fragmentStampsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getStamps();
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentStampsBinding.backBtn.setOnClickListener(v -> requireActivity().onBackPressed());
        fragmentStampsBinding.cartBtn.setOnClickListener(v -> startActivity(new Intent(getActivity(), CartActivity.class)));

        if (preferenceManager.getValue(Constants.NB_ITEMS_IN_CART, 0) > 0) {
            fragmentStampsBinding.count.setVisibility(View.VISIBLE);
            fragmentStampsBinding.count.setText(String.valueOf(preferenceManager.getValue(Constants.NB_ITEMS_IN_CART, 0)));
        } else fragmentStampsBinding.count.setVisibility(View.GONE);
    }

    @Override
    public void onItemQuantityChanged(int index, int quantity) {
        products.get(index).setQuantity(quantity);
    }

    @Override
    public void onItemSelected(int position, Object object) {
        selectedProductPosition = position;
        this.imageView = (ImageView) object;
        addItemToCart(preferenceManager.getValue(Constants.CART_ID, null));
    }

    private void init() {

        fragmentStampsBinding.stampsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentStampsBinding.stampsRecycler.setAdapter(new ProductsAdapter(getContext(), products, this, this));
        fragmentStampsBinding.stampsRecycler.setNestedScrollingEnabled(false);
    }

    private void makeFlyAnimation() {
        new AnimationUtil(0.3f).attachActivity(getActivity()).setTargetView(imageView).setMoveDuration(1000).setDestView(fragmentStampsBinding.cartBtn).setAnimationListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                fragmentStampsBinding.preview.setVisibility(View.VISIBLE);
                fragmentStampsBinding.preview.setImageDrawable(imageView.getDrawable());
                fragmentStampsBinding.count.setVisibility(View.VISIBLE);
                fragmentStampsBinding.count.setText(String.valueOf(preferenceManager.getValue(Constants.NB_ITEMS_IN_CART, 0)));
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).startAnimation();

    }

    private void getStamps() {
        if (connectivity.isConnected()) {
            fragmentStampsBinding.loader.setVisibility(View.VISIBLE);
            mainVM.getProducts(preferenceManager.getValue(Constants.TOKEN, ""), id);
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleStampsData(ProductsData productsData) {
        fragmentStampsBinding.loader.setVisibility(View.GONE);
        if (productsData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = productsData.getHeader().getCode();
            if (code == 200) {
                this.products = productsData.getResponse();
                init();
            } else if (code == 403) {
                Utilities.showErrorPopupWithClick(getContext(), productsData.getHeader().getMessage(), view -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    getStamps();
                });

            } else {
                Utilities.showErrorPopup(getContext(), productsData.getHeader().getMessage());
            }
        }
    }

    private void addItemToCart(String id) {
        if (connectivity.isConnected()) {
            cartVM.addItem(preferenceManager.getValue(Constants.TOKEN, ""), id, products.get(selectedProductPosition).getSku(), products.get(selectedProductPosition).getQuantity());
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleAddItemToCartData(AddItemData addItemData) {

        if (addItemData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = addItemData.getHeader().getCode();
            if (code == 200) {
                preferenceManager.putValue(Constants.NB_ITEMS_IN_CART, preferenceManager.getValue(Constants.NB_ITEMS_IN_CART, 0) + products.get(selectedProductPosition).getQuantity());
                makeFlyAnimation();
            } else if (code == 403)
                Utilities.showErrorPopupWithClick(getContext(), addItemData.getHeader().getMessage(), view -> preferenceManager.clearValue(Constants.TOKEN));
            else
                Utilities.showErrorPopup(getContext(), addItemData.getHeader().getMessage());
        }
    }
}