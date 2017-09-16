package com.caletes.game.octree;

public class OctreeFactory {
    public static <T> Node<T> createWithSize(Class<T> clazz, int size) {
        return new Node(Node.convertSizeToExponent(size));
    }
}