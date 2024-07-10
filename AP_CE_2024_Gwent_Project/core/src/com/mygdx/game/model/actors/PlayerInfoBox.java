package com.mygdx.game.model.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Gwent;

import java.util.ArrayList;
import java.util.List;

public class PlayerInfoBox {
    private static final String AVATAR_TEXTURE_PATH = "icons/profile.png";
    private static final String BORDER_TEXTURE_PATH = "icons/icon_player_border.png";
    private static final String CARDS_TEXTURE_PATH = "icons/icon_card_count.png";
    private static final String GEM_ON_PATH = "icons/icon_gem_on.png";
    private static final String GEM_OFF_PATH = "icons/icon_gem_off.png";

    private Table infoTable;
    private Image avatar;
    private Image cards;
    private List<Image> gems;
    private Table gemsTable;
    private Label cardsRemainingLabel;

    public PlayerInfoBox(int numberOfCardsRemaining, String username, String faction, int playerHealth) {
        infoTable = new Table();
        gemsTable = new Table();
        // Create an Image as the background for the notification box
        Image background = new Image(new Texture("bg/Blur-Effect.png"));
        background.setColor(1, 1, 1, 1);
        background.setFillParent(true); // Fill the entire table with the background image
        infoTable.setSize(370, 170);
        infoTable.setBackground(background.getDrawable());

        avatar = new Image(new Texture(AVATAR_TEXTURE_PATH));
        cards = new Image(new Texture(CARDS_TEXTURE_PATH));
        gems = new ArrayList<>();

        cardsRemainingLabel = new Label(Integer.toString(numberOfCardsRemaining), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        infoTable.add(avatar).width(80).height(80);
        infoTable.add(cards).width(30).height(30);
        infoTable.add(cardsRemainingLabel).width(100).height(30);
        infoTable.row(); // Move to the next row
        infoTable.add(new Label(faction, new Label.LabelStyle(new BitmapFont(), Color.WHITE))).width(100).height(30);
        infoTable.row(); // Move to the next row
        infoTable.row(); // Move to the next row
        infoTable.add(new Label(username, new Label.LabelStyle(new BitmapFont(), Color.WHITE))).width(100).height(30);
        displayHealth(playerHealth);
        infoTable.add(gemsTable);

    }

    private void displayHealth(int playerHealth) {
        gemsTable.clear(); // Clear the gems table
        for (int i = 0; i < playerHealth; i++) {
            Image gem = new Image(new Texture(GEM_ON_PATH));
            gems.add(gem);
            gemsTable.add(gem).width(30).height(30).padRight(10);
        }
        for (int i = 0; i < 2 - playerHealth; i++) {
            Image gem = new Image(new Texture(GEM_OFF_PATH));
            gems.add(gem);
            gemsTable.add(gem).width(30).height(30).padRight(10);
        }
    }
    public Table getInfoTable() {
        return infoTable;
    }
    public void setPosition(int x, int y) {
        infoTable.setPosition(x, y);
    }


}
