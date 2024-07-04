package com.mygdx.game.controller.remote;

import com.google.gson.Gson;
import com.mygdx.game.model.network.massage.clientRequest.preSignInRequest.SecurityQuestionRequest;
import com.mygdx.game.model.network.massage.clientRequest.preSignInRequest.SignUpRequest;
import com.mygdx.game.model.network.massage.serverResponse.SecurityQuestionResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;
import com.mygdx.game.model.network.massage.serverResponse.SecurityQuestionResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;
import com.mygdx.game.model.network.massage.serverResponse.SignUpResponse;
import com.mygdx.game.model.user.SecurityQuestion;
import com.mygdx.game.model.user.User;

public class RegisterHandler {
    private String request;

    public RegisterHandler(String request) {
        this.request = request;
    }

    public SignUpResponse handleRegister(Gson gson) {
        SignUpRequest signUpRequest = gson.fromJson(request, SignUpRequest.class);
        if(isUsernameTaken(signUpRequest.getUsername())) {
            return new SignUpResponse("this username is already taken", signUpRequest.getUsername());
        }
        else {
            return new SignUpResponse(new User(signUpRequest.getUsername(), signUpRequest.getNickname(), signUpRequest.getPassword(), signUpRequest.getEmail()));
        }
    }

    public SecurityQuestionResponse handleAbort(Gson gson) {
        SecurityQuestionRequest req = gson.fromJson(request, SecurityQuestionRequest.class);
        return new SecurityQuestionResponse(ServerResponseType.CONFIRM);
    }

    private boolean isUsernameTaken(String username) {
        //todo
        return false;
    }

    public SecurityQuestionResponse handleSecurityQuestion(Gson gson) {
        SecurityQuestionRequest req = gson.fromJson(request, SecurityQuestionRequest.class);
        req.getUser().setSecurityQuestion(req.getQuestion(), req.getAnswer());
        return new SecurityQuestionResponse(ServerResponseType.SECURITY_QUESTION_SET);
    }
}
