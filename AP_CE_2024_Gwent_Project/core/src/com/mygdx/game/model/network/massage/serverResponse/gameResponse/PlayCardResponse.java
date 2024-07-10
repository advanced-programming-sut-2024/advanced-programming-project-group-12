package com.mygdx.game.model.network.massage.serverResponse.gameResponse;

import com.mygdx.game.model.game.Game;
import com.mygdx.game.model.game.GameBoard;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;
import com.mygdx.game.model.network.session.Session;

public class PlayCardResponse extends ServerResponse {
    private Game game;
    private ActionResponse actionResponse;
    private boolean permission;

    public PlayCardResponse(Game game, ActionResponse actionResponse) {
        super(ServerResponseType.PLAY_CARD_RESPONSE, null);
        this.game = game;
        this.actionResponse = actionResponse;
        permission = actionResponse != null && actionResponse.getAction().equals(ActionResponseType.SELECTION) &&
                     actionResponse.getAction().equals(ActionResponseType.DECOY);
    }

    public PlayCardResponse(Game game) {
        super(ServerResponseType.PLAY_CARD_RESPONSE, null);
        this.game = game;
        actionResponse = null;
        permission = false;
    }

    public PlayCardResponse(Game game, boolean permission) {
        super(ServerResponseType.PLAY_CARD_RESPONSE, null);
        this.game = game;
        actionResponse = null;
        this.permission = permission;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public ActionResponse getActionResponse() {
        return actionResponse;
    }

    public void setActionResponse(ActionResponse actionResponse) {
        this.actionResponse = actionResponse;
    }

    public boolean isPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }
}
