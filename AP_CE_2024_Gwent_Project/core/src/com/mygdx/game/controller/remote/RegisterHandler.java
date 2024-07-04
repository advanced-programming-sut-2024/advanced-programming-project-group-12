package com.mygdx.game.controller.remote;

import com.google.gson.Gson;
import com.mygdx.game.model.network.massage.clientRequest.preSignInRequest.SecurityQuestionRequest;
import com.mygdx.game.model.network.massage.clientRequest.preSignInRequest.SignUpRequest;
import com.mygdx.game.model.network.massage.serverResponse.SecurityQuestionResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;
import com.mygdx.game.model.network.massage.serverResponse.SignUpResponse;
import com.mygdx.game.model.user.SecurityQuestion;
import com.mygdx.game.model.user.User;

public class RegisterHandler {
    private String request;

    public RegisterHandler(String request) {
        this.request = request;
    }

    public SignUpResponse handleSignUp(Gson gson) {
        SignUpRequest signUpRequest = gson.fromJson(request, SignUpRequest.class);
        if(isUsernameTaken(signUpRequest.getUsername())) {
            return new SignUpResponse("this username is already taken", signUpRequest.getUsername());
        }
        else {
            return new SignUpResponse(new User(signUpRequest.getUsername(), signUpRequest.getNickname(), signUpRequest.getPassword(), signUpRequest.getEmail()));
        }
    }

    public SecurityQuestionResponse handleSecuritySetUp(Gson gson) {
        SecurityQuestionRequest securityQuestionRequest = gson.fromJson(request, SecurityQuestionRequest.class);
        switch (securityQuestionRequest.getType()) {
            case ABORT_SIGN_IN:
                deleteUser(securityQuestionRequest.getUser());
                return new SecurityQuestionResponse(ServerResponseType.CONFIRM);
            case SET_SECURITY_QUESTION:
                securityQuestionRequest.getUser().setSecurityQuestion(securityQuestionRequest.getQuestion(), securityQuestionRequest.getAnswer());
                return new SecurityQuestionResponse(ServerResponseType.CONFIRM);
        }
        return new SecurityQuestionResponse(ServerResponseType.DENY);
    }

    private void deleteUser(User user) {
        //todo
    }

    private boolean isUsernameTaken(String username) {
        //todo
        return false;
    }
}
