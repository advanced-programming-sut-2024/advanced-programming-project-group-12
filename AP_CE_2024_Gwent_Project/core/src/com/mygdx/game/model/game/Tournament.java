package com.mygdx.game.model.game;

import com.mygdx.game.model.network.RequestHandler;
import com.mygdx.game.model.network.massage.serverResponse.GoToTournamentNextRoundNotify;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.TournamentStartError;
import com.mygdx.game.model.user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Tournament {
    ArrayList<User> quarter;
    ArrayList<User> semi;
    ArrayList<User> finalGame;
    ArrayList<User> winner;

    public Tournament(String username) {
        this.quarter = new ArrayList<>(Arrays.asList(null, null, null, null, null, null, null, null));
        semi = new ArrayList<>(Arrays.asList(null, null, null, null));
        finalGame = new ArrayList<>(Arrays.asList(null, null));
        winner = new ArrayList<>(Arrays.asList(null));
    }

    public GoToTournamentNextRoundNotify gameWon(User user) {
        if(finalGame.contains(user)) {
            winner.set(0, user);
            if(isInStage(winner)) new GoToTournamentNextRoundNotify(this);
        }
        else if(semi.contains(user)) {
            finalGame.set(semi.indexOf(user)/2, user);
            if(isInStage(finalGame)) new GoToTournamentNextRoundNotify(this);

        }
        else {
            semi.set(quarter.indexOf(user)/2, user);
            if(isInStage(semi)) new GoToTournamentNextRoundNotify(this);
        }
        return null;
    }

    public ServerResponse addUser(User user) {
        if(quarter.contains(user)) {
            new TournamentStartError("you are already joined in this tournament");
        }
        for(int i = 0; i< quarter.size(); i++) {
            if(quarter.get(i) == null) {
                quarter.set(i, user);
                if(i == 8) {
                    Collections.shuffle(quarter);
                    notifyAllUsers();
                }
                return null;
            }
        }

        return new TournamentStartError("tournament full");
    }

    private void notifyAllUsers() {
        for(User u: quarter) {
            RequestHandler.allUsers.get(u.getUsername()).sendMassage(new GoToTournamentNextRoundNotify(this));
        }
    }

    public ArrayList<User> getQuarter() {
        return quarter;
    }

    public ArrayList<User> getSemi() {
        return semi;
    }

    public ArrayList<User> getFinalGame() {
        return finalGame;
    }

    public ArrayList<User> getWinner() {
        return winner;
    }

    public boolean isInStage(ArrayList<User> stage) {
        for(User u: stage) {
            if(u == null) return false;
        }
        return true;
    }

    public boolean isUserInTournament(User user) {
        return true;
    }
}
