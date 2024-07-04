package com.mygdx.game.controller.local;

import com.mygdx.game.model.user.User;

import java.util.ArrayList;

public class ProfileMenuController {
    public ArrayList<String> showProfile() {
        User loggedInUser = User.getLoggedInUser();
        return new ArrayList<String>() {{
            add(loggedInUser.getUsername());
            add(loggedInUser.getNickname());
            add(loggedInUser.getEmail());
            add(loggedInUser.getFaction().getName());
        }};
    }

    public boolean changePassword(String oldPassword, String newPassword, String newPasswordConfirmation) {
        User loggedInUser = User.getLoggedInUser();
        if (!oldPassword.equals(loggedInUser.getPassword())) {
            return false;
        }
        if (newPassword.equals(newPasswordConfirmation) && RegisterMenuController.isPasswordValid(newPassword, newPasswordConfirmation).equals("Valid password")) {
            loggedInUser.setPassword(newPassword);
            loggedInUser.updateInfo();
            return true;
        } else {
            return false;
        }
    }

    public boolean changeUsername(String newUsername) {
        if (RegisterMenuController.isUsernameValid(newUsername) && !RegisterMenuController.isUsernameTaken(newUsername)) {
            User loggedInUser = User.getLoggedInUser();
            loggedInUser.setUsername(newUsername);
            loggedInUser.updateInfo();
            return true;
        } else {
            return false;
        }

    }

    public boolean changeNickname(String newNickname) {
        User loggedInUser = User.getLoggedInUser();
        loggedInUser.setNickname(newNickname);
        loggedInUser.updateInfo();
        return true;
    }

    public boolean changeEmail(String newEmail) {
        if (RegisterMenuController.isEmailValid(newEmail)) {
            User loggedInUser = User.getLoggedInUser();
            loggedInUser.setEmail(newEmail);
            loggedInUser.updateInfo();
            return true;
        } else {
            return false;
        }
    }

    public void logout() {
        User.setLoggedInUser(null);
    }

}