package com.mygdx.game.controller.local;

import com.mygdx.game.Gwent;
import com.mygdx.game.model.actors.ChatUI;
import com.mygdx.game.model.actors.Emoji;
import com.mygdx.game.model.network.Client;
import com.mygdx.game.model.network.massage.clientRequest.ChatInGame;
import com.mygdx.game.model.network.massage.clientRequest.ReactionMassageRequest;
import com.mygdx.game.view.screen.GameScreen;

public class ChatController {

    public static void sendMessage(String sender, String messageText, String time, String replyToSender, String replyToMessage, boolean spectator) {
        Client.getInstance().sendMassage(new ChatInGame(sender, time, replyToSender, replyToMessage, messageText, spectator));
    }
    public static void receiveMassage(String sender, String messageText, String time, String replyToSender, String replyToMessage) {
        ChatUI.getInstance().addReceivedMessage(sender, messageText, time, replyToSender, replyToMessage);
    }

    public static void sendEmojiReaction(Emoji emoji) {
        Client.getInstance().sendMassage(new ReactionMassageRequest(emoji, null));
    }
    private static void receiveEmojiReaction(Emoji emoji) {
        ((GameScreen) Gwent.singleton.getCurrentScreen()).setReactedEmoji(emoji);
    }
    public static void sendMessageReaction(String message) {
        Client.getInstance().sendMassage(new ReactionMassageRequest(null, message));
    }
    private static void receiveMessageReaction(String message) {
        ((GameScreen) Gwent.singleton.getCurrentScreen()).setReactedMessage(message);
    }
    public static void receiveMessageReaction(ReactionMassageRequest reactionMassageRequest) {
        if(reactionMassageRequest.getEmoji() == null) {
            receiveMessageReaction(reactionMassageRequest.getMassage());
        }
        else {
            receiveEmojiReaction(reactionMassageRequest.getEmoji());
        }
    }
    public static void showReactionWindow() {
        ((GameScreen) Gwent.singleton.getCurrentScreen()).setShowReactionWindow();
    }
}
