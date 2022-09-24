package com.mygdx.game.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Eurobusiness;
import com.mygdx.game.Pair;
import com.mygdx.game.board.Board;
import com.mygdx.game.board.Field;
import com.mygdx.game.logic.*;
import com.mygdx.game.owners.Player;
import org.jetbrains.annotations.NotNull;
import com.badlogic.gdx.scenes.scene2d.ui.Label;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainGameUI extends BaseUI {
    private final Board board;

    private final EndTurnController endTurnController;
    private final FieldController fieldController;
    private final MoveController moveController;
    private final RollController rollController;

    private final Player currentPlayer;
    private final HashMap<String, Label> stringLabelHashMap;
    private final TextureAtlas colorAtlas;

    private Sprite colorBox;
    private final PauseMenuUI pauseMenuUI;
    private boolean showPause;

    public MainGameUI(final @NotNull Eurobusiness game, final @NotNull Board board,
                      EndTurnController endTurnController, FieldController fieldController,
                      MoveController moveController, RollController rollController) {
        super(game);
        this.board = board;

        this.endTurnController = endTurnController;
        this.fieldController = fieldController;
        this.moveController = moveController;
        this.rollController = rollController;

        this.currentPlayer = game.getCurrentPlayer();

        this.stringLabelHashMap = new HashMap<>();
        this.colorAtlas = new TextureAtlas(Gdx.files.internal("textures/colors.atlas"));
        this.pauseMenuUI = new PauseMenuUI(game);
        this.pauseMenuUI.initializeStage();

        moveController.createMoveAction(board, currentPlayer);
        fieldController.createFieldAction(board, currentPlayer);

        initColorBox();
        initializeButtons();
    }

    @Override
    public void draw(float delta) {
        updateInfo();
        super.draw(delta);

        drawFieldsAndPlayers();
        colorFields();
        drawBuildings();
        drawPlayerBox();
        drawBankBox();

        moveController.process();
        fieldController.process();
    }

    @Override
    public void initializeStage() {
        super.initializeStage();
        setColorbox();
    }

    private void colorFields() {
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        for (Field field : board.getFields()) {
            if (field.getOwner() instanceof Player) {
                com.badlogic.gdx.graphics.Color color = ((Player) field.getOwner()).getColor();
                Gdx.gl.glEnable(GL20.GL_BLEND);
                Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(new Color(color.r, color.g, color.b, .2f));
                shapeRenderer.rect(field.getX(), field.getY(), field.getWidth(), field.getHeight());
                shapeRenderer.end();
            }
        }
    }

    private void drawBuildings() {
        for (Field field : board.getFields())
            field.drawBuildings();
    }

    private void drawFieldsAndPlayers() {
        batch.begin();
        for (Field field : board.getFields())
            field.getFieldSprite().draw(batch);
        for (Player player : game.players)
            player.getPawn().draw(batch);
        batch.end();
    }

    private void drawBankBox() {
        Label info = stringLabelHashMap.get("bankInfo");
        Label money = stringLabelHashMap.get("bankMoney");

        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);

        shapeRenderer.line(info.getX() - 10, info.getY() + info.getHeight() / 2f,
                money.getX() - 10, info.getY() + info.getHeight() / 2f);
        shapeRenderer.line(money.getX() - 10, info.getY() + info.getHeight() / 2f,
                money.getX() - 10, money.getY() - 20);
        shapeRenderer.line(money.getX() - 10, money.getY() - 20,
                money.getX() + money.getWidth() + 10, money.getY() - 20);
        shapeRenderer.line(money.getX() + money.getWidth() + 10, money.getY() - 20,
                money.getX() + money.getWidth() + 10, info.getY() + info.getHeight() / 2f);
        shapeRenderer.line(money.getX() + money.getWidth() + 10, info.getY() + info.getHeight() / 2f,
                info.getX() + info.getWidth() + 10, info.getY() + info.getHeight() / 2f);

        shapeRenderer.end();
    }

    private void drawPlayerBox() {
        Label infoLabel = stringLabelHashMap.get("infoLabel");
        Label colorLabel = stringLabelHashMap.get("playerColor");

        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);

        shapeRenderer.line(infoLabel.getX() - 10, infoLabel.getY() + infoLabel.getHeight() / 2f,
                1100, infoLabel.getY() + infoLabel.getHeight() / 2f);
        shapeRenderer.line(1100, infoLabel.getY() + infoLabel.getHeight() / 2f,
                1100, colorLabel.getY() - 20);
        shapeRenderer.line(1100, colorLabel.getY() - 20,
                infoLabel.getX() + infoLabel.getWidth() + 20, colorLabel.getY() - 20);
        shapeRenderer.line(infoLabel.getX() + infoLabel.getWidth() + 20, colorLabel.getY() - 20,
                infoLabel.getX() + infoLabel.getWidth() + 20, infoLabel.getY() + infoLabel.getHeight() / 2f);
        shapeRenderer.line(infoLabel.getX() + infoLabel.getWidth() + 20, infoLabel.getY() + infoLabel.getHeight() / 2f,
                infoLabel.getX() + infoLabel.getWidth() + 10, infoLabel.getY() + infoLabel.getHeight() / 2f);

        shapeRenderer.end();
    }

    private void initColorBox() {
        Array<Sprite> colorBoxes = colorAtlas.createSprites();

        ArrayList<Color> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        colors.add(Color.OLIVE);
        colors.add(Color.ORANGE);
        colors.add(Color.RED);

        int playerColorIndex = colors.indexOf(currentPlayer.getColor());

        colorBox = colorBoxes.get(playerColorIndex);
    }

    private void setColorbox() {
        colorBox.setSize(40, 40);
        colorBox.setPosition(1110 + stringLabelHashMap.get("playerColor").getWidth() + 20, 760);
    }

    private void updateInfo() {
        Label playerNameLabel = stringLabelHashMap.get("playerName");
        playerNameLabel.setText("Gracz: " + currentPlayer.getName());

        Label playerMoneyLabel = stringLabelHashMap.get("playerMoney");
        playerMoneyLabel.setText("Saldo: " + currentPlayer.getMoney());

        Label bankMoneyLabel = stringLabelHashMap.get("bankMoney");
        bankMoneyLabel.setText("Saldo: " + game.bank.getMoney());
    }

    @Override
    void drawShapeRenderer() {

    }

    @Override
    void drawBatch() {
        batch.begin();
        colorBox.draw(batch);
        batch.end();
        board.getCenter().draw(batch);
    }

    @Override
    void initializeLabels() {
        Label infoLabel = new Label("Informacje o graczu:", skin, "big-label");
        infoLabel.setPosition(1140, 1000);
        stringLabelHashMap.put("infoLabel", infoLabel);

        stage.addActor(infoLabel);

        Label playerNameLabel = new Label("Gracz:" + currentPlayer.getName(), skin, "default");
        playerNameLabel.setPosition(1110, 940);
        stringLabelHashMap.put("playerName", playerNameLabel);

        stage.addActor(playerNameLabel);

        Label playerIdLabel = new Label("Id: " + currentPlayer.getId(), skin, "default");
        playerIdLabel.setPosition(1110, 880);
        stringLabelHashMap.put("playerId", playerIdLabel);

        stage.addActor(playerIdLabel);

        Label playerMoneyLabel = new Label("Saldo:" + currentPlayer.getMoney(), skin, "default");
        playerMoneyLabel.setPosition(1110, 820);
        stringLabelHashMap.put("playerMoney", playerMoneyLabel);

        stage.addActor(playerMoneyLabel);

        Label playerColorLabel = new Label("Kolor: ", skin, "default");
        playerColorLabel.setPosition(1110, 760);
        stringLabelHashMap.put("playerColor", playerColorLabel);

        stage.addActor(playerColorLabel);

        Label bankInfoLabel = new Label("Bank", skin, "big-label");

        bankInfoLabel.setPosition(infoLabel.getX() + infoLabel.getWidth() + 175, infoLabel.getY());
        stringLabelHashMap.put("bankInfo", bankInfoLabel);

        Label bankMoneyLabel = new Label("Saldo: " + game.bank.getMoney(), skin, "default");
        bankMoneyLabel.setPosition(bankInfoLabel.getX() - 25, bankInfoLabel.getY() - bankMoneyLabel.getHeight() - 20);
        stringLabelHashMap.put("bankMoney", bankMoneyLabel);

        stage.addActor(bankInfoLabel);
        stage.addActor(bankMoneyLabel);
    }

    @Override
    void initializeButtons() {
        final TextButton rollButton = new TextButton("Rzuc", skin, "default");
        rollButton.setSize(200, 100);
        rollButton.setPosition(1100, 620);
        rollButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!rollController.isPlayerRolled()) {
                    final int MAX_ROLL = 6;
                    final int MIN_ROLL = 1;
                    Random cube = new Random();
                    Pair<Integer, Integer> firstRoll = new Pair<>(cube.nextInt(MAX_ROLL - MIN_ROLL) + MIN_ROLL,
                            cube.nextInt(MAX_ROLL - MIN_ROLL) + MIN_ROLL);
                    Pair<Integer, Integer> secondRoll = new Pair<>(cube.nextInt(MAX_ROLL - MIN_ROLL) + MIN_ROLL,
                            cube.nextInt(MAX_ROLL - MIN_ROLL) + MIN_ROLL);

                    int rolledValue = firstRoll.getFirst() + firstRoll.getSecond();

                    if (firstRoll.bothValuesEqual()) {
                        rolledValue += secondRoll.getFirst() + secondRoll.getSecond();
                    }

                    rollController.setRolledValue(rolledValue);
                    rollController.setPlayerRolled(true);
                    rollController.setTwoPairsRolled(firstRoll.bothValuesEqual() && secondRoll.bothValuesEqual());

                    rollController.informSubjects();
                }
            }
        });

        TextButton endTurnButton = new TextButton("Zakoncz ture", skin, "default");
        endTurnButton.setSize(200, 100);
        endTurnButton.setPosition(rollButton.getX() + rollButton.getWidth() + 20, rollButton.getY());
        endTurnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (endTurnController.isAllowFinishTour()) {
                    endTurnController.setFinishTour(true);
                    endTurnController.informSubjects();
                }
            }
        });

        stage.addActor(rollButton);
        stage.addActor(endTurnButton);
    }

    @Override
    void initializeTextFields() {

    }
}
