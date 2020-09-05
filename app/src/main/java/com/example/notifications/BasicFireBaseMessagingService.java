package com.example.notifications;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.RemoteMessage;

import static android.content.ContentValues.TAG;

/**
 * Must create this class to register the service
 */
public class BasicFireBaseMessagingService extends RemoteMessageHandler {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG,"Message arrived!!!");
    }
}
