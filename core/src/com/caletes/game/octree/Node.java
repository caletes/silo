package com.caletes.game.octree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        return getCube(morton, 0);
    }

    public Node getCube(long morton, int exponentToStop) {
        Node node = this;
        int currentExponent = exponent;
        while (!node.isLeaf() && node.exponent != exponentToStop) {
            morton = getNextMorton(morton, currentExponent);
            currentExponent--;
            node = node.children[node.getIndex(morton, currentExponent)];
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
        return (T) getLeafAt(x, y, z).object;
    }

    public Node getLeafAt(int x, int y, int z) {
        return getLeaf(MortonCode.pack(x, y, z));
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

    /**
     * ...6--------7
     * ../|       /|
     * ./ |      / |
     * 4--------5  |
     * |  2-----|--3
     * | /      | /
     * |/       |/
     * 0--------1
     */
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

    public Node substract(int x, int y, int z, int exponent) {
        Node node = getLeafAt(x, y, z);
        while (node.exponent < exponent) {
            node = node.parent;
        }
        return node;
    }


    public List<Node> withNeighbors() {
        int size = getSize();
        MortonCode.Vector3 position = getPosition();

        List<Long> mortons = new ArrayList<>();

        // todo comment fair une boucle avec ca ? 3*3*3=27 (x y z)
        mortons.add(getMorton());//center
        mortons.add(MortonCode.pack(position.x - 1, position.y, position.z));//left
        mortons.add(MortonCode.pack(position.x - 1, position.y - 1, position.z));//top left
        mortons.add(MortonCode.pack(position.x - 1, position.y + size, position.z));//bottom left
        mortons.add(MortonCode.pack(position.x + size, position.y, position.z));//right
        mortons.add(MortonCode.pack(position.x + size, position.y - 1, position.z));//top right
        mortons.add(MortonCode.pack(position.x + size, position.y + size, position.z));//bottom right
        mortons.add(MortonCode.pack(position.x, position.y - 1, position.z));//top
        mortons.add(MortonCode.pack(position.x, position.y + size, position.z));//bottom

        mortons.add(MortonCode.pack(position.x, position.y, position.z - 1));//center
        mortons.add(MortonCode.pack(position.x - 1, position.y, position.z - 1));//left
        mortons.add(MortonCode.pack(position.x - 1, position.y - 1, position.z - 1));//top left
        mortons.add(MortonCode.pack(position.x - 1, position.y + size, position.z - 1));//bottom left
        mortons.add(MortonCode.pack(position.x + size, position.y, position.z - 1));//right
        mortons.add(MortonCode.pack(position.x + size, position.y - 1, position.z - 1));//top right
        mortons.add(MortonCode.pack(position.x + size, position.y + size, position.z - 1));//bottom right
        mortons.add(MortonCode.pack(position.x, position.y - 1, position.z - 1));//top
        mortons.add(MortonCode.pack(position.x, position.y + size, position.z - 1));//bottom

        mortons.add(MortonCode.pack(position.x, position.y, position.z + size));//center
        mortons.add(MortonCode.pack(position.x - 1, position.y, position.z + size));//left
        mortons.add(MortonCode.pack(position.x - 1, position.y - 1, position.z + size));//top left
        mortons.add(MortonCode.pack(position.x - 1, position.y + size, position.z + size));//bottom left
        mortons.add(MortonCode.pack(position.x + size, position.y, position.z + size));//right
        mortons.add(MortonCode.pack(position.x + size, position.y - 1, position.z + size));//top right
        mortons.add(MortonCode.pack(position.x + size, position.y + size, position.z + size));//bottom right
        mortons.add(MortonCode.pack(position.x, position.y - 1, position.z + size));//top
        mortons.add(MortonCode.pack(position.x, position.y + size, position.z + size));//bottom

        //trier par morton code croissant
        Collections.sort(mortons);
        List<Node> neighbors = new ArrayList<>();
        //todo : Optimisation rechercher le parent commun le plus proche ?
        Node root = getRoot();
        for (long morton : mortons) {
            neighbors.add(root.getCube(morton, exponent));
        }
        return neighbors;
    }

    private Node getRoot() {
        Node node = this;
        while (!node.isRoot()) {
            node = node.parent;
        }
        return node;
    }
}
