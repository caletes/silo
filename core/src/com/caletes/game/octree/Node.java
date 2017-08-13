package com.caletes.game.octree;


//cf. http://pierre-benet.developpez.com/tutoriels/algorithme-3d/octree-morton/
public class Node<T> {

    protected final int exponent;
    protected T object = null;
    protected Node[] children = null;

    protected Node(int exponent) {
        this.exponent = exponent;
    }

    public int getExponent() {
        return exponent;
    }

    public int getSize() {
        return convertExponentToSize(exponent);
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
        return (int) Math.pow(2, exponent);
    }

    public T getObjectAt(int x, int y, int z) {
        return (T) getLeaf(x, y, z).object;
    }

    public Node getLeaf(int x, int y, int z) {
        if (isLeaf())
            return this;
        return getChildAt(x, y, z).getLeaf(x, y, z);
    }

    public int getIndex(int x, int y, int z) {
        int sub = exponent - 1;
        long mortonCode = MortonCode.pack(x, y, z);
        return (int) (mortonCode >> 3 * sub) & 7;
    }

    public void addObjectAt(T object, int x, int y, int z) {
        if (!isFinalLeaf()) {
            if (isLeaf())
                split();
            getChildAt(x, y, z).addObjectAt(object, x, y, z);
        } else {
            this.object = object;
        }
    }

    protected Node getChildAt(int x, int y, int z) {
        return children[getIndex(x, y, z)];
    }

    protected void split() {
        int childrenExponent = exponent - 1;
        children = new Node[8];
        children[0] = new Node(childrenExponent);
        children[1] = new Node(childrenExponent);
        children[2] = new Node(childrenExponent);
        children[3] = new Node(childrenExponent);
        children[4] = new Node(childrenExponent);
        children[5] = new Node(childrenExponent);
        children[6] = new Node(childrenExponent);
        children[7] = new Node(childrenExponent);
    }

    public boolean isLeaf() {
        return children == null;
    }

    public boolean isFinalLeaf() {
        return exponent == 0;
    }
}
