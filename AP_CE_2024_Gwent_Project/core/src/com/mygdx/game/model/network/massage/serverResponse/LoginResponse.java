package com.mygdx.game.model.network.massage.serverResponse;

import com.mygdx.game.model.user.User;
import com.mygdx.game.model.network.session.Session;

public class LoginResponse extends ServerResponse {
    User user;
    String error;
    public LoginResponse(ServerResponseType type ,User user) {
        super(type, new Session(user));
        this.user = user;
    }
    public LoginResponse(ServerResponseType type , String error) {
        super(type, null);
        this.error = error;
    }

    public User getUser() {
        return user;
    }

    public String getError() {
        return error;
    }
}
