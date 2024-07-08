package com.mygdx.game.model.network.email;

import java.util.UUID;

public class Registration {

    public static void registerNewUser(String email) {
        // Save the user details to your database
        String verificationToken = UUID.randomUUID().toString();

        // Send the verification email
        Sender.sendEmail(email, verificationToken);
    }
}