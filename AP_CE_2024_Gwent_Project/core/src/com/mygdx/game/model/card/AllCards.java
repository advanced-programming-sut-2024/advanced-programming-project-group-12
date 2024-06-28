package com.mygdx.game.model.card;

import com.mygdx.game.model.Action;
import com.mygdx.game.model.Faction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public enum AllCards {
    //special cards
    COMMANDER_HORN(new SpellCard("commander horn", "", Action.HORN, Arrays.asList(0,1,2), null, Faction.SPECIAL)),
    SCORCH(new SpellCard("scorch", "", Action.SCORCH, null, null, Faction.SPECIAL) {
        @Override
        public void place(int row) {}
    }),
    DECOY(new Decoy("decoy", "", Action.DECOY, Arrays.asList(0,1,2), null, Faction.SPECIAL)),
    MARDROEME(new SpellCard("mardroeme", "", Action.MUSHROOM, Arrays.asList(0,1,2), null, Faction.SPECIAL)),

    //weather cards
    CLEAR(new SpellCard("clear", "", Action.CLEAR, Arrays.asList(3), null, Faction.WEATHER)),
    FOG(new SpellCard("fog", "", Action.FOG, Arrays.asList(3), null, Faction.WEATHER)),
    FROST(new SpellCard("frost", "", Action.FROST, Arrays.asList(3), null, Faction.WEATHER)),
    RAIN(new SpellCard("rain", "", Action.RAIN, Arrays.asList(3), null, Faction.WEATHER)),
    STORM(new SpellCard("storm", "", Action.STORM, Arrays.asList(3), null, Faction.WEATHER)),


    //northern realms
    KEIRA_METZ(new PlayableCard("keira metz", "", Action.NO_ACTION, Arrays.asList(1), 5, null, Faction.NORTHERN_REALMS)),
    POOR_FUCKING_INFANTRY(new PlayableCard("poor fucking infantry", "", Action.TIGHT_BOND, Arrays.asList(0), 1, null, Faction.NORTHERN_REALMS)),
    BALLISTA(new PlayableCard("ballista", "", Action.NO_ACTION, Arrays.asList(2), 6, null, Faction.NORTHERN_REALMS)),
    BLUE_STRIPES_COMMANDO(new PlayableCard("blue stripes commando", "", Action.TIGHT_BOND, Arrays.asList(0), 4, null, Faction.NORTHERN_REALMS)),
    CATAPULT(new PlayableCard("catapult", "", Action.NO_ACTION, Arrays.asList(2), 8, null, Faction.NORTHERN_REALMS)),
    CRINFRID(new PlayableCard("crinfrid", "", Action.TIGHT_BOND, Arrays.asList(1), 5, null, Faction.NORTHERN_REALMS)),
    DETHMOLD(new PlayableCard("dethmold", "", Action.NO_ACTION, Arrays.asList(1), 6, null, Faction.NORTHERN_REALMS)),
    DIJKSTARA(new PlayableCard("dijkstara", "", Action.SPY, Arrays.asList(0), 4, null, Faction.NORTHERN_REALMS)),
    DUN_BANNER_MEDIC(new PlayableCard("dun banner medic", "", Action.MEDIC, Arrays.asList(2), 5, null, Faction.NORTHERN_REALMS)),
    ESTERAD(new Hero("esterad", "", Action.NO_ACTION, Arrays.asList(0), 10, null, Faction.NORTHERN_REALMS)),
    KAEDWENI_1(new PlayableCard("kaedweni", "", Action.MORALE, Arrays.asList(2), 1, 1, Faction.NORTHERN_REALMS)),
    KAEDWENI_2(new PlayableCard("kaedweni", "", Action.MORALE, Arrays.asList(2), 1, 2, Faction.NORTHERN_REALMS)),
    KAEDWENI_3(new PlayableCard("kaedweni", "", Action.MORALE, Arrays.asList(2), 1, 3, Faction.NORTHERN_REALMS)),
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
    BOVINE_DEFENSE_FORCE(new PlayableCard("bovine defense force", "", Action.NO_ACTION, Arrays.asList(0), 8, null, Faction.UNUSABLE)),

    CIRILLA_FIONA_ELEN_RIANNON(new Hero("ciri", "", Action.NO_ACTION, Arrays.asList(0), 15, null, Faction.NEUTRAL)),
    YENNEFER_OF_VENGENBERG(new Hero("yennefer", "" , Action.MEDIC, Arrays.asList(1),7, null, Faction.NEUTRAL)),
    AVALLACH(new PlayableCard("avallach", "", Action.SPY, Arrays.asList(0), 0, null, Faction.NEUTRAL)),
    COW(new PlayableCard("cow", "", Action.COW, Arrays.asList(1), 0, null, Faction.NEUTRAL,(PlayableCard) BOVINE_DEFENSE_FORCE.getCard())),
    DANDELION(new PlayableCard("dandelion", "", Action.HORN, Arrays.asList(0), 2, null, Faction.NEUTRAL)),
    EMIEL(new PlayableCard("emiel", "", Action.NO_ACTION, Arrays.asList(0), 5, null, Faction.NEUTRAL)),
    GAUNTER_ODIMM_DARKNESS_1(new PlayableCard("gaunter odimm", "", Action.MUSKET, Arrays.asList(0), 4, null, Faction.NEUTRAL)),
    GAUNTER_ODIMM(new PlayableCard("gaunter odimm", "", Action.MUSKET, Arrays.asList(2), 2, null, Faction.NEUTRAL)),
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

    //monsters
    ARACHAS_BEHEMOTH(new PlayableCard("arachas", "", Action.MUSKET, Arrays.asList(2), 6, null, Faction.MONSTERS)),
    ARACHAS_1(new PlayableCard("arachas_1", "", Action.MUSKET, Arrays.asList(0), 4, null, Faction.MONSTERS)),
    ARACHAS_2(new PlayableCard("arachas_2", "", Action.MUSKET, Arrays.asList(0), 4, null, Faction.MONSTERS)),
    ARACHAS_3(new PlayableCard("arachas_3", "", Action.MUSKET, Arrays.asList(0), 4, null, Faction.MONSTERS)),
    BOTCHLING(new PlayableCard("botchling", "", Action.NO_ACTION, Arrays.asList(0), 4, null, Faction.MONSTERS)),
    BRUXA(new PlayableCard("vampire", "", Action.MUSKET, Arrays.asList(0), 4, null, Faction.MONSTERS)),
    CELAENO_HARPY(new PlayableCard("celaeno harpy", "", Action.NO_ACTION, Arrays.asList(0,1), 2, null, Faction.MONSTERS)),
    COCKATRICE(new PlayableCard("cockatrice", "", Action.NO_ACTION, Arrays.asList(1), 2, null, Faction.MONSTERS)),
    CRONE_BREWESS(new PlayableCard("crone", "", Action.MUSKET, Arrays.asList(0), 6, null, Faction.MONSTERS)),
    CRONE_WEAVESS(new PlayableCard("crone", "", Action.MUSKET, Arrays.asList(0), 6, null, Faction.MONSTERS)),
    CRONE_WHISPESS(new PlayableCard("crone", "", Action.MUSKET, Arrays.asList(0), 6, null, Faction.MONSTERS)),
    DRAUG(new Hero("draug", "", Action.NO_ACTION, Arrays.asList(0), 10, null, Faction.MONSTERS)),
    EARTH_ELEMENTAL(new PlayableCard("earth elemental", "", Action.NO_ACTION, Arrays.asList(2), 6, null, Faction.MONSTERS)),
    EKKIMA(new PlayableCard("vampire", "", Action.MUSKET, Arrays.asList(0), 4, null, Faction.MONSTERS)),
    ENDREGA(new PlayableCard("endrega", "", Action.NO_ACTION, Arrays.asList(1), 2, null, Faction.MONSTERS)),
    FIEND(new PlayableCard("fiend", "", Action.NO_ACTION, Arrays.asList(0), 6, null, Faction.MONSTERS)),
    FIRE_ELEMENTAL(new PlayableCard("fire elemental", "", Action.NO_ACTION, Arrays.asList(2), 6, null, Faction.MONSTERS)),
    FLEDER(new PlayableCard("vampire", "", Action.MUSKET, Arrays.asList(0), 4, null, Faction.MONSTERS)),
    FOGLING(new PlayableCard("fogling", "", Action.NO_ACTION, Arrays.asList(0), 2, null, Faction.MONSTERS)),
    FORKTAIL(new PlayableCard("forktail", "", Action.NO_ACTION, Arrays.asList(0), 5, null, Faction.MONSTERS)),
    FRIGHTENER(new PlayableCard("frightener", "", Action.NO_ACTION, Arrays.asList(0), 5, null, Faction.MONSTERS)),
    FROST_GIANT(new PlayableCard("frost giant", "", Action.NO_ACTION, Arrays.asList(1), 5, null, Faction.MONSTERS)),
    GARGOYLE(new PlayableCard("gargoyle", "", Action.NO_ACTION, Arrays.asList(1), 2, null, Faction.MONSTERS)),
    GARKAIN(new PlayableCard("vampire", "", Action.MUSKET, Arrays.asList(0), 4, null, Faction.MONSTERS)),
    GOUL_1(new PlayableCard("goul", "", Action.MUSKET, Arrays.asList(0), 1, null, Faction.MONSTERS)),
    GOUL_2(new PlayableCard("goul", "", Action.MUSKET, Arrays.asList(0), 1, null, Faction.MONSTERS)),
    GOUL_3(new PlayableCard("goul", "", Action.MUSKET, Arrays.asList(0), 1, null, Faction.MONSTERS)),
    GRAVEHAG(new PlayableCard("gravehag", "", Action.NO_ACTION, Arrays.asList(1), 5, null, Faction.MONSTERS)),
    GRYFFIN(new PlayableCard("gryffin", "", Action.NO_ACTION, Arrays.asList(0), 5, null, Faction.MONSTERS)),
    HARPY(new PlayableCard("harpy", "", Action.NO_ACTION, Arrays.asList(0,1), 2, null, Faction.MONSTERS)),
    IMLERITH(new Hero("imlerith", "", Action.NO_ACTION, Arrays.asList(0), 10, null, Faction.MONSTERS)),
    KATAKAN(new PlayableCard("vampire", "", Action.MUSKET, Arrays.asList(0), 5, null, Faction.MONSTERS)),
    KAYRAN(new Hero("kayran", "", Action.MORALE, Arrays.asList(0,1), 8, null, Faction.MONSTERS)),
    LESHEN(new Hero("leshen", "", Action.NO_ACTION, Arrays.asList(1), 10, null, Faction.MONSTERS)),
    NEKKER_1(new PlayableCard("nekker", "", Action.MUSKET, Arrays.asList(0), 2, null, Faction.MONSTERS)),
    NEKKER_2(new PlayableCard("nekker", "", Action.MUSKET, Arrays.asList(0), 2, null, Faction.MONSTERS)),
    NEKKER_3(new PlayableCard("nekker", "", Action.MUSKET, Arrays.asList(0), 2, null, Faction.MONSTERS)),
    PLAGUE_MAIDEN(new PlayableCard("plague maiden", "", Action.NO_ACTION, Arrays.asList(0), 5, null, Faction.MONSTERS)),
    TOAD(new PlayableCard("toad", "", Action.SCORCH, Arrays.asList(1), 7, null, Faction.MONSTERS)),
    WEREWOLF(new PlayableCard("werewolf", "", Action.NO_ACTION, Arrays.asList(0), 5, null, Faction.MONSTERS)),
    WYVERN(new PlayableCard("wyvern", "", Action.NO_ACTION, Arrays.asList(1), 2, null, Faction.MONSTERS)),

    //scoiatael
    BARCLAY(new PlayableCard("barclay", "", Action.NO_ACTION, Arrays.asList(0,1), 6, null, Faction.SCOIATAEL)),
    CIARAN(new PlayableCard("ciaran", "", Action.NO_ACTION, Arrays.asList(0,1), 3, null, Faction.SCOIATAEL)),
    DENNIS(new PlayableCard("dennis", "", Action.NO_ACTION, Arrays.asList(0), 6, null, Faction.SCOIATAEL)),
    DOL_BLATHANNA_INFANTRY_1(new PlayableCard("dol blathanna infantry", "", Action.NO_ACTION, Arrays.asList(0,1), 6, null, Faction.SCOIATAEL)),
    DOL_BLATHANNA_INFANTRY_2(new PlayableCard("dol blathanna infantry", "", Action.NO_ACTION, Arrays.asList(0,1), 6, null, Faction.SCOIATAEL)),
    DOL_BLATHANNA_INFANTRY_3(new PlayableCard("dol blathanna infantry", "", Action.NO_ACTION, Arrays.asList(0,1), 6, null, Faction.SCOIATAEL)),
    DOL_BLATHANNA_ARCHER(new PlayableCard("dol blathanna archer", "", Action.NO_ACTION, Arrays.asList(1), 4, null, Faction.SCOIATAEL)),
    DWARF_1(new PlayableCard("dwarf", "", Action.MUSKET, Arrays.asList(0), 3, null, Faction.SCOIATAEL)),
    DWARF_2(new PlayableCard("dwarf", "", Action.MUSKET, Arrays.asList(0), 3, null, Faction.SCOIATAEL)),
    DWARF_3(new PlayableCard("dwarf", "", Action.MUSKET, Arrays.asList(0), 3, null, Faction.SCOIATAEL)),
    EITHNE(new Hero("eithne", "", Action.NO_ACTION, Arrays.asList(1), 10, null, Faction.SCOIATAEL)),
    ELVEN_SKIRMISHER_1(new PlayableCard("elven skirmisher", "", Action.MUSKET, Arrays.asList(1), 2, null, Faction.SCOIATAEL)),
    ELVEN_SKIRMISHER_2(new PlayableCard("elven skirmisher", "", Action.MUSKET, Arrays.asList(1), 2, null, Faction.SCOIATAEL)),
    ELVEN_SKIRMISHER_3(new PlayableCard("elven skirmisher", "", Action.MUSKET, Arrays.asList(1), 2, null, Faction.SCOIATAEL)),
    SCOIATAEL_FILAVAN(new PlayableCard("scoiatael filavan", "", Action.NO_ACTION, Arrays.asList(0), 6, null, Faction.SCOIATAEL)),
    HAVEKAR_HEALER_1(new PlayableCard("havekar healer", "", Action.MEDIC, Arrays.asList(1), 0, null, Faction.SCOIATAEL)),
    HAVEKAR_HEALER_2(new PlayableCard("havekar healer", "", Action.MEDIC, Arrays.asList(1), 0, null, Faction.SCOIATAEL)),
    HAVEKAR_HEALER_3(new PlayableCard("havekar healer", "", Action.MEDIC, Arrays.asList(1), 0, null, Faction.SCOIATAEL)),
    HAVEKAR_SMUGGLER_1(new PlayableCard("havekar smuggler", "", Action.MUSKET, Arrays.asList(0), 5, null, Faction.SCOIATAEL)),
    HAVEKAR_SMUGGLER_2(new PlayableCard("havekar smuggler", "", Action.MUSKET, Arrays.asList(0), 5, null, Faction.SCOIATAEL)),
    HAVEKAR_SMUGGLER_3(new PlayableCard("havekar smuggler", "", Action.MUSKET, Arrays.asList(0), 5, null, Faction.SCOIATAEL)),
    IDA(new PlayableCard("ida", "", Action.NO_ACTION, Arrays.asList(1), 6, null, Faction.SCOIATAEL)),
    IORVETH(new Hero("iorveth", "", Action.NO_ACTION, Arrays.asList(1), 10, null, Faction.SCOIATAEL)),
    ISENGRIM(new Hero("isengrim", "", Action.MORALE, Arrays.asList(0), 10, null, Faction.SCOIATAEL)),
    MAHAKAM_1(new PlayableCard("mahakam", "", Action.NO_ACTION, Arrays.asList(0), 5, null, Faction.SCOIATAEL)),
    MAHAKAM_2(new PlayableCard("mahakam", "", Action.NO_ACTION, Arrays.asList(0), 5, null, Faction.SCOIATAEL)),
    MAHAKAM_3(new PlayableCard("mahakam", "", Action.NO_ACTION, Arrays.asList(0), 5, null, Faction.SCOIATAEL)),
    MAHAKAM_4(new PlayableCard("mahakam", "", Action.NO_ACTION, Arrays.asList(0), 5, null, Faction.SCOIATAEL)),
    MAHAKAM_5(new PlayableCard("mahakam", "", Action.NO_ACTION, Arrays.asList(0), 5, null, Faction.SCOIATAEL)),
    MILVA(new PlayableCard("milva", "", Action.MORALE, Arrays.asList(0), 10, null, Faction.SCOIATAEL)),
    RIORDAIN(new PlayableCard("riordain", "", Action.NO_ACTION, Arrays.asList(1), 1, null, Faction.SCOIATAEL)),
    SAESENTHESSIS(new Hero("saesenthessis", "", Action.NO_ACTION, Arrays.asList(1), 10, null, Faction.SCOIATAEL)),
    SCHIRRU(new PlayableCard("schirru", "", Action.SCORCH, Arrays.asList(2), 8, null, Faction.SCOIATAEL)),
    TORUVIEL(new PlayableCard("toruviel", "", Action.NO_ACTION, Arrays.asList(1), 2, null, Faction.SCOIATAEL)),
    VRIHEDD_BRIGADE_1(new PlayableCard("vrihedd brigade", "", Action.NO_ACTION, Arrays.asList(0,1), 5, null, Faction.SCOIATAEL)),
    VRIHEDD_BRIGADE_2(new PlayableCard("vrihedd brigade", "", Action.NO_ACTION, Arrays.asList(0,1), 5, null, Faction.SCOIATAEL)),
    VRIHEDD_CADET(new PlayableCard("vrihedd cadet", "", Action.NO_ACTION, Arrays.asList(1), 4, null, Faction.SCOIATAEL)),
    YAEVINN(new PlayableCard("yaevinn", "", Action.NO_ACTION, Arrays.asList(0,1), 6, null, Faction.SCOIATAEL)),

    //skellige
    HEMDALL(new PlayableCard("hemdall", "", Action.NO_ACTION, Arrays.asList(0), 11, null, Faction.UNUSABLE)),

    VILDKAARL(new PlayableCard("vildkaarl", "", Action.MORALE, Arrays.asList(0), 14, null, Faction.SKELLIGE)),
    BERSERKER(new PlayableCard("berserker", "", Action.BEAR, Arrays.asList(0), 4, null, Faction.SKELLIGE,(PlayableCard) VILDKAARL.getCard())),
    BIRNA(new PlayableCard("birna", "", Action.MEDIC, Arrays.asList(0), 2, null, Faction.SKELLIGE)),
    BLUEBOY(new PlayableCard("blueboy", "", Action.NO_ACTION, Arrays.asList(0), 6, null, Faction.SKELLIGE)),
    BROKVA_ARCHER(new PlayableCard("brokva archer", "", Action.NO_ACTION, Arrays.asList(1), 6, null, Faction.SKELLIGE)),
    CERYS(new Hero("cerys", "", Action.MUSKET, Arrays.asList(0), 10, null, Faction.SKELLIGE)),
    CRAITE_WARRIOR(new PlayableCard("craite warrior", "", Action.TIGHT_BOND, Arrays.asList(0), 6, null, Faction.SKELLIGE)),
    DUMUN_PIRATE(new PlayableCard("dumun pirate", "", Action.SCORCH, Arrays.asList(0), 6, null, Faction.SKELLIGE)),
    DONAR(new PlayableCard("donar", "", Action.NO_ACTION, Arrays.asList(0), 4, null, Faction.SKELLIGE)),
    DRAIG(new PlayableCard("draig", "", Action.HORN, Arrays.asList(0), 2, null, Faction.SKELLIGE)),
    ERMION(new Hero("ermion", "", Action.MUSHROOM, Arrays.asList(1), 8, null, Faction.SKELLIGE)),
    HEYMAEY(new PlayableCard("heymaey", "", Action.NO_ACTION, Arrays.asList(0), 4, null, Faction.SKELLIGE)),
    HJALMAR(new PlayableCard("hjalmar", "", Action.NO_ACTION, Arrays.asList(1), 10, null, Faction.SKELLIGE)),
    HOLGER(new PlayableCard("holger", "", Action.NO_ACTION, Arrays.asList(2), 4, null, Faction.SKELLIGE)),
    KAMBI(new PlayableCard("kambi", "", Action.COW, Arrays.asList(0), 0, null, Faction.SKELLIGE,(PlayableCard) HEMDALL.getCard())),
    LIGHT_LONGSHIP(new PlayableCard("light longship", "", Action.MUSKET, Arrays.asList(1), 4, null, Faction.SKELLIGE)),
    MADMAN_LUGOS(new PlayableCard("madman lugos", "", Action.NO_ACTION, Arrays.asList(0), 6, null, Faction.SKELLIGE)),
    OLAF(new PlayableCard("olaf", "", Action.MORALE, Arrays.asList(0,1), 12, null, Faction.SKELLIGE)),
    SHIELD_MAIDEN_1(new PlayableCard("shield maiden", "", Action.TIGHT_BOND, Arrays.asList(0), 4, 1, Faction.SKELLIGE)),
    SHIELD_MAIDEN_2(new PlayableCard("shield maiden", "", Action.TIGHT_BOND, Arrays.asList(0), 4, 2, Faction.SKELLIGE)),
    SHIELD_MAIDEN_3(new PlayableCard("shield maiden", "", Action.TIGHT_BOND, Arrays.asList(0), 4, 3, Faction.SKELLIGE)),
    SVANRIGE(new PlayableCard("svanrige", "", Action.NO_ACTION, Arrays.asList(0), 4, null, Faction.SKELLIGE)),
    TORDARROCH(new PlayableCard("tordarroch", "", Action.NO_ACTION, Arrays.asList(0), 4, null, Faction.SKELLIGE)),
    UDALRYK(new PlayableCard("udalryk", "", Action.NO_ACTION, Arrays.asList(0), 4, null, Faction.SKELLIGE)),
    WAR_LONGSHIP(new PlayableCard("war longship", "", Action.TIGHT_BOND, Arrays.asList(2), 6, null, Faction.SKELLIGE)),
    YOUNG_VILDKAARL(new PlayableCard("young vildkaarl", "", Action.TIGHT_BOND, Arrays.asList(1), 8, null, Faction.SKELLIGE)),
    YOUNG_BERSERKER(new PlayableCard("young berseker", "", Action.BEAR, Arrays.asList(1), 2, null, Faction.SKELLIGE,(PlayableCard) YOUNG_VILDKAARL.getCard())),

    ;
    private final AbstractCard abstractCard;
    private final static HashMap<Faction, ArrayList<AbstractCard>> faction = new HashMap<>();

    //commander cards
    AllCards(AbstractCard abstractCard) {
        this.abstractCard = abstractCard;
    }

    static {
        for(Faction i: Faction.values()) {
            faction.put(i , new ArrayList<>());
        }
        for(AllCards i: AllCards.values()) {
            AbstractCard card = i.getCard();
            faction.get(card.getFaction()).add(card);
        }
    }

    public static ArrayList<AbstractCard> getFactionCardsByFaction(Faction faction) {
        return AllCards.faction.get(faction);
    }

    private AbstractCard getCard() {return abstractCard;}

    public AbstractCard getAbstractCard() {
        return abstractCard.clone();
    }

}
