package com.mobiblanc.gbam.datamanager.retrofit;

import com.mobiblanc.gbam.models.html.HtmlData;
import com.mobiblanc.gbam.models.account.checkotp.CheckOTPData;
import com.mobiblanc.gbam.models.account.otp.OTPData;
import com.mobiblanc.gbam.models.account.profile.ProfileData;
import com.mobiblanc.gbam.models.cart.add.AddItemData;
import com.mobiblanc.gbam.models.cart.delete.DeleteItemData;
import com.mobiblanc.gbam.models.cart.guest.GuestCartData;
import com.mobiblanc.gbam.models.cart.items.CartItemsData;
import com.mobiblanc.gbam.models.controlversion.ControlVersionData;
import com.mobiblanc.gbam.models.dashboard.DashboardData;
import com.mobiblanc.gbam.models.payment.PaymentRecapData;
import com.mobiblanc.gbam.models.products.ProductsData;
import com.mobiblanc.gbam.models.shipping.address.AddressData;
import com.mobiblanc.gbam.models.shipping.agencies.AgenciesData;
import com.mobiblanc.gbam.models.tracking.TrackingData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RestEndpoint {

    @POST(ApiUrls.CONTROL_VERSION_URL)
    Call<ControlVersionData> controlVersion();

    @FormUrlEncoded
    @POST(ApiUrls.REGISTRATION_URL)
    Call<OTPData> registration(@Field("email") String email,
                               @Field("firstname") String firstName,
                               @Field("lastname") String lastName,
                               @Field("phoneNumber") String phoneNumber);

    @FormUrlEncoded
    @POST(ApiUrls.CONFIRM_REGISTRATION_URL)
    Call<CheckOTPData> confirmRegistration(@Field("phoneNumber") String phoneNumber,
                                           @Field("otp") String otp,
                                           @Field("quoteId") String quoteId);

    @FormUrlEncoded
    @POST(ApiUrls.SEND_OTP_URL)
    Call<OTPData> sendOTP(@Field("phoneNumber") String phoneNumber,
                          @Field("uuid") String uuid);

    @FormUrlEncoded
    @POST(ApiUrls.LOGIN_URL)
    Call<CheckOTPData> login(@Field("phoneNumber") String phoneNumber,
                             @Field("otp") String otp,
                             @Field("quoteId") String quoteId);

    @POST(ApiUrls.GET_DASHBOARD_URL)
    Call<DashboardData> getDashboard(@Header("x-auth-token") String token);

    @FormUrlEncoded
    @POST(ApiUrls.GET_PRODUCTS_URL)
    Call<ProductsData> getProductsList(@Header("x-auth-token") String token,
                                       @Field("idCategory") int id);

    @POST(ApiUrls.CREATE_CART_URL)
    Call<GuestCartData> createCart();

    @FormUrlEncoded
    @POST(ApiUrls.GET_CART_ITEMS_URL)
    Call<CartItemsData> getCartItems(@Header("x-auth-token") String token,
                                     @Field("quoteId") String idCart);

    @FormUrlEncoded
    @POST(ApiUrls.ADD_ITEM_TO_CART_URL)
    Call<AddItemData> addItemToCart(@Header("x-auth-token") String token,
                                    @Field("quoteId") String idCart,
                                    @Field("sku") String sku,
                                    @Field("qty") int quantity);

    @FormUrlEncoded
    @POST(ApiUrls.ADD_ITEM_TO_CART_URL)
    Call<AddItemData> updateItemQty(@Header("x-auth-token") String token,
                                    @Field("quoteId") String cartId,
                                    @Field("itemId") int itemId,
                                    @Field("qty") int quantity);

    @FormUrlEncoded
    @POST(ApiUrls.DELETE_ITEM_FROM_CART_URL)
    Call<DeleteItemData> deleteItem(@Header("x-auth-token") String token,
                                    @Field("quoteId") String cartId,
                                    @Field("itemId") int itemId);

    @POST(ApiUrls.GET_PROFILE_URL)
    Call<ProfileData> getProfile(@Header("x-auth-token") String token);

    @FormUrlEncoded
    @POST(ApiUrls.UPDATE_PROFILE_URL)
    Call<ProfileData> updateProfile(@Header("x-auth-token") String token,
                                    @Field("email") String email,
                                    @Field("firstname") String firstName,
                                    @Field("lastname") String lastName);

    @POST(ApiUrls.LOGOUT_URL)
    Call<OTPData> logout(@Header("x-auth-token") String token);

    @FormUrlEncoded
    @POST(ApiUrls.CONTACT_URL)
    Call<OTPData> contact(@Header("x-auth-token") String token,
                          @Field("name") String name,
                          @Field("email") String email,
                          @Field("phoneNumber") String phoneNumber,
                          @Field("objet") String objet,
                          @Field("message") String message);

    //not yet
    @POST(ApiUrls.GET_AGENCIES_URL)
    Call<AgenciesData> getAgencies(@Header("x-auth-token") String token);

    @POST(ApiUrls.GET_ADDRESSES_URL)
    Call<AddressData> getAddress(@Header("x-auth-token") String token);

    @FormUrlEncoded
    @POST(ApiUrls.ADD_ADDRESS_URL)
    Call<AddressData> addAddress(@Header("x-auth-token") String token,
                                 @Field("address_name") String addressName,
                                 @Field("street_number") String streetNumber,
                                 @Field("complement_address") String complementAddress,
                                 @Field("city") String city,
                                 @Field("postcode") String postCode,
                                 @Field("phone_number") String phoneNumber,
                                 @Field("ice") String ice,
                                 @Field("tax_identification") String taxIdentification,
                                 @Field("cni") String cni,
                                 @Field("type") String type);

    @FormUrlEncoded
    @POST(ApiUrls.GET_RECAP_URL)
    Call<PaymentRecapData> getPaymentRecap(@Header("x-auth-token") String token,
                                           @Field("shipping_methode") String shippingMethod,
                                           @Field("address_id") int addressId,
                                           @Field("agence_id") int agencyId);

    @FormUrlEncoded
    @POST(ApiUrls.TRACKING_COMMAND_URL)
    Call<TrackingData> trackCommand(@Header("x-auth-token") String token,
                                    @Field("tracking_command_id ") String tracking_command_id);

    @POST(ApiUrls.CGU_URL)
    Call<HtmlData> getCGU();

    @POST(ApiUrls.DASHBOARD_DESCRIPTION_URL)
    Call<HtmlData> getDashboardDescription();



    /*@POST(ApiUrls.GET_DASHBOARD_URL)
    Call<DashboardData> getGuestDashboard(@Header("Authorization") String credentials);

    @FormUrlEncoded
    @POST(ApiUrls.GET_PRODUCTS_URL)
    Call<ProductsData> getGuestProductsList(@Header("Authorization") String credentials,
                                            @Field("idCategory") int id);

    @FormUrlEncoded
    @POST(ApiUrls.ADD_ITEM_TO_CART_URL)
    Call<AddItemData> addItemToCartByGuest(@Header("Authorization") String credentials,
                                           @Field("quoteId") String idCart,
                                           @Field("sku") String sku,
                                           @Field("qty") int quantity);

    @FormUrlEncoded
    @POST(ApiUrls.GET_CART_ITEMS_URL)
    Call<CartItemsData> getGuestCartItems(@Header("Authorization") String credentials,
                                          @Field("quoteId") String idCart);

    @FormUrlEncoded
    @POST(ApiUrls.ADD_ITEM_TO_CART_URL)
    Call<AddItemData> updateItemQtyByGuest(@Header("Authorization") String credentials,
                                           @Field("quoteId") String cartId,
                                           @Field("itemId") int itemId,
                                           @Field("qty") int quantity);

    @FormUrlEncoded
    @POST(ApiUrls.DELETE_ITEM_FROM_CART_URL)
    Call<DeleteItemData> deleteItemByGuest(@Header("Authorization") String credentials,
                                           @Field("quoteId") String cartId,
                                           @Field("itemId") int itemId);*/

}
