package com.caletes.game.models;


import com.caletes.game.IsoConverter;
import com.caletes.game.models.items.Item;
import com.caletes.game.octree.Direction;
import com.caletes.game.octree.Node;
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

    public Node getPeakNode(int x, int y, int z) {
        Node node = getLeafAt(x, y, z);
        while (node.getObject() != null) {
            node = node.getNextOn(Direction.TOP);
        }
        return node;
    }

    public boolean isVisible(Node node) {
        return node.getNextOn(Direction.TOP).getObject() == null ||
                node.getNextOn(Direction.EAST).getObject() == null ||
                node.getNextOn(Direction.SOUTH).getObject() == null;
    }

}
