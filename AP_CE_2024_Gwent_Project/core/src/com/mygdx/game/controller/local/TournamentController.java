package com.mygdx.game.controller.local;

import com.mygdx.game.model.network.Client;
import com.mygdx.game.model.network.massage.clientRequest.TournamentStartRequest;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.StartTournamentGameRequest;
import com.mygdx.game.model.user.User;
import java.util.ArrayList;

public class TournamentController {
    private static TournamentController singleton;
    private ArrayList<User> tournamentParticipants;
    private boolean newRoundStarted = false;
    private String joinResponse = null;
    private TournamentController() {
        this.tournamentParticipants = new ArrayList<>();
    }

    public static TournamentController getInstance() {
        if (singleton == null) {
            singleton = new TournamentController();
        }
        return singleton;
    }
    public void addToParticipants(User user) {
        tournamentParticipants.add(user);
    }


    public boolean isPlayerAlreadyAdded(String newUser) {
        for(User user : tournamentParticipants) {
            if(user.getUsername().equals(newUser)) return true;
        }
        return false;
    }
    public ArrayList<User> getTournamentParticipants() {
        return tournamentParticipants;
    }
    public void updateTournamentParticipants(ArrayList<User> users) {
        tournamentParticipants = users;
    }
    public boolean isNewRoundStarted() {
        return newRoundStarted;
    }
    public void startNewRound() {
        newRoundStarted = true;
    }
    public void setNewRoundStarted() {
        newRoundStarted = false;
    }

    public void startGameInTournament(User player1, User player2, int round) {
        Client.getInstance().sendMassage(new StartTournamentGameRequest(player1, player2));
    }

    public void joinTournament() {
        Client.getInstance().sendMassage(new TournamentStartRequest());
    }
    public void getJoinResponse(String error) {

    }
}