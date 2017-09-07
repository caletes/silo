package com.caletes.game.models;


import com.caletes.game.IsoConverter;
import com.caletes.game.models.items.Item;
import com.caletes.game.octree.Node;
import com.caletes.game.octree.Octree;
import com.caletes.game.octree.OctreeOutOfBoundsException;

public class Chunk {

    private int size;
    private Octree<Item> items;
    private IsoConverter isoConverter;

    public Chunk(int size, IsoConverter isoConverter) {
        this.size = size;
        this.items = new Octree(nextPowOf2(size));
        this.isoConverter = isoConverter;
    }

    public Octree<Item> getItems() {
        return items;
    }

    public Node pushObjectAt(Item item, int x, int y, int z, int worldX, int worldY) throws OctreeOutOfBoundsException {
        int cubeX = x + worldX * size;
        int cubeY = y + worldY * size;
        int[] screenPosition = isoConverter.toScreen(cubeX, cubeY, z);
        item.setPosition(screenPosition[0], screenPosition[1]);
        return items.setObjectAt(item, x, y, z);
    }

    public static int nextPowOf2(int a) {
        int nextPow;
        for (nextPow = 1; nextPow < a; nextPow <<= 1) ;
        return nextPow;
    }
}
