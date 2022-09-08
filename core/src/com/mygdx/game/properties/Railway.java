package com.mygdx.game.properties;


public class Railway extends Property{
    private int charge;

    public Railway() {
        super();
    }

    public Railway(String name, int price, int fieldNumber, int charge, int mortgage) {
        super(name, price, fieldNumber, mortgage);
        this.charge = charge;
    }

    @Override
    public int getCharge() {
        return charge;
    }

    @Override
    public String toString() {
        return super.toString() + "[charge: " + charge + "]";
    }

}
