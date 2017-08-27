package com.caletes.game.models.tilesheet;

import com.badlogic.gdx.graphics.Texture;

public abstract class Cubesheet {
    private static Texture texture;
    private static int spriteWidth, spriteHeight;
    private static int tileWidth, tileHeight;

    public Cubesheet(Texture texture, int spriteWidth, int spriteHeight, int tileWidth, int tileHeight) {
        this.texture = texture;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    public static Texture getTexture() {
        return texture;
    }

    public int getSpriteWidth() {
        return spriteWidth;
    }

    public int getSpriteHeight() {
        return spriteHeight;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public int getOriginX() {
        return spriteWidth / 2;
    }

    public int getOriginY() {
        return spriteHeight - tileHeight / 2;
    }

    public abstract int[] getGrass();

    public abstract int[] getGround();

    public abstract int[] getStone();

    public abstract int[] getSand();

    public abstract int[] getSnow();

    public abstract int[] getWater();
    
    public abstract int[] getMarker();
}
