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
        final int screenWidth = (int) getGame().settings.getScreenHeight();
        final int screenHeight = (int) getGame().settings.getScreenHeight();

        final float widthPadding = screenWidth * 0.1f;
        final float heightPadding = screenHeight * 0.1f;

        Label enterName = new Label("Gracze", getSkin(), "big-label");
        enterName.setPosition(widthPadding, screenHeight - heightPadding);
        getStage().addActor(enterName);
        labelHashMap.put("enterName", enterName);

        Label humanPlayers = new Label("Gracze ludzcy: ", getSkin(), "big-label");
        humanPlayers.setPosition(widthPadding, enterName.getY() - heightPadding);
        getStage().addActor(humanPlayers);
        labelHashMap.put("humanPlayers", humanPlayers);

        Label aiPlayers = new Label("Gracze AI: ", getSkin(), "big-label");
        aiPlayers.setPosition(widthPadding, humanPlayers.getY() - heightPadding);
        getStage().addActor(aiPlayers);
        labelHashMap.put("aiPlayers", aiPlayers);
    }

    @Override
    void initializeButtons() {
        final int screenWidth = (int) getGame().settings.getScreenWidth();
        final int screenHeight = (int) getGame().settings.getScreenHeight();
        final float widthPadding = screenWidth * 0.15f;
        final float heightPadding = screenWidth * 0.1f;

        final float buttonWidth = screenWidth * 0.15f;
        final float buttonHeight = screenHeight * 0.10f;

        TextButton backButton = new TextButton("Wroc", getSkin(), "default");
        backButton.setSize(buttonWidth, buttonHeight);
        backButton.setPosition(widthPadding, heightPadding);

        TextButton nextButton = new TextButton("Dalej", getSkin(), "default");
        nextButton.setSize(buttonWidth, buttonHeight);
        nextButton.setPosition(screenWidth - widthPadding - buttonWidth, heightPadding);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().setScreen(getGame().mainMenuScreen);
            }
        });

        nextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().players.setFirst(humanPlayersNum);
                getGame().players.setSecond(aiPlayersNum);
                getGame().setScreen(getGame().createPlayerScreen);
            }
        });

        final float lesserButtonWidth = 40f;
        final float lesserButtonHeight = 40f;

        Button addHumanPlayerButton = new Button(getSkin(), "add-button");
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

        Button removeHumanPlayerButton = new Button(getSkin(), "remove-button");
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

        Button addAiPlayerButton = new Button(getSkin(), "add-button");
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

        Button removeAiPlayerButton = new Button(getSkin(), "remove-button");
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

        getStage().addActor(backButton);
        getStage().addActor(nextButton);
        getStage().addActor(addHumanPlayerButton);
        getStage().addActor(removeHumanPlayerButton);
        getStage().addActor(addAiPlayerButton);
        getStage().addActor(removeAiPlayerButton);
    }

    @Override
    public void initializeStage() {
        initializeLabels();
        initializeTextFields();
        initializeButtons();
    }

    @Override
    void initializeTextFields() {
        final int screenWidth = (int) getGame().settings.getScreenWidth();
        final int screenHeight = (int) getGame().settings.getScreenHeight();

        final float widthPadding = screenWidth * 0.1f;
        final float heightPadding = screenHeight * 0.1f;

        final int width = 60;
        final int height = 60;

        TextField humanPlayers = new TextField("2", getSkin(), "default");
        humanPlayers.setSize(width, height);
        humanPlayers.setAlignment(Align.center);
        humanPlayers.setDisabled(true);
        humanPlayers.setPosition(labelHashMap.get("humanPlayers").getX() +
                        labelHashMap.get("humanPlayers").getWidth() + widthPadding,
                labelHashMap.get("humanPlayers").getY());
        textFieldHashMap.put("humanPlayers", humanPlayers);

        TextField aiPlayers = new TextField("0", getSkin(), "default");
        aiPlayers.setSize(width, height);
        aiPlayers.setAlignment(Align.center);
        aiPlayers.setDisabled(true);
        aiPlayers.setPosition(humanPlayers.getX(), humanPlayers.getY() - heightPadding);
        textFieldHashMap.put("aiPlayers", aiPlayers);

        getStage().addActor(humanPlayers);
        getStage().addActor(aiPlayers);
    }
}
