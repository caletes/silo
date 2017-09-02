package com.caletes.game.models;


import com.caletes.game.IsoConverter;
import com.caletes.game.models.items.Item;
import com.caletes.game.octree.Node;
import com.caletes.game.octree.Octree;
import com.caletes.game.octree.OctreeOutOfBoundsException;

public class Region extends Octree<Item> {

    private final IsoConverter isoConverter;

    public Region(int size, IsoConverter isoConverter) {
        super(size);
        this.isoConverter = isoConverter;
    }

    public Node pushObjectAt(Item item, int x, int y, int z, int regionSize, int worldX, int worldY) throws OctreeOutOfBoundsException {
        int cubeX = x + worldX * regionSize;
        int cubeY = y + worldY * regionSize;
        int[] screenPosition = isoConverter.toScreen(cubeX, cubeY, z);
        item.setPosition(screenPosition[0], screenPosition[1]);
        return super.pushObjectAt(item, x, y, z);
    }
}
