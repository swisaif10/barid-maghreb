package com.mobiblanc.baridal_maghrib.views.cart.cartdetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.baridal_maghrib.listeners.OnDialogButtonsClickListener;
import com.mobiblanc.baridal_maghrib.listeners.OnItemQuantityChangedListener;
import com.mobiblanc.baridal_maghrib.models.cart.add.AddItemData;
import com.mobiblanc.baridal_maghrib.models.cart.delete.DeleteItemData;
import com.mobiblanc.baridal_maghrib.models.cart.guest.GuestCartData;
import com.mobiblanc.baridal_maghrib.models.cart.items.CartItem;
import com.mobiblanc.baridal_maghrib.models.cart.items.CartItemsData;
import com.mobiblanc.baridal_maghrib.models.cart.items.CartItemsResponseData;
import com.mobiblanc.baridal_maghrib.utilities.Connectivity;
import com.mobiblanc.baridal_maghrib.utilities.Constants;
import com.mobiblanc.baridal_maghrib.utilities.Utilities;
import com.mobiblanc.baridal_maghrib.viewmodels.CartVM;
import com.mobiblanc.baridal_maghrib.views.account.AccountActivity;
import com.mobiblanc.baridal_maghrib.views.cart.CartActivity;
import com.mobiblanc.baridal_maghrib.views.cart.delivery.DeliveryModeFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifImageView;

public class CartDetailsFragment extends Fragment implements OnDialogButtonsClickListener, OnItemQuantityChangedListener {

    @BindView(R.id.cartRecycler)
    RecyclerView cartRecycler;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.fee)
    TextView fee;
    @BindView(R.id.total)
    TextView total;
    @BindView(R.id.loader)
    GifImageView loader;
    private CartAdapter cartAdapter;
    private Boolean started = false;
    private Connectivity connectivity;
    private CartVM cartVM;
    private PreferenceManager preferenceManager;
    private List<CartItem> items;

    public CartDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cartVM = ViewModelProviders.of(this).get(CartVM.class);
        connectivity = new Connectivity(getContext(), this);
        cartVM.getGuestCartLiveData().observe(this, this::handleCreateGuestCartData);
        cartVM.getCartItemsLiveData().observe(this, this::handleCartItemsData);
        cartVM.getUpdateItemLiveData().observe(this, this::handleUpdateItemInCartData);
        cartVM.getDeleteItemLiveData().observe(this, this::handleDeleteItemFromCartData);

        preferenceManager = new PreferenceManager.Builder(getContext(), Context.MODE_PRIVATE)
                .name(Constants.SHARED_PREFS_NAME)
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart_details, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (started) {
            ((CartActivity) getActivity()).replaceFragment(new DeliveryModeFragment());
            started = false;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String id = preferenceManager.getValue(Constants.CART_ID, null);
        if (id == null)
            createGuestCart();
        else
            getCartItems(id);
    }

    @OnClick({R.id.backBtn, R.id.nextBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                getActivity().onBackPressed();
                break;
            case R.id.nextBtn:
                started = true;
                new LoginDialog(getActivity(), this).show();
                break;
        }
    }

    @Override
    public void onItemQuantityChanged(int index, int quantity) {
        updateItemQty(index, quantity);
    }

    @Override
    public void onFirstButtonClick() {
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

    private void init(CartItemsResponseData response) {

        items = response.getItems();
        cartRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        cartAdapter = new CartAdapter(getContext(), items, this::onItemQuantityChanged);
        cartRecycler.setAdapter(cartAdapter);

        price.setText(response.getProductsPrice() + " MAD");
        fee.setText(response.getFees() + " MAD");
        total.setText(response.getTotalPrice() + " MAD");

        enableSwipeToDelete();

    }

    private void createGuestCart() {
        if (connectivity.isConnected()) {
            loader.setVisibility(View.VISIBLE);
            cartVM.createGuestCart();
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
                getCartItems(guestCartData.getResponse().getQuoteId());
            } else {
                Utilities.showErrorPopup(getContext(), guestCartData.getHeader().getMessage());
            }
        }
    }

    private void getCartItems(String id) {
        if (connectivity.isConnected()) {
            loader.setVisibility(View.VISIBLE);
            cartVM.getCartItems(id);
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleCartItemsData(CartItemsData cartItemsData) {
        loader.setVisibility(View.GONE);
        if (cartItemsData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = cartItemsData.getHeader().getCode();
            if (code == 200) {
                init(cartItemsData.getResponse());
            } else {
                Utilities.showErrorPopup(getContext(), cartItemsData.getHeader().getMessage());
            }
        }
    }

    private void updateItemQty(int index, int qty) {
        if (connectivity.isConnected()) {
            cartVM.updateItem(preferenceManager.getValue(Constants.CART_ID, ""), items.get(index).getItemId(), qty);
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleUpdateItemInCartData(AddItemData addItemData) {

        if (addItemData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = addItemData.getHeader().getCode();
            if (code != 200) {
                Utilities.showErrorPopup(getContext(), addItemData.getHeader().getMessage());
            }
        }
    }

    private void deleteItem(int index) {
        if (connectivity.isConnected()) {
            cartVM.deleteItem(preferenceManager.getValue(Constants.CART_ID, ""), items.get(index).getItemId());
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleDeleteItemFromCartData(DeleteItemData deleteItemData) {

        if (deleteItemData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = deleteItemData.getHeader().getCode();
            if (code != 200 || !deleteItemData.getResponse()) {
                Utilities.showErrorPopup(getContext(), deleteItemData.getHeader().getMessage());
            }
        }
    }

    private void enableSwipeToDelete() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                final int position = viewHolder.getAdapterPosition();
                deleteItem(position);
                cartAdapter.removeItem(position);
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(cartRecycler);
    }

}