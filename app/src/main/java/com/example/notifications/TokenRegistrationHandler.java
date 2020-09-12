package com.example.notifications;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * This class handles token registration in remote server, for example Firebase DB
 */
public class TokenRegistrationHandler {

    /**
     * Registration of token for specific username
     * @param userName user's name
     * @param token generated fire-base access token
     * @return indication weather registration has completed successfully
     */
    public static boolean registerToken(String userName, String token){
        // Writing to DB
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("/userNameToToken/" + userName);
        myRef.setValue(token);
        return true;
    }
}
