package com.caletes.game.models.items;


import com.caletes.game.models.items.cubes.Cube;
import com.caletes.game.octree.Octree;

public class OctreeWorld extends Octree<Cube> {

    public OctreeWorld(int size) {
        super(size);
    }
}
