package com.mygdx.game.controller.remote;

import com.mygdx.game.model.game.Tournament;
import com.mygdx.game.model.network.massage.clientRequest.TournamentStartRequest;

public class TournamentHandler {
    private static Tournament currentTournament;

    public TournamentHandler(TournamentStartRequest tournamentStartRequest) {
        currentTournament = new Tournament(tournamentStartRequest.getUsers());
    }

    public static Tournament getCurrentTournament() {
        return currentTournament;
    }
}
