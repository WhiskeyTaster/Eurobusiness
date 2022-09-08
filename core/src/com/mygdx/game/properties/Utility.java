package com.mygdx.game.properties;

public class Utility extends Property{
    private int chargeMultiplier;

    public Utility() {
        super();
    }

    public Utility(String name, int price, int fieldNumber, int chargeMultiplier, int mortgage) {
        super(name, price, fieldNumber, mortgage);
        this.chargeMultiplier = chargeMultiplier;
    }

    @Override
    public int getCharge() {
        return 10;
    }

    @Override
    public String toString() {
        return super.toString() + "[chargeMultiplier: " + chargeMultiplier + "]";
    }
}
