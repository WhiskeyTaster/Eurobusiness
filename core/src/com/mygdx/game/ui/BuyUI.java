package com.mygdx.game.ui;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Eurobusiness;
import com.mygdx.game.board.Field;
import com.mygdx.game.logic.BuyController;
import com.mygdx.game.owners.Player;
import com.mygdx.game.windows.BaseWindow;
import com.mygdx.game.windows.FieldWindow;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BuyUI extends BaseUI {

    private final Player currentPlayer;
    private final Field field;
    private final BuyController buyController;

    private final BaseWindow baseWindow;
    private final FieldWindow fieldWindow;

    public BuyUI(@NotNull Eurobusiness game, Field field, BuyController buyController) {
        super(game);
        this.currentPlayer = game.getCurrentPlayer();
        this.field = Objects.requireNonNull(field, "field is null");
        this.buyController = Objects.requireNonNull(buyController, "buyController is null");
        this.baseWindow = new BaseWindow(screenWidth / 2f - 200f, screenHeight / 2f - 200f, 400f, 400f, Color.BLACK);
        this.fieldWindow = new FieldWindow(screenWidth / 2f - 500f, screenHeight / 2f - 200f, 300f, 400f, Color.BLACK,
                field.getPropertySprite());
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
        Label playerName = new Label("Gracz: " + currentPlayer.getName(), skin, "default");
        Label playerId = new Label("Id: " + currentPlayer.getId(), skin, "default");
        Label playerMoney = new Label("Saldo: " + currentPlayer.getMoney(), skin, "default");
        Label price = new Label("Cena: " + field.getPrice(), skin, "default");

        playerName.setPosition(780, 700);
        playerId.setPosition(playerName.getX() + playerName.getWidth() + 20, 700);
        playerMoney.setPosition(780, 640);
        price.setPosition(780, 580);

        stage.addActor(playerName);
        stage.addActor(playerId);
        stage.addActor(playerMoney);
        stage.addActor(price);
    }

    @Override
    void initializeButtons() {
        TextButton cancel = new TextButton("Back", skin, "default");
        TextButton buy = new TextButton("Buy", skin, "default");

        cancel.setSize(120, 80);
        buy.setSize(120, 80);

        cancel.setPosition(780, 470);
        buy.setPosition(940, 470);

        cancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buyController.cancelBuying();
                buyController.startAuction();
                buyController.informSubjects();
            }
        });

        buy.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buyController.buyField();
                buyController.informSubjects();
            }
        });

        stage.addActor(cancel);
        stage.addActor(buy);
    }

    @Override
    void initializeTextFields() {

    }
}
