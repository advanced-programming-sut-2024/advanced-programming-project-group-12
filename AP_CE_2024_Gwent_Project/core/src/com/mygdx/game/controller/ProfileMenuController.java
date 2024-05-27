package com.mygdx.game.controller;

import com.mygdx.game.model.User;
import com.mygdx.game.view.screen.ValidInputs;

import java.util.ArrayList;

public class ProfileMenuController {
    public ArrayList<String> showProfile() {
        User loggedInUser = User.getLoggedInUser();
        return new ArrayList<String>() {{
            add(loggedInUser.getUsername() == null ? loggedInUser.getUsername() : "No username");
            add(loggedInUser.getNickname() == null ? loggedInUser.getNickname() : "No nickname");
            add(loggedInUser.getEmail() == null ? loggedInUser.getEmail() : "No email");
        }};
    }

    public boolean changePassword(String oldPassword, String newPassword, String newPasswordConfirmation) {
        User loggedInUser = User.getLoggedInUser();
        if (!oldPassword.equals(loggedInUser.getPassword())) {
            return false;
        }
        if (newPassword.equals(newPasswordConfirmation) && RegisterMenuController.isPasswordValid(newPassword, newPasswordConfirmation).equals("Valid password")) {
            loggedInUser.setPassword(newPassword);
            return true;
        } else {
            return false;
        }
    }

    public boolean changeUsername(String newUsername) {
        if (RegisterMenuController.isUsernameValid(newUsername) && !RegisterMenuController.isUsernameTaken(newUsername)) {
            User loggedInUser = User.getLoggedInUser();
            loggedInUser.setUsername(newUsername);
            return true;
        } else {
            return false;
        }
    }

    public boolean changeNickname(String newNickname) {
        User loggedInUser = User.getLoggedInUser();
        loggedInUser.setNickname(newNickname);
        return true;
    }

    public boolean changeEmail(String newEmail) {
        if (RegisterMenuController.isEmailValid(newEmail)) {
            User loggedInUser = User.getLoggedInUser();
            loggedInUser.setEmail(newEmail);
            return true;
        } else {
            return false;
        }
    }
}
