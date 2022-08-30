package com.mygdx.game.owners;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.board.Field;

import java.util.ArrayList;

public class Player extends Owner{
    private final Color color;
    private final ArrayList<Field> ownedFields;
    private final Sprite pawn;


    public Player(String name, int money, Color color, Sprite pawn) {
        super(name, money);
        this.color = new Color(color);
        this.ownedFields = new ArrayList<>();
        this.pawn = new Sprite(pawn);
    }
}
