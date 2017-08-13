package com.caletes.game.octree;


//cf. http://pierre-benet.developpez.com/tutoriels/algorithme-3d/octree-morton/
public class Node<T> {

    protected final int exponent;
    protected T object = null;
    protected Node parent = null;
    protected Node[] children = null;

    protected Node(int exponent) {
        this(exponent, null);
    }

    protected Node(int exponent, Node parent) {
        this.exponent = exponent;
        this.parent = parent;
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

    public void pushObjectAt(T object, int x, int y, int z) {
        if (!isFinalLeaf()) {
            if (isLeaf())
                split();
            getLeaf(x, y, z).pushObjectAt(object, x, y, z);
        } else {
            this.object = object;
        }
    }

    public T popObjectAt(int x, int y, int z) {
        Node leaf = getLeaf(x, y, z);
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

    protected Node getChildAt(int x, int y, int z) {
        return children[getIndex(x, y, z)];
    }

    public int getIndex(int x, int y, int z) {
        int sub = exponent - 1;
        long mortonCode = MortonCode.pack(x, y, z);
        return (int) (mortonCode >> 3 * sub) & 7;
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
}
