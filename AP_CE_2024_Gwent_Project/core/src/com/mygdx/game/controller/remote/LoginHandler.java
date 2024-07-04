package com.mygdx.game.controller.remote;

import com.google.gson.Gson;
import com.mygdx.game.controller.local.LoginMenuController;
import com.mygdx.game.model.network.massage.clientRequest.preSignInRequest.LoginRequest;
import com.mygdx.game.model.network.massage.serverResponse.LoginResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;
import com.mygdx.game.model.user.User;

public class LoginHandler {
    private String request;
    private Gson gson;

    public LoginHandler(String request, Gson gson) {
        this.request = request;
        this.gson = gson;
    }

    public LoginResponse handle() {
        LoginRequest loginRequest = gson.fromJson(request, LoginRequest.class);
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        String response = LoginMenuController.loginHandler(username, password);
        if(response.equals("accept")) {
            return new LoginResponse(ServerResponseType.LOGIN_CONFIRM , User.getUserByUsername(username));
        } else {
            return new LoginResponse(ServerResponseType.LOGIN_DENY, response);
        }
    }
}
