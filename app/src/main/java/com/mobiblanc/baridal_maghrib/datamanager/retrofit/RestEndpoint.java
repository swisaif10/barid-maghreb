package com.mobiblanc.baridal_maghrib.datamanager.retrofit;

import com.mobiblanc.baridal_maghrib.models.dashboard.DashboardData;
import com.mobiblanc.baridal_maghrib.models.products.ProductsData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RestEndpoint {

    @POST(ApiUrls.GET_DASHBOARD_URL)
    Call<DashboardData> getDashboard(@Header("Authorization") String credentials);

    @FormUrlEncoded
    @POST(ApiUrls.GET_PRODUCTS_URL)
    Call<ProductsData> getProductsList(@Header("Authorization") String credentials,
                                       @Field("idCategory") int id);

}
