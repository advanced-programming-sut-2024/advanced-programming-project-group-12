package com.mygdx.game.model;

public enum AllCards {
    COMMANDER_HORN(null),
    ;
    private AbstractCard abstractCard;

    AllCards(AbstractCard abstractCard) {
        this.abstractCard = abstractCard;
    }

    public void setAbstractCard(AbstractCard abstractCard) {
        this.abstractCard = abstractCard;
    }

    public AbstractCard getAbstractCard() {
        return abstractCard;
    }
}
