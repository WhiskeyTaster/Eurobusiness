package com.mygdx.game.managers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Eurobusiness;
import com.mygdx.game.board.Field;
import com.mygdx.game.owners.Player;

import java.util.Objects;

public class FieldManager {

    private final Eurobusiness game;
    private final Vector3 touchPos;
    private Field selectedField;

    public FieldManager(final Eurobusiness game) {
        this.game = Objects.requireNonNull(game, "game is null");
        this.touchPos = new Vector3();
    }

    public void process() {

    }

    private void checkTouchedField(Player currentPlayer) {
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.camera.unproject(touchPos);
            for (Field field : currentPlayer.getOwnedFields()) {
                Sprite sprite = field.getFieldSprite();
                float xStart = sprite.getX();
                float xEnd = xStart + sprite.getWidth();
                float yStart = sprite.getY();
                float yEnd = yStart + sprite.getHeight();

                if (touchPos.x >= xStart && touchPos.x <= xEnd) {
                    if (touchPos.y >= yStart && touchPos.y <= yEnd)
                        selectedField = field;
                }
            }
        }
    }
}
