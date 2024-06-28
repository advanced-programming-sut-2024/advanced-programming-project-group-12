package com.mygdx.game.model.network.massage.clientRequest;

import com.mygdx.game.model.network.massage.Session;

public class LoginRequest extends ClientRequest{
    private String username;
    private String password;

    public LoginRequest(ClientRequestType type, Session session, String username, String password) {
        super(type, session);
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
