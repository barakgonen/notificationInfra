package com.example.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFireBaseMessagingService extends FirebaseMessagingService {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        // This code is activated when push notification received while application is running and user in it.. sees it's screen, uses it
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this);
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        noBuilder.setContentTitle("FCM NOTIFICATION");
        noBuilder.setContentText(notification.getBody());
        noBuilder.setAutoCancel(true);
        noBuilder.setSmallIcon(R.drawable.alien);
        noBuilder.setContentIntent(pendingIntent);
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemServiceName(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0, noBuilder.build());
    }

}
