package com.mygdx.game.properties;

import com.mygdx.game.owners.Owner;

// TODO: finish

import java.util.HashMap;

public class Railway extends Property{
    private HashMap<Integer, Integer> charge;

    public Railway() {
        super();
    }

    public Railway(String name, int price, int fieldNumber, HashMap<Integer, Integer> charge, int mortgage) {
        super(name, price, fieldNumber, mortgage);
        this.charge = charge;
    }

    @Override
    public int getCharge() {
        return 0;
    }

    @Override
    public String toString() {
        return super.toString() + "[charge: " + charge.toString() + "]";
    }

}
