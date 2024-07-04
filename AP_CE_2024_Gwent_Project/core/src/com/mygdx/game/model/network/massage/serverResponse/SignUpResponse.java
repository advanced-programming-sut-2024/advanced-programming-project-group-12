package com.mygdx.game.model.network.massage.serverResponse;

import com.mygdx.game.model.network.session.Session;

public class SignUpResponse extends ServerResponse{
    private String error;
    private String username;
    public SignUpResponse() {
        super(ServerResponseType.CONFIRM, null);
    }
    public SignUpResponse(String error, String username) {
        super(ServerResponseType.SIGN_IN_DENY, null);
        this.error = error;
        this.username = username;
    }

    public String getError() {
        return error;
    }

    public String getUsername() {
        return username;
    }
}
