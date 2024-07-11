package com.mygdx.game.controller.local;

import com.mygdx.game.Gwent;
import com.mygdx.game.model.network.Client;
import com.mygdx.game.model.network.massage.clientRequest.GetRandomGamesRequest;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.ClientInviteResponse;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.StartGameRequest;
import com.mygdx.game.model.user.User;
import com.mygdx.game.view.screen.GameRequestScreen;

import java.util.ArrayList;
import java.util.LinkedList;

public class GameRequestController {
    public void sendGameRequest(String to, boolean isPublic) {
        //public : true & private : false
        boolean isPrivate = !isPublic;
        Client.getInstance().sendMassage(new StartGameRequest(to, User.getLoggedInUser().getUsername(), User.getLoggedInUser(), isPrivate));
    }

    public void requestTimedOut() {

    }
    public boolean userExists(String username) {
        // Replace with actual implementation
        return User.getUserByUsername(username) != null;
    }

    public void acceptGameRequest(String from) {
        User user = User.getLoggedInUser();
        Client.getInstance().sendMassage(new ClientInviteResponse(user.getFaction(), new LinkedList<>(user.getDeck()), user.getLeaderAsCard(), from));
    }

    public void rejectGameRequest(String from) {
        Client.getInstance().sendMassage(new ClientInviteResponse(from));
    }

    public void sendRandomGameRequest() {
        Client.getInstance().sendMassage(new StartGameRequest(null, User.getLoggedInUser().getUsername(), User.getLoggedInUser(), true, false));
    }

    public void sendListOfRandomGamesRequest() {
        Client.getInstance().sendMassage(new GetRandomGamesRequest());
    }
    public void setListOfRandomGames(ArrayList<String> list) {
        ((GameRequestScreen) Gwent.singleton.getCurrentScreen()).listOfRandomGames  = list;
    }

    public void joinRandomGame(String game) {
        sendRandomGameRequest();
    }
}
