package com.mygdx.game.model.network.email;

import java.util.UUID;

public class Registration {

    public static void registerNewUser(String email) {
        // Generate verification token
        String verificationToken = UUID.randomUUID().toString();

        // Send the verification email
//        Sender.sendEmail(email, verificationToken);

        // Save the verificationToken in your database associated with the user
        // This can be done by your backend server
    }
}
