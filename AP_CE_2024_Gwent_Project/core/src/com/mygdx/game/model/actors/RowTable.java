package com.mygdx.game.model.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Gwent;
import com.mygdx.game.model.game.Row;
import com.mygdx.game.model.game.card.AbstractCard;
import com.mygdx.game.model.game.card.PlayableCard;
import com.mygdx.game.model.game.card.SpellCard;
import com.mygdx.game.model.network.Client;
import com.mygdx.game.model.user.Player;
import com.mygdx.game.view.screen.GameScreen;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class RowTable extends Table {
    private final List<CardActor> cardActors;
    private final HornArea hornArea;
    private final int rowNumber;
    private final Image background;
    private Image overlayImage;
    private RepeatAction blinkAction;
    private final boolean side;
    /*
    true : player
    false : enemy
     */
    public RowTable(int rowNumber, boolean side, ArrayList<PlayableCard> cards, String pathToOverLayImage, HashSet<SpellCard> spellCards) {
        Player player;
        if(side) {
            player = Client.getInstance().getGame().getCurrentPlayer();
        } else {
            player = Client.getInstance().getGame().getOpposition();
        }
        Row row = Client.getInstance().getGame().getGameBoard().getRowForPlayer(rowNumber, player);
        this.setSize(680, 110);
        this.cardActors = new ArrayList<>();
        this.hornArea = new HornArea();
        for(PlayableCard card : cards) {
            CardActor newCard = new CardActor(card);
            newCard.updatePower(row.calculatePowerOfPlayableCard(card));
            this.add(newCard.getCardTable()).size(85,130);
        }

        for(SpellCard spellCard : spellCards) {
            hornArea.addCard(spellCard);
        }
        // Create the hornArea and set its position
        hornArea.setPosition(this.getX() - hornArea.getWidth(), this.getY());

        // Add the hornArea to the RowTable
        this.addActor(hornArea);
        this.rowNumber = rowNumber;
        this.side = side;
        this.setTouchable(Touchable.enabled);
        // Create the background image and set its size and position
        background = new Image(new Texture("bg/empty.png"));
        background.setSize(this.getWidth(), this.getHeight());
        background.setPosition(0, 0);
        background.setColor(Color.CLEAR);
        // Create a semi-transparent yellow color
        Color highlightColor = new Color(Color.YELLOW.r, Color.YELLOW.g, Color.YELLOW.b, 0.5f);

        // Create the blinking action
        blinkAction = Actions.forever(Actions.sequence(Actions.color(highlightColor, 1.5f), Actions.color(Color.CLEAR, 1.5f)));
        overlayImage = new Image(new Texture("icons/overlay_rain.png"));
        overlayImage.setSize(this.getWidth(), this.getHeight());
        overlayImage.setPosition(0, 0);
        overlayImage.setColor(Color.CLEAR);
        this.addActor(overlayImage);
        if(pathToOverLayImage != null) {
            showOverLayImage(pathToOverLayImage);
        } else {
            hideOverLayImage();
        }
        this.addActor(background);
        setPosition();
        this.center();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Call the super.draw method to draw all actors
        super.draw(batch, parentAlpha);

        // Draw each card in the row
        for (CardActor cardActor : cardActors) {
            cardActor.draw(batch, parentAlpha);
        }
    }
    public List<CardActor> getCards() {
        return cardActors;
    }


    private void setPosition() {
        if (side) {
            this.setPosition(590, 480 - ((rowNumber) * 125));
        } else {
            this.setPosition(590, 485 + ((rowNumber + 1) * 125));
        }
    }

    public boolean getSide() {
        return side;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public HornArea getHornArea() {
        return hornArea;
    }
    public void highlight() {
        if (blinkAction != null && !background.hasActions()) {
            background.addAction(blinkAction);
        }
    }
    public void unhighlight() {
        // Check if blinkAction is not null before removing it from the background
        if (blinkAction != null && background.hasActions()) {
            background.removeAction(blinkAction);
        }
        Color highlightColor = new Color(Color.YELLOW.r, Color.YELLOW.g, Color.YELLOW.b, 0.5f);
        blinkAction = Actions.forever(Actions.sequence(Actions.color(highlightColor, 1.5f),
                Actions.color(Color.CLEAR, 1.5f)));

        background.setColor(Color.CLEAR);
        hornArea.unhighlight();
    }
    public void showOverLayImage(String overlayImagePath) {
        overlayImage.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(overlayImagePath)))));
        overlayImage.setColor(Color.WHITE);
    }
    public void hideOverLayImage() {
        overlayImage.setColor(Color.CLEAR);
    }

}