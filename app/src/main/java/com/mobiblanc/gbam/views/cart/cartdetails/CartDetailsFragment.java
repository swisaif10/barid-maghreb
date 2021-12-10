package com.mobiblanc.gbam.views.cart.cartdetails;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
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
import com.mobiblanc.gbam.models.payment.recap.info.RecapInfoData;
import com.mobiblanc.gbam.models.shipping.address.AddressData;
import com.mobiblanc.gbam.utilities.Connectivity;
import com.mobiblanc.gbam.utilities.Constants;
import com.mobiblanc.gbam.utilities.SwipeHelper;
import com.mobiblanc.gbam.utilities.Utilities;
import com.mobiblanc.gbam.viewmodels.CartVM;
import com.mobiblanc.gbam.views.account.AccountActivity;
import com.mobiblanc.gbam.views.cart.CartActivity;
import com.mobiblanc.gbam.views.cart.shipping.StandardShippingFragment;
import com.mobiblanc.gbam.views.main.MainActivity;

import java.text.DecimalFormat;
import java.util.List;

public class CartDetailsFragment extends Fragment implements OnDialogButtonsClickListener, OnItemQuantityChangedListener {

    private FragmentCartDetailsBinding fragmentCartDetailsBinding;
    private Boolean started = false;
    private Connectivity connectivity;
    private CartVM cartVM;
    private PreferenceManager preferenceManager;
    private List<Item> items;
    private int itemsToAddInCart;
    private int deletedItemId;
    private CartAdapter cartAdapter;

    public CartDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cartVM = ViewModelProviders.of(this).get(CartVM.class);

        connectivity = new Connectivity(requireContext(), this);

        cartVM.getCartItemsLiveData().observe(this, this::handleCartItemsData);
        cartVM.getUpdateItemLiveData().observe(this, this::handleUpdateItemInCartData);
        cartVM.getDeleteItemLiveData().observe(this, this::handleDeleteItemFromCartData);
        cartVM.getAddressLiveData().observe(this, this::handleAddressData);
        cartVM.getRecapInfoLiveData().observe(this, this::handleRecapInfoData);

        preferenceManager = new PreferenceManager.Builder(requireContext(), Context.MODE_PRIVATE)
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
            getAddress();
            started = false;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        enableSwipeToDelete();
        getCartItems();
    }

    @Override
    public void onItemQuantityChanged(int index, int quantity) {
        deactivateUserInteraction();

        itemsToAddInCart = quantity - (Integer.parseInt(items.get(index).getQty()));
        //itemsToAddInCart = ((quantity - Integer.parseInt(items.get(index).getQty())) / items.get(index).getStep());
        Log.d("TAG", "onItemQuantityChanged: " + quantity);
        Log.d("TAG", "onItemQuantityChanged: " + items.get(index).getQty());
        Log.d("TAG", "onItemQuantityChanged: " + itemsToAddInCart);
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
        fragmentCartDetailsBinding.infoBtn.setOnClickListener(v -> getRecapInfo());

        if (!response.getItems().isEmpty())
            fragmentCartDetailsBinding.nextBtn.setEnabled(true);
        fragmentCartDetailsBinding.nextBtn.setOnClickListener(v -> {
            if (preferenceManager.getValue(Constants.TOKEN, null) != null)
                getAddress();
            else {
                started = true;
                Utilities.showLoginPopup(requireContext(), this);
            }
        });
        items = response.getItems();
        fragmentCartDetailsBinding.cartRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        cartAdapter = new CartAdapter(getContext(), items, this);
        fragmentCartDetailsBinding.cartRecycler.setAdapter(cartAdapter);
        //preferenceManager.putValue(Constants.NB_ITEMS_IN_CART,response.getCountCart());
        DecimalFormat df = new DecimalFormat("0.00");
        df.setMaximumFractionDigits(2);
        fragmentCartDetailsBinding.price.setText(df.format(response.getProductsPrice()) + " MAD");
        fragmentCartDetailsBinding.fee.setText(df.format(response.getFees()) + " MAD");
        fragmentCartDetailsBinding.total.setText(df.format(response.getTotalPrice()) + " MAD");
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
                    preferenceManager.clearValue(Constants.CART_ID);
                    preferenceManager.clearValue(Constants.NB_ITEMS_IN_CART);
                    requireActivity().finishAffinity();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                });
            } else if (code == 409) {
                Utilities.showErrorPopupWithClick(requireContext(), cartItemsData.getHeader().getMessage(), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        preferenceManager.clearValue(Constants.CART_ID);
                        startActivity(new Intent(requireActivity(), MainActivity.class));
                        requireActivity().finish();
                    }
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
        activateUserInteraction();
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
                    preferenceManager.clearValue(Constants.CART_ID);
                    preferenceManager.clearValue(Constants.NB_ITEMS_IN_CART);
                    requireActivity().finishAffinity();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                });
            } else
                Utilities.showErrorPopup(getContext(), addItemData.getHeader().getMessage());
        }
    }

    private void deleteItem() {
        if (connectivity.isConnected()) {
            fragmentCartDetailsBinding.loader.setVisibility(View.VISIBLE);
            cartVM.deleteItem(preferenceManager.getValue(Constants.TOKEN, ""), preferenceManager.getValue(Constants.CART_ID, ""), deletedItemId);
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    @SuppressLint("SetTextI18n")
    private void handleDeleteItemFromCartData(DeleteItemData deleteItemData) {
        fragmentCartDetailsBinding.loader.setVisibility(View.GONE);
        if (deleteItemData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = deleteItemData.getHeader().getCode();
            if (code == 200 && deleteItemData.getResponse().getDeleted()) {
                preferenceManager.putValue(Constants.NB_ITEMS_IN_CART, preferenceManager.getValue(Constants.NB_ITEMS_IN_CART, 0) + itemsToAddInCart);
                fragmentCartDetailsBinding.price.setText(deleteItemData.getResponse().getPanier().getProductsPrice() + " MAD");
                fragmentCartDetailsBinding.fee.setText(deleteItemData.getResponse().getPanier().getFees() + " MAD");
                fragmentCartDetailsBinding.total.setText(deleteItemData.getResponse().getPanier().getTotalPrice() + " MAD");
                if (preferenceManager.getValue(Constants.NB_ITEMS_IN_CART, 0) <= 0)
                    fragmentCartDetailsBinding.nextBtn.setEnabled(false);
            } else if (code == 403) {
                Utilities.showErrorPopupWithClick(getContext(), deleteItemData.getHeader().getMessage(), view -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    preferenceManager.clearValue(Constants.CART_ID);
                    preferenceManager.clearValue(Constants.NB_ITEMS_IN_CART);
                    requireActivity().finishAffinity();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                });
            } else
                Utilities.showErrorPopup(getContext(), deleteItemData.getHeader().getMessage());
        }
    }

    private void enableSwipeToDelete() {

        new SwipeHelper(requireContext(), fragmentCartDetailsBinding.cartRecycler) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        ContextCompat.getDrawable(requireContext(), R.drawable.supp),
                        pos -> {
                            deletedItemId = items.get(pos).getItemId();
                            //itemsToAddInCart = -(Integer.parseInt(items.get(pos).getQty()) / items.get(pos).getStep());
                            itemsToAddInCart = -(Integer.parseInt(items.get(pos).getQty()));
                            Log.d("DELETE", "instantiateUnderlayButton: " + itemsToAddInCart);
                            cartAdapter.removeItem(pos);
                            deleteItem();
                        }
                ));
            }
        };
    }

    private void getAddress() {
        if (connectivity.isConnected()) {
            fragmentCartDetailsBinding.loader.setVisibility(View.VISIBLE);
            cartVM.getAddress(preferenceManager.getValue(Constants.TOKEN, null));
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleAddressData(AddressData addressData) {
        fragmentCartDetailsBinding.loader.setVisibility(View.GONE);
        if (addressData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = addressData.getHeader().getCode();
            if (code == 200) {
                ((CartActivity) requireActivity()).replaceFragment(StandardShippingFragment.newInstance(addressData.getResponse().getAddresses(), true));
            } else if (code == 403) {
                Utilities.showErrorPopupWithClick(getContext(), addressData.getHeader().getMessage(), view -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    preferenceManager.clearValue(Constants.CART_ID);
                    preferenceManager.clearValue(Constants.NB_ITEMS_IN_CART);
                    requireActivity().finishAffinity();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                });
            } else {
                Utilities.showErrorPopup(getContext(), addressData.getHeader().getMessage());
            }
        }
    }

    private void getRecapInfo() {
        if (connectivity.isConnected()) {
            fragmentCartDetailsBinding.loader.setVisibility(View.VISIBLE);
            cartVM.getRecapInfo("recap_discount");
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleRecapInfoData(RecapInfoData recapInfoData) {
        fragmentCartDetailsBinding.loader.setVisibility(View.GONE);
        if (recapInfoData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = recapInfoData.getHeader().getCode();
            if (code == 200) {
                Utilities.showInfoPopup(getContext(), "Information", recapInfoData.getResponse().getContent());
            } else if (code == 403) {
                Utilities.showErrorPopupWithClick(getContext(), recapInfoData.getHeader().getMessage(), view -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    preferenceManager.clearValue(Constants.CART_ID);
                    preferenceManager.clearValue(Constants.NB_ITEMS_IN_CART);
                    requireActivity().finishAffinity();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                });
            } else {
                Utilities.showErrorPopup(getContext(), recapInfoData.getHeader().getMessage());
            }
        }
    }

    public void deactivateUserInteraction() {
        requireActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void activateUserInteraction() {
        requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

    }
}