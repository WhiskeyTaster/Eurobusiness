package com.mygdx.game.properties;

import java.util.HashMap;

public class City extends Property{
    private String country;
    private HashMap<Integer, Integer> charge;
    private int buildingCost;
    private int buildings;

    public City() {
        super();
    }

    public City(String country, String name, int price, int fieldNumber, HashMap<Integer, Integer> charge,
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
        return charge.get(buildings);
    }

    @Override
    public String toString() {
        return super.toString() +
                "[country: " + country + ", charge: " + charge.toString() + ", buildingCost: " + buildingCost + "]";
    }
}
