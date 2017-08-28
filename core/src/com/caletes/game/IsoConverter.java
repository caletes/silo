package com.caletes.game;

public class IsoConverter {
    private static int tileWidth, tileHeight;

    public IsoConverter(int tileWidth, int tileHeight) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    public int[] toScreen(int x, int y, int z) {
        int sX = (x - y) * tileWidth / 2;
        int sY = (x + y) * -(tileHeight / 2) + z * tileHeight;
        return new int[]{sX, sY};
    }

    public int[] toWorld(float screenX, float screenY) {
        float wX = -(screenY / tileHeight - screenX / tileWidth);
        float wY = -(screenX / tileWidth + screenY / tileHeight);
        return new int[]{(int) wX, (int) wY};
    }
}
