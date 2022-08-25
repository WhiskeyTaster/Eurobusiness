package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;

public class FontHolder {
    public final HashMap<String, BitmapFont> fontHashMap;

    public FontHolder() {
        this.fontHashMap = new HashMap<>();

    }

    public BitmapFont getFont(String fontName) {
        return fontHashMap.get(fontName);
    }

    public void addFont(int size, Color color, String fontName) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Arcon.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

        params.size = size;
        params.color = color;

        BitmapFont font = generator.generateFont(params);
        fontHashMap.put(fontName, font);
    }
}
