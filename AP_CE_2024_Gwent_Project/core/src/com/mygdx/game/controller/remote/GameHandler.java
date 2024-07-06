package com.mygdx.game.controller.remote;

import com.mygdx.game.model.game.Faction;
import com.mygdx.game.model.game.Game;
import com.mygdx.game.model.network.RequestHandler;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.PlayCardResponse;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.TurnDecideRequest;
import com.mygdx.game.model.user.Player;
import com.mygdx.game.model.user.User;

public class GameHandler {
    private User user1;
    private User user2;
    private Game game;

    public GameHandler(User user1) {
        this.user1 = user1;
    }

    public void addUser(User user) {
        this.user2 = user;
        start();
    }

    private void start() {
        user1.setPlayer(new Player(user1));
        user2.setPlayer(new Player(user2));
        game = new Game(user1.getPlayer(), user2.getPlayer());

        if(user1.getFaction().equals(Faction.SCOIATAEL) && !user2.getFaction().equals(Faction.SCOIATAEL)) {
            RequestHandler.allUsers.get(user1.getUsername()).sendMassage(new TurnDecideRequest());
        }
        else if(!user1.getFaction().equals(Faction.SCOIATAEL) && user2.getFaction().equals(Faction.SCOIATAEL)) {
            RequestHandler.allUsers.get(user2.getUsername()).sendMassage(new TurnDecideRequest());
        }
        else {
            //let current player start
            letCurrentPlayerPlay("currentPlayer");
        }
    }

    public void letCurrentPlayerPlay(String playerToStart) {
        if(playerToStart.equals("opponent")) {
            game.switchTurn();
        }
        RequestHandler.allUsers.get(game.getCurrentPlayer().getUsername()).sendMassage(new PlayCardResponse(game, null));
    }
}
