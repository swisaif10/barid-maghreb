package com.mobiblanc.baridal_maghrib.datamanager.retrofit;

import com.mobiblanc.baridal_maghrib.models.cart.add.AddItemData;
import com.mobiblanc.baridal_maghrib.models.cart.delete.DeleteItemData;
import com.mobiblanc.baridal_maghrib.models.cart.guest.GuestCartData;
import com.mobiblanc.baridal_maghrib.models.cart.items.CartItemsData;
import com.mobiblanc.baridal_maghrib.models.dashboard.DashboardData;
import com.mobiblanc.baridal_maghrib.models.login.LoginData;
import com.mobiblanc.baridal_maghrib.models.products.ProductsData;
import com.mobiblanc.baridal_maghrib.models.registration.RegistrationData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RestEndpoint {

    @POST(ApiUrls.GET_DASHBOARD_URL)
    Call<DashboardData> getGuestDashboard(@Header("Authorization") String credentials);

    @POST(ApiUrls.GET_DASHBOARD_URL)
    Call<DashboardData> getDashboard(@Header("x-auth-token") String token);

    @FormUrlEncoded
    @POST(ApiUrls.GET_PRODUCTS_URL)
    Call<ProductsData> getGuestProductsList(@Header("Authorization") String credentials,
                                       @Field("idCategory") int id);

    @FormUrlEncoded
    @POST(ApiUrls.GET_PRODUCTS_URL)
    Call<ProductsData> getProductsList(@Header("x-auth-token") String token,
                                       @Field("idCategory") int id);

    @POST(ApiUrls.CREATE_CART_URL)
    Call<GuestCartData> createGuestCart(@Header("Authorization") String credentials);

    @POST(ApiUrls.CREATE_CART_URL)
    Call<GuestCartData> createCart(@Header("x-auth-token") String token);

    @FormUrlEncoded
    @POST(ApiUrls.GET_CART_ITEMS_URL)
    Call<CartItemsData> getGuestCartItems(@Header("Authorization") String credentials,
                                     @Field("quoteId") String idCart);

    @FormUrlEncoded
    @POST(ApiUrls.GET_CART_ITEMS_URL)
    Call<CartItemsData> getCartItems(@Header("x-auth-token") String token,
                                     @Field("quoteId") String idCart);

    @FormUrlEncoded
    @POST(ApiUrls.ADD_ITEM_TO_CART_URL)
    Call<AddItemData> addItemToCartByGuest(@Header("Authorization") String credentials,
                                    @Field("quoteId") String idCart,
                                    @Field("sku") String sku,
                                    @Field("qty") int quantity);

    @FormUrlEncoded
    @POST(ApiUrls.ADD_ITEM_TO_CART_URL)
    Call<AddItemData> addItemToCart(@Header("x-auth-token") String token,
                                    @Field("quoteId") String idCart,
                                    @Field("sku") String sku,
                                    @Field("qty") int quantity);

    @FormUrlEncoded
    @POST(ApiUrls.ADD_ITEM_TO_CART_URL)
    Call<AddItemData> updateItemQtyByGuest(@Header("Authorization") String credentials,
                                    @Field("quoteId") String cartId,
                                    @Field("itemId") int itemId,
                                    @Field("qty") int quantity);

    @FormUrlEncoded
    @POST(ApiUrls.ADD_ITEM_TO_CART_URL)
    Call<AddItemData> updateItemQty(@Header("x-auth-token") String token,
                                    @Field("quoteId") String cartId,
                                    @Field("itemId") int itemId,
                                    @Field("qty") int quantity);

    @FormUrlEncoded
    @POST(ApiUrls.DELETE_ITEM_FROM_CART_URL)
    Call<DeleteItemData> deleteItemByGuest(@Header("Authorization") String credentials,
                                    @Field("quoteId") String cartId,
                                    @Field("itemId") int itemId);

    @FormUrlEncoded
    @POST(ApiUrls.DELETE_ITEM_FROM_CART_URL)
    Call<DeleteItemData> deleteItem(@Header("x-auth-token") String token,
                                    @Field("quoteId") String cartId,
                                    @Field("itemId") int itemId);

    @FormUrlEncoded
    @POST(ApiUrls.REGISTRATION_URL)
    Call<RegistrationData> registration(@Header("Authorization") String credentials,
                                        @Field("email") String email,
                                        @Field("firstname") String firstname,
                                        @Field("lastname") String lastname,
                                        @Field("password") String password,
                                        @Field("numContact") String numContact,
                                        @Field("raison") String raison,
                                        @Field("adresse") String adresse,
                                        @Field("commentaire") String commentaire);

    @FormUrlEncoded
    @POST(ApiUrls.LOGIN_URL)
    Call<LoginData> login(@Header("Authorization") String credentials,
                          @Field("username") String username,
                          @Field("password") String password);

    @FormUrlEncoded
    @POST(ApiUrls.LOGIN_URL)
    Call<LoginData> loginWithCart(@Header("Authorization") String credentials,
                                  @Field("username") String username,
                                  @Field("password") String password,
                                  @Field("quoteId") String id);

}
