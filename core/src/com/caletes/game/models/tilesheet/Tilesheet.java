package com.caletes.game.models.tilesheet;

import com.badlogic.gdx.graphics.Texture;

public abstract class Tilesheet {
    private static Texture texture;
    private static int tileWidth, tileHeight;

    public Tilesheet(Texture texture, int tileWidth, int tileHeight) {
        this.texture = texture;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    public static Texture getTexture() {
        return texture;
    }

    public static int getTileWidth() {
        return tileWidth;
    }

    public static int getTileHeight() {
        return tileHeight;
    }

    public int getOriginX() {
        return tileWidth / 2;
    }

    public int getOriginY() {
        return tileHeight - tileWidth / 2;
    }

    public abstract int[] getGrass();

    public abstract int[] getGround();

    public abstract int[] getStone();

    public abstract int[] getSand();

    public abstract int[] getSnow();

    public abstract int[] getWater();
}
