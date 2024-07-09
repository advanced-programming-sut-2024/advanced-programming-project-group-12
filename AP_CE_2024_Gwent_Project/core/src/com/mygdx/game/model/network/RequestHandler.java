package com.mygdx.game.model.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mygdx.game.controller.remote.*;
import com.mygdx.game.model.game.card.AbstractElementAdapter;
import com.mygdx.game.model.game.card.AbstractCard;
import com.mygdx.game.model.network.massage.clientRequest.ChatInGame;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequest;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.*;
import com.mygdx.game.model.network.massage.clientRequest.preSignInRequest.ChangeMenuRequest;
import com.mygdx.game.model.network.massage.serverResponse.ChangeMenuResponse;
import com.mygdx.game.model.network.massage.serverResponse.GetAllUsersResponse;
import com.mygdx.game.model.network.session.InvalidSessionException;
import com.mygdx.game.model.network.session.SessionExpiredException;
import com.mygdx.game.model.user.User;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;
import com.mygdx.game.model.network.session.Session;

import java.io.*;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class RequestHandler extends Thread {
    public static HashMap<String, RequestHandler> allUsers = new HashMap<>();


    private Server server;
    private String request;
    private Gson gson;
    private DataOutputStream dataOutputStream;

    private Session session;
    private GameHandler gameHandler;
    private User user;
    private Timer unfinishedGame;


    public RequestHandler(Server server) {
        this.server = server;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(AbstractCard.class, new AbstractElementAdapter());
        this.gson = gsonBuilder.create();
    }

    public void setUser(String user) {
        allUsers.put(user, this);
    }

    public GameHandler getGameHandler() {
        return gameHandler;
    }

    public boolean hasUnfinishedGame() {
        return unfinishedGame != null;
    }

    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
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
        System.out.println("request: " + request);
        ClientRequest clientRequest = gson.fromJson(request, ClientRequest.class);
        ServerResponse serverResponse = null;
        try {
            session = clientRequest.getSession();
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
                    new InviteHandler(request, gson).handle(this, user);
                   break;
                case INVITE_ANSWER:
                    new InviteResponseHandler(request, gson).handle(this, user);
                    break;
                case TURN_DECIDE:
                    TurnDecideResponse turnDecideResponse = gson.fromJson(request, TurnDecideResponse.class);
                    gameHandler.letCurrentPlayerPlay(turnDecideResponse.getPlayerToPlay());
                    break;
                case RE_DRAW:
                    ReDrawResponse response = gson.fromJson(request, ReDrawResponse.class);
                    gameHandler.reDraw(user.getPlayer(), response);
                    break;
                case PLAY_CARD_REQUEST:
                    PlayCardRequest playCardRequest = gson.fromJson(request, PlayCardRequest.class);
                    serverResponse = gameHandler.playCard(playCardRequest, user);
                    break;
                case CARD_SELECT_ANSWER:
                    CardSelectionAnswer answer = gson.fromJson(request, CardSelectionAnswer.class);
                    serverResponse = user.getPlayer().getGame().getCardSelectHandler().handle(answer, user.getPlayer());
                    break;
                case PASS_ROUND:
                    user.getPlayer().pass();
                    break;
                case CHAT:
                    ChatInGame chat = gson.fromJson(request, ChatInGame.class);
                    gameHandler.handleChat(chat, user);
                    break;
                case SPECTATOR_CHAT:
                    ChatInGame spectatorChat = gson.fromJson(request, ChatInGame.class);
                    gameHandler.spectatorChatHandle(spectatorChat, user);
                    break;
                case GET_PUBLIC_GAMES:
                    serverResponse = GameHandler.sendAllGamesList();
                    break;
                case JOIN_AS_SPECTATOR:
                    JoinPublicGame joinPublicGame = gson.fromJson(request, JoinPublicGame.class);
                    String username = joinPublicGame.getServerName().split(" ")[0];
                    allUsers.get(username).gameHandler.addAsAnSpectator(user);
                    break;
                case GET_ALL_USERS:
                    serverResponse = new GetAllUsersResponse(User.getAllUsers());
                    break;
            }

            if(serverResponse != null && session != null) {
                serverResponse.setSession(session.renewSession());
            }

        } catch (SessionExpiredException | InvalidSessionException e) {
            serverResponse = new ServerResponse(ServerResponseType.DENY, null);
        }

        try {
            System.out.println("request response: " + gson.toJson(serverResponse));
            dataOutputStream.writeUTF(gson.toJson(serverResponse));
        } catch (IOException e) {
            System.err.println("IO exception in request handler");
            terminate();
        }
    }

    public void sendMassage(ServerResponse massage) {
        //perhaps wait for response
        try {
            massage.setSession(session);
            System.out.println("massage sent: " + gson.toJson(massage));
            dataOutputStream.writeUTF(gson.toJson(massage));
        } catch (IOException e) {
            System.err.println("IO exception in sendMassage");
        }
    }

    public void terminate() {
        try {
            allUsers.remove(user.getUsername());
            dataOutputStream.close();
        } catch (IOException e) {
            System.err.println("IO exception in request handler ");
        } catch (NullPointerException ignored) {}
    }

    public void connectionLost() {
        if(gameHandler != null) {
            unfinishedGame = new Timer();
            unfinishedGame.schedule(new TimerTask() {
                @Override
                public void run() {
                    gameHandler.gameAborted(user);
                    terminate();
                }
            }, 60000);
        }
    }

    public void connectionReturned() {
        unfinishedGame.cancel();
        terminate();
    }

    private void writeLog(String string) {
        File file = new File("Data/Users/" + user.getUsername() + "/gameLog/friendRequests.json");
        Gson gson = new Gson();
        if(file.exists()) {
            file.delete();
        } else {
            file.getParentFile().mkdirs();
        }
        try {
            FileWriter writer = new FileWriter(file, true);
            gson.toJson(string, writer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
