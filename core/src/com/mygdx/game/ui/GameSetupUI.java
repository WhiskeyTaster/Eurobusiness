package com.mygdx.game.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Eurobusiness;

// TODO: ADD PADDING DEPENDS OF RESOLUTION

import java.util.HashMap;

public class GameSetupUI extends BaseUI{

    private final HashMap<String, Label> labelHashMap;
    private final HashMap<String, TextField> textFieldHashMap;

    private final int MAX_PLAYERS = 5;
    private final int MIN_PLAYERS = 2;
    private final int MIN_HUMAN_PLAYERS = 1;
    private final int MIN_AI_PLAYERS = 0;

    private int aiPlayersNum;
    private int humanPlayersNum;
    private int playersNum;

    public GameSetupUI(final Eurobusiness game) {
        super(game);
        this.labelHashMap = new HashMap<>();
        this.textFieldHashMap = new HashMap<>();

        this.aiPlayersNum = 0;
        this.humanPlayersNum = 2;
        this.playersNum = this.aiPlayersNum + this.humanPlayersNum;
    }

    @Override
    void drawShapeRenderer() {

    }

    @Override
    void drawBatch() {

    }

    @Override
    void initializeLabels() {
        Label enterName = new Label("Gracze", skin, "big-label");
        enterName.setPosition(widthPadding, screenHeight - heightPadding);
        stage.addActor(enterName);
        labelHashMap.put("enterName", enterName);

        Label humanPlayers = new Label("Gracze ludzcy: ", skin, "big-label");
        humanPlayers.setPosition(widthPadding, enterName.getY() - heightPadding);
        stage.addActor(humanPlayers);
        labelHashMap.put("humanPlayers", humanPlayers);

        Label aiPlayers = new Label("Gracze AI: ", skin, "big-label");
        aiPlayers.setPosition(widthPadding, humanPlayers.getY() - heightPadding);
        stage.addActor(aiPlayers);
        labelHashMap.put("aiPlayers", aiPlayers);
    }

    @Override
    void initializeButtons() {
        final float buttonWidth = screenWidth * 0.15f;
        final float buttonHeight = screenHeight * 0.1f;

        TextButton backButton = new TextButton("Wroc", skin, "default");
        backButton.setSize(buttonWidth, buttonHeight);
        backButton.setPosition(widthPadding, heightPadding);

        TextButton nextButton = new TextButton("Dalej", skin, "default");
        nextButton.setSize(buttonWidth, buttonHeight);
        nextButton.setPosition(screenWidth - widthPadding - buttonWidth, heightPadding);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.mainMenuScreen);
            }
        });

        nextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.numberOfPlayers.setFirst(humanPlayersNum);
                game.numberOfPlayers.setSecond(aiPlayersNum);
                game.setScreen(game.createPlayerScreen);
            }
        });

        final float lesserButtonWidth = 40f;
        final float lesserButtonHeight = 40f;

        Button addHumanPlayerButton = new Button(skin, "add-button");
        addHumanPlayerButton.setSize(lesserButtonWidth, lesserButtonHeight);
        addHumanPlayerButton.setPosition(textFieldHashMap.get("humanPlayers").getX() +
                        textFieldHashMap.get("humanPlayers").getWidth() + widthPadding / 8,
                textFieldHashMap.get("humanPlayers").getY() + lesserButtonHeight / 4f);
        addHumanPlayerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (playersNum < MAX_PLAYERS) {
                    int value = Integer.parseInt(textFieldHashMap.get("humanPlayers").getText()) + 1;
                    playersNum += 1;
                    humanPlayersNum += 1;
                    textFieldHashMap.get("humanPlayers").setText(Integer.toString(value));
                }
            }
        });

        Button removeHumanPlayerButton = new Button(skin, "remove-button");
        removeHumanPlayerButton.setSize(lesserButtonWidth, lesserButtonHeight);
        removeHumanPlayerButton.setPosition(textFieldHashMap.get("humanPlayers").getX() - widthPadding / 8 - lesserButtonWidth,
                textFieldHashMap.get("humanPlayers").getY() + lesserButtonHeight / 4f);
        removeHumanPlayerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (playersNum > MIN_PLAYERS && humanPlayersNum > MIN_HUMAN_PLAYERS) {
                    int value = Integer.parseInt(textFieldHashMap.get("humanPlayers").getText()) - 1;
                    playersNum -= 1;
                    humanPlayersNum -= 1;
                    textFieldHashMap.get("humanPlayers").setText(Integer.toString(value));
                }
            }
        });

        Button addAiPlayerButton = new Button(skin, "add-button");
        addAiPlayerButton.setSize(lesserButtonWidth, lesserButtonHeight);
        addAiPlayerButton.setPosition(textFieldHashMap.get("aiPlayers").getX() +
                        textFieldHashMap.get("aiPlayers").getWidth() + widthPadding / 8,
                textFieldHashMap.get("aiPlayers").getY() + lesserButtonHeight / 4f);
        addAiPlayerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (playersNum < MAX_PLAYERS) {
                    int value = Integer.parseInt(textFieldHashMap.get("aiPlayers").getText()) + 1;
                    playersNum += 1;
                    aiPlayersNum += 1;
                    textFieldHashMap.get("aiPlayers").setText(Integer.toString(value));
                }

            }
        });

        Button removeAiPlayerButton = new Button(skin, "remove-button");
        removeAiPlayerButton.setSize(lesserButtonWidth, lesserButtonHeight);
        removeAiPlayerButton.setPosition(textFieldHashMap.get("aiPlayers").getX() - widthPadding / 8 - lesserButtonWidth,
                textFieldHashMap.get("aiPlayers").getY() + lesserButtonHeight / 4f);
        removeAiPlayerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (playersNum > MIN_PLAYERS && aiPlayersNum > MIN_AI_PLAYERS) {
                    int value = Integer.parseInt(textFieldHashMap.get("aiPlayers").getText()) - 1;
                    playersNum -= 1;
                    aiPlayersNum -= 1;
                    textFieldHashMap.get("aiPlayers").setText(Integer.toString(value));
                }
            }
        });


        stage.addActor(backButton);
        stage.addActor(nextButton);
        stage.addActor(addHumanPlayerButton);
        stage.addActor(removeHumanPlayerButton);
        // stage.addActor(addAiPlayerButton);
        // stage.addActor(removeAiPlayerButton);
    }

    @Override
    public void initializeStage() {
        initializeLabels();
        initializeTextFields();
        initializeButtons();
    }

    @Override
    void initializeTextFields() {
        final int width = 60;
        final int height = 60;

        TextField humanPlayers = new TextField("2", skin, "default");
        humanPlayers.setSize(width, height);
        humanPlayers.setAlignment(Align.center);
        humanPlayers.setDisabled(true);
        humanPlayers.setPosition(labelHashMap.get("humanPlayers").getX() +
                        labelHashMap.get("humanPlayers").getWidth() + widthPadding,
                labelHashMap.get("humanPlayers").getY());
        textFieldHashMap.put("humanPlayers", humanPlayers);


        TextField aiPlayers = new TextField("0", skin, "default");
        aiPlayers.setSize(width, height);
        aiPlayers.setAlignment(Align.center);
        aiPlayers.setDisabled(true);
        aiPlayers.setPosition(humanPlayers.getX(), humanPlayers.getY() - heightPadding);
        textFieldHashMap.put("aiPlayers", aiPlayers);

        stage.addActor(humanPlayers);
        // stage.addActor(aiPlayers);
    }
}
