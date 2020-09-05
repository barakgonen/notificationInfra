package com.example.notifications;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFireBaseInstanceIdService extends FirebaseInstanceIdService {
    private final static String regToken = "REG_TOKEN";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String recentToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(regToken, recentToken);
    }
}
