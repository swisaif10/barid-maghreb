package com.mobiblanc.baridal_maghrib.datamanager.retrofit;


import com.mobiblanc.baridal_maghrib.BuildConfig;

import okhttp3.Credentials;

public interface ApiUrls {

    String GET_DASHBOARD_URL = "api/dashboard/";
    String GET_PRODUCTS_URL = "api/categories/products";
    String CREATE_GUEST_CART_URL = "api/cart";
    String GET_CART_ITEMS_URL = "api/cart/items";
    String ADD_ITEM_TO_CART_URL = "api/cart/item";
    String DELETE_ITEM_FROM_CART_URL = "api/cart/item/delete";

    String AUTHORIZATION = Credentials.basic(BuildConfig.ID, BuildConfig.DOMAIN);

}
