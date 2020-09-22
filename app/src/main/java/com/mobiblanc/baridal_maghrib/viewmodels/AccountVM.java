package com.mobiblanc.baridal_maghrib.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.baridal_maghrib.datamanager.retrofit.ApiUrls;
import com.mobiblanc.baridal_maghrib.datamanager.retrofit.RestService;
import com.mobiblanc.baridal_maghrib.models.registration.RegistrationData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountVM extends AndroidViewModel {

    private MutableLiveData<RegistrationData> registrationLiveData;

    public AccountVM(@NonNull Application application) {
        super(application);

        init();
    }

    public MutableLiveData<RegistrationData> getRegistrationLiveData() {
        return registrationLiveData;
    }

    private void init() {
        registrationLiveData = new MutableLiveData<>();
    }

    public void registration(String email, String firstname, String lastname, String password, String number, String raison, String address, String comment) {
        Call<RegistrationData> call = RestService.getInstance().endpoint().registration(ApiUrls.AUTHORIZATION, email, firstname, lastname, password, number, raison, address, comment);
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
}
