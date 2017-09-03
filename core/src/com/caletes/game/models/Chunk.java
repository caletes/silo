package com.caletes.game.models;


import com.caletes.game.IsoConverter;
import com.caletes.game.models.items.Item;
import com.caletes.game.octree.Node;
import com.caletes.game.octree.Octree;
import com.caletes.game.octree.OctreeOutOfBoundsException;

public class Chunk extends Octree<Item> {

    private final IsoConverter isoConverter;

    public Chunk(int size, IsoConverter isoConverter) {
        super(size);
        this.isoConverter = isoConverter;
    }

    public Node pushObjectAt(Item item, int x, int y, int z, int chunkSize, int worldX, int worldY) throws OctreeOutOfBoundsException {
        int cubeX = x + worldX * chunkSize;
        int cubeY = y + worldY * chunkSize;
        int[] screenPosition = isoConverter.toScreen(cubeX, cubeY, z);
        item.setPosition(screenPosition[0], screenPosition[1]);
        return super.pushObjectAt(item, x, y, z);
    }
}
