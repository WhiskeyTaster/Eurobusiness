package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Eurobusiness;
import com.mygdx.game.Pair;
import com.mygdx.game.creators.OwnerCreator;

import java.util.ArrayList;
import java.util.HashMap;

// TODO: add color to frames after choosing element like pawn or colorbox
// TODO: resolution

public class CreatePlayerUI extends BaseUI{
    private final OwnerCreator ownerCreator;

    private final Array<Sprite> pawns;
    private final Array<Sprite> usedPawns;
    private final Array<Sprite> boxes;
    private final Array<Sprite> usedBoxes;

    private final TextureAtlas boxesAtlas;
    private final TextureAtlas pawnsAtlas;

    private Sprite boxSpriteSelected;
    private Sprite pawnSpriteSelected;

    private Vector3 touchPos;

    private final HashMap<String, Label> labelHashMap;
    private final HashMap<String, TextField> textFieldHashMap;

    private final Pair<Integer, Integer> players;

    public CreatePlayerUI(final Eurobusiness game, final Pair<Integer, Integer> players) {
        super(game);
        this.players = players;
        this.ownerCreator = new OwnerCreator();

        this.boxesAtlas = new TextureAtlas(Gdx.files.internal("images/textures/colors.atlas"));
        this.pawnsAtlas = new TextureAtlas(Gdx.files.internal("images/textures/pawns.atlas"));

        this.boxes = new Array<>();
        this.pawns = new Array<>();

        this.usedBoxes = new Array<>();
        this.usedPawns = new Array<>();

        this.boxSpriteSelected = new Sprite();
        this.pawnSpriteSelected = new Sprite();

        this.touchPos = new Vector3();
        this.labelHashMap = new HashMap<>();
        this.textFieldHashMap = new HashMap<>();
    }

    @Override
    public void draw(float delta) {
        super.draw(delta);

        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            getGame().camera.unproject(touchPos);
            for (Sprite sprite : boxes) {
                float xStart = sprite.getX();
                float xEnd = xStart + sprite.getWidth();
                float yStart = sprite.getY();
                float yEnd = yStart + sprite.getHeight();

                if (touchPos.x >= xStart && touchPos.x <= xEnd) {
                    if (touchPos.y >= yStart && touchPos.y <= yEnd)
                        boxSpriteSelected = sprite;
                }
            }

            for (Sprite sprite : pawns) {
                float xStart = sprite.getX();
                float xEnd = xStart + sprite.getWidth();
                float yStart = sprite.getY();
                float yEnd = yStart + sprite.getHeight();

                if (touchPos.x >= xStart && touchPos.x <= xEnd) {
                    if (touchPos.y >= yStart && touchPos.y <= yEnd)
                        pawnSpriteSelected = sprite;
                }
            }
        }

        for (Sprite sprite : boxes) {
            if (usedBoxes.contains(sprite, true))
                continue;
            if (sprite == boxSpriteSelected)
                sprite.setAlpha(0.5f);
            else
                sprite.setAlpha(1);
        }

        for (Sprite sprite : pawns) {
            if (usedPawns.contains(sprite, true))
                continue;
            if (sprite == pawnSpriteSelected)
                sprite.setAlpha(0.5f);
            else
                sprite.setAlpha(1);
        }
    }

    @Override
    void drawShapeRenderer() {

    }

    @Override
    void drawBatch() {
        getBatch().begin();
        for (Sprite sprite : boxes)
            sprite.draw(getBatch());
        for (Sprite sprite : pawns)
            sprite.draw(getBatch());
        getBatch().end();
    }

    @Override
    void initializeLabels() {
        int screenWidth = (int) getGame().settings.getScreenWidth();
        int screenHeight = (int) getGame().settings.getScreenHeight();

        final float widthPadding = screenWidth * 0.15f;
        final float heightPadding = screenWidth * 0.1f;

        Label playerName = new Label("Nazwa gracza:", getSkin(), "big-label");
        playerName.setPosition(widthPadding, screenHeight - heightPadding);
        labelHashMap.put("playerName", playerName);
        getStage().addActor(playerName);

        Label playerColor = new Label("Kolor gracza:", getSkin(), "big-label");
        playerColor.setPosition(widthPadding, playerName.getY() - heightPadding);
        labelHashMap.put("playerColor", playerColor);
        getStage().addActor(playerColor);

        Label choosePawn = new Label("Wybierz pionek:", getSkin(), "big-label");
        choosePawn.setPosition(widthPadding, playerColor.getY() - heightPadding);
        labelHashMap.put("choosePawn", choosePawn);
        getStage().addActor(choosePawn);
    }

    @Override
    void initializeButtons() {
        int screenWidth = (int) getGame().settings.getScreenWidth();
        int screenHeight = (int) getGame().settings.getScreenHeight();

        float buttonWidth = screenWidth * 0.15f;
        float buttonHeight = screenHeight * 0.10f;

        final float widthPadding = screenWidth * 0.15f;
        final float heightPadding = screenWidth * 0.1f;

        TextButton nextButton = new TextButton("Next", getSkin(), "default");
        nextButton.setSize(buttonWidth, buttonHeight);
        nextButton.setPosition(screenWidth - buttonWidth - widthPadding, heightPadding);
        nextButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                final int MIN_PLAYER_NAME_LENGTH = 2;
                String playerName = textFieldHashMap.get("playerName").getText();
                if (playerName.length() < MIN_PLAYER_NAME_LENGTH)
                    return;
                if (!boxes.contains(boxSpriteSelected, true))
                    return;

                boxSpriteSelected.setAlpha(1);
                Color playerColor = new Color(boxSpriteSelected.getColor());
                boxSpriteSelected.setAlpha(0.5f);

                if (!pawns.contains(pawnSpriteSelected, true))
                    return;

                ownerCreator.createPlayer(playerName, playerColor, pawnSpriteSelected);

                textFieldHashMap.get("playerName").setText("");
                usedBoxes.add(boxSpriteSelected);
                usedPawns.add(pawnSpriteSelected);
                boxSpriteSelected = new Sprite();
                pawnSpriteSelected = new Sprite();

                if (players.getFirst() == ownerCreator.getHumansNumber()) {
                    // TODO: create AI (after AI will be implemented)
                    System.out.println("CREATED");
                    // getGame().players = playerArrayList;
                    // getGame().setScreen(new MainGameScreen(getGame()));
                    // TODO: change screen to game
                }
            }
        });
        getStage().addActor(nextButton);
    }

    @Override
    void initializeTextFields() {
        int screenWidth = (int) getGame().settings.getScreenWidth();
        int screenHeight = (int) getGame().settings.getScreenHeight();

        final float widthPadding = screenWidth * 0.15f;
        final float heightPadding = screenWidth * 0.1f;

        final int MAX_LENGTH = 12;
        int width = 300;
        int height = 60;

        TextField playerName = new TextField("", getSkin(), "default");
        playerName.setSize(width, labelHashMap.get("playerName").getHeight());
        playerName.setPosition(labelHashMap.get("playerName").getX() +
                        labelHashMap.get("playerName").getWidth() + widthPadding / 8,
                labelHashMap.get("playerName").getY());

        playerName.setMaxLength(MAX_LENGTH);
        textFieldHashMap.put("playerName", playerName);
        getStage().addActor(playerName);
    }

    @Override
    public void initializeStage() {
        initializeLabels();
        initializeTextFields();
        initializeButtons();
        initializeSpriteBoxes();
        initializeSpritePawns();
    }

    private void initializeSpriteBoxes() {
        int screenWidth = (int) getGame().settings.getScreenWidth();
        int screenHeight = (int) getGame().settings.getScreenHeight();

        final float widthPadding = screenWidth * 0.15f;
        final float heightPadding = screenWidth * 0.1f;

        Array<Sprite> spriteBoxes = boxesAtlas.createSprites();
        for (Sprite sprite : spriteBoxes)
            boxes.add(sprite);

        float initX = textFieldHashMap.get("playerName").getX();
        float initY = labelHashMap.get("playerName").getY() - heightPadding;
        Pair<Float, Float> lastPos = new Pair<>();
        lastPos.setFirst(initX);
        lastPos.setSecond(initY);

        ArrayList<Color> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        colors.add(Color.OLIVE);
        colors.add(Color.ORANGE);
        colors.add(Color.RED);


        float width = 60;
        float height = 60;
        int i = 0;
        for (Sprite sprite : boxes) {
            sprite.setSize(width, height);
            sprite.setPosition(lastPos.getFirst(), lastPos.getSecond());
            lastPos.setFirst(lastPos.getFirst() + width + widthPadding / 8);
            sprite.setColor(colors.get(i));
            i++;
        }

    }

    private void initializeSpritePawns() {
        int screenWidth = (int) getGame().settings.getScreenWidth();
        int screenHeight = (int) getGame().settings.getScreenHeight();

        final float widthPadding = screenWidth * 0.15f;
        final float heightPadding = screenWidth * 0.1f;

        Array<Sprite> pawnSprites = pawnsAtlas.createSprites();
        for (Sprite sprite : pawnSprites)
            pawns.add(sprite);
        int spriteSizeX = 64;
        int spriteSizeY = 64;

        Pair<Float, Float> pos = new Pair<>();
        pos.setFirst(labelHashMap.get("choosePawn").getX() + labelHashMap.get("choosePawn").getWidth() + widthPadding / 8);
        pos.setSecond(labelHashMap.get("choosePawn").getY());

        for (Sprite sprite : pawns) {
            sprite.setSize(spriteSizeX, spriteSizeY);
            sprite.setPosition(pos.getFirst(), pos.getSecond());
            pos.setFirst(pos.getFirst() + spriteSizeX + widthPadding / 8);
        }
    }
}
