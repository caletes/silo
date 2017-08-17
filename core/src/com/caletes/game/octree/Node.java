package com.caletes.game.octree;

//cf. http://pierre-benet.developpez.com/tutoriels/algorithme-3d/octree-morton/
public class Node<T> implements Iterable<Node> {

    protected final int index;
    protected final int exponent;
    protected T object = null;
    protected Node parent = null;
    protected Node[] children = null;

    protected Node(int exponent) {
        this(0, exponent, null);
    }

    protected Node(int index, int exponent, Node parent) {
        this.index = index;
        this.exponent = exponent;
        this.parent = parent;
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
        //Equivalent à return (int) Math.pow(2, exponent);
        return 1 << exponent;
    }

    public Node getLeaf(long morton) {
        Node node = this;
        int currentExponent = exponent;
        while (!node.isLeaf()) {
            morton = getNextMorton(morton, currentExponent);
            node = node.children[node.getIndex(morton, currentExponent - 1)];
            currentExponent--;
        }
        return node;
    }

    public static long getNextMorton(long morton, int exponent) {
        //Equivalent à morton += Math.pow(2, 3 * exponent);
        morton += 1 << 3 * exponent;
        return morton;
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

    public int getIndex(long morton, int exponent) {
        return (int) (morton >> 3 * exponent) & 7;
    }

    public long getMorton() {
        Node node = this;
        long morton = 0;
        while (!node.isRoot()) {
            //Equivalent à morton |= node.index * (long) Math.pow(2, 3 * node.exponent);
            morton |= node.index << 3 * node.exponent;
            node = node.parent;
        }
        return morton;
    }

    public MortonCode.Vector3 getPosition() {
        return MortonCode.unpack(getMorton());
    }

    protected void split() {
        int childrenExponent = exponent - 1;
        children = new Node[8];
        children[0] = new Node(0, childrenExponent, this);
        children[1] = new Node(1, childrenExponent, this);
        children[2] = new Node(2, childrenExponent, this);
        children[3] = new Node(3, childrenExponent, this);
        children[4] = new Node(4, childrenExponent, this);
        children[5] = new Node(5, childrenExponent, this);
        children[6] = new Node(6, childrenExponent, this);
        children[7] = new Node(7, childrenExponent, this);
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
    public NodeIterator iterator() {
        return new NodeIterator(this, getSize());
    }

}