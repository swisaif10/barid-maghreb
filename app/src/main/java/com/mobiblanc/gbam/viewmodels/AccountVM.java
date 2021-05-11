package com.mobiblanc.gbam.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.gbam.datamanager.retrofit.RestService;
import com.mobiblanc.gbam.models.account.checkotp.CheckOTPData;
import com.mobiblanc.gbam.models.account.otp.OTPData;
import com.mobiblanc.gbam.models.account.profile.ProfileData;
import com.mobiblanc.gbam.models.contact.message.SendMessageData;
import com.mobiblanc.gbam.models.contact.objects.MessageObjectsData;
import com.mobiblanc.gbam.models.faqstamp.FAQStampData;
import com.mobiblanc.gbam.models.html.HtmlData;
import com.mobiblanc.gbam.models.orders.OrdersListData;
import com.mobiblanc.gbam.models.orders.details.OrderDetailsData;
import com.mobiblanc.gbam.models.webview.WebViewData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountVM extends AndroidViewModel {

    private MutableLiveData<OTPData> registrationLiveData;
    private MutableLiveData<CheckOTPData> confirmRegistrationLiveData;
    private MutableLiveData<OTPData> sendOTPLiveData;
    private MutableLiveData<CheckOTPData> loginLiveData;
    private MutableLiveData<ProfileData> profileLiveData;
    private MutableLiveData<ProfileData> updateProfileLiveData;
    private MutableLiveData<OTPData> logoutLiveData;
    private MutableLiveData<OTPData> contactLiveData;
    private MutableLiveData<HtmlData> cguLiveData;
    private MutableLiveData<WebViewData> pdfLiveData;
    private MutableLiveData<OrdersListData> ordersListLiveData;
    private MutableLiveData<OrderDetailsData> orderDetailsLiveData;
    private MutableLiveData<WebViewData> faqPortraitLiveData;
    private MutableLiveData<FAQStampData> faqStampLiveData;
    private MutableLiveData<MessageObjectsData> messageObjectsListLiveData;
    private MutableLiveData<SendMessageData> sendMessageLiveData;

    public AccountVM(@NonNull Application application) {
        super(application);

        init();
    }

    public MutableLiveData<OTPData> getRegistrationLiveData() {
        return registrationLiveData;
    }

    public MutableLiveData<CheckOTPData> getConfirmRegistrationLiveData() {
        return confirmRegistrationLiveData;
    }

    public MutableLiveData<OTPData> getSendOTPLiveData() {
        return sendOTPLiveData;
    }

    public MutableLiveData<CheckOTPData> getLoginLiveData() {
        return loginLiveData;
    }

    public MutableLiveData<ProfileData> getUpdateProfileLiveData() {
        return updateProfileLiveData;
    }

    public MutableLiveData<ProfileData> getProfileLiveData() {
        return profileLiveData;
    }

    public MutableLiveData<OTPData> getLogoutLiveData() {
        return logoutLiveData;
    }

    public MutableLiveData<OTPData> getContactLiveData() {
        return contactLiveData;
    }

    public MutableLiveData<HtmlData> getCguLiveData() {
        return cguLiveData;
    }

    public MutableLiveData<WebViewData> getPdfLiveData() {
        return pdfLiveData;
    }

    public MutableLiveData<OrdersListData> getOrdersListLiveData() {
        return ordersListLiveData;
    }

    public MutableLiveData<OrderDetailsData> getOrderDetailsLiveData() {
        return orderDetailsLiveData;
    }

    public MutableLiveData<WebViewData> getFaqPortraitLiveData() {
        return faqPortraitLiveData;
    }

    public MutableLiveData<FAQStampData> getFaqStampLiveData() {
        return faqStampLiveData;
    }

    public MutableLiveData<MessageObjectsData> getMessageObjectsListLiveData() {
        return messageObjectsListLiveData;
    }

    public MutableLiveData<SendMessageData> getSendMessageLiveData() {
        return sendMessageLiveData;
    }

    private void init() {
        registrationLiveData = new MutableLiveData<>();
        confirmRegistrationLiveData = new MutableLiveData<>();
        sendOTPLiveData = new MutableLiveData<>();
        loginLiveData = new MutableLiveData<>();
        profileLiveData = new MutableLiveData<>();
        updateProfileLiveData = new MutableLiveData<>();
        logoutLiveData = new MutableLiveData<>();
        contactLiveData = new MutableLiveData<>();
        cguLiveData = new MutableLiveData<>();
        pdfLiveData = new MutableLiveData<>();
        ordersListLiveData = new MutableLiveData<>();
        orderDetailsLiveData = new MutableLiveData<>();
        faqPortraitLiveData = new MutableLiveData<>();
        faqStampLiveData = new MutableLiveData<>();
        messageObjectsListLiveData = new MutableLiveData<>();
        sendMessageLiveData = new MutableLiveData<>();
    }

    public void register(String email, String firstName, String lastName, String phoneNumber) {
        Call<OTPData> call = RestService.getInstance().endpoint().registration(email, firstName, lastName, phoneNumber);
        call.enqueue(new Callback<OTPData>() {
            @Override
            public void onResponse(@NonNull Call<OTPData> call, @NonNull Response<OTPData> response) {
                registrationLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<OTPData> call, @NonNull Throwable t) {
                registrationLiveData.setValue(null);
            }
        });
    }

    public void confirmRegistration(String phoneNumber, String otp, String quoteId) {
        Call<CheckOTPData> call = RestService.getInstance().endpoint().confirmRegistration(phoneNumber, otp, quoteId);
        call.enqueue(new Callback<CheckOTPData>() {
            @Override
            public void onResponse(@NonNull Call<CheckOTPData> call, @NonNull Response<CheckOTPData> response) {
                confirmRegistrationLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<CheckOTPData> call, @NonNull Throwable t) {
                confirmRegistrationLiveData.setValue(null);
            }
        });
    }

    public void sendOtp(String phoneNumber, String uuid) {
        Call<OTPData> call = RestService.getInstance().endpoint().sendOTP(phoneNumber, uuid);
        call.enqueue(new Callback<OTPData>() {
            @Override
            public void onResponse(@NonNull Call<OTPData> call, @NonNull Response<OTPData> response) {
                sendOTPLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<OTPData> call, @NonNull Throwable t) {
                sendOTPLiveData.setValue(null);
            }
        });
    }

    public void login(String phoneNumber, String otp, String quoteId) {
        Call<CheckOTPData> call = RestService.getInstance().endpoint().login(phoneNumber, otp, quoteId);
        call.enqueue(new Callback<CheckOTPData>() {
            @Override
            public void onResponse(@NonNull Call<CheckOTPData> call, @NonNull Response<CheckOTPData> response) {
                assert response.body() != null;
                if (response.body().getHeader().getCode() == 200) {
                    String token = response.raw().header("x-auth-token");
                    response.body().getResponse().setToken("Bearer " + token);
                }
                loginLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<CheckOTPData> call, @NonNull Throwable t) {
                loginLiveData.setValue(null);
            }
        });
    }

    public void getProfile(String token) {
        Call<ProfileData> call = RestService.getInstance().endpoint().getProfile(token);
        call.enqueue(new Callback<ProfileData>() {
            @Override
            public void onResponse(@NonNull Call<ProfileData> call, @NonNull Response<ProfileData> response) {
                profileLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ProfileData> call, @NonNull Throwable t) {
                profileLiveData.setValue(null);
            }
        });
    }

    public void updateProfile(String token, String email, String firstName, String lastName) {
        Call<ProfileData> call = RestService.getInstance().endpoint().updateProfile(token, email, firstName, lastName);
        call.enqueue(new Callback<ProfileData>() {
            @Override
            public void onResponse(@NonNull Call<ProfileData> call, @NonNull Response<ProfileData> response) {
                updateProfileLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ProfileData> call, @NonNull Throwable t) {
                updateProfileLiveData.setValue(null);
            }
        });
    }

    public void logout(String token) {
        Call<OTPData> call = RestService.getInstance().endpoint().logout(token);
        call.enqueue(new Callback<OTPData>() {
            @Override
            public void onResponse(@NonNull Call<OTPData> call, @NonNull Response<OTPData> response) {
                logoutLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<OTPData> call, @NonNull Throwable t) {
                logoutLiveData.setValue(null);
            }
        });
    }

    public void contact(String token, String name, String email, String phoneNumber, String objet, String message) {
        Call<OTPData> call = RestService.getInstance().endpoint().contact(token, name, email, phoneNumber, objet, message);
        call.enqueue(new Callback<OTPData>() {
            @Override
            public void onResponse(@NonNull Call<OTPData> call, @NonNull Response<OTPData> response) {
                contactLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<OTPData> call, @NonNull Throwable t) {
                contactLiveData.setValue(null);
            }
        });
    }

    public void getCGU() {
        Call<HtmlData> call = RestService.getInstance().endpoint().getCGU();
        call.enqueue(new Callback<HtmlData>() {
            @Override
            public void onResponse(@NonNull Call<HtmlData> call, @NonNull Response<HtmlData> response) {
                cguLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<HtmlData> call, @NonNull Throwable t) {
                cguLiveData.setValue(null);
            }
        });
    }

    public void getPDF() {
        Call<WebViewData> call = RestService.getInstance().endpoint().getPDF();
        call.enqueue(new Callback<WebViewData>() {
            @Override
            public void onResponse(@NonNull Call<WebViewData> call, @NonNull Response<WebViewData> response) {
                pdfLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<WebViewData> call, @NonNull Throwable t) {
                pdfLiveData.setValue(null);
            }
        });
    }

    public void getOrders(String token) {
        Call<OrdersListData> call = RestService.getInstance().endpoint().getOrdersList(token);
        call.enqueue(new Callback<OrdersListData>() {
            @Override
            public void onResponse(@NonNull Call<OrdersListData> call, @NonNull Response<OrdersListData> response) {
                ordersListLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<OrdersListData> call, @NonNull Throwable t) {
                ordersListLiveData.setValue(null);
            }
        });
    }

    public void getOrderDetails(String token, String id) {
        Call<OrderDetailsData> call = RestService.getInstance().endpoint().getOrderDetails(token, id);
        call.enqueue(new Callback<OrderDetailsData>() {
            @Override
            public void onResponse(@NonNull Call<OrderDetailsData> call, @NonNull Response<OrderDetailsData> response) {
                orderDetailsLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<OrderDetailsData> call, @NonNull Throwable t) {
                orderDetailsLiveData.setValue(null);
            }
        });
    }

    public void getFAQPortrait() {
        Call<WebViewData> call = RestService.getInstance().endpoint().getFAQPortrait();
        call.enqueue(new Callback<WebViewData>() {
            @Override
            public void onResponse(@NonNull Call<WebViewData> call, @NonNull Response<WebViewData> response) {
                faqPortraitLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<WebViewData> call, @NonNull Throwable t) {
                faqPortraitLiveData.setValue(null);
            }
        });
    }

    public void getFAQStamp() {
        Call<FAQStampData> call = RestService.getInstance().endpoint().getFAQStamp();
        call.enqueue(new Callback<FAQStampData>() {
            @Override
            public void onResponse(@NonNull Call<FAQStampData> call, @NonNull Response<FAQStampData> response) {
                faqStampLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<FAQStampData> call, @NonNull Throwable t) {
                faqStampLiveData.setValue(null);
            }
        });
    }

    public void getMessageObjects() {
        Call<MessageObjectsData> call = RestService.getInstance().endpoint().getMessageObjects();
        call.enqueue(new Callback<MessageObjectsData>() {
            @Override
            public void onResponse(@NonNull Call<MessageObjectsData> call, @NonNull Response<MessageObjectsData> response) {
                messageObjectsListLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<MessageObjectsData> call, @NonNull Throwable t) {
                messageObjectsListLiveData.setValue(null);
            }
        });
    }

    public void sendContactMessage(String token, String object, String subject, String orderNumber) {
        Call<SendMessageData> call = RestService.getInstance().endpoint().sendContactMessage(token, object, subject, orderNumber);
        call.enqueue(new Callback<SendMessageData>() {
            @Override
            public void onResponse(@NonNull Call<SendMessageData> call, @NonNull Response<SendMessageData> response) {
                sendMessageLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<SendMessageData> call, @NonNull Throwable t) {
                sendMessageLiveData.setValue(null);
            }
        });
    }
}
