package com.mygdx.game.controller.remote;

import com.google.gson.Gson;
import com.mygdx.game.model.network.massage.clientRequest.preSignInRequest.SignUpRequest;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.SignUpResponse;
import com.mygdx.game.model.user.User;

public class RegisterHandler {
    private String request;

    public RegisterHandler(String request) {
        this.request = request;
    }

    public SignUpResponse handle(Gson gson) {
        SignUpRequest signUpRequest = gson.fromJson(request, SignUpRequest.class);
        if(isUsernameTaken(signUpRequest.getUsername())) {
            return new SignUpResponse("this username is already taken", signUpRequest.getUsername());
        }
        else {
            new User(signUpRequest.getUsername(), signUpRequest.getNickname(), signUpRequest.getPassword(), signUpRequest.getEmail());
            return new SignUpResponse();
        }
    }

    private boolean isUsernameTaken(String username) {
        //todo
        return false;
    }
}
