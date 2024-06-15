package com.mygdx.game.model.card;

import com.mygdx.game.model.Action;
import com.mygdx.game.model.card.SpellCard;
import com.mygdx.game.model.card.AbstractCard;

import java.util.Arrays;

public enum AllCards {
    //spell cards
    COMMANDER_HORN(new SpellCard("commander horn", "", Action.NO_ACTION, Arrays.asList(0,1,2), null)),
    SCORCH(new SpellCard("scorch", "", Action.SCORCH, null, null)),
    //northern realms
    KIERA_METZ(new PlayableCard("kiera metz", "", Action.NO_ACTION, Arrays.asList(1), 5, null)),
    POOR_FUCKING_INFANTRY(new PlayableCard("poor fucking infantry", "", Action.TIGHT_BOND, Arrays.asList(0), 1, null)),

    //neutral cards
    CIRILLA_FIONA_ELEN_RIANNON(new Hero("cirilla fiona elen fiannon", "", Action.NO_ACTION, Arrays.asList(0), 15, null)),
    YENNEFER_OF_VENGENBERG(new Hero("yennefer of vengenberg", "" , Action.MEDIC, Arrays.asList(1),7, null)),

    //leader cards
    FOLTEST1(new CommanderCard("foltest king of temeria", "" , Action.FOLTEST1, null)),
    ;
    private AbstractCard abstractCard;

    //commander cards

    AllCards(AbstractCard abstractCard) {
        this.abstractCard = abstractCard;
    }

    public AbstractCard getAbstractCard() {
        return abstractCard.clone();
    }
}
