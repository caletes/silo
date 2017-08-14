package com.caletes.game.octree;

import java.util.Iterator;

//cf. http://pierre-benet.developpez.com/tutoriels/algorithme-3d/octree-morton/
public class Node<T> implements Iterable<Node> {

    protected final int exponent;
    protected T object = null;
    protected Node parent = null;
    protected Node[] children = null;
    protected long mortonMax = 0;

    protected Node(int exponent) {
        this(exponent, null);
    }

    protected Node(int exponent, Node parent) {
        this.exponent = exponent;
        this.parent = parent;
        int maxXYZ = getSize() - 1;
        this.mortonMax = MortonCode.pack(maxXYZ, maxXYZ, maxXYZ);
    }

    public int getExponent() {
        return exponent;
    }

    public int getSize() {
        return convertExponentToSize(exponent);
    }

    public T getObject() {
        return object;
    }

    protected static int convertSizeToExponent(int size) {
        if (!isPowOf2(size))
            throw new IllegalArgumentException(String.format("Octree size (%d) is not a power of 2", size));
        return (int) (Math.log(size) / Math.log(2));
    }

    protected static boolean isPowOf2(int x) {
        return (x != 0) && ((x & (x - 1)) == 0);
    }

    protected static int convertExponentToSize(int exponent) {
        return 1 << exponent; // 1*2^exponent;
    }

    public Node getLeaf(long morton) {
        Node node = this;
        int childrenExponent = exponent;
        while (!node.isLeaf()) {
            morton += 1 << 3 * childrenExponent;
            node = node.children[node.getIndex(morton)];
            childrenExponent--;
        }
        return node;
    }

    public void pushObjectAt(T object, int x, int y, int z) {
        long morton = MortonCode.pack(x, y, z);
        pushObjectAt(object, morton);
    }

    public void pushObjectAt(T object, long morton) {
        if (!isFinalLeaf()) {
            if (isLeaf())
                split();
            getLeaf(morton).pushObjectAt(object, morton);
        } else {
            this.object = object;
        }
    }

    public T getObjectAt(int x, int y, int z) {
        long morton = MortonCode.pack(x, y, z);
        return (T) getLeaf(morton).object;
    }

    public T popObjectAt(int x, int y, int z) {
        long morton = MortonCode.pack(x, y, z);
        Node leaf = getLeaf(morton);
        T object = (T) leaf.object;
        leaf.object = null;
        leaf.cleanBranch();
        return object;
    }

    private void cleanBranch() {
        if (!isRoot() && this.parent.allChildrenAreEmpty()) {
            this.parent.cleanBranch();
            this.parent = null;
        }
    }

    private boolean allChildrenAreEmpty() {
        for (Node child : children) {
            if (child.object != null)
                return false;
        }
        return true;
    }

    public int getIndex(long morton) {
        int childrenExponent = exponent - 1;
        return (int) (morton >> 3 * childrenExponent) & 7;
    }

    protected void split() {
        int childrenExponent = exponent - 1;
        children = new Node[8];
        children[0] = new Node(childrenExponent, this);
        children[1] = new Node(childrenExponent, this);
        children[2] = new Node(childrenExponent, this);
        children[3] = new Node(childrenExponent, this);
        children[4] = new Node(childrenExponent, this);
        children[5] = new Node(childrenExponent, this);
        children[6] = new Node(childrenExponent, this);
        children[7] = new Node(childrenExponent, this);
    }

    public boolean isLeaf() {
        return children == null;
    }

    public boolean isFinalLeaf() {
        return exponent == 0;
    }

    public boolean isRoot() {
        return parent == null;
    }

    @Override
    public Iterator<Node> iterator() {
        return new NodeIterator(this, getSize());
    }

}
