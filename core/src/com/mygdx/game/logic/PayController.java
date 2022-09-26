package com.mygdx.game.logic;

import com.mygdx.game.board.Board;
import com.mygdx.game.board.Field;
import com.mygdx.game.action.Action;
import com.mygdx.game.owners.Owner;
import com.mygdx.game.owners.Player;
import com.mygdx.game.properties.*;

import java.util.ArrayList;
import java.util.Objects;

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

    public void createPayAction(Field field, Player currentPlayer) {
        this.payAction = new PayAction(field, currentPlayer);
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
        private final Field field;
        private final Player currentPlayer;

        private boolean playerOutOfMoney;

        private boolean initialized;

        public PayAction(Field field, Player currentPlayer) {
            this.field = Objects.requireNonNull(field, "field is null");
            this.currentPlayer = Objects.requireNonNull(currentPlayer, "player is null");

            this.playerOutOfMoney = false;

            this.initialized = false;
        }

        public boolean isPlayerHasPaid() {
            return playerHasPaid;
        }

        public boolean isPlayerOutOfMoney() {
            return playerOutOfMoney;
        }

        public void calculateAmountToPay(Field field, Player owner, int rolledValue) {
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

        private int calculateCityFee(final City city, final Player owner) {
            Countries country = Objects.requireNonNull(Countries.getCountry(city.getCountry()), "country is null");
            return owner.ownedCities(city.getCountry()) == country.getCities() && city.getBuildings() == 0
                    ? 2 * city.getCharge() : city.getCharge();
        }

        private int calculateRailwayFee(final Railway railway, final Player owner) {
            return owner.ownedRailways() * railway.getCharge();
        }

        private int calculateUtilityFee(final Utility utility, final Player owner, int rolledValue) {
            return owner.ownedUtilities() * utility.getCharge() * rolledValue;
        }

        @Override
        public void process() {
            if (!initialized) {
                final Player fieldOwner = (Player) field.getOwner();
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
