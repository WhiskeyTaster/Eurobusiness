package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Eurobusiness;

public class CreatePlayerUI extends BaseUI{
    private Array<Sprite> pawns;
    private Array<Sprite> usedPawns;
    private Array<Sprite> boxes;
    private Array<Sprite> usedBoxes;

    private TextureAtlas boxesAtlas;
    private TextureAtlas pawnsAtlas;

    public CreatePlayerUI(final Eurobusiness game) {
        super(game);
        this.boxesAtlas = new TextureAtlas(Gdx.files.internal("textures/colors.atlas"));
        this.pawnsAtlas = new TextureAtlas(Gdx.files.internal("textures/pawns.atlas"));

        this.boxes = new Array<>();
        this.pawns = new Array<>();

        this.usedBoxes = new Array<>();
        this.usedPawns = new Array<>();
    }

    @Override
    void drawShapeRenderer() {

    }

    @Override
    void drawBatch() {

    }

    @Override
    void initializeLabels() {

    }

    @Override
    void initializeButtons() {

    }

    @Override
    void initializeTextFields() {

    }
}
