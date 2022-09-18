package com.mygdx.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Eurobusiness;
import com.mygdx.game.logic.PayController;
import com.mygdx.game.owners.Player;
import com.mygdx.game.windows.BaseWindow;

import java.util.Objects;

public class OutOfMoneyUI extends BaseUI{

    private final PayController payController;
    private final Player player;
    private final BaseWindow baseWindow;
    private boolean closed;

    public OutOfMoneyUI(Eurobusiness game, PayController payController) {
        super(game);
        this.payController = Objects.requireNonNull(payController, "payController is null");
        this.baseWindow = new BaseWindow(screenWidth / 2f - 200, screenHeight / 2f - 200, 400, 400, Color.BLACK);
        this.player = Objects.requireNonNull(game.getCurrentPlayer(), "player is null");
        this.closed = false;
        this.clearScreen = false;
    }

    @Override
    public void draw(float delta) {
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
        Label infoLabel = new Label("Brak pieniedzy na oplaty!", skin, "default");
        Label payingPlayerLabel = new Label("Gracz: " + player.getName(), skin, "default");
        Label payingPlayerIdLabel = new Label("Id: " + player.getId(), skin, "default");
        Label playerSaldoLabel = new Label("Saldo: " + player.getMoney(), skin, "default");

        infoLabel.setPosition(780, 700);
        payingPlayerLabel.setPosition(780, 650);
        payingPlayerIdLabel.setPosition(payingPlayerLabel.getX() + payingPlayerLabel.getWidth() + 60, 650);
        playerSaldoLabel.setPosition(780, 610);

        Label fee = new Label("Do zaplaty: " + payController.getAmountToPay(), skin, "default");
        fee.setPosition(780, 570);

        stage.addActor(infoLabel);
        stage.addActor(payingPlayerLabel);
        stage.addActor(payingPlayerIdLabel);
        stage.addActor(playerSaldoLabel);
        stage.addActor(fee);
    }

    @Override
    void initializeButtons() {
        TextButton okButton = new TextButton("OK", skin, "default");
        okButton.setSize(200, 80);
        okButton.setPosition(850, 430);
        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                payController.setActionFinished(true);
                setClosed(true);
            }
        });

        stage.addActor(okButton);
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public boolean isClosed() {
        return closed;
    }

    @Override
    void initializeTextFields() {

    }
}
