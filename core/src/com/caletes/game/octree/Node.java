package com.caletes.game.octree;

//cf. http://pierre-benet.developpez.com/tutoriels/algorithme-3d/octree-morton/
public class Node<T> implements Iterable<Node<T>> {

    protected final int index;
    protected final int exponent;
    protected int size;
    protected T object = null;
    protected Node<T> parent = null;
    protected Node<T>[] children = null;
    protected Node<T> root = null;
    protected Long mortonMax = null;

    protected Node(int exponent) {
        this(0, exponent, null);
    }

    protected Node(int index, int exponent, Node<T> parent) {
        this.index = index;
        this.exponent = exponent;
        this.parent = parent;
    }

    public int getExponent() {
        return exponent;
    }

    public int getSize() {
        if (size == 0)
            size = convertExponentToSize(exponent);
        return size;
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

    public Node<T> getLeaf(long morton) throws OctreeOutOfBoundsException {
        return getCube(morton, 0);
    }

    public Node<T> getCube(long morton, int exponentToStop) throws OctreeOutOfBoundsException {
        if (morton < 0 || morton > getRoot().getMortonMax())
            throw new OctreeOutOfBoundsException(getMortonMax(), morton);
        Node<T> node = this;
        int currentExponent = exponent;
        while (!node.isLeaf() && node.exponent != exponentToStop) {
            morton = getNextMorton(morton, currentExponent);
            currentExponent--;
            node = node.children[node.getIndex(morton, currentExponent)];
        }
        return node;
    }

    public boolean isWithinBounds(int x, int y, int z) {
        long morton = MortonCode.pack(x, y, z);
        return morton >= 0 && morton < getMortonMax();
    }

    public long getMortonMax() {
        if (mortonMax == null) {
            int maxXYZ = getSize() - 1;
            mortonMax = MortonCode.pack(maxXYZ, maxXYZ, maxXYZ);
        }
        return mortonMax;
    }

    public static long getNextMorton(long morton, int exponent) {
        //Equivalent à morton += Math.pow(2, 3 * exponent);
        morton += 1 << 3 * exponent;
        return morton;
    }

    public Node<T> setObjectAt(T object, int x, int y, int z) throws OctreeOutOfBoundsException {
        long morton = MortonCode.pack(x, y, z);
        return setObjectAt(object, morton);
    }

    public Node<T> setObjectAt(T object, long morton) throws OctreeOutOfBoundsException {
        if (!isFinalLeaf()) {
            if (isLeaf())
                split();
            return getLeaf(morton).setObjectAt(object, morton);
        }
        this.object = object;
        return this;
    }

    public T getObjectAt(int x, int y, int z) throws OctreeOutOfBoundsException {
        return getLeafAt(x, y, z).object;
    }

    public Node<T> getLeafAt(int x, int y, int z) throws OctreeOutOfBoundsException {
        return getLeaf(MortonCode.pack(x, y, z));
    }

    public T clearObjectAt(int x, int y, int z) throws OctreeOutOfBoundsException {
        long morton = MortonCode.pack(x, y, z);
        Node<T> leaf = getLeaf(morton);
        T object = leaf.object;
        leaf.object = null;
        leaf.cleanBranch();
        return object;
    }

    private void cleanBranch() {
        if (!isRoot() && parent.leafsAreEmpty()) {
            parent.children = null;
            parent.cleanBranch();
        }
    }

    private boolean leafsAreEmpty() {
        NodeIterator it = this.iterator();
        while (it.hasNext()) {
            Node<T> leaf = it.next();
            if (leaf.object != null)
                return false;
        }
        return true;
    }

    public int getIndex(long morton, int exponent) {
        return (int) (morton >> 3 * exponent) & 7;
    }

    public long getMorton() {
        Node<T> node = this;
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
     * Disposition des fils au sein d'un noeud :
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
        for (int i = 0; i < 8; i++) {
            children[i] = new Node(i, childrenExponent, this);
        }
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
        return new NodeIterator(this);
    }

    public Node<T> getNextOn(Direction direction) {
        //TODO: peut peut-être être optimiser en ne repartant pas de root mais du parent commun le plus prôche
        try {
            return getRoot().getCube(getNextMortonOn(direction), exponent);
        } catch (OctreeOutOfBoundsException e) {
            return null;
        }
    }

    public long getNextMortonOn(Direction direction) {
        Direction.Delta delta = direction.getDelta();
        int size = getSize();
        int x = delta.x > 0 ? size : delta.x;
        int y = delta.y > 0 ? size : delta.y;
        int z = delta.z > 0 ? size : delta.z;
        MortonCode.Vector3 position = getPosition();
        return MortonCode.pack(position.x + x, position.y + y, position.z + z);
    }

    private Node<T> getRoot() {
        if (root == null) {
            Node<T> node = this;
            while (!node.isRoot()) {
                node = node.parent;
            }
            root = node;
        }
        return root;
    }

}
