package com.mygdx.game.model.network.massage.serverResponse;

import com.mygdx.game.model.network.massage.clientRequest.ChatInGame;
import com.mygdx.game.model.network.session.Session;

public class ChatInGameWrapper extends ServerResponse{
    private ChatInGame chat;
    public ChatInGameWrapper(ChatInGame chat) {
        super(ServerResponseType.CHAT, null);
        this.chat = chat;
    }

    public ChatInGame getChat() {
        return chat;
    }
}
