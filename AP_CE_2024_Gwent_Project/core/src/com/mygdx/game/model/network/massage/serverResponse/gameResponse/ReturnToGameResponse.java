package com.mygdx.game.model.network.massage.serverResponse.gameResponse;

import com.mygdx.game.model.game.Game;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;
import com.mygdx.game.model.user.User;
import com.mygdx.game.model.network.session.Session;

public class ReturnToGameResponse extends ServerResponse {
    private Game game;
    private boolean permission;
    public ReturnToGameResponse(User user, Game game) {
        super(ServerResponseType.RETURN_TO_GAME, new Session(user));
        this.game = game;
        permission = game.getCurrentPlayer().getUsername().equals(user.getUsername());
    }

    public Game getGame() {
        return game;
    }

    public boolean isPermission() {
        return permission;
    }
}
