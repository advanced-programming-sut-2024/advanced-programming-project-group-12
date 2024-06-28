package com.mygdx.game.model.network.massage.clientRequest;

public class LoginRequest extends ClientRequest{
    private String username;
    private String password;

    public LoginRequest(ClientRequestType type, String token, String username, String password) {
        super(type, token);
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
