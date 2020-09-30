package com.mobiblanc.baridal_maghrib.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.baridal_maghrib.datamanager.retrofit.ApiUrls;
import com.mobiblanc.baridal_maghrib.datamanager.retrofit.RestService;
import com.mobiblanc.baridal_maghrib.models.dashboard.DashboardData;
import com.mobiblanc.baridal_maghrib.models.products.ProductsData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainVM extends AndroidViewModel {

    private MutableLiveData<DashboardData> dashboardLiveData;
    private MutableLiveData<ProductsData> productsLiveData;

    public MainVM(@NonNull Application application) {
        super(application);

        init();
    }

    public MutableLiveData<DashboardData> getDashboardLiveData() {
        return dashboardLiveData;
    }

    public MutableLiveData<ProductsData> getProductsLiveData() {
        return productsLiveData;
    }

    private void init() {
        dashboardLiveData = new MutableLiveData<>();
        productsLiveData = new MutableLiveData<>();
    }

    public void getDashboardDetails(String token) {
        Call<DashboardData> call;
        if (token != null)
            call = RestService.getInstance().endpoint().getDashboard(token);
        else
            call = RestService.getInstance().endpoint().getGuestDashboard(ApiUrls.AUTHORIZATION);

        call.enqueue(new Callback<DashboardData>() {
            @Override
            public void onResponse(Call<DashboardData> call, Response<DashboardData> response) {
                dashboardLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<DashboardData> call, Throwable t) {
                dashboardLiveData.setValue(null);
            }
        });
    }

    public void getProducts(String token, int id) {
        Call<ProductsData> call;
        if (token != null)
            call = RestService.getInstance().endpoint().getProductsList(token, id);
        else
            call = RestService.getInstance().endpoint().getGuestProductsList(ApiUrls.AUTHORIZATION, id);
        call.enqueue(new Callback<ProductsData>() {
            @Override
            public void onResponse(Call<ProductsData> call, Response<ProductsData> response) {
                productsLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ProductsData> call, Throwable t) {
                productsLiveData.setValue(null);
            }
        });
    }
}
