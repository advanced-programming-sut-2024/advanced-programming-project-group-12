package com.mygdx.game.model.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Gwent;
import com.mygdx.game.model.game.card.AbstractCard;
import com.mygdx.game.model.game.card.Action;
import com.mygdx.game.model.game.card.Hero;
import com.mygdx.game.model.game.card.PlayableCard;
import com.mygdx.game.model.user.Player;

import java.util.List;

public class CardTable extends Table {
    private final AbstractCard card;
    private Label powerLabel; // Reference to the power label
    private Image abilityImage; // Placeholder for the ability image
    private Image rangeImage; // Placeholder for the range label

    public CardTable(AbstractCard card) {
        this.card = card;
        setSize(90, 150); // Set the size of the card
        setBackground(new NinePatchDrawable(new NinePatch(new Texture(card.getInGameAssetName())))); // Set the card background

        // Power Type and Number
        Table powerTable = new Table();
        Drawable powerBackground = new NinePatchDrawable(new NinePatch(getPowerType()));
        powerTable.setBackground(powerBackground);
        if (card instanceof PlayableCard) {
            powerLabel = new Label(Integer.toString(((PlayableCard) card).getPower()), Gwent.singleton.skin);
            powerLabel.setColor(Color.SKY);

        }
        powerTable.add(powerLabel).padRight(22).padBottom(22);
        add(powerTable).size(55, 55).padBottom(65).padRight(40);

        // Ability and Range
        Table bottomTable = new Table();
        bottomTable.align(Align.center);
        // Check and add ability image if exists
        if (!card.getAction().equals(Action.NO_ACTION)) {
            abilityImage = new Image(getAbiltyTexture());
            bottomTable.add(abilityImage).size(30, 30);
        } else {
            bottomTable.add().size(30, 30);
        }
        // Check and add range label if exists
        if (getRangeTexture() != null) {
            rangeImage = new Image(getRangeTexture());
            bottomTable.add(rangeImage).size(30, 30).padLeft(7);
        } else {
            bottomTable.add().size(30, 30).padLeft(7);
        }
        row().padBottom(5);
        add(bottomTable).expandX().fillX();

        setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
    }

    public void updatePowerNumber(int newPower) {
        int oldPower = Integer.parseInt(String.valueOf(powerLabel.getText()));
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
        //TODO: Implement logic to return the correct ability texture based on the card
        String path = "icons/card_ability_" + card.getAction().toString().toLowerCase() + ".png";
        return new Texture(path);
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