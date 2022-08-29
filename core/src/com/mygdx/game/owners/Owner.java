package com.mygdx.game.owners;

import com.mygdx.game.board.Field;

import java.util.ArrayList;

public abstract class Owner {
    static int next_id = 0;

    private int money;
    private final String name;
    private final ArrayList<Field> fields;

    private final int id;

    public Owner(int money, String name) {
        this.money = money;
        this.name = name;
        this.fields = new ArrayList<>();
        this.id = Owner.next_id;

        next_id++;
    }

    public Field getField(int uniqueFieldNumber) {
        try {
            for (Field field : fields)
                if (field.getFieldNumber() == uniqueFieldNumber)
                    return field;
            throw new RuntimeException("Field doesn't exists.");
        } catch (RuntimeException e) {
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addField(Field field) {
        fields.add(field);
    }

    public void addMoney(int money) {
        this.money += money;
    }

    public void pay(Owner owner, int amount) {
        this.removeMoney(amount);
        owner.addMoney(amount);
    }

    public void removeMoney(int money) {
        this.money -= money;
    }

}
