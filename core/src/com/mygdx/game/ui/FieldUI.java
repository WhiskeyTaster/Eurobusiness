package com.mygdx.game.ui;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Eurobusiness;
import com.mygdx.game.board.Field;
import com.mygdx.game.owners.Bank;
import com.mygdx.game.owners.Player;
import com.mygdx.game.properties.City;
import com.mygdx.game.windows.BaseWindow;
import com.mygdx.game.windows.FieldWindow;

import java.util.HashMap;
import java.util.Objects;

public class FieldUI extends BaseUI{
    private Field selectedField;
    private final Player currentPlayer;
    private final Bank bank;
    private final BaseWindow baseWindow;
    private final FieldWindow fieldWindow;
    private final HashMap<String, Label> stringLabelHashMap;
    private final HashMap<String, TextButton> stringTextButtonHashMap;

    public FieldUI(Eurobusiness game, Field selectedField) {
        super(game);
        this.currentPlayer = Objects.requireNonNull(game.getCurrentPlayer(), "currentPlayer is null");
        this.selectedField = Objects.requireNonNull(selectedField, "selectedField is null");
        this.bank = Objects.requireNonNull(game.bank, "bank is null");

        this.baseWindow = new BaseWindow(screenWidth / 2f, screenHeight / 2f, 400f, 400f, Color.BLACK);
        this.fieldWindow = new FieldWindow(screenWidth / 2f, screenHeight / 2f, 400f, 400f, Color.BLACK,
                selectedField.getPropertySprite());

        this.stringLabelHashMap = new HashMap<>();
        this.stringTextButtonHashMap = new HashMap<>();
    }

    private void updateButton() {
        if (selectedField.isMortgaged())
            stringTextButtonHashMap.get("mortgage").setText("Odkup");
        else
            stringTextButtonHashMap.get("mortgage").setText("Zastaw");
    }

    @Override
    public void draw(float delta) {
        super.draw(delta);
        baseWindow.draw();
        fieldWindow.draw();
        updateButton();
    }

    @Override
    void drawShapeRenderer() {

    }

    @Override
    void drawBatch() {

    }

    @Override
    void initializeLabels() {
        Label infoLabel = new Label("Zarzadzanie polem", skin, "default");
        infoLabel.setPosition(baseWindow.getX() + baseWindow.widthPadding(),
                baseWindow.getY() + baseWindow.getHeight() - baseWindow.heightPadding());
        stringLabelHashMap.put("infoLabel", infoLabel);
        stage.addActor(infoLabel);
    }

    @Override
    void initializeButtons() {
        // TODO: change positions of buttons
        TextButton mortgageButton = new TextButton("Zastaw", skin, "default");
        if (selectedField.isMortgaged())
            mortgageButton.setText("Odkup");

        mortgageButton.setSize(160, 80);
        mortgageButton.setPosition(780, 580);
        stringTextButtonHashMap.put("mortgage", mortgageButton);
        stage.addActor(mortgageButton);

        mortgageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedField.isMortgaged()) {
                    if (currentPlayer.haveMoney((int) (1.1 * selectedField.getMortgage()))) {
                        currentPlayer.pay(bank, (int) (1.1 * selectedField.getMortgage()));
                        selectedField.setMortgaged(false);
                    }
                }
                else {
                    if (bank.haveMoney(selectedField.getMortgage())) {
                        bank.pay(currentPlayer, selectedField.getMortgage());
                        selectedField.setMortgaged(true);
                    }
                }
            }
        });

        TextButton sellFieldButton = new TextButton("Sprzedaj", skin, "default");
        sellFieldButton.setSize(160, 80);
        sellFieldButton.setPosition(960, 580);
        stage.addActor(sellFieldButton);

        sellFieldButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
               // initSettingPriceStage(currentPlayer);
            }
        });

        TextButton cancelButton = new TextButton("Zamknij", skin, "default");
        cancelButton.setSize(340, 80);
        stage.addActor(cancelButton);

        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedField = null;
            }
        });

        if (selectedField.getProperty() instanceof City) {
            TextButton buyHouseButton = new TextButton("Kup dom", skin, "default");
            buyHouseButton.setSize(160, 80);
            buyHouseButton.setPosition(780, 490);
            stage.addActor(buyHouseButton);

            TextButton buyHotelButton = new TextButton("Kup hotel", skin, "default");
            buyHotelButton.setSize(160, 80);
            buyHotelButton.setPosition(960, 490);
            stage.addActor(buyHotelButton);
            cancelButton.setPosition(780, 390);
        }
        else {
            cancelButton.setPosition(780, 490);
        }
    }

    @Override
    void initializeTextFields() {

    }
}
