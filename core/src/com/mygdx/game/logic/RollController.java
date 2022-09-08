package com.mygdx.game.logic;


import java.util.ArrayList;

public class RollController implements LinkedSubject{
    private final ArrayList<LinkedSubject> observers;

    private int rolledValue;

    private boolean playerRolled;
    private boolean twoPairsRolled;

    public RollController() {
        this.observers = new ArrayList<>();
        this.rolledValue = 0;

        this.playerRolled = false;
        this.twoPairsRolled = false;
    }

    public int getRolledValue() {
        return rolledValue;
    }

    public boolean isPlayerRolled() {
        return playerRolled;
    }

    public boolean isTwoPairsRolled() {
        return twoPairsRolled;
    }

    public void setPlayerRolled(boolean playerRolled) {
        this.playerRolled = playerRolled;
    }

    public void setRolledValue(int rolledValue) {
        this.rolledValue = rolledValue;
    }

    public void setTwoPairsRolled(boolean twoPairsRolled) {
        this.twoPairsRolled = twoPairsRolled;
    }

    @Override
    public void update(LinkedSubject subject) {

    }

    @Override
    public ArrayList<LinkedSubject> getSubjects() {
        return observers;
    }
}
