package com.mygdx.game.logic;

import com.mygdx.game.action.Action;
import com.mygdx.game.board.Board;
import com.mygdx.game.board.Field;
import com.mygdx.game.owners.Bank;
import com.mygdx.game.owners.Owner;
import com.mygdx.game.owners.Player;

import java.util.ArrayList;


public class FieldController implements LinkedSubject{
    private final ArrayList<LinkedSubject> observers;

    private boolean fieldChecked;
    private boolean fieldToSell;
    private boolean movingFinished;
    private boolean playerHaveToPay;
    private boolean playerJailed;

    private Action action;

    public FieldController() {
        this.observers = new ArrayList<>();

        this.fieldChecked = false;
        this.fieldToSell = false;
        this.movingFinished = false;
        this.playerHaveToPay = false;
        this.playerJailed = false;
    }

    public void createFieldAction(Board board, Player currentPlayer) {
        this.action = new FieldAction(board, currentPlayer);
    }

    public void process() {
        action.process();
    }

    public boolean isFieldChecked() {
        return fieldChecked;
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

    public boolean isPlayerJailed() {
        return playerJailed;
    }

    @Override
    public void update(LinkedSubject subject) {
        if (subject instanceof MainController) {
            MainController mainController = (MainController) subject;
            this.movingFinished = mainController.isMovingFinished();
        }
    }

    @Override
    public ArrayList<LinkedSubject> getSubjects() {
        return observers;
    }

    class FieldAction implements Action{
        private final Board board;
        private final Player currentPlayer;

        private boolean fieldChecked;
        private boolean playerJailed;
        private boolean fieldToSell;
        private boolean playerHaveToPay;

        public FieldAction(Board board, Player currentPlayer) {
            this.board = board;
            this.currentPlayer = currentPlayer;

            this.fieldChecked = false;
            this.playerJailed = false;
            this.fieldToSell = false;
            this.playerHaveToPay = false;
        }

        public void process() {
            if (isMovingFinished()) {
                if (!isFieldChecked())
                    checkField(currentPlayer);
            }
            informController();
        }

        @Override
        public void informController() {
            FieldController.this.fieldChecked = this.isFieldChecked();
            FieldController.this.playerJailed = this.isPlayerJailed();
            FieldController.this.fieldToSell = this.isFieldToSell();
            FieldController.this.playerHaveToPay = this.isPlayerHaveToPay();

            FieldController.this.informSubjects();
        }

        public boolean isFieldToSell() {
            return fieldToSell;
        }

        public boolean isPlayerHaveToPay() {
            return playerHaveToPay;
        }

        public boolean isPlayerJailed() {
            return playerJailed;
        }

        public boolean isFieldChecked() {
            return fieldChecked;
        }

        private void checkField(Player currentPlayer) {
            fieldChecked = true;
            int fieldNumber = currentPlayer.getCurrentFieldNumber();
            if (fieldNumber == Board.POLICE_FIELD_NUMBER) {
                playerJailed = true;
            }
            else {
                Field field = board.getField(fieldNumber);
                Owner fieldOwner = field.getOwner();

                if (!field.haveProperty())  // EVENTS WILL BE ADDED IN THE FUTURE
                    return;
                if (fieldOwner instanceof Bank) {   // FINISHED
                    fieldToSell = true;
                }
                else if (fieldOwner != currentPlayer) {
                    playerHaveToPay = true;
                }
            }
        /*
        System.out.println("*** FIELD ACTION INFO ***");
        System.out.println("FieldChecked: " + fieldChecked + ", fieldToSell: " + fieldToSell);
        */
        }
    }
}
