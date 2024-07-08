package com.mygdx.game.controller.remote;

import com.google.gson.Gson;
import com.mygdx.game.controller.local.LoginMenuController;
import com.mygdx.game.model.network.RequestHandler;
import com.mygdx.game.model.network.massage.clientRequest.preSignInRequest.LoginRequest;
import com.mygdx.game.model.network.massage.serverResponse.LoginResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.ReturnToGameResponse;
import com.mygdx.game.model.user.User;

public class LoginHandler {
    private String request;
    private Gson gson;

    public LoginHandler(String request, Gson gson) {
        this.request = request;
        this.gson = gson;
    }

    public ServerResponse handle(RequestHandler requestHandler) {
        LoginRequest loginRequest = gson.fromJson(request, LoginRequest.class);
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        String response = loginHandler(username, password);
        if(response.equals("accept")) {
            User user = User.getUserByUsername(username);
            if(user == null) {
                System.err.println("no such user to be loggend in");
                return new LoginResponse(ServerResponseType.LOGIN_DENY, "denied");
            }

            RequestHandler.allUsers.put(user.getUsername(), requestHandler);
            if(checkForUnfinishedGame(username, requestHandler)) {
                return new ReturnToGameResponse(user, requestHandler.getGameHandler().getGame());
            }

            return new LoginResponse(ServerResponseType.LOGIN_CONFIRM , user);
        } else {
            return new LoginResponse(ServerResponseType.LOGIN_DENY, response);
        }
    }

    private boolean checkForUnfinishedGame(String username, RequestHandler newRequestHandler) {
        RequestHandler requestHandler = RequestHandler.allUsers.get(username);
        if(requestHandler == null || !requestHandler.hasUnfinishedGame()) return false;
        requestHandler.connectionReturned();
        newRequestHandler.setGameHandler(requestHandler.getGameHandler());
        return true;
    }

    public String loginHandler(String username, String password) {
        //TODO: remove it
        if(User.getUserByUsername(username) != null || username.equals("admin")) {
            return "accept";
        }
        if (username.isEmpty() || password.isEmpty()) {
            return "Please fill all fields";
        }
        if(!RegisterHandler.isUsernameTaken(username)) {
            return "User does not exist";
        }
        if(!LoginMenuController.doesThisPasswordMatch(username, password)) {
            return "Incorrect password";
        }
        return "accept";
    }
}
