package com.mygdx.game.model.network;

import com.google.gson.Gson;
import com.mygdx.game.controller.remote.LoginHandler;
import com.mygdx.game.controller.remote.RegisterHandler;
import com.mygdx.game.model.game.card.AbstractCard;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequest;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.*;
import com.mygdx.game.model.network.massage.clientRequest.preSignInRequest.LoginRequest;
import com.mygdx.game.model.network.massage.clientRequest.preSignInRequest.SecurityQuestionRequest;
import com.mygdx.game.model.network.massage.clientRequest.preSignInRequest.SignUpRequest;
import com.mygdx.game.model.network.session.InvalidSessionException;
import com.mygdx.game.model.network.session.SessionExpiredException;
import com.mygdx.game.model.user.Player;
import com.mygdx.game.model.user.User;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;
import com.mygdx.game.model.network.session.Session;

import java.io.*;

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
            User user = null;
            if(clientRequest instanceof PostSignInRequest){
                Session session = ((PostSignInRequest)clientRequest).getSession();
                user = Session.getUser(session);
            }
            switch (clientRequest.getClass()) {
                case SignUpRequest.class:
                    serverResponse = new RegisterHandler(request).handleSignUp(gson);
                    break;
                case SecurityQuestionRequest.class:
                    serverResponse = new RegisterHandler(request).handleSecuritySetUp(gson);
                    break;
                case LoginRequest.class:
                    serverResponse = new LoginHandler(request, gson).handle();
                    break;
                case StartGameRequest.class:
                    StartGameRequest startGameRequest = gson.fromJson(request, StartGameRequest.class);
                    User target = startGameRequest.getUserToBeInvited();
                    //AnswerUserInvite) target.sendMassage(new InviteUserToPlay(Session.getUser(startGameRequest.getSession())));
                    //serverResponse = new ServerResponse(answer.isAccept() ? ServerResponseType.CONFIRM : ServerResponseType.DENY, startGameRequest.getSession());
                    break;
                case PlayCardRequest.class:
                    PlayCardRequest playCardRequest = gson.fromJson(request, PlayCardRequest.class);
                    Player player = user.getPlayer();
                    AbstractCard abstractCard = playCardRequest.getCard();
                    serverResponse = abstractCard.place(playCardRequest.getRow(), player);
                    break;
            }
        } catch (SessionExpiredException | InvalidSessionException e) {
            serverResponse = new ServerResponse(ServerResponseType.DENY, null);
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
