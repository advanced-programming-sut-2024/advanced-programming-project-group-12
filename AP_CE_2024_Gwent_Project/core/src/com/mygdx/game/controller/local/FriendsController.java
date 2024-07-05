package com.mygdx.game.controller.local;

import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.ClientFriendRequest;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.GetFriendRequestsRequest;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.GetFriends;
import com.mygdx.game.model.user.FriendRequest;
import com.mygdx.game.model.network.Client;
import com.mygdx.game.model.user.User;

public class FriendsController {
    public void sendFriendRequest(User fromUser, User toUser) {
        FriendRequest request = new FriendRequest(fromUser, toUser, "pending");
        Client.getInstance().sendMassage(new ClientFriendRequest(request));
        // Save the request to the user's sent requests list
    }

    public void acceptFriendRequest(User user, FriendRequest request) {
        request.setStatus("accepted");
//        user.getReceivedFriendRequests().remove(request);
//        user.getFriends().add(request.getFromUser());
//        user.save();
        Client.getInstance().sendMassage(new ClientFriendRequest(request));
    }

    public void rejectFriendRequest(User user, FriendRequest request) {
        request.setStatus("rejected");
//        user.getReceivedFriendRequests().remove(request);
//        user.save();
        Client.getInstance().sendMassage(new ClientFriendRequest(request));
    }

    public void getFriendRequests() {
        Client.getInstance().sendMassage(new GetFriendRequestsRequest());
    }
}
