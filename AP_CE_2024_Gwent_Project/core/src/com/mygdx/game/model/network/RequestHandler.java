package com.mygdx.game.model.network;

import com.google.gson.Gson;
import com.mygdx.game.controller.remote.*;
import com.mygdx.game.model.game.card.AbstractCard;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequest;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.*;
import com.mygdx.game.model.network.massage.clientRequest.preSignInRequest.ChangeMenuRequest;
import com.mygdx.game.model.network.massage.serverResponse.ChangeMenuResponse;
import com.mygdx.game.model.network.session.InvalidSessionException;
import com.mygdx.game.model.network.session.SessionExpiredException;
import com.mygdx.game.model.user.Player;
import com.mygdx.game.model.user.User;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;
import com.mygdx.game.model.network.session.Session;

import java.io.*;
import java.util.HashMap;

public class RequestHandler extends Thread {
    public static HashMap<String, RequestHandler> allUsers = new HashMap<>();


    private Server server;
    private String request;
    private Gson gson;
    private DataOutputStream dataOutputStream;

    public RequestHandler(Server server, Gson gson) {
        this.server = server;
        this.gson = gson;
    }

    public void setUser(String user) {
        allUsers.put(user, this);
    }

    public void setDataOutputStream(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    @Override
    public void run() {
        while(true) {
            synchronized (server.clientRequests) {
                while (server.clientRequests.isEmpty()) {
                    try {
                        server.clientRequests.wait();  //wait until a request is issued to the server thread
                    } catch (InterruptedException e) {
                        System.err.println("interrupted in request handler");
                    }
                }
                request = server.clientRequests.removeFirst();
            }
            handleRequest();
        }
    }

    private void handleRequest() {
        ClientRequest clientRequest = gson.fromJson(request, ClientRequest.class);
        ServerResponse serverResponse = null;
        try {
            Session session = clientRequest.getSession();
            User user = null;
            if(session != null) {
                user = Session.getUser(session);
            }

            switch (clientRequest.getType()) {
                case CHANGE_MENU:
                    ChangeMenuRequest changeMenuRequest = gson.fromJson(request, ChangeMenuRequest.class);
                    serverResponse = new ChangeMenuResponse(changeMenuRequest.getScreen());
                    break;
                case SIGN_IN:
                    serverResponse = new RegisterHandler(request).handleRegister(gson);
                    break;
                case ABORT_SIGN_IN:
                    serverResponse = new RegisterHandler(request).handleAbort(gson);
                    break;
                case SET_SECURITY_QUESTION:
                    serverResponse = new RegisterHandler(request).handleSecurityQuestion(gson);
                    break;
                case LOGIN:
                    serverResponse = new LoginHandler(request, gson).handle(this);
                    break;
                case FRIEND_REQUEST:
                    new FriendRequestHandler(request, gson).handleSendingRequest();
                    break;
                case GET_FRIEND_REQUESTS:
                    serverResponse = new FriendRequestHandler(request, gson).getFriendRequests(user);
                    break;
//                case GET_FRIENDS:
//                    serverResponse = new FriendRequestHandler(request, gson).getFriends(user);
//                    break;
                case START_GAME:
                   new InviteHandler(request, gson).handle();
                   break;
                case INVITE_ANSWER:
                    new InviteResponseHandler(request, gson).handle();
                    break;
                case PLAY_CARD_REQUEST:
                    PlayCardRequest playCardRequest = gson.fromJson(request, PlayCardRequest.class);
                    Player player = user.getPlayer();
                    AbstractCard abstractCard = playCardRequest.getCard();
                    serverResponse = abstractCard.place(playCardRequest.getRow(), player);
                    break;
            }

            if(serverResponse != null && session != null) {
                serverResponse.setSession(session.renewSession());
            }

        } catch (SessionExpiredException | InvalidSessionException e) {
            serverResponse = new ServerResponse(ServerResponseType.DENY, null);
        }

        try {
            System.out.println(gson.toJson(serverResponse));
            dataOutputStream.writeUTF(gson.toJson(serverResponse));
        } catch (IOException e) {
            System.err.println("IO exception in request handler");
        }
    }

    public void sendMassage(ServerResponse massage) {
        //perhaps wait for response
        try {
            dataOutputStream.writeUTF(gson.toJson(massage));
        } catch (IOException e) {
            System.err.println("IO exception in sendMassage");
        }
    }

    public void terminate() {
        try {
            dataOutputStream.close();
        } catch (IOException e) {
            System.err.println("IO exception in request handler ");
        }
    }
}
