package com.caletes.game.octree;


//cf. http://pierre-benet.developpez.com/tutoriels/algorithme-3d/octree-morton/
public class Octree {

    private int exponent;
    private int size;

    private Octree[] children;

    public Octree(int size) {
        if (!isPowOf2(size)) {
            throw new IllegalArgumentException("Octree size must be a power of 2");
        }
        this.size = size;
        exponent = convertSizeToExponent(size);
    }

    private boolean isPowOf2(int x) {
        return (x != 0) && ((x & (x - 1)) == 0);
    }

    public int getExponent() {
        return exponent;
    }

    public int getSize() {
        return size;
    }

    private int convertSizeToExponent(int size) {
        return (int) (Math.log(size) / Math.log(2));
    }


    private Octree getChild(int x, int y, int z) throws PositionOutOfBounds {
        Octree child = null;
        if (children != null) {
            child = children[getIndex(x, y, z)].getChild(x, y, z);
        }
        return child;
    }

    public int getIndex(int x, int y, int z) throws PositionOutOfBounds {
        if (isOutOfBounds(x, y, z)) {
            throw new PositionOutOfBounds(x, y, z);
        }
        int sub = exponent - 1;
        return ((x >> sub) & 1) + (((y >> sub) & 1) << 1) + (((z >> sub) & 1) << 2);
    }

    private boolean isOutOfBounds(int x, int y, int z) {
        return x < 0 || x >= size || y < 0 || y >= size || z < 0 || z >= size;
    }

    private void split() {
        int childrenSize = size - 1;
        children = new Octree[8];
        children[0] = new Octree(childrenSize);
        children[1] = new Octree(childrenSize);
        children[2] = new Octree(childrenSize);
        children[3] = new Octree(childrenSize);
        children[4] = new Octree(childrenSize);
        children[5] = new Octree(childrenSize);
        children[6] = new Octree(childrenSize);
        children[7] = new Octree(childrenSize);
    }

    public class PositionOutOfBounds extends Exception {
        public PositionOutOfBounds(int x, int y, int z) {
            super("point(" + x + "," + y + "," + z + ") is out of bounds");
        }
    }
}
