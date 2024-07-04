package com.mygdx.game.controller.local;

import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.ClientFriendRequest;
import com.mygdx.game.model.user.FriendRequest;
import com.mygdx.game.model.network.Client;
import com.mygdx.game.model.user.User;

public class FriendsController {
    public void sendFriendRequest(User fromUser, User toUser) {
        Client.getInstance().sendMassage(new ClientFriendRequest(new FriendRequest(fromUser, toUser, "pending")));
//        toUser.getReceivedFriendRequests().add(request);
//        fromUser.getSentFriendRequests().add(request);
//        toUser.save();
//        fromUser.save();
    }

    public void receiveFriendRequest(FriendRequest friendRequest) {
        //todo: add this so it goes and is saved somewhere and is shown in gui
    }

    public void acceptFriendRequest(User user, FriendRequest request) {

//        if (user.getReceivedFriendRequests().remove(request)) {
//            user.getFriends().add(request.getFromUser());
//            request.getFromUser().getFriends().add(user);
//            request.getFromUser().getSentFriendRequests().remove(request);
//            request.setStatus("accepted");
//            user.save();
//            request.getFromUser().save();
//        }
        //add the guy as friend locally here
        request.setStatus("accepted");
        Client.getInstance().sendMassage(new ClientFriendRequest(request));
    }

    public void rejectFriendRequest(User user, FriendRequest request) {
        if (user.getReceivedFriendRequests().remove(request)) {
            request.getFromUser().getSentFriendRequests().remove(request);
            request.setStatus("rejected");
            user.save();
            request.getFromUser().save();
        }
        request.setStatus("rejected");
        Client.getInstance().sendMassage(new ClientFriendRequest(request));
    }
}
