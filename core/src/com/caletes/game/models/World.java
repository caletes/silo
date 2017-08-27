package com.caletes.game.models;


import com.caletes.game.IsoConverter;
import com.caletes.game.models.items.Item;
import com.caletes.game.octree.Direction;
import com.caletes.game.octree.Node;
import com.caletes.game.octree.Octree;

public class World extends Octree<Item> {


    public World(int size) {
        super(size);
    }

    @Override
    public void pushObjectAt(Item item, int x, int y, int z) {
        super.pushObjectAt(item, x, y, z);
        int[] screenPosition = IsoConverter.toScreen(x, y, z);
        item.setPosition(screenPosition[0], screenPosition[1]);
    }

}
