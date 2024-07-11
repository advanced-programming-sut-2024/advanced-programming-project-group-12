package com.mygdx.game.controller.remote;

import com.google.gson.Gson;
import com.mygdx.game.model.network.RequestHandler;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.StartGameRequest;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.ServerInviteResponse;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.ServerPlayInvite;
import com.mygdx.game.model.network.session.Session;
import com.mygdx.game.model.user.User;

import java.util.ArrayList;
import java.util.List;

public class InviteHandler {
    private static GameHandler queGameHandler = null;
    private String request;
    private Gson gson;

    public InviteHandler(String request, Gson gson) {
        this.request = request;
        this.gson = gson;
    }

    public ServerResponse handle(RequestHandler requestHandler, User user) {
        StartGameRequest startGameRequest = gson.fromJson(request, StartGameRequest.class);

        //updating user in remote

        user.setDeck(new ArrayList<>(startGameRequest.getDeck()));
        user.setLeader(startGameRequest.getCommanderCard());
        user.setFaction(startGameRequest.getFaction());
        user.save();

        if(startGameRequest.isRandomOpponent()) {
            if(queGameHandler == null) {
                queGameHandler = new GameHandler(user, false, false);
                requestHandler.setGameHandler(queGameHandler);
            }
            else {
                queGameHandler.addUserAndStart(user);
                queGameHandler = null;
            }
            return null;
        }

        if(!RequestHandler.allUsers.containsKey(startGameRequest.getUserToBeInvited())) {
            return new ServerInviteResponse("user not online");
        }
        RequestHandler targetHandler = RequestHandler.allUsers.get(startGameRequest.getUserToBeInvited());
        if(targetHandler.getGameHandler() != null) {
            return new ServerInviteResponse("user already in game");
        }
        if(targetHandler == requestHandler) {
            return new ServerInviteResponse("dash gerefty maro?");
        }
        requestHandler.setGameHandler(new GameHandler(user, startGameRequest.isPrivate(), startGameRequest.isTournament()));


        targetHandler.sendMassage(new ServerPlayInvite(startGameRequest));
        return null;
    }

    public ServerResponse getRandomGameList() {
        List<String> randomGames = new ArrayList<>();
        if(queGameHandler != null) {
            randomGames.add(queGameHandler.getUser1().getUsername() + " : ");
        }
        return new ServerInviteResponse(randomGames);
    }
}
