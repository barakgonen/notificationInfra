package com.example.backhand;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class PushNotificationsSender {

    private static FileInputStream serviceAccount;
    private static boolean isInitializedSuccessfully;

    private PushNotificationsSender() {
        serviceAccount = null;
        isInitializedSuccessfully = false;
    }

    private static void initialize() {
        try {
            serviceAccount = new FileInputStream("C:\\Users\\Barak\\Downloads\\notifications-e0e98-firebase-adminsdk-3hkmi-03a6ef1b71.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://notifications-e0e98.firebaseio.com")
                    .build();
            FirebaseApp.initializeApp(options);
            isInitializedSuccessfully = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void publishNotificationToSpecificUser(String userNameToNotify,
                                                         String msgTitle,
                                                         String msgBody) {
        if (!isInitializedSuccessfully && serviceAccount == null) {
            initialize();
        }
        String token = UserNameToTokenConverter.getTokenForUser(userNameToNotify);
        Message message = Message.builder()
                .setNotification(Notification.builder()
                        .setTitle(msgTitle)
                        .setBody(msgBody)
                        .build())
                .setToken(token)
                .build();

        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }

        if (response != null)
            // Response is a message ID string.
            System.out.println("Dry run successful: " + response);
    }

    public static void publishNotificationForGroupOfUsers(Collection<String> usersToNotify,
                                                          String msgTitle,
                                                          String msgBody) {
        if (!isInitializedSuccessfully && serviceAccount == null) {
            initialize();
        }
        Collection<String> tokens = userNamesToTokens(usersToNotify);
        MulticastMessage multicastMessage = MulticastMessage.builder()
                .setNotification(Notification.builder()
                        .setTitle(msgTitle)
                        .setBody(msgBody)
                        .build()).addAllTokens(tokens)
                .build();

        BatchResponse response = null;
        try {
            response = FirebaseMessaging.getInstance().sendMulticast(multicastMessage);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
        if (response != null)
            // Response is a message ID string.
            System.out.println("Dry run successful: " + response);
    }

    private static Collection<String> userNamesToTokens(Collection<String> userNames) {
        ArrayList<String> tokens = new ArrayList<>();
        for (String userName : userNames)
            tokens.add(UserNameToTokenConverter.getTokenForUser(userName));
        return tokens;
    }
}
