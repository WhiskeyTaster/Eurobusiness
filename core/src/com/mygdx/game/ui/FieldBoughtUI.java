package com.mygdx.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Eurobusiness;
import com.mygdx.game.logic.BuyController;
import com.mygdx.game.owners.Player;
import com.mygdx.game.windows.BaseWindow;

public class FieldBoughtUI extends BaseUI{
    private final BuyController buyController;
    private Player newOwner;
    private boolean closed;
    private final BaseWindow baseWindow;

    public FieldBoughtUI(Eurobusiness game, BuyController buyController, Player newOwner) {
        this(game, buyController);
        this.newOwner = newOwner;
    }

    public FieldBoughtUI(Eurobusiness game, BuyController buyController) {
        super(game);
        this.buyController = buyController;
        this.closed = false;
        this.clearScreen = false;
        this.baseWindow = new BaseWindow(screenWidth / 2f - 200f, screenHeight / 2f - 200f, 400f, 400f, Color.BLACK);
    }

    public void setNewOwner(Player newOwner) {
        this.newOwner = newOwner;
    }

    public boolean haveNewOwner() {
        return newOwner != null;
    }

    @Override
    public void draw(float delta) {
        if (!haveNewOwner())
            throw new NullPointerException();
        baseWindow.draw();
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
        Label infoLabel = new Label("Zakupiles pole!", skin, "big-label");
        Label playerNameLabel = new Label("Gracz: " + newOwner.getName(), skin, "default");
        Label playerIdLabel = new Label("Id: " + newOwner.getId(), skin, "default");
        Label finalPrice = new Label("Cena: " + buyController.getFinalPrice(), skin, "default");

        infoLabel.setPosition(820, 670);
        playerNameLabel.setPosition(780, 600);
        playerIdLabel.setPosition(playerNameLabel.getX() + playerNameLabel.getWidth() + 60, 600);
        finalPrice.setPosition(780, 550);

        stage.addActor(infoLabel);
        stage.addActor(playerNameLabel);
        stage.addActor(playerIdLabel);
        stage.addActor(finalPrice);
    }

    @Override
    void initializeButtons() {
        TextButton finishCommunicate = new TextButton("OK", skin, "default");
        finishCommunicate.setSize(240, 80);
        finishCommunicate.setPosition(830, 440);

        finishCommunicate.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                closed = true;
            }
        });

        stage.addActor(finishCommunicate);
    }

    public boolean isClosed() {
        return closed;
    }

    @Override
    void initializeTextFields() {

    }
}
