package com.mygdx.game.model.actors;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.mygdx.game.Gwent;
import com.mygdx.game.model.game.card.*;

import java.util.List;

public class CardTable extends Table {
    private final AbstractCard card;
    private Label powerLabel; // Reference to the power label
    private Image abilityImage; // Placeholder for the ability image
    private Image rangeImage; // Placeholder for the range label

    public CardTable(AbstractCard card) {
        this.card = card;
        setSize(85, 130); // Set the size of the card
        setBackground(new NinePatchDrawable(new NinePatch(new Texture(card.getInGameAssetName())))); // Set the card background

        // Power Type and Number
        Table powerTable = new Table();
        Drawable powerBackground = new NinePatchDrawable(new NinePatch(getPowerType()));
        powerTable.setBackground(powerBackground);
        if (card instanceof PlayableCard) {
            powerLabel = new Label(Integer.toString(((PlayableCard) card).getPower()), Gwent.singleton.skin);
            if(card instanceof Hero) {
                powerLabel.setColor(Color.WHITE);
            } else {
                powerLabel.setColor(Color.BLACK);
            }


        }
        powerTable.add(powerLabel).padRight(19).padBottom(19);
        add(powerTable).size(55, 55).padBottom(50).padRight(33);

        // Ability and Range
        Table bottomTable = new Table();
        bottomTable.align(Align.center);
        // Check and add ability image if exists
        if (!card.getAction().equals(Action.NO_ACTION) && !(card instanceof SpellCard)) {
            abilityImage = new Image(getAbiltyTexture());
            bottomTable.add(abilityImage).size(30, 30);
        } else {
            bottomTable.add().size(30, 30);
        }
        // Check and add range label if exists
        if (getRangeTexture() != null) {
            rangeImage = new Image(getRangeTexture());
            bottomTable.add(rangeImage).size(30, 30).padLeft(6);
        } else {
            bottomTable.add().size(30, 30).padLeft(6);
        }
        row().padBottom(5);
        add(bottomTable).expandX().fillX();
        setTouchable(Touchable.enabled);
    }

    public void updatePowerNumber(int newPower) {
        int oldPower = ((PlayableCard)card).getPower();
        if(oldPower > newPower) {
            powerLabel.setColor(Color.RED);
        } else if(oldPower < newPower) {
            powerLabel.setColor(Color.GOLD);
        }
        powerLabel.setText(Integer.toString(newPower));
    }

    public Texture getPowerType() {
        if (card instanceof PlayableCard && !(card instanceof Hero)) {
            return new Texture("icons/power_normal.png");
        } else if (card instanceof Hero) {
            return new Texture("icons/power_hero.png");
        } else {
            String path = "icons/power_" + card.getAction().toString().toLowerCase() + ".png";
            return new Texture(path);
        }
    }

    public Texture getAbiltyTexture() {

        String path = "icons/card_ability_" + card.getAction().toString().toLowerCase() + ".png";
        try {
            return new Texture(path);
        } catch (GdxRuntimeException e) {
            return new Texture("icons/power_normal.png");
        }
    }

    public Texture getRangeTexture() {
        if (!(card instanceof PlayableCard)) {
            return null; // Or some default texture if necessary
        }
        PlayableCard playableCard = (PlayableCard) card;
        List<Integer> rows = playableCard.getAllowableRows();
        // Example logic, adjust based on your actual range definitions
        if (rows.contains(0) && rows.contains(1)) {
            return new Texture("icons/card_row_agile.png");
        } else if (rows.contains(0)) {
            return new Texture("icons/card_row_close.png");
        } else if (rows.contains(1)) {
            return new Texture("icons/card_row_ranged.png");
        } else if (rows.contains(2)) {
            return new Texture("icons/card_row_siege.png");
        }
        return null; // Or some default texture if no range matches
    }
}