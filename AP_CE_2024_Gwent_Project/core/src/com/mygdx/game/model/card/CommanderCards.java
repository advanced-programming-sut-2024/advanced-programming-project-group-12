package com.mygdx.game.model.card;

import com.mygdx.game.model.Action;
import com.mygdx.game.model.Faction;

import java.util.ArrayList;
import java.util.HashMap;

public enum CommanderCards {
    FOLTEST_SIEGE(new CommanderCard("foltest the siegemaster", "", Action.FOLTEST_SIEGE, null, 1,Faction.NORTHERN_REALMS)),
    FOLTEST_STEEL(new CommanderCard("foltest the steel forged", "", Action.FOLTEST_STEEL, null, 1,Faction.NORTHERN_REALMS)),
    FOLTEST_KING(new CommanderCard("foltest king of temeria", "", Action.FOLTEST_KING, null, 1,Faction.NORTHERN_REALMS)),
    FOLTEST_LORD(new CommanderCard("foltest lord commander of the north", "", Action.SCORCH_S, null, 1,Faction.NORTHERN_REALMS)),
    FOLTEST_SON(new CommanderCard("foltest son of medell", "", Action.SCORCH_R, null, 1,Faction.NORTHERN_REALMS)),

    EMHYR1(new CommanderCard("foltest son of medell", "", null, null, 1,Faction.NORTHERN_REALMS)),

    ;


    private final static HashMap<Faction, ArrayList<CommanderCard>> factions = new HashMap<>();
    private CommanderCard commanderCard;

    CommanderCards(CommanderCard commanderCard) {
        this.commanderCard = commanderCard;
    }

    static {
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
        return (CommanderCard) commanderCard.clone();
    }

}
