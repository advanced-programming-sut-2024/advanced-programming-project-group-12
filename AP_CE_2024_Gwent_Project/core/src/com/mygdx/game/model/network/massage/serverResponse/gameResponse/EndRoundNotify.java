package com.mygdx.game.model.network.massage.serverResponse.gameResponse;

import com.mygdx.game.model.game.Round;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;
import com.mygdx.game.model.network.session.Session;

public class EndRoundNotify extends ServerResponse {
    private Round round;
    private boolean isToStart;
    public EndRoundNotify(boolean isToStart, Round round) {
        super(ServerResponseType.END_ROUND, null);
        this.isToStart = isToStart;
        this.round = round;
    }

    public boolean isToStart() {
        return isToStart;
    }

    public Round getRound() {
        return round;
    }
}
