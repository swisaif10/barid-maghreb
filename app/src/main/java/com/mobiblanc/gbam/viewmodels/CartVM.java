package com.mobiblanc.gbam.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.gbam.datamanager.retrofit.RestService;
import com.mobiblanc.gbam.models.payment.PaymentRecapData;
import com.mobiblanc.gbam.models.shipping.address.AddressData;
import com.mobiblanc.gbam.models.shipping.agencies.AgenciesData;
import com.mobiblanc.gbam.models.cart.add.AddItemData;
import com.mobiblanc.gbam.models.cart.delete.DeleteItemData;
import com.mobiblanc.gbam.models.cart.guest.GuestCartData;
import com.mobiblanc.gbam.models.cart.items.CartItemsData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.Header;

public class CartVM extends AndroidViewModel {

    private MutableLiveData<CartItemsData> cartItemsLiveData;
    private MutableLiveData<AddItemData> addItemLiveData;
    private MutableLiveData<AddItemData> updateItemLiveData;
    private MutableLiveData<DeleteItemData> deleteItemLiveData;
    private MutableLiveData<AgenciesData> agenciesLiveData;
    private MutableLiveData<AddressData> addressLiveData;
    private MutableLiveData<AddressData> addNewAddressLiveData;
    private MutableLiveData<PaymentRecapData> paymentRecapLiveData;

    public CartVM(@NonNull Application application) {
        super(application);

        init();
    }

    public MutableLiveData<CartItemsData> getCartItemsLiveData() {
        return cartItemsLiveData;
    }

    public MutableLiveData<AddItemData> getAddItemLiveData() {
        return addItemLiveData;
    }

    public MutableLiveData<AddItemData> getUpdateItemLiveData() {
        return updateItemLiveData;
    }

    public MutableLiveData<DeleteItemData> getDeleteItemLiveData() {
        return deleteItemLiveData;
    }

    public MutableLiveData<AgenciesData> getAgenciesLiveData() {
        return agenciesLiveData;
    }

    public MutableLiveData<PaymentRecapData> getPaymentRecapLiveData() {
        return paymentRecapLiveData;
    }

    public MutableLiveData<AddressData> getAddressLiveData() {
        return addressLiveData;
    }

    public MutableLiveData<AddressData> getAddNewAddressLiveData() {
        return addNewAddressLiveData;
    }

    private void init() {
        cartItemsLiveData = new MutableLiveData<>();
        addItemLiveData = new MutableLiveData<>();
        updateItemLiveData = new MutableLiveData<>();
        deleteItemLiveData = new MutableLiveData<>();
        agenciesLiveData = new MutableLiveData<>();
        addressLiveData = new MutableLiveData<>();
        paymentRecapLiveData = new MutableLiveData<>();
        addNewAddressLiveData = new MutableLiveData<>();
    }

    public void getCartItems(String token, String cartId) {
        Call<CartItemsData> call = RestService.getInstance().endpoint().getCartItems(token, cartId);
        call.enqueue(new Callback<CartItemsData>() {
            @Override
            public void onResponse(@NonNull Call<CartItemsData> call, @NonNull Response<CartItemsData> response) {
                cartItemsLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<CartItemsData> call, @NonNull Throwable t) {
                cartItemsLiveData.setValue(null);
            }
        });
    }

    public void addItem(String token, String cartId, String sku, int quantity) {
        Call<AddItemData> call = RestService.getInstance().endpoint().addItemToCart(token, cartId, sku, quantity);
        call.enqueue(new Callback<AddItemData>() {
            @Override
            public void onResponse(@NonNull Call<AddItemData> call, @NonNull Response<AddItemData> response) {
                addItemLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<AddItemData> call, @NonNull Throwable t) {
                addItemLiveData.setValue(null);
            }
        });
    }

    public void updateItem(String token, String cartId, int itemId, int quantity) {
        Call<AddItemData> call = RestService.getInstance().endpoint().updateItemQty(token, cartId, itemId, quantity);
        call.enqueue(new Callback<AddItemData>() {
            @Override
            public void onResponse(@NonNull Call<AddItemData> call, @NonNull Response<AddItemData> response) {
                updateItemLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<AddItemData> call, @NonNull Throwable t) {
                updateItemLiveData.setValue(null);
            }
        });
    }

    public void deleteItem(String token, String cartId, int itemId) {
        Call<DeleteItemData> call = RestService.getInstance().endpoint().deleteItem(token, cartId, itemId);
        call.enqueue(new Callback<DeleteItemData>() {
            @Override
            public void onResponse(@NonNull Call<DeleteItemData> call, @NonNull Response<DeleteItemData> response) {
                deleteItemLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<DeleteItemData> call, @NonNull Throwable t) {
                deleteItemLiveData.setValue(null);
            }
        });
    }

    public void getAgencies(String token) {
        Call<AgenciesData> call = RestService.getInstance().endpoint().getAgencies(token);
        call.enqueue(new Callback<AgenciesData>() {
            @Override
            public void onResponse(@NonNull Call<AgenciesData> call, @NonNull Response<AgenciesData> response) {
                agenciesLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<AgenciesData> call, @NonNull Throwable t) {
                agenciesLiveData.setValue(null);
            }
        });
    }

    public void getAddress(String token) {
        Call<AddressData> call = RestService.getInstance().endpoint().getAddress(token);
        call.enqueue(new Callback<AddressData>() {
            @Override
            public void onResponse(@NonNull Call<AddressData> call, @NonNull Response<AddressData> response) {
                addressLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<AddressData> call, @NonNull Throwable t) {
                addressLiveData.setValue(null);
            }
        });
    }

    public void getPaymentRecap(String token, String paymentMethod, int addressId, int agencyId) {
        Call<PaymentRecapData> call = RestService.getInstance().endpoint().getPaymentRecap(token, paymentMethod, addressId, agencyId);
        call.enqueue(new Callback<PaymentRecapData>() {
            @Override
            public void onResponse(@NonNull Call<PaymentRecapData> call, @NonNull Response<PaymentRecapData> response) {
                paymentRecapLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<PaymentRecapData> call, @NonNull Throwable t) {
                paymentRecapLiveData.setValue(null);
            }
        });
    }

    public void addNewAddress(String token, String addressName, String streetNumber, String complementAddress, String city, String postalCode, String phoneNumber, String ice, String taxIdentification, String cni, String type) {
        Call<AddressData> call = RestService.getInstance().endpoint().addAddress(token, addressName, streetNumber, complementAddress, city, postalCode, phoneNumber, ice, taxIdentification, cni, type);
        call.enqueue(new Callback<AddressData>() {
            @Override
            public void onResponse(@NonNull Call<AddressData> call, @NonNull Response<AddressData> response) {
                addNewAddressLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<AddressData> call, @NonNull Throwable t) {
                addNewAddressLiveData.setValue(null);
            }
        });
    }
}