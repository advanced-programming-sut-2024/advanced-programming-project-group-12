package com.mygdx.game.model.network.massage.serverResponse.gameResponse;

import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;
import com.mygdx.game.model.network.session.Session;

public class EndRoundNotify extends ServerResponse {
    private boolean isToStart;
    public EndRoundNotify(boolean isToStart) {
        super(ServerResponseType.END_ROUND, null);
        this.isToStart = isToStart;
    }

    public boolean isToStart() {
        return isToStart;
    }
}
