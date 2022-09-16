package com.mygdx.game.owners;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.board.Board;
import com.mygdx.game.board.Field;

import java.util.ArrayList;

public class Player extends Owner{
    private final Color color;
    private final ArrayList<Field> ownedFields;
    private final Sprite pawn;
    private int currentFieldNumber;


    public Player(String name, int money, Color color, Sprite pawn) {
        super(name, money);
        this.color = new Color(color);
        this.ownedFields = new ArrayList<>();
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

    public ArrayList<Field> getOwnedFields() {
        return ownedFields;
    }
}
