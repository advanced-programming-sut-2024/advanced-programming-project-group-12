package com.mygdx.game.model.card;

import com.mygdx.game.model.Action;
import com.mygdx.game.model.Faction;
import com.mygdx.game.model.card.SpellCard;
import com.mygdx.game.model.card.AbstractCard;

import java.util.Arrays;

public enum AllCards {
    //spell cards
    COMMANDER_HORN(new SpellCard("commander horn", "", Action.NO_ACTION, Arrays.asList(0,1,2), null, Faction.NORTHERN_REALMS)),
    SCORCH(new SpellCard("scorch", "", Action.SCORCH, null, null, Faction.SPECIAL)),

    //northern realms
    KIERA_METZ(new PlayableCard("kiera metz", "", Action.NO_ACTION, Arrays.asList(1), 5, null, Faction.NORTHERN_REALMS)),
    POOR_FUCKING_INFANTRY(new PlayableCard("poor fucking infantry", "", Action.TIGHT_BOND, Arrays.asList(0), 1, null, Faction.NORTHERN_REALMS)),
    BALLISTA(new PlayableCard("ballista", "", Action.NO_ACTION, Arrays.asList(2), 6, null, Faction.NORTHERN_REALMS)),
    BLUE_STRIPES_COMMANDO(new PlayableCard("blue stripes commando", "", Action.TIGHT_BOND, Arrays.asList(0), 4, null, Faction.NORTHERN_REALMS)),
    CATAPULT(new PlayableCard("catapult", "", Action.NO_ACTION, Arrays.asList(2), 8, null, Faction.NORTHERN_REALMS)),
    CRINFRID(new PlayableCard("crinfrid", "", Action.TIGHT_BOND, Arrays.asList(1), 5, null, Faction.NORTHERN_REALMS)),
    DETHMOLD(new PlayableCard("dethmold", "", Action.NO_ACTION, Arrays.asList(1), 6, null, Faction.NORTHERN_REALMS)),
    DIJKSTARA(new PlayableCard("dijkstara", "", Action.SPY, Arrays.asList(0), 4, null, Faction.NORTHERN_REALMS)),
    DUN_BANNER_MEDIC(new PlayableCard("dun banner medic", "", Action.MEDIC, Arrays.asList(2), 5, null, Faction.NORTHERN_REALMS)),
    ESTERAD(new Hero("esterad", "", Action.NO_ACTION, Arrays.asList(0), 10, null, Faction.NORTHERN_REALMS)),
    KAEDWENI_1(new PlayableCard("kaedweni1", "", Action.MORALE, Arrays.asList(2), 1, 1, Faction.NORTHERN_REALMS)),
    KAEDWENI_2(new PlayableCard("kaedweni2", "", Action.MORALE, Arrays.asList(2), 1, 2, Faction.NORTHERN_REALMS)),
    KAEDWENI_3(new PlayableCard("kaedweni3", "", Action.MORALE, Arrays.asList(2), 1, 3, Faction.NORTHERN_REALMS)),
    NATALIS(new Hero("natalis", "", Action.NO_ACTION, Arrays.asList(0), 10, null, Faction.NORTHERN_REALMS)),
    FILIPPA(new Hero("filippa", "", Action.NO_ACTION, Arrays.asList(1), 10, null, Faction.NORTHERN_REALMS)),
    RADANIAN_FOOT_SOLDIER1(new PlayableCard("radanian foot soldier1", "", Action.NO_ACTION, Arrays.asList(0), 1, null, Faction.NORTHERN_REALMS)),
    RADANIAN_FOOT_SOLDIER2(new PlayableCard("radanian foot soldier2", "", Action.NO_ACTION, Arrays.asList(0), 1, null, Faction.NORTHERN_REALMS)),
    SABRINA(new PlayableCard("sabrina", "", Action.NO_ACTION, Arrays.asList(1), 4, null, Faction.NORTHERN_REALMS)),
    SHELDON(new PlayableCard("sheldon", "", Action.NO_ACTION, Arrays.asList(1), 4, null, Faction.NORTHERN_REALMS)),
    SIEGE_TOWER(new PlayableCard("siege tower", "", Action.NO_ACTION, Arrays.asList(2), 6, null, Faction.NORTHERN_REALMS)),
    SIEGFRIED(new PlayableCard("siegfried", "", Action.NO_ACTION, Arrays.asList(0), 5, null, Faction.NORTHERN_REALMS)),
    SILE_DE_TANSARVILLE(new PlayableCard("sile de tansarville", "", Action.NO_ACTION, Arrays.asList(1), 5, null, Faction.NORTHERN_REALMS)),
    STENNIS(new PlayableCard("stennis", "", Action.SPY, Arrays.asList(0), 5, null, Faction.NORTHERN_REALMS)),
    THALER(new PlayableCard("thaler", "", Action.SPY, Arrays.asList(2), 1, null, Faction.NORTHERN_REALMS)),
    TREBUCHET_1(new PlayableCard("trebuchet1", "", Action.NO_ACTION, Arrays.asList(2), 6, 1, Faction.NORTHERN_REALMS)),
    TREBUCHET_2(new PlayableCard("trebuchet2", "", Action.NO_ACTION, Arrays.asList(2), 6, 2, Faction.NORTHERN_REALMS)),
    VERNON(new Hero("vernon", "", Action.NO_ACTION, Arrays.asList(0), 10, null, Faction.NORTHERN_REALMS)),
    VES(new PlayableCard("ves", "", Action.NO_ACTION, Arrays.asList(0), 5, null, Faction.NORTHERN_REALMS)),
    YARPEN(new PlayableCard("yarpen", "", Action.NO_ACTION, Arrays.asList(0), 2, null, Faction.NORTHERN_REALMS)),

    //neutral cards
    CIRILLA_FIONA_ELEN_RIANNON(new Hero("ciri", "", Action.NO_ACTION, Arrays.asList(0), 15, null, Faction.NEUTRAL)),
    YENNEFER_OF_VENGENBERG(new Hero("yennefer", "" , Action.MEDIC, Arrays.asList(1),7, null, Faction.NEUTRAL)),
    AVALLACH(new PlayableCard("avallach", "", Action.SPY, Arrays.asList(0), 0, null, Faction.NEUTRAL)),
    BOVINE_DEFENSE_FORCE(new PlayableCard("bovine defense force", "", Action.NO_ACTION, Arrays.asList(0), 8, null, Faction.NEUTRAL)),
    COW(new PlayableCard("cow", "", Action.COW, Arrays.asList(1), 0, null, Faction.NEUTRAL)),
    DANDELION(new PlayableCard("dandelion", "", Action.HORN, Arrays.asList(0), 2, null, Faction.NEUTRAL)),
    EMIEL(new PlayableCard("emiel", "", Action.NO_ACTION, Arrays.asList(0), 5, null, Faction.NEUTRAL)),
    GAUNTER_ODIMM_DARKNESS1(new PlayableCard("gaunter odimm darkness", "", Action.NO_ACTION, Arrays.asList(0), 4, null, Faction.NEUTRAL)),
    GAUNTER_ODIMM_DARKNESS2(new PlayableCard("gaunter odimm darkness", "", Action.NO_ACTION, Arrays.asList(2), 2, null, Faction.NEUTRAL)),
    GERALT(new PlayableCard("geralt", "", Action.NO_ACTION, Arrays.asList(0), 15, null, Faction.NEUTRAL)),
    OLGIERD(new PlayableCard("olgierd", "", Action.MORALE, Arrays.asList(0,1), 6, null, Faction.NEUTRAL)),
    TRISS(new PlayableCard("triss", "", Action.NO_ACTION, Arrays.asList(1), 7, null, Faction.NEUTRAL)),
    VESEMIR(new PlayableCard("vesemir", "", Action.NO_ACTION, Arrays.asList(0), 6, null, Faction.NEUTRAL)),
    VILLENTRETENMERTH(new PlayableCard("villentretenmerth", "", Action.SCORCH, Arrays.asList(0), 7, null, Faction.NEUTRAL)),
    ZOLTAN(new PlayableCard("zoltan", "", Action.NO_ACTION, Arrays.asList(0), 5, null, Faction.NEUTRAL)),


    //nilfgaard
    ALBRICH(new PlayableCard("albrich", "", Action.NO_ACTION, Arrays.asList(1), 2, null, Faction.NILFGAARD)),
    ASSIRE(new PlayableCard("assire", "", Action.NO_ACTION, Arrays.asList(1), 6, null, Faction.NILFGAARD)),
    BLACK_INFANTRY_ARCHER1(new PlayableCard("black infantry archer1", "", Action.NO_ACTION, Arrays.asList(1), 10, null, Faction.NILFGAARD)),
    BLACK_INFANTRY_ARCHER2(new PlayableCard("infantry archer2", "", Action.NO_ACTION, Arrays.asList(1), 10, null, Faction.NILFGAARD)),
    CAHIR(new PlayableCard("cahir", "", Action.NO_ACTION, Arrays.asList(0), 6, null, Faction.NILFGAARD)),
    CYNTHIA(new PlayableCard("cynthia", "", Action.NO_ACTION, Arrays.asList(1), 4, null, Faction.NILFGAARD)),
    ETOLIAN_AUXILIARY_ARCHER1(new PlayableCard("etolian auxiliary archer1", "", Action.MEDIC, Arrays.asList(1), 1, null, Faction.NILFGAARD)),
    ETOLIAN_AUXILIARY_ARCHER2(new PlayableCard("auxiliary archer2", "", Action.MEDIC, Arrays.asList(1), 1, null, Faction.NILFGAARD)),
    FRINGILLA(new PlayableCard("fringilla", "", Action.NO_ACTION, Arrays.asList(1), 6, null, Faction.NILFGAARD)),
    HEAVY_ZERRIKANIAN(new PlayableCard("heavy zerrikanian", "", Action.NO_ACTION, Arrays.asList(1), 10, null, Faction.NILFGAARD)),
    IMPERAL_BRIGADE_GUARD(new PlayableCard("imperal brigade guard", "", Action.TIGHT_BOND, Arrays.asList(0), 3, null, Faction.NILFGAARD)),
    LETHO(new Hero("letho", "", Action.NO_ACTION, Arrays.asList(0), 10, null, Faction.NILFGAARD)),
    MENNO(new Hero("menno", "", Action.MEDIC, Arrays.asList(0), 10, null, Faction.NILFGAARD)),
    MOORVRAN(new Hero("moorvran", "", Action.NO_ACTION, Arrays.asList(2), 10, null, Faction.NILFGAARD)),
    MORTEISEN(new PlayableCard("morteisen", "", Action.NO_ACTION, Arrays.asList(0), 3, null, Faction.NILFGAARD)),
    NAUZICA_CAVALRY_RIDER(new PlayableCard("nauzica cavalry rider", "", Action.TIGHT_BOND, Arrays.asList(0), 2, null, Faction.NILFGAARD)),
    PUTTKAMMER(new PlayableCard("puttkammer", "", Action.NO_ACTION, Arrays.asList(1), 3, null, Faction.NILFGAARD)),
    RAINFAN(new PlayableCard("rainfan", "", Action.NO_ACTION, Arrays.asList(0), 4, null, Faction.NILFGAARD)),
    RENUALD(new PlayableCard("renuald", "", Action.NO_ACTION, Arrays.asList(1), 5, null, Faction.NILFGAARD)),
    ROTTEN_MANGONEL(new PlayableCard("rotten mangonel", "", Action.NO_ACTION, Arrays.asList(2), 3, null, Faction.NILFGAARD)),
    SHILARD(new PlayableCard("shilard", "", Action.SPY, Arrays.asList(0), 7, null, Faction.NILFGAARD)),
    SIEGE_ENGINEER(new PlayableCard("siege engineer", "", Action.NO_ACTION, Arrays.asList(2), 6, null, Faction.NILFGAARD)),
    SIEGE_SUPPORT(new PlayableCard("siege support", "", Action.MEDIC, Arrays.asList(2), 0, null, Faction.NILFGAARD)),
    STEFAN(new PlayableCard("stefan", "", Action.SPY, Arrays.asList(0), 9, null, Faction.NILFGAARD)),
    SWEERS(new PlayableCard("sweers", "", Action.NO_ACTION, Arrays.asList(1), 2, null, Faction.NILFGAARD)),
    TIBOR(new Hero("tibor", "", Action.NO_ACTION, Arrays.asList(1), 10, null, Faction.NILFGAARD)),
    VANHEMAR(new PlayableCard("vanhemar", "", Action.NO_ACTION, Arrays.asList(1), 4, null, Faction.NILFGAARD)),
    VATTIER(new PlayableCard("vattier", "", Action.SPY, Arrays.asList(0), 4, null, Faction.NILFGAARD)),
    VREEMDE(new PlayableCard("vreemde", "", Action.NO_ACTION, Arrays.asList(0), 2, null, Faction.NILFGAARD)),
    YOUNG_EMISSARY_1(new PlayableCard("young emmissary", "", Action.TIGHT_BOND, Arrays.asList(0), 5, 1, Faction.NILFGAARD)),
    YOUNG_EMISSARY_2(new PlayableCard("young emmissary", "", Action.TIGHT_BOND, Arrays.asList(0), 5, 2, Faction.NILFGAARD)),
    ZERRIKANIAN(new PlayableCard("zerrikanian", "", Action.NO_ACTION, Arrays.asList(2), 5, null, Faction.NILFGAARD)),


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
