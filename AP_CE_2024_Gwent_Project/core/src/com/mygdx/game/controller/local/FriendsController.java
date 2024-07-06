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
        Client.getInstance().sendMassage(new ClientFriendRequest(request));

        // Add each user to the other's friend list
        User fromUser = request.getFromUser();
        user.addFriend(fromUser);
        fromUser.addFriend(user);

        updateFriendRequests(user);
    }

    public void rejectFriendRequest(User user, FriendRequest request) {
        request.setStatus("rejected");
        Client.getInstance().sendMassage(new ClientFriendRequest(request));
        updateFriendRequests(user);
    }

    public void getFriendRequests() {
        Client.getInstance().sendMassage(new GetFriendRequestsRequest());
    }

    private void updateFriendRequests(User user) {
        // Refresh the requests list after accepting/rejecting a request
        getFriendRequests();
    }
}
