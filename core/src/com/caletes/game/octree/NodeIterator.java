package com.caletes.game.octree;


import java.util.Iterator;

public class NodeIterator implements Iterator {

    private Node root = null;
    private long morton = 0;
    private long currentMorton = 0;
    private long mortonMax = 0;

    public NodeIterator(Node node) {
        this.root = node;
        this.mortonMax = node.getMortonMax();
    }

    @Override
    public boolean hasNext() {
        return morton <= mortonMax;
    }

    @Override
    public Node next() {
        currentMorton = morton;
        Node node = null;
        try {
            node = root.getLeaf(morton);
        } catch (OctreeOutOfBoundsException e) {
            e.printStackTrace();
        }
        morton = Node.getNextMorton(morton, node.exponent);
        return node;
    }

    public MortonCode.Vector3 getPosition() {
        return MortonCode.unpack(currentMorton);
    }
}
