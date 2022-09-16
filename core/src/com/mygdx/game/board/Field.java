package com.mygdx.game.board;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.owners.Owner;
import com.mygdx.game.properties.Property;

public class Field {
    static int FIELD_ID = 1;

    private final Property property;
    private final Sprite fieldSprite;
    private Owner owner;
    private final int fieldNumber;

    public Field(Property property, Sprite fieldSprite, Owner owner) {
        this.property = property;
        this.fieldSprite = fieldSprite;
        this.owner = owner;
        this.fieldNumber = FIELD_ID;

        FIELD_ID++;
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
}
