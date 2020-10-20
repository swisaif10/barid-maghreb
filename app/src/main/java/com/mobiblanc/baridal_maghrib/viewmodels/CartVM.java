package com.mobiblanc.baridal_maghrib.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.baridal_maghrib.datamanager.retrofit.ApiUrls;
import com.mobiblanc.baridal_maghrib.datamanager.retrofit.RestService;
import com.mobiblanc.baridal_maghrib.models.payment.PaymentRecapData;
import com.mobiblanc.baridal_maghrib.models.shipping.address.AddressData;
import com.mobiblanc.baridal_maghrib.models.shipping.agencies.AgenciesData;
import com.mobiblanc.baridal_maghrib.models.cart.add.AddItemData;
import com.mobiblanc.baridal_maghrib.models.cart.delete.DeleteItemData;
import com.mobiblanc.baridal_maghrib.models.cart.guest.GuestCartData;
import com.mobiblanc.baridal_maghrib.models.cart.items.CartItemsData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartVM extends AndroidViewModel {

    private MutableLiveData<GuestCartData> guestCartLiveData;
    private MutableLiveData<CartItemsData> cartItemsLiveData;
    private MutableLiveData<AddItemData> addItemLiveData;
    private MutableLiveData<AddItemData> updateItemLiveData;
    private MutableLiveData<DeleteItemData> deleteItemLiveData;
    private MutableLiveData<AgenciesData> agenciesLiveData;
    private MutableLiveData<AddressData> addressLiveData;
    private MutableLiveData<PaymentRecapData> paymentRecapLiveData;

    public CartVM(@NonNull Application application) {
        super(application);

        init();
    }

    public MutableLiveData<GuestCartData> getGuestCartLiveData() {
        return guestCartLiveData;
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

    private void init() {
        guestCartLiveData = new MutableLiveData<>();
        cartItemsLiveData = new MutableLiveData<>();
        addItemLiveData = new MutableLiveData<>();
        updateItemLiveData = new MutableLiveData<>();
        deleteItemLiveData = new MutableLiveData<>();
        agenciesLiveData = new MutableLiveData<>();
        addressLiveData = new MutableLiveData<>();
        paymentRecapLiveData = new MutableLiveData<>();
    }

    public void createCart(String token) {
        Call<GuestCartData> call;
        if (token != null)
            call = RestService.getInstance().endpoint().createCart(token);
        else
            call = RestService.getInstance().endpoint().createGuestCart(ApiUrls.AUTHORIZATION);
        call.enqueue(new Callback<GuestCartData>() {
            @Override
            public void onResponse(Call<GuestCartData> call, Response<GuestCartData> response) {
                guestCartLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GuestCartData> call, Throwable t) {
                guestCartLiveData.setValue(null);
            }
        });
    }

    public void getCartItems(String token, String cartId) {
        Call<CartItemsData> call;
        if (token != null)
            call = RestService.getInstance().endpoint().getCartItems(token, cartId);
        else
            call = RestService.getInstance().endpoint().getGuestCartItems(ApiUrls.AUTHORIZATION, cartId);

        call.enqueue(new Callback<CartItemsData>() {
            @Override
            public void onResponse(Call<CartItemsData> call, Response<CartItemsData> response) {
                cartItemsLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CartItemsData> call, Throwable t) {
                cartItemsLiveData.setValue(null);
            }
        });
    }

    public void addItem(String token, String cartId, String sku, int quantity) {
        Call<AddItemData> call;
        if (token != null)
            call = RestService.getInstance().endpoint().addItemToCart(token, cartId, sku, quantity);
        else
            call = RestService.getInstance().endpoint().addItemToCartByGuest(ApiUrls.AUTHORIZATION, cartId, sku, quantity);
        call.enqueue(new Callback<AddItemData>() {
            @Override
            public void onResponse(Call<AddItemData> call, Response<AddItemData> response) {
                addItemLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<AddItemData> call, Throwable t) {
                addItemLiveData.setValue(null);
            }
        });
    }

    public void updateItem(String token, String cartId, int itemId, int quantity) {
        Call<AddItemData> call;
        if (token != null)
            call = RestService.getInstance().endpoint().updateItemQty(token, cartId, itemId, quantity);
        else
            call = RestService.getInstance().endpoint().updateItemQtyByGuest(ApiUrls.AUTHORIZATION, cartId, itemId, quantity);
        call.enqueue(new Callback<AddItemData>() {
            @Override
            public void onResponse(Call<AddItemData> call, Response<AddItemData> response) {
                updateItemLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<AddItemData> call, Throwable t) {
                updateItemLiveData.setValue(null);
            }
        });
    }

    public void deleteItem(String token, String cartId, int itemId) {
        Call<DeleteItemData> call;
        if (token != null)
            call = RestService.getInstance().endpoint().deleteItem(token, cartId, itemId);
        else
            call = RestService.getInstance().endpoint().deleteItemByGuest(ApiUrls.AUTHORIZATION, cartId, itemId);
        call.enqueue(new Callback<DeleteItemData>() {
            @Override
            public void onResponse(Call<DeleteItemData> call, Response<DeleteItemData> response) {
                deleteItemLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<DeleteItemData> call, Throwable t) {
                deleteItemLiveData.setValue(null);
            }
        });
    }

    public void getAgencies(String token) {
        Call<AgenciesData> call = RestService.getInstance().endpoint().getAgencies(token);
        call.enqueue(new Callback<AgenciesData>() {
            @Override
            public void onResponse(Call<AgenciesData> call, Response<AgenciesData> response) {
                agenciesLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<AgenciesData> call, Throwable t) {
                agenciesLiveData.setValue(null);
            }
        });
    }

    public void getAddress(String token) {
        Call<AddressData> call = RestService.getInstance().endpoint().getAddress(token);
        call.enqueue(new Callback<AddressData>() {
            @Override
            public void onResponse(Call<AddressData> call, Response<AddressData> response) {
                addressLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<AddressData> call, Throwable t) {
                addressLiveData.setValue(null);
            }
        });
    }

    public void getPaymentRecap(String token, String paymentMethod, int addressId, int agencyId) {
        Call<PaymentRecapData> call = RestService.getInstance().endpoint().getPaymentRecap(token,paymentMethod,addressId,agencyId);
        call.enqueue(new Callback<PaymentRecapData>() {
            @Override
            public void onResponse(Call<PaymentRecapData> call, Response<PaymentRecapData> response) {
                paymentRecapLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<PaymentRecapData> call, Throwable t) {
                paymentRecapLiveData.setValue(null);
            }
        });
    }

}
