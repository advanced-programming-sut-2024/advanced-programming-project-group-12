package com.mygdx.game.controller.local;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.model.network.Client;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequestType;
import com.mygdx.game.model.network.massage.clientRequest.preSignInRequest.SecurityQuestionRequest;
import com.mygdx.game.model.network.massage.clientRequest.preSignInRequest.SignUpRequest;
import com.mygdx.game.model.user.SecurityQuestion;
import com.mygdx.game.model.user.User;
import com.mygdx.game.view.ValidInputs;

import java.util.Random;

public class RegisterMenuController {
    public static void register(String username, String nickname, String password, String email) {
    }

    public static boolean isUsernameValid(String username) {
        return ValidInputs.USERNAME.isMatch(username);
    }

    public static boolean isUsernameTaken(String username) {
        return User.getUserByUsername(username) != null;
    }

    public static String isPasswordValid(String password, String PasswordConfirmation) {
        if (!ValidInputs.LOWERCASE.isFind(password)) {
            return "Password must contain at least one lowercase letter";
        } else if (!ValidInputs.UPPERCASE.isFind(password)) {
            return "Password must contain at least one uppercase letter";
        } else if (!ValidInputs.NUMBER.isFind(password)) {
            return "Password must contain at least one number";
        } else if (!ValidInputs.SPECIAL_CHARACTER.isFind(password)) {
            return "Password must contain at least one special character";
        } else if (password.length() < 8) {
            return "Password must be at least 8 characters long";
        } else if (!password.equals(PasswordConfirmation)) {
            return "Passwords do not match";
        } else {
            return "Valid password";
        }
    }

    public static boolean isEmailValid(String email) {
        return ValidInputs.EMAIL.isMatch(email);
    }

    public static String randomPasswordGenerator() {
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        String specialCharacters = "!@#$%^&*()_+";
        String allCharacters = lowerCaseLetters + upperCaseLetters + numbers + specialCharacters;
        Random random = new Random();
        StringBuilder password = new StringBuilder();

        // Ensure password has at least one lowercase letter, one uppercase letter, one number, and one special character
        password.append(lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length())));
        password.append(upperCaseLetters.charAt(random.nextInt(upperCaseLetters.length())));
        password.append(numbers.charAt(random.nextInt(numbers.length())));
        password.append(specialCharacters.charAt(random.nextInt(specialCharacters.length())));

        // Fill the rest of the password with random characters
        for (int i = 4; i < 10; i++) {
            password.append(allCharacters.charAt(random.nextInt(allCharacters.length())));
        }

        return password.toString();
    }

    public static String generateNewUsername(String username) {
        Random random = new Random();
        int i = Math.abs(random.nextInt() % 100);
        String newUsername = username;
        while (User.getUserByUsername(newUsername) != null) {
            newUsername = username + String.valueOf(i);
            i = Math.abs(random.nextInt() % 100);
        }
        return newUsername;
    }

    public static int calculatePasswordStrength(String password) {
        if (password.length() < 4 || !ValidInputs.UPPERCASE.isFind(password)) {
            return 0; // weak
        } else if (password.length() <= 8 || !ValidInputs.NUMBER.isFind(password)) {
            return 1; // normal
        } else if (!ValidInputs.SPECIAL_CHARACTER.isFind(password)) {
            return 2; // good
        } else {
            return 3; // strong
        }
    }

    public static void updatePasswordStrength(String password, Label passwordStateLabel) {
        passwordStateLabel.setText("");
        if (calculatePasswordStrength(password) == 0) {
            passwordStateLabel.setText("Password is too weak");
            passwordStateLabel.setColor(Color.RED);
        } else if (calculatePasswordStrength(password) == 1) {
            passwordStateLabel.setText("Password is normal");
            passwordStateLabel.setColor(Color.YELLOW);
        } else if (calculatePasswordStrength(password) == 2) {
            passwordStateLabel.setText("Password is medium");
            passwordStateLabel.setColor(Color.ORANGE);
        } else if (calculatePasswordStrength(password) == 3) {
            passwordStateLabel.setText("Password is strong");
            passwordStateLabel.setColor(Color.GREEN);
        }
    }

    public static void setQuestionAndAnswerForUser(SecurityQuestion question, String answer) {
        Client.getInstance().sendMassage( new SecurityQuestionRequest(User.getToBeSignedUp(), question, answer));
    }

    public static void abortSignUp() {
        Client.getInstance().sendMassage( new SecurityQuestionRequest(User.getToBeSignedUp()));
    }


}
