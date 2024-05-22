package com.mygdx.game.model;

import java.util.ArrayList;

public class GameBoard {
    private ArrayList<RangedCombatCard> rangedCombatCards;
    private ArrayList<CloseCombatCard> closeCombatCards;
    private ArrayList<CommanderCard> commanderCards;
    private ArrayList<SpellCard> spellCards;

    public GameBoard() {
        rangedCombatCards = new ArrayList<RangedCombatCard>();
        closeCombatCards = new ArrayList<CloseCombatCard>();
        commanderCards = new ArrayList<CommanderCard>();
        spellCards = new ArrayList<SpellCard>();
    }

    public ArrayList<RangedCombatCard> getRangedCombatCards() {
        return rangedCombatCards;
    }

    public void setRangedCombatCards(ArrayList<RangedCombatCard> rangedCombatCards) {
        this.rangedCombatCards = rangedCombatCards;
    }

    public ArrayList<CloseCombatCard> getCloseCombatCards() {
        return closeCombatCards;
    }

    public void setCloseCombatCards(ArrayList<CloseCombatCard> closeCombatCards) {
        this.closeCombatCards = closeCombatCards;
    }

    public ArrayList<CommanderCard> getCommanderCards() {
        return commanderCards;
    }

    public void setLeaderCards(ArrayList<CommanderCard> commanderCards) {
        this.commanderCards = commanderCards;
    }

    public ArrayList<SpellCard> getSpellCards() {
        return spellCards;
    }

    public void setSpellCards(ArrayList<SpellCard> spellCards) {
        this.spellCards = spellCards;
    }
}
