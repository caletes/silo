package com.caletes.game;

public class IsoConverter {
    private static final int TILE_WIDTH = 128;
    private static final int TILE_HEIGHT = 64;

    private IsoConverter() {
    }

    public static int[] toScreen(int x, int y, int z) {
        int sX = (x - y) * TILE_WIDTH / 2;
        int sY = (x + y) * -(TILE_HEIGHT / 2) + z * TILE_HEIGHT;
        return new int[]{sX, sY};
    }

    public static int[] toWorld(float screenX, float screenY) {
        float wX = -(screenY / TILE_HEIGHT - screenX / TILE_WIDTH);
        float wY = -(screenX / TILE_WIDTH + screenY / TILE_HEIGHT);
        return new int[]{(int) wX, (int) wY};
    }
}
