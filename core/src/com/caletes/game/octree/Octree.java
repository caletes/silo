package com.caletes.game.octree;


import java.util.ArrayList;
import java.util.List;

//cf. http://pierre-benet.developpez.com/tutoriels/algorithme-3d/octree-morton/
public class Octree {

    private final int exponent;
    private List<Positionable3D> objects = new ArrayList<>();
    private Octree[] children = null;
    
    public static Octree create(int size) {
        return new Octree(convertSizeToExponent(size));
    }

    private Octree(int exponent) {
        this.exponent = exponent;
    }

    public int getExponent() {
        return exponent;
    }

    public int getSize() {
        return convertExponentToSize(exponent);
    }

    public List<Positionable3D> getObjects() {
        return objects;
    }

    private static int convertSizeToExponent(int size) {
        if (!isPowOf2(size)) {
            throw new IllegalArgumentException(String.format("Octree size (%d) is not a power of 2", size));
        }
        return (int) (Math.log(size) / Math.log(2));
    }

    private static boolean isPowOf2(int x) {
        return (x != 0) && ((x & (x - 1)) == 0);
    }

    private static int convertExponentToSize(int exponent) {
        return (int) Math.pow(2, exponent);
    }

    public List<Positionable3D> findObjectsAt(int x, int y, int z) {
        return getLeaf(x, y, z).objects;
    }

    public Octree getLeaf(int x, int y, int z) {
        if (isLeaf()) {
            return this;
        }
        return children[getIndex(x, y, z)].getLeaf(x, y, z);
    }

    public int getIndex(Positionable3D object) {
        return getIndex(object.getX(), object.getY(), object.getZ());
    }

    public int getIndex(int x, int y, int z) {
        int sub = exponent - 1;
        long mortonCode = MortonCode.pack(x, y, z);
        return (int) (mortonCode >> 3 * sub) & 7;
    }

    public void addObject(Positionable3D object) {
        if (!isFinalLeaf()) {
            if (isLeaf()) {
                split();
            }
            children[getIndex(object)].addObject(object);
        } else {
            objects.add(object);
        }
    }

    private void split() {
        int childrenExponent = exponent - 1;
        children = new Octree[8];
        children[0] = new Octree(childrenExponent);
        children[1] = new Octree(childrenExponent);
        children[2] = new Octree(childrenExponent);
        children[3] = new Octree(childrenExponent);
        children[4] = new Octree(childrenExponent);
        children[5] = new Octree(childrenExponent);
        children[6] = new Octree(childrenExponent);
        children[7] = new Octree(childrenExponent);
    }

    public boolean isLeaf() {
        return children == null;
    }

    public boolean isFinalLeaf() {
        return exponent == 0;
    }
}
