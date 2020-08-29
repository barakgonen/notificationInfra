package com.example.notifications;

import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    NotificationsHandler notificationsHandler;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HashMap<Integer, String> levelToMsg = new HashMap<>();
        levelToMsg.put(NotificationManager.IMPORTANCE_MIN, "MIN-IMPORTANCE");
        levelToMsg.put(NotificationManager.IMPORTANCE_NONE, "NONE-IMPORTANCE");
        levelToMsg.put(NotificationManager.IMPORTANCE_DEFAULT, "DEFAULT-IMPORTANCE");
        levelToMsg.put(NotificationManager.IMPORTANCE_HIGH, "HIGH-IMPORTANCE");
        levelToMsg.put(NotificationManager.IMPORTANCE_MAX, "MAX-IMPORTANCE");


        notificationsHandler = new NotificationsHandler(levelToMsg,
                                               "myChannelID",
                                            "myChannelName",
                                             "channelDescription",
                                                 this);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayNotification();
            }
        });
    }

    private void displayNotification() {
        Random rnd = new Random();
        notificationsHandler.displayNotification(rnd.nextInt(NotificationManager.IMPORTANCE_MAX));
    }
}