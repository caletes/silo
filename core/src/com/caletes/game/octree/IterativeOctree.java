package com.caletes.game.octree;

public class IterativeOctree<T> extends IterativeNode<T> {
    public IterativeOctree(int size) {
        super(convertSizeToExponent(size));
    }
}