package com.mygdx.game.properties;

// TODO: depends on railways

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Property {
    private String name;
    private int price;
    private int fieldNumber;
    private int mortgage;
    private boolean mortgaged;
    private Sprite propertySprite;

    public Property() {
    }

    public Property(String name, int price, int fieldNumber, int mortgage) {
        this.name = name;
        this.price = price;
        this.fieldNumber = fieldNumber;
        this.mortgage = mortgage;
        this.mortgaged = false;
    }

    @Override
    public String toString() {
        return "[name: " + name + ", price: " + price + ", fieldNumber: " +
                fieldNumber + ", mortgage: " + mortgage + "]";
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getFieldNumber() {
        return fieldNumber;
    }

    public int getMortgage() {
        return mortgage;
    }

    public boolean isMortgaged() {
        return mortgaged;
    }

    public void setMortgaged(boolean mortgaged) {
        this.mortgaged = mortgaged;
    }

    public abstract int getCharge();

    public void setPropertySprite(Sprite sprite) {
        this.propertySprite = sprite;
    }

    public Sprite getPropertySprite() {
        return propertySprite;
    }
}
