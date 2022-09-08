package com.mygdx.game.logic;

import com.mygdx.game.board.Board;
import com.mygdx.game.board.Field;
import com.mygdx.game.action.Action;
import com.mygdx.game.owners.Owner;
import com.mygdx.game.owners.Player;
import com.mygdx.game.properties.City;
import com.mygdx.game.properties.Property;
import com.mygdx.game.properties.Railway;
import com.mygdx.game.properties.Utility;

import java.util.ArrayList;

public class PayController implements LinkedSubject{
    private final ArrayList<LinkedSubject> observers;

    private boolean playerHaveToPay;

    private boolean playerHasPaid;
    private boolean playerOutOfMoney;

    private PayAction payAction;

    private int rolledValue;
    private int amountToPay;

    private boolean actionFinished;

    public PayController() {
        this.observers = new ArrayList<>();

        this.playerHaveToPay = false;

        this.playerHasPaid = false;
        this.playerOutOfMoney = false;

        this.rolledValue = 0;
        this.amountToPay = 0;

        this.actionFinished = false;
    }

    public void transaction(Player currentPlayer, Owner owner) {
        currentPlayer.pay(owner, getAmountToPay());
        playerHasPaid = true;
        actionFinished = true;
    }

    @Override
    public void update(LinkedSubject subject) {
        if (subject instanceof MainController) {
            MainController mainController = (MainController) subject;
            this.playerHaveToPay = mainController.isPlayerHaveToPay();
            this.rolledValue = mainController.getRolledValue();
        }
    }

    public boolean isActionFinished() {
        return actionFinished;
    }

    public void setActionFinished(boolean actionFinished) {
        this.actionFinished = actionFinished;
    }

    public void createPayAction(Board board, Player currentPlayer) {
        this.payAction = new PayAction(board, currentPlayer);
    }

    @Override
    public ArrayList<LinkedSubject> getSubjects() {
        return observers;
    }

    public void update(Action action) {
        if (action instanceof PayAction) {
            this.playerOutOfMoney = ((PayAction) action).isPlayerOutOfMoney();
            this.playerHasPaid = ((PayAction) action).isPlayerHasPaid();
        }
    }

    public int getRolledValue() {
        return rolledValue;
    }

    public int getAmountToPay() {
        return amountToPay;
    }

    public void process() {
        payAction.process();
        if (actionFinished)
            informSubjects();
    }

    public boolean isPlayerOutOfMoney() {
        return playerOutOfMoney;
    }

    class PayAction implements Action{
        private final Board board;
        private final Player currentPlayer;

        private boolean playerOutOfMoney;

        private boolean initialized;

        public PayAction(Board board, Player currentPlayer) {
            this.board = board;
            this.currentPlayer = currentPlayer;

            this.playerOutOfMoney = false;

            this.initialized = false;
        }

        public boolean isPlayerHasPaid() {
            return playerHasPaid;
        }

        public boolean isPlayerOutOfMoney() {
            return playerOutOfMoney;
        }

        public void calculateAmountToPay(Field field, Owner owner, int rolledValue) {
            int amount = 0;
            if (!field.haveProperty()) {    // EVENT HERE, DEPENDS ON KIND OF FIELD

            }
            else {
                final Property property = field.getProperty();
                if (property instanceof City) {
                    final City city = (City) property;
                    amount = calculateCityFee(city, owner);
                }
                if (property instanceof Railway) {
                    final Railway railway = (Railway) property;
                    amount = calculateRailwayFee(railway, owner);
                }
                if (property instanceof Utility) {
                    final Utility utility = (Utility) property;
                    amount = calculateUtilityFee(utility, owner, rolledValue);
                }
            }
            amountToPay = amount;
        }

        private int calculateCityFee(final City city, final Owner owner) {
            int amount = 0;
            /*
            City.Buildings ownedBuildings = city.getBuildings();
            boolean ownerOfCountry = board.isPlayerHaveCountry(owner, city.getCountry());

            if (ownedBuildings == City.Buildings.ZERO && ownerOfCountry)
                amount = 2 * city.getCharge();
            else
                amount = city.getCharge();
            */
            return amount;
        }

        private int calculateRailwayFee(final Railway railway, final Owner owner) {
            //int ownedRailways = board.ownedRailways(owner);

            //return railway.getCharge(ownedRailways);
            return 0;
        }

        private int calculateUtilityFee(final Utility utility, final Owner owner, int rolledValue) {
            /*
            boolean ownedAllFields = board.ownAllUtilities(owner);
            int amount = utility.getCharge(rolledValue);
            if (ownedAllFields)
                amount *= 2;

            return amount;
             */
            return 0;
        }

        @Override
        public void process() {
            if (!initialized) {
                final int fieldNumber = currentPlayer.getCurrentFieldNumber();
                final Field field = board.getField(fieldNumber);

                final Owner fieldOwner = field.getOwner();
                calculateAmountToPay(field, fieldOwner, rolledValue);
                if (!currentPlayer.haveMoney(amountToPay)) {
                    playerOutOfMoney = true;
                }
                initialized = true;
                informController();
            }
        }

        @Override
        public void informController() {
            PayController.this.playerOutOfMoney = this.playerOutOfMoney;
        }
    }
}
