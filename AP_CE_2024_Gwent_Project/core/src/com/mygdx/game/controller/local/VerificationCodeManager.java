package com.mygdx.game.controller.local;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class VerificationCodeManager {
    private static final Map<String, String> verificationCodes = new HashMap<>();
    private static final Random random = new Random();

    public static String generateVerificationCode() {
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    public static void storeVerificationCode(String username, String code) {
        verificationCodes.put(username, code);
    }

    public static boolean verifyCode(String username, String code) {
        return code.equals(verificationCodes.get(username));
    }

    public static void removeVerificationCode(String username) {
        verificationCodes.remove(username);
    }
}
