package com.mygdx.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Eurobusiness;
import com.mygdx.game.board.Field;
import com.mygdx.game.logic.PayController;
import com.mygdx.game.owners.Player;
import com.mygdx.game.properties.City;
import com.mygdx.game.properties.Railway;
import com.mygdx.game.properties.Utility;
import com.mygdx.game.windows.BaseWindow;
import com.mygdx.game.windows.FieldWindow;

import java.util.HashMap;
import java.util.Objects;

public class PayUI extends BaseUI{
    private final Field field;
    private final PayController payController;
    private final Player player;
    private final Player fieldOwner;
    private final HashMap<String, Label> stringLabelHashMap;
    private final BaseWindow baseWindow;
    private final FieldWindow fieldWindow;
    private boolean closed;
    private boolean outOfMoney;


    public PayUI(Eurobusiness game, Field field, PayController payController) {
        super(game);
        this.field = Objects.requireNonNull(field, "field is null");
        this.payController = Objects.requireNonNull(payController, "payController is null");
        this.player = Objects.requireNonNull(game.getCurrentPlayer(), "player is null");
        this.fieldOwner = Objects.requireNonNull((Player) field.getOwner(), "owner is null");
        this.stringLabelHashMap = new HashMap<>();
        this.baseWindow = new BaseWindow(screenWidth / 2f - 200, screenHeight / 2f - 200, 400f, 400f, Color.BLACK);
        this.fieldWindow = new FieldWindow(screenWidth / 2f - 500, screenHeight / 2f - 200, 300, 400f, Color.BLACK,
                field.getPropertySprite());
        this.clearScreen = false;
        this.closed = false;
        this.outOfMoney = false;
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
        Label payingPlayerLabel = new Label("Gracz: " + player.getName(), skin, "default");
        Label payingPlayerIdLabel = new Label("Id: " + player.getId(), skin, "default");
        Label playerSaldoLabel = new Label("Saldo: " + player.getMoney(), skin, "default");

        payingPlayerLabel.setPosition(780, 700);
        payingPlayerIdLabel.setPosition(payingPlayerLabel.getX() + payingPlayerLabel.getWidth() + 60, 700);
        playerSaldoLabel.setPosition(780, 660);

        Label ownerLabel = new Label("Wlasciciel: " + fieldOwner.getName(), skin, "default");
        Label ownerId = new Label("Id: " + fieldOwner.getId(), skin, "default");

        ownerLabel.setPosition(780, 610);
        ownerId.setPosition(ownerLabel.getX() + ownerLabel.getWidth() + 60, 610);

        Label fee = new Label("Do zaplaty: " + payController.getAmountToPay(), skin, "default");
        stringLabelHashMap.put("fee", fee);

        stage.addActor(payingPlayerLabel);
        stage.addActor(payingPlayerIdLabel);
        stage.addActor(playerSaldoLabel);
        stage.addActor(ownerLabel);
        stage.addActor(ownerId);
        stage.addActor(fee);
    }

    @Override
    void initializeButtons() {
        TextButton okButton = new TextButton("Zaplac", skin, "default");
        okButton.setSize(200, 80);

        stage.addActor(okButton);

        Label fee = stringLabelHashMap.get("fee");

        if (field.getProperty() instanceof City) {
            City city = (City) field.getProperty();
            Label housesLabel = new Label("Domy: " + city.getHouses(), skin, "default");
            Label hotelsLabel = new Label("Hotel: " + city.getHotels(), skin, "default");

            housesLabel.setPosition(780, 560);
            hotelsLabel.setPosition(780, 520);

            stage.addActor(housesLabel);
            stage.addActor(hotelsLabel);

            fee.setPosition(780, 480);
            okButton.setPosition(850, 370);

        }
        if (field.getProperty() instanceof Railway) {
            Label ownedRailwaysLabel = new Label("Posiadane koleje: " + fieldOwner.ownedRailways(), skin, "default");

            ownedRailwaysLabel.setPosition(780, 560);

            stage.addActor(ownedRailwaysLabel);

            fee.setPosition(780, 520);
            okButton.setPosition(850, 410);
        }
        if (field.getProperty() instanceof Utility) {
            Label ownedUtilitiesLabel = new Label("Posiadane zaklady: " + fieldOwner.ownedUtilities(), skin, "default");
            Label rolledValueLabel = new Label("Suma oczek: " + payController.getRolledValue(), skin, "default");

            ownedUtilitiesLabel.setPosition(780, 560);
            rolledValueLabel.setPosition(780, 520);

            stage.addActor(ownedUtilitiesLabel);
            stage.addActor(rolledValueLabel);

            fee.setPosition(780, 480);
            okButton.setPosition(850, 350);
        }
        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (payController.isPlayerOutOfMoney()) {
                    setClosed(true);
                    setOutOfMoney(true);
                }
                else {
                    payController.transaction(player, fieldOwner);
                    setClosed(true);
                }
            }
        });
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setOutOfMoney(boolean outOfMoney) {
        this.outOfMoney = outOfMoney;
    }

    public boolean isOutOfMoney() {
        return outOfMoney;
    }

    @Override
    void initializeTextFields() {

    }
}
