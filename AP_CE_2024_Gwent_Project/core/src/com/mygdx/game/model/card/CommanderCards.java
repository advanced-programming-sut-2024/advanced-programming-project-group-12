package com.mygdx.game.model.card;

import com.mygdx.game.model.Faction;

import java.util.ArrayList;
import java.util.HashMap;

public enum CommanderCards {
    FOLTEST1(new CommanderCard("foltest the siegemaster", "", null, null, 1,Faction.NORTHERN_REALMS));


    private final static HashMap<Faction, ArrayList<CommanderCard>> factions = new HashMap<>();
    private CommanderCard commanderCard;

    CommanderCards(CommanderCard commanderCard) {
        this.commanderCard = commanderCard;
    }

    static {
        for(Faction i: Faction.values()) {
            factions.put(i, new ArrayList<>());
        }
        for(CommanderCards i: CommanderCards.values()) {
            CommanderCard card = i.getCard();
            factions.get(card.getFaction()).add(card);
        }
    }

    public static ArrayList<CommanderCard> getFactionCardByFaction(Faction faction) {
        return factions.get(faction);
    }

    private CommanderCard getCard() {return commanderCard;}

    public CommanderCard getAbstractCard() {
        return (CommanderCard) commanderCard;
    }

}
