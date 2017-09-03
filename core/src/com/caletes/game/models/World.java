package com.caletes.game.models;


import com.caletes.game.octree.Octree;

public class World extends Octree<Chunk> {
    public World(int size) {
        super(size);
    }
}
