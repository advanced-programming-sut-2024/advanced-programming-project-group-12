package com.mygdx.game.model.network.massage.serverResponse.preGameRosponse;

import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;
import com.mygdx.game.model.user.User;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;

public class InviteUserToPlay extends ServerResponse {
    User toPlayWith;
    public InviteUserToPlay(User user) {
        super( null);
        toPlayWith = user;
    }
}
