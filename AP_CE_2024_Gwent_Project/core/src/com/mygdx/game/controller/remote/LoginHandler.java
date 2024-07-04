package com.mygdx.game.controller.remote;

import com.google.gson.Gson;
import com.mygdx.game.controller.local.LoginMenuController;
import com.mygdx.game.model.network.RequestHandler;
import com.mygdx.game.model.network.massage.clientRequest.preSignInRequest.LoginRequest;
import com.mygdx.game.model.network.massage.serverResponse.LoginResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;
import com.mygdx.game.model.user.User;

public class LoginHandler {
    private String request;
    private Gson gson;

    public LoginHandler(String request, Gson gson) {
        this.request = request;
        this.gson = gson;
    }

    public LoginResponse handle(RequestHandler requestHandler) {
        LoginRequest loginRequest = gson.fromJson(request, LoginRequest.class);
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        //todo: seperate server and client logincs bellow
        String response = loginHandler(username, password);
        if(response.equals("accept")) {
            User user = User.getUserByUsername(username);
            RequestHandler.allUsers.put(user, requestHandler);
            return new LoginResponse(ServerResponseType.LOGIN_CONFIRM , user);
        } else {
            return new LoginResponse(ServerResponseType.LOGIN_DENY, response);
        }
    }
    public String loginHandler(String username, String password) {
        if(username.equals("admin")) {
            return "accept";
        }
        if (username.isEmpty() || password.isEmpty()) {
            return "Please fill all fields";
        }
        if(!RegisterHandler.isUsernameTaken(username)) {
            return "User does not exist";
        }
        if(!LoginMenuController.doesThisPasswordMatch(username, password)) {
            return "Incorrect password";
        }
        return "accept";
    }
}
