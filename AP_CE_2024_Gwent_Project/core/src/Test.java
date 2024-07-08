import com.google.gson.Gson;
import com.mygdx.game.controller.remote.FriendRequestHandler;
import com.mygdx.game.model.game.Game;
import com.mygdx.game.model.game.card.Action;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.ClientFriendRequest;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.GetFriendRequestsRequest;
import com.mygdx.game.model.network.massage.serverResponse.LoginResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;
import com.mygdx.game.model.network.session.Session;
import com.mygdx.game.model.user.FriendRequest;
import com.mygdx.game.model.user.Player;
import com.mygdx.game.model.user.User;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Test {
    @org.junit.jupiter.api.Test
    public void testFriendRequest() {
        String a = "a";
        HashMap<String, HashMap<String, FriendRequest>> map = new HashMap<>();
        HashMap<String, FriendRequest> req = new HashMap<>();
        FriendRequest fr = new FriendRequest(new User("admin", a,a,a), new User("alijan",a,a,a), "accepted");
        req.put("alijan", fr);
        map.put("sent" , req);
        Gson gson = new Gson();
        new FriendRequestHandler(gson.toJson(new ClientFriendRequest(fr)), gson).handleSendingRequest();
    }
}
