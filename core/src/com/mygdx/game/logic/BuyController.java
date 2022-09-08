package com.mygdx.game.logic;


import com.mygdx.game.action.Action;
import com.mygdx.game.action.AuctionAction;
import com.mygdx.game.board.Board;
import com.mygdx.game.board.Field;
import com.mygdx.game.owners.Owner;
import com.mygdx.game.owners.Player;

import java.util.ArrayList;


public class BuyController implements LinkedSubject{
    private final ArrayList<LinkedSubject> observers;

    private boolean fieldToSell;
    private boolean buyingFinished;
    private boolean auctionFinished;

    private BuyAction buyAction;
    private AuctionAction auctionAction;

    private Field field;
    private Player currentPlayer;

    private int finalPrice;

    private boolean fieldSold;

    public BuyController() {
        this.observers = new ArrayList<>();
        this.fieldToSell = false;
        this.buyingFinished = false;
        this.finalPrice = 0;
        this.fieldSold = false;
        this.auctionFinished = false;
    }

    public boolean isBuyingFinished() {
        return buyingFinished;
    }

    public boolean isFieldToSell() {
        return fieldToSell;
    }

    public void setFieldAndPlayer(Field field, Player currentPlayer) {
        this.field = field;
        this.finalPrice = field.getPrice();
        this.currentPlayer = currentPlayer;
    }

    public void createBuyAction() {
        this.buyAction = new BuyAction();
    }

    public void createAuctionAction(Board board, ArrayList<Player> players, Player currentPlayer) {
        Field field = board.getField(currentPlayer.getCurrentFieldNumber());
        this.auctionAction = new AuctionAction(players, currentPlayer, field);
    }

    @Override
    public void update(LinkedSubject subject) {
        if (subject instanceof MainController) {
            this.fieldToSell = ((MainController) subject).isFieldToSell();
        }
    }

    @Override
    public ArrayList<LinkedSubject> getSubjects() {
        return observers;
    }

    public void process() {
        if (!isBuyActionFinished())
            buyAction.process();
        if (isAuctionStarted() && !isAuctionFinished()) {
            auctionAction.process();
        }
        if (auctionAction.isFieldSold() && auctionAction.isAuctionFinished() && !auctionAction.isActionFinished()) {
            auctionAction.sellField();
        }
    }

    public void cancelBuying() {
        buyAction.setActionFinished(true);
    }

    public void buyField() {
        buyAction.sellField();
    }

    public boolean isBuyActionFinished() {
        return buyAction.isActionFinished();
    }

    public boolean isAuctionActionFinished() {return auctionAction.isActionFinished(); }

    public void bidAuction(int amount) {
        if (amount > auctionAction.getCurrentPrice()) {
            auctionAction.updatePrice(amount);
            this.setFinalPrice(amount);
            auctionAction.nextPlayer();
        }
    }

    public void passAuction(Player currentAuctioneer) {
        auctionAction.removeFromAuctioneers(currentAuctioneer);
        auctionAction.nextPlayerFixed();
    }

    public Player getCurrentAuctioneer() {
        return auctionAction.getCurrentPlayer();
    }

    public int getCurrentPrice() {
        return auctionAction.getCurrentPrice();
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

    public void startAuction() {
        auctionAction.setAuctionStarted(true);
    }

    public boolean isAuctionStarted() {
        return auctionAction.isAuctionStarted();
    }

    public boolean isAuctionFinished() {
        return auctionAction.isAuctionFinished();
    }

    class BuyAction implements Action{

        private boolean actionFinished;
        private boolean playerBalanceChecked;

        public BuyAction() {
            this.actionFinished = false;
            this.playerBalanceChecked = false;
        }

        public boolean isActionFinished() {
            return actionFinished;
        }

        public void setActionFinished(boolean actionFinished) {
            this.actionFinished = actionFinished;
        }

        private void check(Player currentPlayer, Field field) {
            final int price = field.getPrice();
            this.setActionFinished(!currentPlayer.haveMoney(price));
        }

        private void sellField() {
            currentPlayer.pay(field.getOwner(), field.getPrice());
            transferField(currentPlayer, field.getOwner(), field);
            this.setActionFinished(true);
        }

        private void transferField(Owner paying, Owner owner, Field field) {
            owner.removeField(field);
            paying.addField(field);
            field.setOwner(paying);
        }

        @Override
        public void process() {
            // 1. CHECK THAT FIELD CAN BE SOLD
            if (!isFieldToSell())
                return;
            // 2. CHECK THAT PLAYER HAS MONEY TO BUY FIELD
            if (!playerBalanceChecked) {
                playerBalanceChecked = true;
                check(currentPlayer, field);
            }
            informController();
            /*
            System.out.println("*** INFO BUY ACTION ***");
            System.out.println("FieldToSell: " + fieldToSell + ", checked: " + checked + ", finished: " + finished +
                    ", canBuyField: " + canBuyField);
             */
        }

        @Override
        public void informController() {
            BuyController.this.buyingFinished = this.isActionFinished();
            BuyController.this.informSubjects();
        }
    }
}
