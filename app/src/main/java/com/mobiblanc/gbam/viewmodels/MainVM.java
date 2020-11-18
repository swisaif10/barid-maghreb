package com.mobiblanc.gbam.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.gbam.datamanager.retrofit.ApiUrls;
import com.mobiblanc.gbam.datamanager.retrofit.RestService;
import com.mobiblanc.gbam.models.cart.guest.GuestCartData;
import com.mobiblanc.gbam.models.dashboard.DashboardData;
import com.mobiblanc.gbam.models.products.ProductsData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainVM extends AndroidViewModel {

    private MutableLiveData<DashboardData> dashboardLiveData;
    private MutableLiveData<GuestCartData> guestCartLiveData;
    private MutableLiveData<ProductsData> productsLiveData;

    public MainVM(@NonNull Application application) {
        super(application);

        init();
    }

    public MutableLiveData<DashboardData> getDashboardLiveData() {
        return dashboardLiveData;
    }

    public MutableLiveData<GuestCartData> getGuestCartLiveData() {
        return guestCartLiveData;
    }

    public MutableLiveData<ProductsData> getProductsLiveData() {
        return productsLiveData;
    }

    private void init() {
        dashboardLiveData = new MutableLiveData<>();
        guestCartLiveData = new MutableLiveData<>();
        productsLiveData = new MutableLiveData<>();
    }

    public void getDashboardDetails(String token) {
        Call<DashboardData> call = RestService.getInstance().endpoint().getDashboard(token);
        call.enqueue(new Callback<DashboardData>() {
            @Override
            public void onResponse(@NonNull Call<DashboardData> call, @NonNull Response<DashboardData> response) {
                dashboardLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<DashboardData> call, @NonNull Throwable t) {
                dashboardLiveData.setValue(null);
            }
        });
    }

    public void createCart() {
        Call<GuestCartData> call;
        call = RestService.getInstance().endpoint().createCart();
        call.enqueue(new Callback<GuestCartData>() {
            @Override
            public void onResponse(@NonNull Call<GuestCartData> call, @NonNull Response<GuestCartData> response) {
                guestCartLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<GuestCartData> call, @NonNull Throwable t) {
                guestCartLiveData.setValue(null);
            }
        });
    }

    public void getProducts(String token, int id) {
        Call<ProductsData> call = RestService.getInstance().endpoint().getProductsList(token, id);

        call.enqueue(new Callback<ProductsData>() {
            @Override
            public void onResponse(@NonNull Call<ProductsData> call, @NonNull Response<ProductsData> response) {
                productsLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ProductsData> call, @NonNull Throwable t) {
                productsLiveData.setValue(null);
            }
        });
    }
}
