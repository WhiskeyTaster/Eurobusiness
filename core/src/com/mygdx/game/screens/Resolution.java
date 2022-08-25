package com.mygdx.game.screens;

import com.mygdx.game.Pair;

public enum Resolution {
    RESOLUTION_1920_1080,
    RESOLUTION_800_600;

    public static Resolution getResolution(float width, float height) {
        final Pair<Float, Float> resolution_1920_1080 = new Pair<>(1920f, 1080f);
        final Pair<Float, Float> resolution_800_600 = new Pair<>(800f, 600f);

        if (resolution_1920_1080.getFirst().equals(width) && resolution_1920_1080.getSecond().equals(height))
            return RESOLUTION_1920_1080;
        else
            return RESOLUTION_800_600;
    }
}
