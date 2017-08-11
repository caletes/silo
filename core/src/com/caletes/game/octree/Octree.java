package com.caletes.game.octree;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//cf. http://pierre-benet.developpez.com/tutoriels/algorithme-3d/octree-morton/
public class Octree {

    private static final int DEFAULT_MAX_OBJECTS = 10;

    private final int exponent;
    private final int maxObjects;
    private List<Positionable3D> objects = new ArrayList<>();
    private Octree[] children = null;

    public static Octree create(int size) {
        return Octree.create(size, DEFAULT_MAX_OBJECTS);
    }

    public static Octree create(int size, int maxObjects) {
        return new Octree(convertSizeToExponent(size), maxObjects);
    }

    private Octree(int exponent, int maxObjects) {
        this.exponent = exponent;
        this.maxObjects = maxObjects;
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

    public List<Positionable3D> findObjectsNear(int x, int y, int z) {
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
        return ((x >> sub) & 1) + (((y >> sub) & 1) << 1) + (((z >> sub) & 1) << 2);
    }

    public void addObject(Positionable3D toAdd) {
        objects.add(toAdd);
        boolean leaf = isLeaf();
        if (hasMaxObjectsExceeded() || !leaf) {
            if (leaf) {
                split();
            }
            for (Positionable3D object : objects) {
                children[getIndex(object)].addObject(object);
            }
            objects.clear();
        }
    }

    private boolean hasMaxObjectsExceeded() {
        return objects.size() > maxObjects;
    }

    private void split() {
        int childrenExponent = exponent - 1;
        children = new Octree[8];
        children[0] = new Octree(childrenExponent, maxObjects);
        children[1] = new Octree(childrenExponent, maxObjects);
        children[2] = new Octree(childrenExponent, maxObjects);
        children[3] = new Octree(childrenExponent, maxObjects);
        children[4] = new Octree(childrenExponent, maxObjects);
        children[5] = new Octree(childrenExponent, maxObjects);
        children[6] = new Octree(childrenExponent, maxObjects);
        children[7] = new Octree(childrenExponent, maxObjects);
    }

    public boolean isLeaf() {
        return children == null;
    }
}
