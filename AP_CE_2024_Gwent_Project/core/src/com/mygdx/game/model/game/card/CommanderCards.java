package com.mygdx.game.model.game.card;

import com.mygdx.game.model.game.Faction;

import java.util.ArrayList;
import java.util.HashMap;

public enum CommanderCards {
    FOLTEST_SIEGE(new CommanderCard("foltest the siegemaster", "", Action.FOLTEST_SIEGE, null, null,Faction.NORTHERN_REALMS)),
    FOLTEST_STEEL(new CommanderCard("foltest the steel forged", "", Action.FOLTEST_STEEL, null, null,Faction.NORTHERN_REALMS)),
    FOLTEST_KING(new CommanderCard("foltest king of temeria", "", Action.FOLTEST_KING, null, null,Faction.NORTHERN_REALMS)),
    FOLTEST_LORD(new CommanderCard("foltest lord commander of the north", "", Action.SCORCH_S, null, null,Faction.NORTHERN_REALMS)),
    FOLTEST_SON(new CommanderCard("foltest son of medell", "", Action.SCORCH_R, null, null,Faction.NORTHERN_REALMS)),

    EMHYR_EMPEROR(new CommanderCard("emhyr emperor of nilfgaard", "", Action.EMHYR_EMPEROR, null, null,Faction.NILFGAARD)),
    EMHYR_EMPERIAL(new CommanderCard("emhyr his imperial majesty", "", Action.EMHYR_EMPERIAL, null, null,Faction.NILFGAARD)),
    EMHYR_WHITEFLAME(new CommanderCard("emhyr the white flame", "", Action.EMHYR_WHITEFLAME, null, null,Faction.NILFGAARD)),
    EMHYR_RELENTLESS(new CommanderCard("emhyr the relentless", "", Action.EMHYR_RELENTLESS, null, null,Faction.NILFGAARD)),
    EMHYR_INVADER(new CommanderCard("emhyr invader of the north", "", Action.EMHYR_INVADER, null, null,Faction.NILFGAARD)),

    ERIDIN_COMMANDER(new CommanderCard("eredin commander of the red riders", "", Action.ERIDIN_COMMANDER, null, null,Faction.MONSTERS)),
    ERIDIN_BRINGER(new CommanderCard("eredin bringer of death", "", Action.ERIDIN_COMMANDER, null, null,Faction.MONSTERS)),
    ERIDIN_DESTROYER(new CommanderCard("eredin destroyer of worlds", "", Action.ERIDIN_DESTROYER, null, null,Faction.MONSTERS)),
    ERIDIN_KING(new CommanderCard("eredin king of the wild hunt", "", Action.ERIDIN_KING, null, null,Faction.MONSTERS)),
    ERIDIN_TREACHEROUS(new CommanderCard("eredin the treacherous", "", Action.ERIDIN_TREACHEROUS, null, null,Faction.MONSTERS)),

    FRANCESCA_QUEEN(new CommanderCard("francesca queen of dol blathanna", "", Action.FRANCESCA_QUEEN, null, null,Faction.SCOIATAEL)),
    FRANCESCA_BEAUTIFUL(new CommanderCard("francesca the beautiful", "", Action.FRANCESCA_BEAUTIFUL, null, null,Faction.SCOIATAEL)),
    FRANCESCA_DAISY(new CommanderCard("francesca daisy of the vally", "", Action.FRANCESCA_DAISY, null, null,Faction.SCOIATAEL)),
    FRANCESCA_PUREBLOOD(new CommanderCard("francesca pureblood elf", "", Action.FRANCESCA_PUREBLOOD, null, null,Faction.SCOIATAEL)),
    FRANCESCA_HOPE(new CommanderCard("francesca  hope of the aen seidhe", "", Action.FRANCESCA_HOPE, null, null,Faction.SCOIATAEL)),

    CRACH_AN_CRAIT(new CommanderCard("crach an craite", "", Action.CRACH_AN_CRAITE, null, null,Faction.SKELLIGE)),
    KING_BRAN(new CommanderCard("king bran", "", Action.KING_BRAN, null, null,Faction.SKELLIGE)),

    ;


    private final static HashMap<Faction, ArrayList<CommanderCard>> factions = new HashMap<>();
    private final CommanderCard commanderCard;

    CommanderCards(CommanderCard commanderCard) {
        this.commanderCard = commanderCard;
    }

    static {
        for(Faction i: Faction.values()) {
            factions.put(i , new ArrayList<>());
        }
        for(CommanderCards i: CommanderCards.values()) {
            CommanderCard card = i.getCard();
            factions.get(card.getFaction()).add(card);
        }
    }

    public static ArrayList<CommanderCard> getFactionCardsByFaction(Faction faction) {
        return factions.get(faction);
    }

    private CommanderCard getCard() {return commanderCard;}

    public CommanderCard getAbstractCard() {
        return (CommanderCard) commanderCard.clone();
    }

    public static CommanderCard getCardByCardName(String cardName) {
        for(CommanderCards i: CommanderCards.values()) {
            if(i.getCard().getName().equals(cardName)) {
                return i.getCard();
            }
        }
        System.err.println("card not found: " + cardName);
        return null;
    }
}
