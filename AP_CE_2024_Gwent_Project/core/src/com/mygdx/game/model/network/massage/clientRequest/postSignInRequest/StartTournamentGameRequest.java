package com.mygdx.game.model.network.massage.clientRequest.postSignInRequest;

import com.mygdx.game.model.network.massage.clientRequest.ClientRequest;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequestType;
import com.mygdx.game.model.user.User;

public class StartTournamentGameRequest extends ClientRequest {
    User user1;
    User user2;

    public StartTournamentGameRequest(User user1, User user2) {
        super(ClientRequestType.START_TOURNAMENT_GAME);
        this.user1 = user1;
        this.user2 = user2;
    }

    public User getUser1() {
        return user1;
    }

    public User getUser2() {
        return user2;
    }
}
