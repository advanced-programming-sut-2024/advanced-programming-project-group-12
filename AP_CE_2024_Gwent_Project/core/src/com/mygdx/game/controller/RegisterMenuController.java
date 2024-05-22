package com.mygdx.game.controller;

import com.mygdx.game.model.SecurityQuestion;
import com.mygdx.game.model.User;
import com.mygdx.game.view.screen.ValidInputs;

import java.util.Random;

public class RegisterMenuController {
    public void register(String username, String password, String PasswordConfirmation, String email, SecurityQuestion question, String answer) {
        new User(username, password, email, question, answer);

    }

    public String isUsernameValid(String username) {
        if(!ValidInputs.USERNAME.isMatch(username)) {
            return "Username must contain only letters, numbers, and hyphens";
        } else if(username.length() < 3) {
            return "Username must be at least 3 characters long";
        } else if(User.getUserByUsername(username) != null) {
            return "Username already exists";
        } else {
            return "Valid username";
        }
    }

    public String isPasswordValid(String password, String PasswordConfirmation) {
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

    public boolean isEmailValid(String email) {
        return ValidInputs.EMAIL.isMatch(email);
    }

    public String randomPasswordGenerator() {
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
    public String generateNewUsername(String username) {
        int i = 1;
        String newUsername = username;
        while(User.getUserByUsername(newUsername) != null) {
            newUsername = username + i;
            i++;
        }
        return newUsername;
    }
}
