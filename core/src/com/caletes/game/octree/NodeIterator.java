package com.caletes.game.octree;


import java.util.Iterator;

public class NodeIterator implements Iterator {

    private Node root = null;
    private long morton = 0;
    private long currentMorton = 0;
    private long mortonMax = 0;

    public NodeIterator(Node node, int size) {
        this.root = node;
        int maxXYZ = size - 1;
        this.mortonMax = MortonCode.pack(maxXYZ, maxXYZ, maxXYZ);
    }

    @Override
    public boolean hasNext() {
        return morton <= mortonMax;
    }

    @Override
    public Node next() {
        currentMorton = morton;
        Node node = root.getLeaf(morton);
        morton = Node.getNextMorton(morton, node.exponent);
        return node;
    }

    public MortonCode.Vector3 getPosition() {
        return MortonCode.unpack(currentMorton);
    }
}
