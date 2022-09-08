package com.mygdx.game.logic;



import com.mygdx.game.board.Field;

import java.util.ArrayList;

public class MainController implements LinkedSubject{
    private final ArrayList<LinkedSubject> observers;
    private Field field;

    private boolean buyingFinished;
    private boolean fieldChecked;
    private boolean fieldToSell;
    private boolean movingFinished;
    private boolean playerHaveToPay;
    private boolean playerJailed;
    private boolean playerRolled;
    private boolean tourFinished;
    private boolean twoPairsRolled;

    private int rolledValue;

    private boolean payActionFinished;


    public MainController() {
        this.observers = new ArrayList<>();

        this.buyingFinished = false;
        this.fieldChecked = false;
        this.fieldToSell = false;
        this.movingFinished = false;
        this.playerHaveToPay = false;
        this.playerJailed = false;
        this.playerRolled = false;
        this.tourFinished = false;
        this.twoPairsRolled = false;

        this.rolledValue = 0;

        this.payActionFinished = false;
    }

    public boolean isPayActionFinished() {
        return payActionFinished;
    }

    public int getRolledValue() {
        return rolledValue;
    }

    public boolean isBuyingFinished() {
        return buyingFinished;
    }

    public boolean isFieldToSell() {
        return fieldToSell;
    }

    public boolean isMovingFinished() {
        return movingFinished;
    }

    public boolean isPlayerHaveToPay() {
        return playerHaveToPay;
    }

    public boolean isPlayerRolled() {
        return playerRolled;
    }

    public boolean isTourFinished() {
        return tourFinished;
    }

    public boolean isTwoPairsRolled() {
        return twoPairsRolled;
    }

    @Override
    public void update(LinkedSubject subject) {
        if (subject instanceof RollController) {
            RollController rollController = (RollController) subject;
            this.playerRolled = rollController.isPlayerRolled();
            this.twoPairsRolled = rollController.isTwoPairsRolled();
            this.rolledValue = rollController.getRolledValue();
        }
        if (subject instanceof MoveController) {
            MoveController moveController = (MoveController) subject;
            this.movingFinished = moveController.isMovingFinished();
        }
        if (subject instanceof EndTurnController) {
            EndTurnController endTurnController = (EndTurnController) subject;
            this.tourFinished = endTurnController.isTourFinished();
        }
        if (subject instanceof FieldController) {
            FieldController fieldController = (FieldController) subject;
            this.fieldChecked = fieldController.isFieldChecked();
            this.playerJailed = fieldController.isPlayerJailed();
            this.fieldToSell = fieldController.isFieldToSell();
            this.playerHaveToPay = fieldController.isPlayerHaveToPay();
        }
        if (subject instanceof BuyController) {
            BuyController buyController = (BuyController) subject;
            this.buyingFinished = buyController.isBuyingFinished();
        }
        if (subject instanceof PayController) {
            PayController payController = (PayController) subject;
            this.payActionFinished = payController.isActionFinished();
        }
        informSubjects();
    }

    @Override
    public ArrayList<LinkedSubject> getSubjects() {
        return observers;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
