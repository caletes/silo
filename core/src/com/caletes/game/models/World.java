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
    public Node pushObjectAt(Item item, int x, int y, int z) {
        int[] screenPosition = isoConverter.toScreen(x, y, z);
        item.setPosition(screenPosition[0], screenPosition[1]);
        Node node = super.pushObjectAt(item, x, y, z);
        setItemVisibility(node);
        Node bottom = node.getNextOn(Direction.BOTTOM);
        Node west = node.getNextOn(Direction.WEST);
        Node north = node.getNextOn(Direction.NORTH);
        if (bottom != null)
            setItemVisibility(bottom);
        if (west != null)
            setItemVisibility(west);
        if (north != null)
            setItemVisibility(north);
        return node;
    }

    public void setItemVisibility(Node node) {
        Item item = (Item) node.getObject();
        if (item != null) {
            Node top = node.getNextOn(Direction.TOP);
            Node east = node.getNextOn(Direction.EAST);
            Node south = node.getNextOn(Direction.SOUTH);
            boolean visible = top == null || east == null || south == null || !hasVisibleItem(top) || !hasVisibleItem(east) || !hasVisibleItem(south);
            item.setVisible(visible);
        }
    }

    private boolean hasVisibleItem(Node node) {
        Item item = (Item) node.getObject();
        return item != null && item.isVisible();
    }

    public Node getPeakNode(int x, int y, int z) {
        Node node = getLeafAt(x, y, z);
        Node peak = null;
        while (true) {
            node = node.getNextOn(Direction.TOP);
            if (node.getObject() != null)
                peak = node;
            else
                return peak;
        }
    }
}
