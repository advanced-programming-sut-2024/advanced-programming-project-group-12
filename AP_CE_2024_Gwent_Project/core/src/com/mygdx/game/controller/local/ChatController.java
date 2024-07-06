package com.mygdx.game.controller.local;

import com.mygdx.game.model.network.Client;
import com.mygdx.game.model.network.massage.clientRequest.ChatInGame;

public class ChatController {

    public static void sendMessage(String username, String message) {
        //set spectator true for multi-person chat if user is a spectator
        Client.getInstance().sendMassage(new ChatInGame(message, username, false));
    }

    public static void receiveMassage(String massage, String username) {
        //todo
    }
}
