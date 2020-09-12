package com.example.backhand;

import java.util.HashMap;

public class UserNameToTokenConverter {

    // Temp map for mapping between username to token SHOULD BE removed when DB connection will be implemented
    private static HashMap<String, String> userNameToToken;

    private UserNameToTokenConverter(){
        userNameToToken = new HashMap<>();
        userNameToToken.put("emulator", "fctr0cpmTj6udcsoci5AQe:APA91bFMrQMSp-6hQPDipxL8_BFav74W2vkQTRobhjURUYEujtrQlu07c1mzQRJmsjUYtDHDiout3pcv76Xl4LNrkMTq9vFlGoYutD0Ns12-5WzTnmvzFIBvjhUtHoPZ2w4vOKAhOJhA");
        userNameToToken.put("barakg", "eFfzG80uQCGYddtqRPUi5o:APA91bFpVqY3Ss98Aka3n2bI_NzP16UA2gV-fVRbcIMjnIXAQXgV1eZKXGjQmfXE86y9a6CBqC17kKKgrMHm6DeDywBHoHdP08TE1_dUDdqygEt4fQBnCx9om9u2xlYBlb2WRwdrc1j6");
    }

    public static String getTokenForUser(String userName){
        if (userNameToToken == null)
            new UserNameToTokenConverter();

        // TODO: Yuval / Rayan
        return userNameToToken.get(userName);
    }
}
