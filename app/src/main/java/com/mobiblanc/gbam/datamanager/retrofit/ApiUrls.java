package com.mobiblanc.gbam.datamanager.retrofit;


import com.mobiblanc.gbam.BuildConfig;

import okhttp3.Credentials;

public interface ApiUrls {
    String CONTROL_VERSION_URL = "api/version-control/check";
    String GET_DASHBOARD_URL = "api/dashboard/";
    String GET_PRODUCTS_URL = "api/categories/products";
    String CREATE_CART_URL = "api/cart";
    String GET_CART_ITEMS_URL = "api/cart/items";
    String ADD_ITEM_TO_CART_URL = "api/cart/item";
    String DELETE_ITEM_FROM_CART_URL = "api/cart/item/delete";
    String REGISTRATION_URL = "api/account/registration";
    String CONFIRM_REGISTRATION_URL = "api/account/registration/confirm";
    String SEND_OTP_URL = "api/account/send-otp";
    String LOGIN_URL = "api/account/login";
    String GET_PROFILE_URL = "api/account/infos";
    String UPDATE_PROFILE_URL = "api/account/edit";
    String LOGOUT_URL = "api/account/logout";
    String GET_AGENCIES_URL = "api/shipping/agencies";
    String GET_ADDRESSES_URL = "api/shipping/addresses";
    String ADD_ADDRESS_URL = "/api/shipping/add-address";
    String GET_RECAP_URL = "api/shipping/get-recap";
    String TRACKING_COMMAND_URL = "api/shipping/tracking-command";
    String CONTACT_URL = "api/contact/message";
    String CGU_URL = "api/dashboard/cgv";
    String DASHBOARD_DESCRIPTION_URL = "api/dashboard/description";
    String PDF_URL = "api/infos/loi-0908";
    String PAY_URL = "api/payment/pre-order";
    String UPDATE_ADDRESS_URL = "api/shipping/edit-address";
    String PAYMENT_URL = "api/shipping/payment-url";
    String CITIES_URL = "api/shipping/cities";
    String GET_INFO_URL = "api/html-description/";
    String GET_ORDERS_LIST_URL = "api/account/get-orders";
    String GET_ORDER_DETAILS_URL = "api/account/get-detail-order";
    String GET_FAQ_PORTRAIT_URL = "api/contact/faq-portrait";
    String GET_FAQ_STAMP_URL = "api/contact/faq-timbre";
    String GET_FAQ_PORTRAIT_OFFICIEL = "api/contact/faq-portrait";
    //String GET_CONTACT_SUBJECTS_URL = "api/contact/get-subject";
    String GET_CONTACT_SUBJECTS_URL_AND_NUM = "api/contact/get-info-contact";
    String SEND_CONTACT_MESSAGE_URL = "api/contact/message";
    String GET_CITIES_AUTO_COMPLETE = "api/locality/cities";
    String GET_DISTRICTS_AUTO_COMPLETE = "api/locality/districts";
    String GET_WAY_AUTO_COMPLETE = "api/locality/way";
    String DELETE_ADDRESS = "/api/shipping/delete-address";
    String AUTHORIZATION = Credentials.basic(BuildConfig.ID, BuildConfig.DOMAIN);


}
