package com.mobiblanc.baridal_maghrib.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.baridal_maghrib.datamanager.retrofit.ApiUrls;
import com.mobiblanc.baridal_maghrib.datamanager.retrofit.RestService;
import com.mobiblanc.baridal_maghrib.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.baridal_maghrib.models.authentication.login.LoginData;
import com.mobiblanc.baridal_maghrib.models.authentication.registration.RegistrationData;
import com.mobiblanc.baridal_maghrib.utilities.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountVM extends AndroidViewModel {

    private MutableLiveData<RegistrationData> registrationLiveData;
    private MutableLiveData<LoginData> loginLiveData;

    public AccountVM(@NonNull Application application) {
        super(application);

        init();
    }

    public MutableLiveData<RegistrationData> getRegistrationLiveData() {
        return registrationLiveData;
    }

    public MutableLiveData<LoginData> getLoginLiveData() {
        return loginLiveData;
    }

    private void init() {
        registrationLiveData = new MutableLiveData<>();
        loginLiveData = new MutableLiveData<>();
    }

    public void register(String email, String firstName, String lastName,String phoneNumber) {
        Call<RegistrationData> call = RestService.getInstance().endpoint().registration(ApiUrls.AUTHORIZATION, email, firstName, lastName, phoneNumber);
        call.enqueue(new Callback<RegistrationData>() {
            @Override
            public void onResponse(Call<RegistrationData> call, Response<RegistrationData> response) {
                registrationLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<RegistrationData> call, Throwable t) {
                registrationLiveData.setValue(null);
            }
        });
    }

    public void login(String username, String password, String id, PreferenceManager preferenceManager) {
        Call<LoginData> call;
        if (id == null)
            call = RestService.getInstance().endpoint().login(ApiUrls.AUTHORIZATION, username, password);
        else
            call = RestService.getInstance().endpoint().loginWithCart(ApiUrls.AUTHORIZATION, username, password, id);

        call.enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                String token = response.raw().header("x-auth-token");
                preferenceManager.putValue(Constants.TOKEN, "Bearer " + token);
                loginLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {
                loginLiveData.setValue(null);
            }
        });
    }
}
