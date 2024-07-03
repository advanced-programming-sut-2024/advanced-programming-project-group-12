package com.mygdx.game.model.network.massage.clientRequest;

import com.mygdx.game.model.network.session.Session;

public class LoginRequest extends ClientRequest{
    private String username;
    private String password;

    public LoginRequest(String username, String password) {
        super(ClientRequestType.LOGIN, null);
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
