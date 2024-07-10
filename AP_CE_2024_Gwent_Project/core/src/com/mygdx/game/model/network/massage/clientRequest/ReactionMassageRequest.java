package com.mygdx.game.model.network.massage.clientRequest;

import com.mygdx.game.model.actors.Emoji;

public class ReactionMassageRequest extends ChatInGame{
    private Emoji emoji;
    private String massage;
    public ReactionMassageRequest(Emoji emoji, String massage) {
        super("", "", "", "", "", false);
        this.emoji = emoji;
        this.massage = massage;
    }

    public Emoji getEmoji() {
        return emoji;
    }

    @Override
    public String getMassage() {
        return massage;
    }
}
