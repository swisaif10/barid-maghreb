package com.mobiblanc.baridal_maghrib.datamanager.retrofit;

import com.mobiblanc.baridal_maghrib.models.cart.add.AddItemData;
import com.mobiblanc.baridal_maghrib.models.cart.delete.DeleteItemData;
import com.mobiblanc.baridal_maghrib.models.cart.guest.GuestCartData;
import com.mobiblanc.baridal_maghrib.models.cart.items.CartItemsData;
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

    @POST(ApiUrls.CREATE_GUEST_CART_URL)
    Call<GuestCartData> createGuestCart(@Header("Authorization") String credentials);

    @FormUrlEncoded
    @POST(ApiUrls.GET_CART_ITEMS_URL)
    Call<CartItemsData> getCartItems(@Header("Authorization") String credentials,
                                     @Field("quoteId") String idCart);

    @FormUrlEncoded
    @POST(ApiUrls.ADD_ITEM_TO_CART_URL)
    Call<AddItemData> addItemToCart(@Header("Authorization") String credentials,
                                    @Field("quoteId") String idCart,
                                    @Field("sku") String sku,
                                    @Field("qty") int quantity);

    @FormUrlEncoded
    @POST(ApiUrls.ADD_ITEM_TO_CART_URL)
    Call<AddItemData> updateItemQty(@Header("Authorization") String credentials,
                                    @Field("quoteId") String cartId,
                                    @Field("itemId") int itemId,
                                    @Field("qty") int quantity);

    @FormUrlEncoded
    @POST(ApiUrls.DELETE_ITEM_FROM_CART_URL)
    Call<DeleteItemData> deleteItem(@Header("Authorization") String credentials,
                                    @Field("quoteId") String cartId,
                                    @Field("itemId") int itemId);

}
