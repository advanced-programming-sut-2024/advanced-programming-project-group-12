package com.mygdx.game.controller;

import com.mygdx.game.model.User;

public class GameRequestController {
    public void sendGameRequest(String to, String from) {
    }

    public void requestTimedOut() {

    }
    public boolean userExists(String username) {
        // Replace with actual implementation
        return User.getUserByUsername(username) != null;
    }
}
