package com.mygdx.game.model.network;

import com.google.gson.Gson;
import com.mygdx.game.model.game.card.AbstractCard;
import com.mygdx.game.model.network.session.InvalidSessionException;
import com.mygdx.game.model.network.session.SessionExpiredException;
import com.mygdx.game.model.user.Player;
import com.mygdx.game.model.user.User;
import com.mygdx.game.model.network.massage.clientRequest.*;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;
import com.mygdx.game.model.network.massage.serverResponse.preGameRosponse.InviteUserToPlay;
import com.mygdx.game.model.network.session.Session;

import java.io.DataOutputStream;
import java.io.IOException;

public class RequestHandler extends Thread {
    private Server server;
    private String request;
    private Gson gson;
    private DataOutputStream dataOutputStream;

    public RequestHandler(Server server, Gson gson) {
        this.server = server;
        this.gson = gson;
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
            User user = Session.getUser(session);
            switch (clientRequest.getType()) {
                case SIGN_IN:
                    ;//handle the shit
                case LOGIN:
                    ;
                case ADD_TO_FRIEND:
                    ;
                case ACCEPT_FRIEND_REQUEST:
                    ;
                case START_GAME:
                    StartGameRequest startGameRequest = gson.fromJson(request, StartGameRequest.class);
                    User target = startGameRequest.getUserToBeInvited();
                    AnswerUserInvite answer = (AnswerUserInvite) target.sendMassage(new InviteUserToPlay(Session.getUser(startGameRequest.getSession())));
                    serverResponse = new ServerResponse(answer.isAccept() ? ServerResponseType.CONFIRM : ServerResponseType.DENY, startGameRequest.getSession());
                    break;
                case PLAY_CARD_REQUEST:
                    PlayCardRequest playCardRequest = gson.fromJson(request, PlayCardRequest.class);
                    Player player = user.getPlayer();
                    AbstractCard abstractCard = playCardRequest.getCard();
                    serverResponse = abstractCard.place(playCardRequest.getRow(), player);
                    break;
            }
        } catch (SessionExpiredException | InvalidSessionException e) {
            serverResponse = new ServerResponse(e);
        }

        try {
            dataOutputStream.writeUTF(gson.toJson(serverResponse));
        } catch (IOException e) {
            System.err.println("IO exception in request handler");
        }
    }

    private void sendMassage(ServerResponse massage) {
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
