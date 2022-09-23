package com.mygdx.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Eurobusiness;
import com.mygdx.game.action.AuctionAction;
import com.mygdx.game.board.Field;
import com.mygdx.game.owners.Player;
import com.mygdx.game.windows.BaseWindow;
import com.mygdx.game.windows.FieldWindow;

import java.util.HashMap;
import java.util.Objects;

public class PlayerAuctionUI extends BaseUI{

    private final Field field;
    private final BaseWindow baseWindow;
    private final FieldWindow fieldWindow;
    private final AuctionAction auctionAction;
    private Player currentAuctioneer;
    private final HashMap<String, Label> auctionStringLabelMap;
    private final HashMap<String, TextField> stringTextFieldHashMap;

    public PlayerAuctionUI(Eurobusiness game, Field field, int price) {
        super(game);
        this.field = Objects.requireNonNull(field, "field is null");
        this.baseWindow = new BaseWindow(screenWidth / 2f - 200, screenHeight / 2f - 200, 400f, 400f, Color.BLACK);
        this.fieldWindow = new FieldWindow(screenWidth / 2f - 500, screenHeight / 2f - 200, 300f, 400f, Color.BLACK,
                field.getPropertySprite());
        this.auctionAction = new AuctionAction(game.players, game.getCurrentPlayer(), field);
        this.auctionAction.setupAuction(field, price);
        this.currentAuctioneer = this.auctionAction.getCurrentPlayer();
        this.auctionStringLabelMap = new HashMap<>();
        this.stringTextFieldHashMap = new HashMap<>();
        this.clearScreen = false;

    }

    @Override
    public void draw(float delta) {
        baseWindow.draw();
        fieldWindow.draw();
        super.draw(delta);
    }

    @Override
    void drawShapeRenderer() {

    }

    @Override
    void drawBatch() {

    }

    @Override
    void initializeLabels() {
        Player currentAuctioneer = auctionAction.getCurrentPlayer();
        Label playerNameLabel = new Label("Gracz: " + currentAuctioneer.getName(), skin, "default");
        Label playerIdLabel = new Label("Id: " + currentAuctioneer.getId(), skin, "default");
        Label playerMoney = new Label("Saldo: " + currentAuctioneer.getMoney(), skin, "default");
        Label priceLabel = new Label("Cena: " + auctionAction.getCurrentPrice(), skin, "default");

        auctionStringLabelMap.put("playerName", playerNameLabel);
        auctionStringLabelMap.put("playerId", playerIdLabel);
        auctionStringLabelMap.put("playerMoney", playerMoney);
        auctionStringLabelMap.put("price", priceLabel);

        playerNameLabel.setPosition(780, 700);
        playerIdLabel.setPosition(playerNameLabel.getX() + playerNameLabel.getWidth() + 60, 700);
        playerMoney.setPosition(780, 640);
        priceLabel.setPosition(780, 560);

        stage.addActor(playerNameLabel);
        stage.addActor(playerIdLabel);
        stage.addActor(playerMoney);
        stage.addActor(priceLabel);
    }

    @Override
    void initializeButtons() {
        TextButton cancel = new TextButton("Wycofaj", skin, "default");
        cancel.setSize(160, 80);
        cancel.setPosition(780, 360);

        TextButton bid = new TextButton("Licytuj", skin, "default");
        bid.setSize(160, 80);
        bid.setPosition(cancel.getX() + cancel.getWidth() + 20, 360);

        cancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                auctionAction.removeFromAuctioneers(auctionAction.getCurrentPlayer());
                auctionAction.nextPlayerFixed();
                // updateAuctionStage(auctionAction.getCurrentPlayer());
            }
        });

        bid.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (stringTextFieldHashMap.get("propose").getText().length() > 0) {
                    int amount = Integer.parseInt(stringTextFieldHashMap.get("propose").getText());
                    if (amount > auctionAction.getCurrentPrice()) {
                        auctionAction.updatePrice(amount);
                        auctionAction.nextPlayer();
                    }
                    // updateAuctionStage(auctionAction.getCurrentPlayer());
                }
            }
        });
        stage.addActor(cancel);
        stage.addActor(bid);
    }

    @Override
    void initializeTextFields() {
        TextField proposePrice = new TextField("", skin, "default");
        proposePrice.setPosition(780, 460);
        proposePrice.setSize(360, 60);

        proposePrice.setMaxLength(6);
        proposePrice.setTextFieldFilter((textField, c) -> Character.isDigit(c));
        stringTextFieldHashMap.put("propose", proposePrice);
        stage.addActor(proposePrice);
    }
}
