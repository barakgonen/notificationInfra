package com.example.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.HashMap;

import static androidx.core.content.ContextCompat.getSystemService;

public class NotificationsHandler {
    private HashMap<Integer, String> levelToMsg;
    private String channelId;
    private String channelName;
    private String channelDesc;
    private Context context;
    private NotificationChannel channel;

    public NotificationsHandler(HashMap<Integer, String> levelToMsg,
                                String channelId,
                                String channelName,
                                String channelDesc,
                                Context context) {
        this.levelToMsg = levelToMsg;
        this.channelId = channelId;
        this.channelName = channelName;
        this.channelDesc = channelDesc;
        this.context = context;
        System.out.println(NotificationManager.IMPORTANCE_DEFAULT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationManager manager = getSystemService(this.context, NotificationManager.class);
            this.channel = new NotificationChannel(this.channelId,
                    this.channelName,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(this.channelDesc);
            manager.createNotificationChannel(channel);
        }


    }

    public void displayNotification(int level) {
        if (levelToMsg.get(level) != null) {
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.drawable.alien)
                    .setContentTitle(levelToMsg.get(level))
                    .setContentText("BLABLA")
                    .setPriority(level);
            NotificationManagerCompat notificationManagerCompat =
                    NotificationManagerCompat.from(context);
            notificationManagerCompat.notify(1, notificationBuilder.build());
        }
    }
}
