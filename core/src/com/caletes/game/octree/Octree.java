package com.caletes.game.octree;

public class Octree<T> extends Node<T> {
    public Octree(int size) {
        super(convertSizeToExponent(size));
    }
}