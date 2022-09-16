package com.mygdx.game.action;

import com.mygdx.game.board.Field;
import com.mygdx.game.owners.Player;

import java.util.ArrayList;


public class AuctionAction implements Action{

    private ArrayList<Player> players;
    private Player currentPlayer;
    private Field field;

    private int currentPrice;
    private int playerIndex;
    private int startingPrice;

    private boolean auctionStarted;
    private boolean auctionFinished;

    private boolean setupFinished;
    private boolean actionFinished;

    public AuctionAction(ArrayList<Player> players, Player currentPlayer, Field field) {
        this.players = new ArrayList<>();
        this.players.addAll(players);

        this.currentPlayer = currentPlayer;
        this.field = field;

        this.currentPrice = 0;
        this.playerIndex = 0;
        this.startingPrice = 0;

        this.auctionStarted = false;
        this.auctionFinished = false;

        this.setupFinished = false;
        this.actionFinished = false;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setAuctionStarted(boolean auctionStarted) {
        this.auctionStarted = auctionStarted;
    }

    public boolean isAuctionStarted() {
        return auctionStarted;
    }

    public boolean isAuctionFinished() {
        auctionFinished = (players.size() == 1 && currentPrice != startingPrice) || players.size() == 0;
        return auctionFinished;
    }

    public boolean isFieldSold() {
        return players.size() == 1 && currentPrice != startingPrice;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public void nextPlayer() {
        playerIndex++;
        if (playerIndex >= players.size())
            playerIndex = 0;
        currentPlayer = players.get(playerIndex);
    }

    public void nextPlayerFixed() {
        if (players.size() > 0) {
            if (playerIndex >= players.size())
                playerIndex = 0;
            currentPlayer = players.get(playerIndex);
        }
    }

    public void removeFromAuctioneers(Player currentPlayer) {
        players.remove(currentPlayer);
    }

    public void setupAuction(Field auctionField) {
        this.field = auctionField;
        orderPlayers();
        setPrice();
        playerFilter(startingPrice);
        auctionStarted = players.size() >= 1;
        if (auctionStarted)
            currentPlayer = players.get(0);
        setupFinished = true;
    }

    public void setupAuction(Field auctionField, int amount) {
        this.field = auctionField;
        orderPlayers();
        setPrice(amount);
        playerFilter(startingPrice);
        auctionStarted = players.size() >= 1;
        if (auctionStarted)
            currentPlayer = players.get(0);
        setupFinished = true;
    }

    public void updatePrice(int amount) {
        currentPrice = amount;
    }

    private void orderPlayers() {
        ArrayList<Player> orderedPlayers = new ArrayList<>();
        for (int i = currentPlayer.getId() - 1; i < players.size(); i++)
            orderedPlayers.add(players.get(i));
        for (int i = 0; i < currentPlayer.getId() - 1; i++)
            orderedPlayers.add(players.get(i));
        players = orderedPlayers;
    }

    private void playerFilter(int price) {
        ArrayList<Player> newOrderedPlayers = new ArrayList<>();
        for (Player player : players)
            if (player.haveMoney(price) && player != field.getOwner())
                newOrderedPlayers.add(player);
        players = newOrderedPlayers;
    }

    private void setPrice() {
        startingPrice = field.getPrice() / 2;
        currentPrice = startingPrice;
    }

    private void setPrice(int amount) {
        startingPrice = amount;
        currentPrice = startingPrice;
    }

    @Override
    public void process() {
        if (!setupFinished)
            this.setupAuction(field);
    }

    public void setActionFinished(boolean actionFinished) {
        this.actionFinished = actionFinished;
    }

    public boolean isActionFinished() {
        return actionFinished;
    }

    public void sellField() {
        currentPlayer.pay(field.getOwner(), currentPrice);
        transferField();
        setActionFinished(true);
    }

    private void transferField() {
        field.getOwner().removeField(field);
        field.setOwner(currentPlayer);
        currentPlayer.addField(field);
    }

    @Override
    public void informController() {

    }
}
