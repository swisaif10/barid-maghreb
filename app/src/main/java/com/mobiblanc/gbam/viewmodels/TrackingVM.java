package com.mobiblanc.gbam.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.gbam.datamanager.retrofit.ApiUrls;
import com.mobiblanc.gbam.datamanager.retrofit.RestService;
import com.mobiblanc.gbam.models.tracking.TrackingData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackingVM extends AndroidViewModel {

    private MutableLiveData<TrackingData> TrackingLiveData;

    public TrackingVM(@NonNull Application application) {
        super(application);

        init();
    }

    public MutableLiveData<TrackingData> getTrackingLiveData() {
        return TrackingLiveData;
    }

    private void init() {
        TrackingLiveData = new MutableLiveData<>();
    }

    public void trackCommand(String id) {
        Call<TrackingData> call = RestService.getInstance().endpoint().trackCommand(ApiUrls.AUTHORIZATION, id);

        call.enqueue(new Callback<TrackingData>() {
            @Override
            public void onResponse(@NonNull Call<TrackingData> call, @NonNull Response<TrackingData> response) {
                TrackingLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TrackingData> call, @NonNull Throwable t) {
                TrackingLiveData.setValue(null);
            }
        });
    }
}
