package com.caletes.game.models;


import com.caletes.game.models.items.Item;
import com.caletes.game.octree.Octree;

public class World extends Octree<Item> {

    private static final int TILE_WIDTH = 128;
    private static final int TILE_HEIGHT = 64;

    public World(int size) {
        super(size);
    }

    @Override
    public void pushObjectAt(Item item, int x, int y, int z) {
        super.pushObjectAt(item, x, y, z);
        int[] screenPosition = worldToScreenPosition(x, y, z);
        item.setPosition(screenPosition[0], screenPosition[1]);
    }

    private static int[] worldToScreenPosition(int x, int y, int z) {
        int isoX = (x - y) * TILE_WIDTH / 2;
        int isoY = (y + x) * -(TILE_HEIGHT / 2) + z * TILE_HEIGHT;
        return new int[]{isoX, isoY};
    }

}
