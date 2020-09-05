package com.example.backhand;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("HELLO!!!");
        System.out.println("This main file should run the application's backhand. need to handle notification distribution to specific client. " +
                "it should use library which wraps notifications distribution for logic team to use as they want to");

        PushNotificationsSender.publishNotificationToSpecificUser("emulator", "Hello", "World!");
        PushNotificationsSender.publishNotificationToSpecificUser("barakg", "Hello", "World!");
        ArrayList<String> usersToNotify = new ArrayList<>();
        usersToNotify.add("barakg");
        usersToNotify.add("emulator");
        PushNotificationsSender.publishNotificationForGroupOfUsers(usersToNotify, "MULTY HsELLO", "HIsdI!");
    }
}
