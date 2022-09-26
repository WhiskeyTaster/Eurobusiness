package com.mygdx.game.board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.owners.Owner;
import com.mygdx.game.properties.City;
import com.mygdx.game.properties.Property;

import java.util.ArrayList;
import java.util.List;


public class Field {
    private final Property property;
    private final Sprite fieldSprite;
    private Owner owner;
    private final int fieldNumber;
    private final ArrayList<Sprite> houses;
    private Sprite hotel;

    public Field(Property property, Sprite fieldSprite, Owner owner, int fieldNumber) {
        this.property = property;
        this.fieldSprite = fieldSprite;
        this.owner = owner;
        this.fieldNumber = fieldNumber;
        this.houses = new ArrayList<>();
    }

    public void setBuildings(Direction direction) {
        setHouses(direction);
        setHotels(direction);
    }

    private void setHouses(Direction direction) {
        Texture textureZero = new Texture(Gdx.files.internal("textures/buildings/1024/house_0.jpg"));
        Texture textureOne = new Texture(Gdx.files.internal("textures/buildings/1024/house_1.jpg"));
        Sprite houseZero = new Sprite(textureZero);
        Sprite houseOne = new Sprite(textureOne);

        float xSize = 12;
        float ySize = 16;
        houseZero.setSize(xSize, ySize);
        houseOne.setSize(ySize, xSize);

        float xPadding = 8;
        float yPadding = 7;
        float paddingBetween = 4;
        float startingX = 0;
        float startingY = 0;

        if (direction == Direction.SOUTH) {
            startingX = getX() + xPadding;
            startingY = getY() + getHeight() - yPadding - ySize;
        } else if (direction == Direction.WEST){
            xSize = 16;
            ySize = 12;
            startingX = getX() + getWidth() - yPadding - xSize;
            startingY = getY() + getHeight() - xPadding - ySize;
        } else if (direction == Direction.NORTH) {
            startingX = getX() + xPadding;
            startingY = getY() + yPadding;
        } else {
            xSize = 16;
            ySize = 12;
            startingX = getX() + yPadding;
            startingY = getY() + getHeight() - xPadding - ySize;
        }

        if (direction == Direction.SOUTH || direction == Direction.NORTH) {
            for (int i = 0; i < 4; i++) {
                houseZero.setPosition(startingX, startingY);
                houses.add(new Sprite(houseZero));
                startingX = startingX + paddingBetween + xSize;
            }
        } else {
            for (int i = 0; i < 4; i++) {
                houseOne.setPosition(startingX, startingY);
                houses.add(new Sprite(houseOne));
                startingY = startingY - paddingBetween - ySize;
            }
        }
    }

    private void setHotels(Direction direction) {
        Texture textureZero = new Texture(Gdx.files.internal("textures/buildings/1024/hotel_0.jpg"));
        Texture textureOne = new Texture(Gdx.files.internal("textures/buildings/1024/hotel_1.jpg"));
        Sprite hotelZero = new Sprite(textureZero);
        Sprite hotelOne = new Sprite(textureOne);

        float xSize = 20;
        float ySize = 16;
        float xPadding = 8;
        float yPadding = 7;
        float startingX = 0;
        float startingY = 0;

        hotelZero.setSize(xSize, ySize);
        hotelOne.setSize(ySize, xSize);

        if (direction == Direction.SOUTH) {
            startingX = getX() + xPadding;
            startingY = getY() + getHeight() - yPadding - ySize;
        } else if (direction == Direction.WEST){
            xSize = 16;
            ySize = 20;
            startingX = getX() + getWidth() - yPadding - xSize;
            startingY = getY() + getHeight() - xPadding - ySize;
        } else if (direction == Direction.NORTH) {
            startingX = getX() + xPadding;
            startingY = getY() + yPadding;
        } else {
            xSize = 16;
            ySize = 20;
            startingX = getX() + yPadding;
            startingY = getY() + getHeight() - xPadding - ySize;
        }

        if (direction == Direction.SOUTH || direction == Direction.NORTH) {
            hotelZero.setPosition(startingX, startingY);
            hotel = new Sprite(hotelZero);
        } else {
            hotelOne.setPosition(startingX, startingY);
            hotel = new Sprite(hotelOne);
        }
    }

    public List<Sprite> getHouses() {
        if (haveProperty() && property instanceof City) {
            City city = (City) property;
            return houses.subList(0, getHousesNumber());
        } else
            return new ArrayList<>();
    }

    public Sprite getHotel() {
        if (haveProperty() && property instanceof City)
            return hotel;
        else
            return null;
    }

    public int getFieldNumber() {
        return fieldNumber;
    }

    public float getX() {
        return fieldSprite.getX();
    }

    public float getY() {
        return fieldSprite.getY();
    }

    public float getWidth() {
        return fieldSprite.getWidth();
    }

    public float getHeight() {
        return fieldSprite.getHeight();
    }

    public void setPosition(float x, float y) {
        fieldSprite.setPosition(x, y);
    }

    public Sprite getPropertySprite() {
        return property.getPropertySprite();
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public int getPrice() {
        return property.getPrice();
    }

    public boolean haveProperty() {
        return property != null;
    }

    public Property getProperty() {
        return haveProperty() ? property : null;
    }

    public Sprite getFieldSprite() {
        return fieldSprite;
    }

    public boolean isMortgaged() {
        return haveProperty() && property.isMortgaged();
    }

    public void setMortgaged(boolean mortgaged) {
        if (haveProperty())
            property.setMortgaged(mortgaged);
    }

    public int getMortgage() {
        return haveProperty() ? property.getMortgage() : 0;
    }

    public int getHousesNumber() {
        if (haveProperty() && property instanceof City) {
            return ((City) property).getHouses();
        }
        return 0;
    }

    public int getHotelsNumber() {
        if (haveProperty() && property instanceof City) {
            return ((City) property).getHotels();
        }
        return 0;
    }


}
