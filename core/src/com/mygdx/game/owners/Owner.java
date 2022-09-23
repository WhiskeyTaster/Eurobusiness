package com.mygdx.game.owners;

import com.mygdx.game.board.Field;

import java.util.ArrayList;

public abstract class Owner {
    static int next_id = 0;

    private int money;
    private final String name;
    private final ArrayList<Field> ownedFields;

    private final int id;

    public Owner(String name, int money) {
        this.name = name;
        this.money = money;
        this.ownedFields = new ArrayList<>();
        this.id = Owner.next_id;

        next_id++;
    }

    public Field getField(int uniqueFieldNumber) {
        try {
            for (Field field : ownedFields)
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
        System.out.println("Field added, player id: " + id);
        ownedFields.add(field);
        for (Field field1 : ownedFields)
            System.out.println(field1.getProperty().getName());
    }

    public void removeField(Field field) {
        ownedFields.remove(field);
    }

    public void addMoney(int money) {
        this.money += money;
    }

    public void pay(Owner owner, int amount) {
        this.removeMoney(amount);
        owner.addMoney(amount);
    }

    public ArrayList<Field> getOwnedFields() {
        return ownedFields;
    }

    public void removeMoney(int money) {
        this.money -= money;
    }
    public boolean haveMoney(int amount) {
        return money - amount >= 0;
    }
    public static void resetId() {
        next_id = 0;
    }
}
