package com.mygdx.game.model.network.massage.serverResponse;

import com.mygdx.game.model.game.Tournament;

public class GoToTournamentNextRoundNotify extends ServerResponse {
    private Tournament tournament;

    public GoToTournamentNextRoundNotify(Tournament tournament) {
        super(ServerResponseType.GOTO_TOURNAMENT_NEXT_ROUND, null);
        this.tournament = tournament;
    }

    public Tournament getTournament() {
        return tournament;
    }
}
