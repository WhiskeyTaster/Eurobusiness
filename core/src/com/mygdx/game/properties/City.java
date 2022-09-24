package com.mygdx.game.properties;

import java.util.HashMap;

public class City extends Property{
    private final static int HOTEL = 5;

    private String country;
    private HashMap<String, Integer> charge;
    private int buildingCost;
    private int buildings;

    public City() {
        super();
    }

    public City(String country, String name, int price, int fieldNumber, HashMap<String, Integer> charge,
                int buildingCost, int mortgage) {
        super(name, price, fieldNumber, mortgage);
        this.country = country;
        this.charge = charge;
        this.buildingCost = buildingCost;
        this.buildings = 0;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public int getCharge() {
        return charge.get(String.valueOf(buildings));
    }

    public int getHouses() {
        return buildings < HOTEL ? buildings : 0;
    }

    public int getHotels() {
        return buildings == HOTEL ? 1 : 0;
    }

    public int getBuildings() {
        return buildings;
    }

    public int getBuildingCost() {
        return buildingCost;
    }

    public void addBuilding() {
        buildings++;
    }

    @Override
    public String toString() {
        return super.toString() +
                "[country: " + country + ", charge: " + charge.toString() + ", buildingCost: " + buildingCost + "]";
    }
}
