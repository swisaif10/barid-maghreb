package com.mobiblanc.gbam.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseService";


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        //Log.d(TAG, "From: " + remoteMessage.getFrom());

    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d(TAG, "onNewToken: firebase token has been changed : " + s);

    }
}
