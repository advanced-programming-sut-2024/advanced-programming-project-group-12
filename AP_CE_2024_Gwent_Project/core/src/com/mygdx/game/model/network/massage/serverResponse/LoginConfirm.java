package com.mygdx.game.model.network.massage.serverResponse;

import com.mygdx.game.model.User;
import com.mygdx.game.model.network.massage.Session;

public class LoginConfirm extends ServerResponse {
    public LoginConfirm(User user) {
        super(ServerResponseType.LOGIN_CONFIRM);
        super.session = new Session(user);
    }
}
