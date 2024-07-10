package com.mygdx.game.controller.local;

import com.mygdx.game.Gwent;
import com.mygdx.game.model.actors.ChatUI;
import com.mygdx.game.model.actors.Emoji;
import com.mygdx.game.model.network.Client;
import com.mygdx.game.model.network.massage.clientRequest.ChatInGame;
import com.mygdx.game.view.screen.GameScreen;

public class ChatController {

    public static void sendMessage(String sender, String messageText, String time, String replyToSender, String replyToMessage, boolean spectator) {
        Client.getInstance().sendMassage(new ChatInGame(sender, messageText, time, replyToSender, replyToMessage, spectator));
    }
    public static void receiveMassage(String sender, String messageText, String time, String replyToSender, String replyToMessage) {
        ChatUI.getInstance().addReceivedMessage(sender, messageText, time, replyToSender, replyToMessage);
    }

    public static void sendEmojiReaction(Emoji emoji) {
        //TODO : send this emoji reaction
    }
    public static void receiveEmojiReaction(Emoji emoji) {
        ((GameScreen) Gwent.singleton.getCurrentScreen()).setReactedEmoji(emoji);
    }
    public static void sendMessageReaction(String message) {
        //TODO : send this message reaction
    }
    public static void receiveMessageReaction(String message) {
        ((GameScreen) Gwent.singleton.getCurrentScreen()).setReactedMessage(message);
    }
    public static void showReactionWindow() {
        //TODO : call it when we should show reaction window
        ((GameScreen) Gwent.singleton.getCurrentScreen()).setShowReactionWindow();
    }
}
