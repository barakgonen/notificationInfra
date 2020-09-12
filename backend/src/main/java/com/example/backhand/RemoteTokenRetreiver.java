package com.example.backhand;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class RemoteTokenRetreiver {

    private static FileInputStream serviceAccount;
    private static boolean isInitializedSuccessfully;
    private static FirebaseDatabase database;
    private static DatabaseReference myRef;
    private static boolean stopApp;

    public RemoteTokenRetreiver() {
        serviceAccount = null;
        isInitializedSuccessfully = false;
        stopApp = false;
    }

    private void initialize() {
        try {
            serviceAccount = new FileInputStream("C:\\Users\\Barak\\Downloads\\notifications-e0e98-firebase-adminsdk-3hkmi-03a6ef1b71.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://notifications-e0e98.firebaseio.com")
                    .build();
            FirebaseApp.initializeApp(options);
            isInitializedSuccessfully = true;
             database = FirebaseDatabase.getInstance();
             myRef = database.getReference("/userNameToToken/");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getting user's token from fire-base DB
     * @param userName user name
     * @return access token
     */
    public String getTokenForUser(String userName){
        if (!isInitializedSuccessfully && serviceAccount == null) {
            initialize();
        }

        DatabaseReference s = database.getReference("/userNameToToken/").getDatabase().getReference();
        DatabaseReference ss = myRef.child(userName.toString());
        String n = ss.getDatabase().getApp().getName();
        return "";
    }

    public boolean shouldStopApp(){
        return stopApp;
    }
}
