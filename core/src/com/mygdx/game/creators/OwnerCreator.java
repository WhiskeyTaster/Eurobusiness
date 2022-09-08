package com.mygdx.game.creators;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.owners.Bank;
import com.mygdx.game.owners.Owner;
import com.mygdx.game.owners.Player;

import java.util.ArrayList;

public class OwnerCreator {
    private final ArrayList<Owner> owners;
    private int humansNumber;
    private int aiNumber;

    public OwnerCreator() {
        this.owners = new ArrayList<>();
        this.humansNumber = 0;
        this.aiNumber = 0;
    }

    public void createBank(int numberOfPlayers) {
        final int playerStartingMoney = 3000;
        final int amountOfAllMoney = 31950;
        final int moneyInBank = amountOfAllMoney - numberOfPlayers * playerStartingMoney;
        Bank bank = new Bank("Bank", moneyInBank);
        owners.add(bank);
    }

    public void createPlayer(String name, Color color, Sprite pawn) {
        final int money = 3000;
        Player player = new Player(name, money, color, pawn);
        owners.add(player);
        incrementHumansNumber();
    }

    private void incrementHumansNumber() {
        humansNumber++;
    }

    public int getHumansNumber() {
        return humansNumber;
    }

    public ArrayList<Owner> getOwners() {
        return owners;
    }
}
