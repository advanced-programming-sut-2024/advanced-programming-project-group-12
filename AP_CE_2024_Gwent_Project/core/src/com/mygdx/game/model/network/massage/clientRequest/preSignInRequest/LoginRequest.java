package com.mygdx.game.model.network.massage.clientRequest.preSignInRequest;

import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.ClientRequest;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.ClientRequestType;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.PostSignInRequest;

public class LoginRequest extends PreSignInRequest {
    private String username;
    private String password;

    public LoginRequest(String username, String password) {
        super(ClientRequestType.LOGIN);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
