package com.mygdx.game.controller.remote;

import com.mygdx.game.model.game.Faction;
import com.mygdx.game.model.game.Game;
import com.mygdx.game.model.network.RequestHandler;
import com.mygdx.game.model.network.massage.clientRequest.ChatInGame;
import com.mygdx.game.model.network.massage.serverResponse.ChatInGameWrapper;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.PlayTurnPermission;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.SetGameToStart;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.TurnDecideRequest;
import com.mygdx.game.model.user.Player;
import com.mygdx.game.model.user.User;

import java.util.ArrayList;

public class GameHandler {
    private static final ArrayList<GameHandler> allGames = new ArrayList<>();

    private User user1;
    private User user2;
    private Game game;

    private ArrayList<User> spectators;

    public GameHandler(User user1) {
        this.user1 = user1;
    }

    public void addUserAndStart(User user) {
        this.user2 = user;
        allGames.add(this);
        spectators = new ArrayList<>();
        start();
    }

    private void start() {
        user1.setPlayer(new Player(user1));
        user2.setPlayer(new Player(user2));
        game = new Game(user1.getPlayer(), user2.getPlayer(), this);

        RequestHandler.allUsers.get(user1).sendMassage(new SetGameToStart(game));
        RequestHandler.allUsers.get(user2).sendMassage(new SetGameToStart(game));

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
        RequestHandler.allUsers.get(game.getCurrentPlayer().getUsername()).sendMassage(new PlayTurnPermission(game));
    }

    public void handleChat(ChatInGame chat, User user) {
        User toUser = user.equals(user1)? user2: user1;
        RequestHandler.allUsers.get(toUser).sendMassage(new ChatInGameWrapper(chat));
    }

    public void addAsAnSpectator(User user) {
        spectators.add(user);
    }

    public void sendMassageToSpectators(ServerResponse serverResponse) {
        for(User user: spectators) {
            RequestHandler.allUsers.get(user).sendMassage(serverResponse);
        }
    }
}
