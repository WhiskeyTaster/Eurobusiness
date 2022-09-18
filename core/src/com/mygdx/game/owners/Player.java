package com.mygdx.game.owners;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.board.Board;
import com.mygdx.game.board.Field;
import com.mygdx.game.properties.City;
import com.mygdx.game.properties.Railway;
import com.mygdx.game.properties.Utility;

import java.util.ArrayList;
import java.util.Objects;

public class Player extends Owner{
    private final Color color;
    private final Sprite pawn;
    private int currentFieldNumber;


    public Player(String name, int money, Color color, Sprite pawn) {
        super(name, money);
        this.color = new Color(color);
        this.pawn = new Sprite(pawn);
        this.pawn.setAlpha(1f);
        this.currentFieldNumber = Board.START_FIELD_NUMBER;
    }

    public int getCurrentFieldNumber() {
        return currentFieldNumber;
    }

    public void setCurrentFieldNumber(int currentFieldNumber) {
        this.currentFieldNumber = currentFieldNumber;
    }

    public Sprite getPawn() {
        return pawn;
    }

    public void move(float x, float y) {
        pawn.setPosition(pawn.getX() + x, pawn.getY() + y);
    }

    public Color getColor() {
        return color;
    }

    public int ownedCities(String country) {
        int num = 0;
        for (Field field : getOwnedFields()) {
            if (field.getProperty() instanceof City)
                if (Objects.equals(((City) field.getProperty()).getCountry(), country))
                    num++;
        }
        return num;
    }

    public int ownedRailways() {
        int num = 0;
        for (Field field : getOwnedFields())
            if (field.getProperty() instanceof Railway)
                num++;
        return num;
    }

    public int ownedUtilities() {
        int num = 0;
        for (Field field : getOwnedFields())
            if (field.getProperty() instanceof Utility)
                num++;
        return num;
    }
}
