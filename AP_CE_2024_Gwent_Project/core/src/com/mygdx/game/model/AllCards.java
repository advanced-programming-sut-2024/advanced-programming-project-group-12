package com.mygdx.game.model;

public enum AllCards {
    ;
    private AbstractCard abstractCard;

    AllCards(AbstractCard abstractCard) {
        this.abstractCard = abstractCard;
    }

    public void setAbstractCard(AbstractCard abstractCard) {
        this.abstractCard = abstractCard;
    }
}
