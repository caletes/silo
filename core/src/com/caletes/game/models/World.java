package com.caletes.game.models;


import com.caletes.game.octree.Octree;

public class World extends Octree<Region> {
    public World(int size) {
        super(size);
    }
}
