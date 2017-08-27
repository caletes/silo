package com.caletes.game.models;


import com.caletes.game.IsoConverter;
import com.caletes.game.models.items.Item;
import com.caletes.game.octree.Octree;

public class World extends Octree<Item> {

    private final IsoConverter isoConverter;

    public World(int size, IsoConverter isoConverter) {
        super(size);
        this.isoConverter = isoConverter;
    }

    @Override
    public void pushObjectAt(Item item, int x, int y, int z) {
        super.pushObjectAt(item, x, y, z);
        int[] screenPosition = isoConverter.toScreen(x, y, z);
        item.setPosition(screenPosition[0], screenPosition[1]);
    }

}
