package com.mygdx.game.owners;

import com.mygdx.game.board.Field;

import java.util.ArrayList;

public abstract class Owner {
    static int next_id = 0;

    private int money;
    private final String name;
    private final ArrayList<Field> fields;

    private final int id;

    public Owner(String name, int money) {
        this.name = name;
        this.money = money;
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

    public int getMoney() {
        return money;
    }

    public String getName() {
        return name;
    }

    public void addField(Field field) {
        fields.add(field);
    }

    public void removeField(Field field) {
        fields.remove(field);
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
    public boolean haveMoney(int amount) {
        return money - amount >= 0;
    }
}
