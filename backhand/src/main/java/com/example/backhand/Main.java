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
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void sendMulticast() throws FirebaseMessagingException {
        List<String> regestrationTokens = Arrays.asList("eMy2aBUoTa-X8mv5lRqGCc:APA91bGPSu1xtSVWUk3BlAJuhh32yvJo-_gQZQ-rEgA0BRizzzxp0aDh2m07plj5yzEBucP0NSLNOi9WLiwnGdvnNv2g18pAEgl29Lgi9i7VI9ABTfyHBPiiWsrT9Xbr6lEsSt5FWmm-",
                "fctr0cpmTj6udcsoci5AQe:APA91bFMrQMSp-6hQPDipxL8_BFav74W2vkQTRobhjURUYEujtrQlu07c1mzQRJmsjUYtDHDiout3pcv76Xl4LNrkMTq9vFlGoYutD0Ns12-5WzTnmvzFIBvjhUtHoPZ2w4vOKAhOJhA");
        MulticastMessage multicastMessage =
                MulticastMessage.builder().
                        setNotification(Notification.builder().setBody("U R the KKIINNGG").setTitle("HI KING").setTitle("BG?? ITS MULTICAST!!!").build())
                        .addAllTokens(regestrationTokens)
                        .build();

        // [START send_dry_run]
        // Send a message in the dry run mode.
        boolean dryRun = false;
        BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(multicastMessage);
        // Response is a message ID string.
        System.out.println("Dry run successful: " + response);
        // [END send_dry_run]
    }

    public static void sendDryRun() throws FirebaseMessagingException {
        Message message = Message.builder()
                .setNotification(Notification.builder().setBody("U R the KKIINNGG").setTitle("HI KING").setTitle("BG??").build())
                .setToken("fctr0cpmTj6udcsoci5AQe:APA91bFMrQMSp-6hQPDipxL8_BFav74W2vkQTRobhjURUYEujtrQlu07c1mzQRJmsjUYtDHDiout3pcv76Xl4LNrkMTq9vFlGoYutD0Ns12-5WzTnmvzFIBvjhUtHoPZ2w4vOKAhOJhA")
                .build();

        // [START send_dry_run]
        // Send a message in the dry run mode.
        boolean dryRun = false;
        String response = FirebaseMessaging.getInstance().send(message);
        // Response is a message ID string.
        System.out.println("Dry run successful: " + response);
        // [END send_dry_run]
    }

    public static void main(String[] args) {
        System.out.println("HELLO!!!");
        System.out.println("This main file should run the application's backhand. need to handle notification distribution to specific client. " +
                "it should use library which wraps notifications distribution for logic team to use as they want to");

        FileInputStream serviceAccount = null;
        try {
            serviceAccount = new FileInputStream("C:\\Users\\Barak\\Downloads\\notifications-e0e98-firebase-adminsdk-3hkmi-03a6ef1b71.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://notifications-e0e98.firebaseio.com")
                    .build();
            FirebaseApp.initializeApp(options);
//            sendDryRun();
            sendMulticast();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
