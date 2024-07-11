package com.mygdx.game.model.network.email;

import java.util.concurrent.ConcurrentHashMap;

public class UserVerificationStore {
    private static ConcurrentHashMap<String, String> emailToToken = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Boolean> verifiedEmails = new ConcurrentHashMap<>();

    public static void addVerificationToken(String email, String token) {
        emailToToken.put(token, email);
        verifiedEmails.put(email, false);
    }

    public static String getEmailByToken(String token) {
        return emailToToken.get(token);
    }

    public static void markUserAsVerified(String email) {
        verifiedEmails.put(email, true);
    }

    public static boolean isUserVerified(String email) {
        return verifiedEmails.getOrDefault(email, false);
    }
}
