package com.mygdx.game.model.network.massage.clientRequest;

import com.mygdx.game.model.actors.Emoji;

public class ReactionMassageRequest extends ChatInGame{
    Emoji emoji;
    String massageReaction;
    public ReactionMassageRequest(Emoji emoji, String massageReaction) {
        super();
        this.emoji = emoji;
        this.massageReaction = massageReaction;
    }

    public Emoji getEmoji() {
        return emoji;
    }

    @Override
    public String getMassageReaction() {
        return massageReaction;
    }
}
