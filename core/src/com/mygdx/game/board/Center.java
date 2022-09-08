package com.mygdx.game.board;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Center {
    private Sprite center;

    public Center(Texture texture) {
        this.center = new Sprite(texture);
    }

    public void setPosition(float x, float y) {
        center.setPosition(x, y);
    }

    public void setSize(float width, float height) {
        center.setSize(width, height);
    }
}
