package com.mygdx.game.model.game;

import com.mygdx.game.model.network.massage.serverResponse.GoToTournamentNextRoundNotify;
import com.mygdx.game.model.user.User;

import java.util.ArrayList;
import java.util.Arrays;

public class Tournament {
    ArrayList<User> quarter;
    ArrayList<User> semi;
    ArrayList<User> finalGame;
    ArrayList<User> winner;

    public Tournament(ArrayList<User> quarter) {
        this.quarter = quarter;
        semi = new ArrayList<>(Arrays.asList(null, null, null, null));
        finalGame = new ArrayList<>(Arrays.asList(null, null));
        winner = new ArrayList<>();
    }

    public GoToTournamentNextRoundNotify gameWon(User user) {
        if(finalGame.contains(user)) {
            winner.add(user);
            if(winner.size() == 1) return new GoToTournamentNextRoundNotify(this);
        }
        else if(semi.contains(user)) {
            finalGame.set(semi.indexOf(user)/2, user);
            for(User u: finalGame) {
                if(user == null) return null;
            }
            return new GoToTournamentNextRoundNotify(this);
        }
        else {
            semi.set(quarter.indexOf(user)/2, user);
            if(winner.size() == 1) return new GoToTournamentNextRoundNotify(this);
            for(User u: semi) {
                if(user == null) return null;
            }
            return new GoToTournamentNextRoundNotify(this);
        }

        return null;
    }

    public boolean isUserInTournament(User user) {
        return true;
    }
}
