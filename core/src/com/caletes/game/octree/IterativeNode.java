package com.caletes.game.octree;


//cf. http://pierre-benet.developpez.com/tutoriels/algorithme-3d/octree-morton/
public class IterativeNode<T> extends Node<T>{

    protected IterativeNode(int exponent) {
        super(exponent);
    }

    @Override
    public Node getLeaf(int x, int y, int z) {
        long mortonCode = MortonCode.pack(x, y, z);
        Node node = children[getIndex(mortonCode)];
        int sub = exponent;
        while (!node.isLeaf()) {
            mortonCode += 1 << 3 * sub;
            node = node.children[node.getIndex(mortonCode)];
            sub--;
        }
        return node;
    }

}
