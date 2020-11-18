package com.mobiblanc.gbam.views.cart.cartdetails;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.databinding.FragmentCartDetailsBinding;
import com.mobiblanc.gbam.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.gbam.listeners.OnDialogButtonsClickListener;
import com.mobiblanc.gbam.listeners.OnItemQuantityChangedListener;
import com.mobiblanc.gbam.models.cart.add.AddItemData;
import com.mobiblanc.gbam.models.cart.delete.DeleteItemData;
import com.mobiblanc.gbam.models.cart.items.CartItemsData;
import com.mobiblanc.gbam.models.cart.items.CartItemsResponseData;
import com.mobiblanc.gbam.models.common.Item;
import com.mobiblanc.gbam.utilities.Connectivity;
import com.mobiblanc.gbam.utilities.Constants;
import com.mobiblanc.gbam.utilities.Utilities;
import com.mobiblanc.gbam.viewmodels.CartVM;
import com.mobiblanc.gbam.views.account.AccountActivity;
import com.mobiblanc.gbam.views.cart.CartActivity;
import com.mobiblanc.gbam.views.cart.shipping.ShippingMethodFragment;
import com.mobiblanc.gbam.views.main.MainActivity;

import java.util.List;

public class CartDetailsFragment extends Fragment implements OnDialogButtonsClickListener, OnItemQuantityChangedListener {

    private FragmentCartDetailsBinding fragmentCartDetailsBinding;
    private Boolean started = false;
    private Connectivity connectivity;
    private CartVM cartVM;
    private PreferenceManager preferenceManager;
    private List<Item> items;
    private int itemsToAddInCart;
    private int position;

    public CartDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cartVM = ViewModelProviders.of(this).get(CartVM.class);

        connectivity = new Connectivity(getContext(), this);

        cartVM.getCartItemsLiveData().observe(this, this::handleCartItemsData);
        cartVM.getUpdateItemLiveData().observe(this, this::handleUpdateItemInCartData);
        cartVM.getDeleteItemLiveData().observe(this, this::handleDeleteItemFromCartData);

        preferenceManager = new PreferenceManager.Builder(getContext(), Context.MODE_PRIVATE)
                .name(Constants.SHARED_PREFS_NAME)
                .build();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentCartDetailsBinding = FragmentCartDetailsBinding.inflate(inflater, container, false);
        return fragmentCartDetailsBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (started && preferenceManager.getValue(Constants.TOKEN, null) != null) {
            ((CartActivity) getActivity()).replaceFragment(new ShippingMethodFragment());
            started = false;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getCartItems();
    }

    @Override
    public void onItemQuantityChanged(int index, int quantity) {
        itemsToAddInCart = quantity - Integer.parseInt(items.get(index).getQty());
        updateItemQty(index, quantity);
    }

    @Override
    public void onFirstButtonClick(String code) {
        Intent intent = new Intent(getActivity(), AccountActivity.class);
        intent.putExtra("destination", 0);
        startActivity(intent);
    }

    @Override
    public void onSecondButtonClick() {
        Intent intent = new Intent(getActivity(), AccountActivity.class);
        intent.putExtra("destination", -1);
        startActivity(intent);
    }

    @SuppressLint("SetTextI18n")
    private void init(CartItemsResponseData response) {
        fragmentCartDetailsBinding.nextBtn.setOnClickListener(v -> {
            started = true;
            if (preferenceManager.getValue(Constants.TOKEN, null) != null)
                ((CartActivity) getActivity()).replaceFragment(new ShippingMethodFragment());
            else
                new LoginDialog(getActivity(), CartDetailsFragment.this).show();
        });
        items = response.getItems();
        fragmentCartDetailsBinding.cartRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        CartAdapter cartAdapter = new CartAdapter(getContext(), items, this);
        fragmentCartDetailsBinding.cartRecycler.setAdapter(cartAdapter);

        fragmentCartDetailsBinding.price.setText(response.getProductsPrice() + " MAD");
        fragmentCartDetailsBinding.fee.setText(response.getFees() + " MAD");
        fragmentCartDetailsBinding.total.setText(response.getTotalPrice() + " MAD");


        enableSwipeToDelete();

    }

    private void getCartItems() {
        if (connectivity.isConnected()) {
            fragmentCartDetailsBinding.loader.setVisibility(View.VISIBLE);
            cartVM.getCartItems(preferenceManager.getValue(Constants.TOKEN, ""), preferenceManager.getValue(Constants.CART_ID, null));
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleCartItemsData(CartItemsData cartItemsData) {
        fragmentCartDetailsBinding.loader.setVisibility(View.GONE);
        if (cartItemsData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = cartItemsData.getHeader().getCode();
            if (code == 200) {
                init(cartItemsData.getResponse());
            } else if (code == 403) {
                Utilities.showErrorPopupWithClick(getContext(), cartItemsData.getHeader().getMessage(), view -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    getActivity().finishAffinity();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                });
            } else {
                Utilities.showErrorPopup(getContext(), cartItemsData.getHeader().getMessage());
            }
        }
    }

    private void updateItemQty(int index, int qty) {
        if (connectivity.isConnected()) {
            cartVM.updateItem(preferenceManager.getValue(Constants.TOKEN, ""), preferenceManager.getValue(Constants.CART_ID, ""), items.get(index).getItemId(), qty);
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleUpdateItemInCartData(AddItemData addItemData) {

        if (addItemData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = addItemData.getHeader().getCode();
            if (code == 200) {
                preferenceManager.putValue(Constants.NB_ITEMS_IN_CART, preferenceManager.getValue(Constants.NB_ITEMS_IN_CART, 0) + itemsToAddInCart);
                getCartItems();
            } else if (code == 403) {
                Utilities.showErrorPopupWithClick(getContext(), addItemData.getHeader().getMessage(), view -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    getActivity().finishAffinity();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                });
            } else
                Utilities.showErrorPopup(getContext(), addItemData.getHeader().getMessage());
        }
    }

    private void deleteItem(int index) {
        if (connectivity.isConnected()) {
            cartVM.deleteItem(preferenceManager.getValue(Constants.TOKEN, ""), preferenceManager.getValue(Constants.CART_ID, ""), items.get(index).getItemId());
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleDeleteItemFromCartData(DeleteItemData deleteItemData) {

        if (deleteItemData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = deleteItemData.getHeader().getCode();
            if (code == 200 && deleteItemData.getResponse()) {
                getCartItems();
                itemsToAddInCart = -Integer.parseInt(items.get(position).getQty());
                preferenceManager.putValue(Constants.NB_ITEMS_IN_CART, preferenceManager.getValue(Constants.NB_ITEMS_IN_CART, 0) + itemsToAddInCart);
            } else if (code == 403) {
                Utilities.showErrorPopupWithClick(getContext(), deleteItemData.getHeader().getMessage(), view -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    getActivity().finishAffinity();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                });
            } else
                Utilities.showErrorPopup(getContext(), deleteItemData.getHeader().getMessage());
        }
    }

    private void enableSwipeToDelete() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                position = viewHolder.getAdapterPosition();
                deleteItem(position);
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(fragmentCartDetailsBinding.cartRecycler);
    }

}