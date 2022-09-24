package com.mygdx.game.board;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.owners.Owner;
import com.mygdx.game.properties.City;
import com.mygdx.game.properties.Property;


public class Field {
    private final Property property;
    private final Sprite fieldSprite;
    private Owner owner;
    private final int fieldNumber;
    private Direction direction;

    public Field(Property property, Sprite fieldSprite, Owner owner, int fieldNumber) {
        this.property = property;
        this.fieldSprite = fieldSprite;
        this.owner = owner;
        this.fieldNumber = fieldNumber;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    private void drawHotels() {
        float xSize = 20;
        float ySize = 16;
        float xPadding = 8;
        float yPadding = 7;
        float startingX = 0;
        float startingY = 0;

        if (direction == Direction.SOUTH) {
            startingX = getX() + xPadding;
            startingY = getY() + getHeight() - yPadding - ySize;
        } else if (direction == Direction.WEST){
            xSize = 16;
            ySize = 20;
            startingX = getX() + getWidth() - yPadding - xSize;
            startingY = getY() + getHeight() - xPadding - ySize;
        } else if (direction == Direction.NORTH) {
            startingX = getX() + xPadding;
            startingY = getY() + yPadding;
        } else {
            xSize = 16;
            ySize = 20;
            startingX = getX() + yPadding;
            startingY = getY() + getHeight() - xPadding - ySize;
        }
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(startingX, startingY, xSize, ySize, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(startingX, startingY, xSize, ySize);
        shapeRenderer.end();
    }

    private void drawHouses() {
        float xSize = 12;
        float ySize = 16;
        float xPadding = 1;
        float paddingBetween = 4;
        float yPadding = 7;
        float startingX = 0;
        float startingY = 0;

        if (direction == Direction.SOUTH) {
            startingX = getX() + xPadding + paddingBetween;
            startingY = getY() + getHeight() - yPadding - ySize;
        } else if (direction == Direction.WEST){
            xSize = 16;
            ySize = 12;
            startingX = getX() + getWidth() - yPadding - xSize;
            startingY = getY() + getHeight() - xPadding - paddingBetween - ySize;
        } else if (direction == Direction.NORTH) {
            startingX = getX() + xPadding + paddingBetween;
            startingY = getY() + yPadding;
        } else {
            xSize = 16;
            ySize = 12;
            startingX = getX() + yPadding;
            startingY = getY() + getHeight() - xPadding - paddingBetween - ySize;
        }
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < getHouses(); i++) {
            if (direction == Direction.SOUTH || direction == Direction.NORTH) {
                shapeRenderer.rect(startingX + paddingBetween, startingY, xSize, ySize,
                        Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE);
                startingX = startingX + paddingBetween + xSize;
            }
            else {
                shapeRenderer.rect(startingX, startingY - paddingBetween, xSize, ySize,
                        Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE);
                startingY = startingY - paddingBetween - ySize;
            }
        }
        shapeRenderer.end();
        /*
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        for (int i = 0; i < getHouses(); i++) {
            if (direction == Direction.SOUTH || direction == Direction.NORTH) {
                shapeRenderer.rect(startingX + paddingBetween, startingY, xSize, ySize);
                startingX = startingX + paddingBetween + xSize;
            }
            else {
                shapeRenderer.rect(startingX, startingY - paddingBetween, xSize, ySize);
                startingY = startingY - paddingBetween - ySize;
            }
        }
        shapeRenderer.end();
        */
    }

    public void drawBuildings() {
        if (haveProperty() && property instanceof City) {
            if (getHotels() == 1)
                drawHotels();
            else
                drawHouses();
        }
    }

    public int getFieldNumber() {
        return fieldNumber;
    }

    public float getX() {
        return fieldSprite.getX();
    }

    public float getY() {
        return fieldSprite.getY();
    }

    public float getWidth() {
        return fieldSprite.getWidth();
    }

    public float getHeight() {
        return fieldSprite.getHeight();
    }

    public void setPosition(float x, float y) {
        fieldSprite.setPosition(x, y);
    }

    public Sprite getPropertySprite() {
        return property.getPropertySprite();
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public int getPrice() {
        return property.getPrice();
    }

    public boolean haveProperty() {
        return property != null;
    }

    public Property getProperty() {
        return haveProperty() ? property : null;
    }

    public Sprite getFieldSprite() {
        return fieldSprite;
    }

    public boolean isMortgaged() {
        return haveProperty() && property.isMortgaged();
    }

    public void setMortgaged(boolean mortgaged) {
        if (haveProperty())
            property.setMortgaged(mortgaged);
    }

    public int getMortgage() {
        return haveProperty() ? property.getMortgage() : 0;
    }

    public int getHouses() {
        if (haveProperty() && property instanceof City) {
            return ((City) property).getHouses();
        }
        return 0;
    }

    public int getHotels() {
        if (haveProperty() && property instanceof City) {
            return ((City) property).getHotels();
        }
        return 0;
    }


}
