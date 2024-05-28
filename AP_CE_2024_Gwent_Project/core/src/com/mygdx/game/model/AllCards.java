package com.mygdx.game.model;

public enum AllCards {
    //spell cards
    COMMANDER_HORN(new SpellCard("commander horn", "", Action.NO_ACTION)),
    SCORCH(new SpellCard("scorch", "", Action.SCORCH))
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
