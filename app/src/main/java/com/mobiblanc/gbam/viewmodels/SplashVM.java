package com.mobiblanc.gbam.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.gbam.datamanager.retrofit.RestService;
import com.mobiblanc.gbam.models.controlversion.ControlVersionData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashVM extends AndroidViewModel {

    private MutableLiveData<ControlVersionData> controlVersionLiveData;

    public SplashVM(@NonNull Application application) {
        super(application);

        init();
    }

    public MutableLiveData<ControlVersionData> getControlVersionLiveData() {
        return controlVersionLiveData;
    }

    private void init() {
        controlVersionLiveData = new MutableLiveData<>();
    }

    public void controlVersion(String firebaseToken) {
        Call<ControlVersionData> call = RestService.getInstance().endpoint().controlVersion(firebaseToken);

        call.enqueue(new Callback<ControlVersionData>() {
            @Override
            public void onResponse(Call<ControlVersionData> call, Response<ControlVersionData> response) {
                controlVersionLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ControlVersionData> call, Throwable t) {
                controlVersionLiveData.setValue(null);
            }
        });
    }
}