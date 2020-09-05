package com.example.backhand;

import java.util.HashMap;

public class UserNameToTokenConverter {

    // Temp map for mapping between username to token SHOULD BE removed when DB connection will be implemented
    private static HashMap<String, String> userNameToToken;

    private UserNameToTokenConverter(){
        userNameToToken = new HashMap<>();
        userNameToToken.put("emulator", "fctr0cpmTj6udcsoci5AQe:APA91bFMrQMSp-6hQPDipxL8_BFav74W2vkQTRobhjURUYEujtrQlu07c1mzQRJmsjUYtDHDiout3pcv76Xl4LNrkMTq9vFlGoYutD0Ns12-5WzTnmvzFIBvjhUtHoPZ2w4vOKAhOJhA");
        userNameToToken.put("barakg", "fq7ZfA2-TSedmpouLoC1Cb:APA91bEq0lC7z9kzKxT7opggMRdr6Ea3QDZfNJ7em_emWD90A_xmoERcY74-7IYzZWuoW2QK2z_Dahc7gQEzGYu0RHe_BKdem3N9_4wU4MvquWHtaptb7_8KAAYhHG3-81LIcvCHP0Rv");
    }

    public static String getTokenForUser(String userName){
        if (userNameToToken == null)
            new UserNameToTokenConverter();

        // TODO: Yuval / Rayan
        return userNameToToken.get(userName);
    }
}
