package com.mygdx.game.model.network.massage.serverResponse;

import com.mygdx.game.model.network.session.Session;
import com.mygdx.game.model.user.User;

public class SignUpResponse extends ServerResponse{
    private User user;
    private String error;
    private String username;
    public SignUpResponse(User user) {
        super(ServerResponseType.SIGN_IN_CONFIRM, null);
        this.user = user;
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

    public User getUser() {
        return user;
    }
}
