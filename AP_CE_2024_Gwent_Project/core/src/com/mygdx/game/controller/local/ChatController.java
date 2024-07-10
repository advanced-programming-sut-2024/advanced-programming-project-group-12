package com.mygdx.game.controller.local;

import com.mygdx.game.model.actors.ChatUI;
import com.mygdx.game.model.network.Client;
import com.mygdx.game.model.network.massage.clientRequest.ChatInGame;

public class ChatController {

    public static void sendMessage(String sender, String messageText, String time, String replyToSender, String replyToMessage) {
        //TODO: send this message to all players
    }
    public static void receiveMassage(String sender, String messageText, String time, String replyToSender, String replyToMessage) {
        ChatUI.getInstance().addReceivedMessage(sender, messageText, time, replyToSender, replyToMessage);
    }
}
