package com.caletes.game;

import com.badlogic.gdx.math.Vector2;

public class IsoConverter {
    private static int tileWidth, tileHeight;

    public IsoConverter(int tileWidth, int tileHeight) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    public Vector2 toScreen(int x, int y, int z) {
        int sX = (x - y) * tileWidth / 2;
        int sY = (x + y) * -(tileHeight / 2) + z * tileHeight;
        return new Vector2(sX, sY);
    }

    public Vector2 toWorld(float screenX, float screenY) {
        float wX = -(screenY / tileHeight - screenX / tileWidth);
        float wY = -(screenX / tileWidth + screenY / tileHeight);
        return new Vector2((int) wX, (int) wY);
    }
}
