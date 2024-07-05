package com.mygdx.game.controller.local;

import com.mygdx.game.model.network.Client;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.ClientInviteResponse;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.StartGameRequest;
import com.mygdx.game.model.user.User;

public class GameRequestController {
    public void sendGameRequest(String to) {
        Client.getInstance().sendMassage(new StartGameRequest(to, User.getLoggedInUser().getUsername(), User.getLoggedInUser()));
    }

    public void requestTimedOut() {

    }
    public boolean userExists(String username) {
        // Replace with actual implementation
        return User.getUserByUsername(username) != null;
    }

    public void acceptGameRequest(String from) {
        User user = User.getLoggedInUser();
        Client.getInstance().sendMassage(new ClientInviteResponse(user.getFaction(), user.getDeckAsCard(), user.getLeaderAsCard(), from));
    }

    public void rejectGameRequest(String from) {
        Client.getInstance().sendMassage(new ClientInviteResponse(from));
    }
}
