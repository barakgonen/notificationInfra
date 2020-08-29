package com.example.notifications;

import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    NotificationsHandler notificationsHandler;
    TextView txt;
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
        txt = findViewById(R.id.textViewToken);
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (task.isSuccessful()){
                    String token = task.getResult().getToken();
                    txt.setText("Token:" + token);
                } else{
                    txt.setText(task.getException().toString());
                }
            }
        });

    }

    private void displayNotification() {
        Random rnd = new Random();
        notificationsHandler.displayNotification(rnd.nextInt(NotificationManager.IMPORTANCE_MAX));
    }
}