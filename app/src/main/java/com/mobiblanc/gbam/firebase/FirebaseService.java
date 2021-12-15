package com.mobiblanc.gbam.firebase;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mobiblanc.gbam.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.gbam.utilities.Constants;

public class FirebaseService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseService";
    private PreferenceManager preferenceManager;


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d(TAG, "onNewToken: firebase token has been changed : " + s);

        preferenceManager = new PreferenceManager.Builder(this, Context.MODE_PRIVATE)
                .name(Constants.SHARED_PREFS_NAME)
                .build();

        preferenceManager.putValue(Constants.FIREBASE_TOKEN, s);
    }
}
