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
import com.mygdx.game.windows.BaseWindow;
import com.mygdx.game.windows.FieldWindow;

import java.util.HashMap;
import java.util.Objects;

public class SetupAuctionUI extends BaseUI{

    private final Field field;
    private final BaseWindow baseWindow;
    private final FieldWindow fieldWindow;
    private final HashMap<String, TextField> stringTextFieldHashMap;
    private int price;

    public SetupAuctionUI(Eurobusiness game, Field field) {
        super(game);
        this.field = Objects.requireNonNull(field, "field is null");
        this.baseWindow = new BaseWindow(screenWidth / 2f - 200, screenHeight / 2f - 200, 400f, 400f, Color.BLACK);
        this.fieldWindow = new FieldWindow(screenWidth / 2f - 500, screenHeight / 2f - 200, 300f, 400f, Color.BLACK,
                field.getPropertySprite());
        stringTextFieldHashMap = new HashMap<>();
        this.price = 0;
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
        Label info = new Label("Licytacja", skin, "big-label");
        info.setPosition(820, 700);

        stage.addActor(info);
    }

    @Override
    void initializeButtons() {
        TextButton backButton = new TextButton("Cofnij", skin, "default");
        backButton.setSize(160, 80);
        backButton.setPosition( 780, 440);

        stage.addActor(backButton);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // displayMainStage = true;
                // displaySettingPriceStage = false;
                // displayAuctionStage = false;
            }
        });

        TextButton startButton = new TextButton("Wystaw", skin, "default");
        startButton.setSize(160, 80);
        startButton.setPosition(960, 440);

        stage.addActor(startButton);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (stringTextFieldHashMap.get("price").getText().length() > 0) {
                    price = Integer.parseInt(stringTextFieldHashMap.get("price").getText());
                    // initAuctionStage();
                    // displayMainStage = false;
                    // displaySettingPriceStage = false;
                    // displayAuctionStage = true;
                }
            }
        });
    }

    @Override
    void initializeTextFields() {
        TextField priceTextField = new TextField("", skin, "default");
        priceTextField.setPosition(780, 540);
        priceTextField.setSize(360, 60);

        priceTextField.setMaxLength(6);
        priceTextField.setTextFieldFilter((textField, c) -> Character.isDigit(c));
        stringTextFieldHashMap.put("price", priceTextField);
        stage.addActor(priceTextField);
    }

    public int getPrice() {
        return price;
    }
}
