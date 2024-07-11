package com.mygdx.game.model.network.massage.serverResponse.gameResponse;

import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;
import com.mygdx.game.model.network.session.Session;

public class ConnectionLostNotify extends ServerResponse {
    private boolean lost;

    public ConnectionLostNotify(boolean lost) {
        super(ServerResponseType.CONNECTION_LOST, null);
        this.lost = lost;
    }

    public boolean isLost() {
        return lost;
    }
}
