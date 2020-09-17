package com.mobiblanc.baridal_maghrib.datamanager.retrofit;


import com.mobiblanc.baridal_maghrib.BuildConfig;

import okhttp3.Credentials;

public interface ApiUrls {

    String GET_DASHBOARD_URL = "api/dashboard/";
    String GET_PRODUCTS_URL = "api/categories/products";

    String AUTHORIZATION = Credentials.basic(BuildConfig.ID, BuildConfig.DOMAIN);

}
