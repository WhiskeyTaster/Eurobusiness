package com.mygdx.game.board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.Pair;
import com.mygdx.game.owners.Owner;
import com.mygdx.game.properties.Property;
import com.mygdx.game.screens.Resolution;

// TODO: add settings depending on resolution

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    public static final int START_FIELD_NUMBER = 1;
    public static final int PRISON_FIELD_NUMBER = 11;
    public static final int PARKING_FIELD_NUMBER = 21;
    public static final int POLICE_FIELD_NUMBER = 31;

    private final Array<Field> fields;
    private final Center center;

    public Board(Resolution resolution, Pair<Float, Float> position, Owner owner) {
        this.fields = new Array<>();
        this.center = new Center(new Texture(Gdx.files.internal("textures/fields/1024/center.jpg")));
        createFields(resolution, owner);
        setPosition(resolution, position);
    }

    private void createFields(Resolution resolution, Owner owner) {
        HashMap<Integer, Property> propertyHashMap = importProperties();
        final int NUMBER_OF_FIELDS = 40;
        String path = "textures/fields/1024/";
        for (int i = 0; i < NUMBER_OF_FIELDS; i++) {
            String fileName = "field" + (i + 1) + ".jpg";
            Texture texture = new Texture(Gdx.files.internal(path + fileName));
            Sprite fieldSprite = new Sprite(texture);
            Field field = new Field(propertyHashMap.get(i + 1), fieldSprite, owner, i + 1);
            fields.add(field);
        }
    }

    private HashMap<Integer, Property> importProperties() {
        HashMap<Integer, Property> propertyHashMap = new HashMap<>();
        ArrayList<Property> properties = new Json().fromJson(ArrayList.class,
                Gdx.files.internal("json/properties.json"));
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("textures/propertyCards.atlas"));
        for (Property property : properties) {
            propertyHashMap.put(property.getFieldNumber(), property);
            property.setPropertySprite(atlas.createSprite("property" + property.getFieldNumber()));
        }
        return propertyHashMap;
    }

    private void setPosition(Resolution resolution, Pair<Float, Float> position) {

        final int BOARD_SIZE = (resolution == Resolution.RESOLUTION_1920_1080) ? 1024 : 800;
        final int RIGHT_BOTTOM_CORNER = 0;
        final int LEFT_BOTTOM_CORNER = 10;
        final int LEFT_TOP_CORNER = 20;
        final int RIGHT_TOP_CORNER = 30;
        final int FINISH_CORNER = 40;
        final float startX = position.getFirst();
        final float startY = position.getSecond();

        float beginX = startX + BOARD_SIZE;
        float beginY = startY;

        int southStart = RIGHT_BOTTOM_CORNER;
        int southEnd = LEFT_BOTTOM_CORNER + 1;

        for (int i = southStart; i < southEnd; i++) {
            Field field = fields.get(i);
            beginX -= field.getWidth();
            field.setPosition(beginX, beginY);
            field.setBuildings(Direction.SOUTH);
        }
        beginX = fields.get(LEFT_BOTTOM_CORNER).getX();
        beginY = fields.get(LEFT_BOTTOM_CORNER).getY() + fields.get(LEFT_BOTTOM_CORNER).getHeight();

        int westStart = LEFT_BOTTOM_CORNER + 1;
        int westEnd = LEFT_TOP_CORNER + 1;
        for (int i = westStart; i < westEnd; i++) {
            Field field = fields.get(i);
            field.setPosition(beginX, beginY);
            beginY += field.getHeight();
            field.setBuildings(Direction.WEST);

        }
        beginX = fields.get(LEFT_TOP_CORNER).getX() + fields.get(LEFT_TOP_CORNER).getWidth();
        beginY = fields.get(LEFT_TOP_CORNER).getY();

        int northStart = LEFT_TOP_CORNER + 1;
        int northEnd = RIGHT_TOP_CORNER + 1;
        for (int i = northStart; i < northEnd; i++) {
            Field field = fields.get(i);
            field.setPosition(beginX, beginY);
            beginX += field.getWidth();
            field.setBuildings(Direction.NORTH);

        }
        beginX = fields.get(RIGHT_TOP_CORNER).getX();
        beginY = fields.get(RIGHT_TOP_CORNER).getY();

        int eastStart = RIGHT_TOP_CORNER + 1;
        int eastEnd = FINISH_CORNER;
        for (int i = eastStart; i < eastEnd; i++) {
            Field field = fields.get(i);
            beginY -= field.getHeight();
            field.setPosition(beginX, beginY);
            field.setBuildings(Direction.EAST);
        }
        center.setPosition(fields.get(LEFT_BOTTOM_CORNER).getX() + fields.get(LEFT_BOTTOM_CORNER).getWidth(),
                fields.get(LEFT_BOTTOM_CORNER).getY() + fields.get(LEFT_BOTTOM_CORNER).getHeight());
    }

    public Field getField(int fieldNumber) {
        for (Field field : fields)
            if (field.getFieldNumber() == fieldNumber)
                return field;
        return null;
    }

    public Array<Field> getFields() {
        return fields;
    }

    public Center getCenter() {
        return center;
    }
}
