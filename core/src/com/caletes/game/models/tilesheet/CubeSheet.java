package com.caletes.game.models.tilesheet;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public abstract class CubeSheet {

    private TextureAtlas atlas;
    private static int spriteWidth, spriteHeight;
    private static int tileWidth, tileHeight;

    public CubeSheet(String internalPackFile, int spriteWidth, int spriteHeight, int tileWidth, int tileHeight) {
        this.atlas = new TextureAtlas(internalPackFile);
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
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
        return tileWidth / 2;
    }

    public int getOriginY() {
        return spriteHeight - tileHeight / 2;
    }

    public TextureAtlas.AtlasRegion getTexture(String name) {
        return atlas.findRegion(name);
    }
}
