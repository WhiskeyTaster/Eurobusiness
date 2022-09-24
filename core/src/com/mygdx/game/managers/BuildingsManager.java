package com.mygdx.game.managers;

import com.mygdx.game.Eurobusiness;
import com.mygdx.game.board.Field;
import com.mygdx.game.logic.MainController;
import com.mygdx.game.owners.Player;
import com.mygdx.game.properties.City;
import com.mygdx.game.properties.Countries;

import java.util.ArrayList;
import java.util.Objects;

public class BuildingsManager{
    private final Eurobusiness game;
    private final MainController mainController;
    private Integer housesBought;
    private Integer hotelsBought;

    public BuildingsManager(Eurobusiness game, MainController mainController) {
        this.game = Objects.requireNonNull(game, "game is null");
        this.mainController = Objects.requireNonNull(mainController, "mainController is null");
        this.housesBought = 0;
        this.hotelsBought = game.hotelBuyCounter.get(game.getCurrentPlayer());
    }

    public void process() {
        reset();
    }

    private boolean canBuyBuilding() {
        return !mainController.isPlayerRolled();
    }

    public boolean canBuyHouse(Player currentPlayer, Field currentField) {
        return canBuyBuilding() && checkHousesRule(currentPlayer, currentField) && housesBought < 3;
    }

    public boolean canBuyHotel(Player currentPlayer, Field currentField) {
        return canBuyBuilding() && checkHotelRule(currentPlayer, currentField) && hotelsBought < 3;
    }

    private boolean checkHotelRule(Player currentPlayer, Field currentField) {
        City city = (City) currentField.getProperty();
        String country = city.getCountry();
        Integer ownedCities = currentPlayer.ownedCities(country);
        if (!ownedCities.equals(Objects.requireNonNull(Countries.getCountry(country)).getCities()))
            return false;
        if (currentField.getHotels() == 1)
            return false;
        ArrayList<Field> cities = currentPlayer.citiesList(country);
        for (Field field : cities)
            if (!(field.getHouses() == 4 || (field.getHouses() == 0 && field.getHotels() == 1))) {
                return false;
            }
        return true;
    }

    private boolean checkHousesRule(Player currentPlayer, Field currentField) {
        City city = (City) currentField.getProperty();
        String country = city.getCountry();
        Integer ownedCities = currentPlayer.ownedCities(country);
        if (!ownedCities.equals(Objects.requireNonNull(Countries.getCountry(country)).getCities()))
            return false;
        if (currentField.getHotels() == 1)
            return false;
        ArrayList<Field> cities = currentPlayer.citiesList(country);
        int minHouses = currentField.getHouses();
        for (Field field : cities)
            minHouses = Math.min(field.getHouses(), minHouses);
        return minHouses != 4 && city.getHouses() <= minHouses;
    }

    public void buyBuilding(Player currentPlayer, Field currentField) {
        City city = (City) currentField.getProperty();
        int price = city.getBuildingCost();
        currentPlayer.pay(game.bank, price);
        city.addBuilding();
    }

    public void houseBought() {
        housesBought = housesBought + 1;
    }

    public void hotelBought() {
        hotelsBought = hotelsBought + 1;
        game.houseBuyCounter.replace(game.getCurrentPlayer(), hotelsBought);
    }

    private void reset() {
        if (mainController.isRound())
            game.hotelBuyCounter.replace(game.getCurrentPlayer(), 0);
    }
}
