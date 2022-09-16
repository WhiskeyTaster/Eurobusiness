package com.mygdx.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Eurobusiness;
import com.mygdx.game.board.Board;
import com.mygdx.game.board.Field;
import com.mygdx.game.logic.BuyController;
import com.mygdx.game.owners.Player;
import com.mygdx.game.windows.BaseWindow;
import com.mygdx.game.windows.FieldWindow;

import java.util.HashMap;
import java.util.Objects;

public class AuctionUI extends BaseUI{
    private final Board board;
    private final Field field;
    private final BuyController buyController;

    private Player currentAuctioneer;
    private final HashMap<String, Label> stringLabelMap;
    private final HashMap<String, TextField> stringTextFieldHashMap;

    private final BaseWindow baseWindow;
    private final FieldWindow fieldWindow;

    private boolean closeUI;

    public AuctionUI(Eurobusiness game, Board board, Field field, BuyController buyController) {
        super(game);
        this.board = Objects.requireNonNull(board, "board is null");
        this.field = Objects.requireNonNull(field, "field is null");
        this.buyController = Objects.requireNonNull(buyController, "BuyController is null");
        this.currentAuctioneer = buyController.getCurrentAuctioneer();
        this.stringLabelMap = new HashMap<>();
        this.stringTextFieldHashMap = new HashMap<>();

        this.baseWindow = new BaseWindow(screenWidth / 2f - 200f, screenHeight / 2f - 200f, 400f, 400f, Color.BLACK);
        this.fieldWindow = new FieldWindow(screenWidth / 2f - 500f, screenHeight / 2f - 200f, 300f, 400f, Color.BLACK,
                field.getPropertySprite());
        this.clearScreen = false;
        this.closeUI = false;
    }

    @Override
    public void draw(float delta) {
        baseWindow.draw();
        fieldWindow.draw();
        updateInfo();
        super.draw(delta);
        closeUI = buyController.isAuctionActionFinished();
    }

    @Override
    void drawShapeRenderer() {

    }

    @Override
    void drawBatch() {

    }

    @Override
    void initializeLabels() {
        Label playerNameLabel = new Label("Gracz: " + currentAuctioneer.getName(), skin, "default");
        Label playerIdLabel = new Label("Id: " + currentAuctioneer.getId(), skin, "default");
        Label playerMoney = new Label("Saldo: " + currentAuctioneer.getMoney(), skin, "default");
        Label priceLabel = new Label("Cena: " + buyController.getCurrentPrice(), skin, "default");

        stringLabelMap.put("playerName", playerNameLabel);
        stringLabelMap.put("playerId", playerIdLabel);
        stringLabelMap.put("playerMoney", playerMoney);
        stringLabelMap.put("price", priceLabel);

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
        TextButton cancel = new TextButton("End", skin, "default");
        cancel.setSize(160, 80);
        cancel.setPosition(780, 360);

        TextButton bid = new TextButton("Buy", skin, "default");
        bid.setSize(160, 80);
        bid.setPosition(cancel.getX() + cancel.getWidth() + 20, 360);

        cancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buyController.passAuction(buyController.getCurrentAuctioneer());
                currentAuctioneer = buyController.getCurrentAuctioneer();
            }
        });

        bid.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (stringTextFieldHashMap.get("propose").getText().length() > 0) {
                    buyController.bidAuction(Integer.parseInt(stringTextFieldHashMap.get("propose").getText()));
                    currentAuctioneer = buyController.getCurrentAuctioneer();
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

    private void updateInfo() {
        stringLabelMap.get("playerName").setText("Gracz: " + currentAuctioneer.getName());
        stringLabelMap.get("playerId").setText("Id: " + currentAuctioneer.getId());
        stringLabelMap.get("playerMoney").setText("Saldo: " + currentAuctioneer.getMoney());
        stringLabelMap.get("price").setText("Cena: " + buyController.getCurrentPrice());
    }

    public boolean shouldCloseUI() {
        return closeUI;
    }
}
