package com.mygdx.game.windows;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public final class FieldWindow extends BaseWindow{

    private Sprite fieldSprite;
    private final SpriteBatch batch;

    public FieldWindow(float x, float y, float width, float height, Color color) {
        super(x, y, width, height, color);
        this.fieldSprite = new Sprite();
        this.batch = new SpriteBatch();
    }

    public FieldWindow(float x, float y, float width, float height, Color color, Sprite propertySprite) {
        super(x, y, width, height, color);
        this.fieldSprite = new Sprite(propertySprite);
        this.batch = new SpriteBatch();

        this.fieldSprite.setSize(width, height);
        this.fieldSprite.setPosition(x, y);
    }

    public void setFieldSprite(Texture texture) {
        this.fieldSprite = new Sprite(texture);
        this.fieldSprite.setSize(width, height);
        this.fieldSprite.setPosition(x, y);
    }

    @Override
    public void draw() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();

        batch.begin();
        fieldSprite.draw(batch);
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();
    }
}
