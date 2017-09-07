package com.caletes.game.models;


import com.caletes.game.octree.Node;
import com.caletes.game.octree.Octree;
import com.caletes.game.octree.OctreeOutOfBoundsException;

public class World {
    private Octree<Chunk> chunks;

    public World(int size) {
        chunks = new Octree<>(size);
    }

    public boolean isIn(int x, int y, int z) {
        return chunks.isWithinBounds(x, y, z);
    }

    public void setChunk(Chunk chunk, int x, int y, int z) throws WorldOutOfBoundsException {
        try {
            chunks.setObjectAt(chunk, x, y, z);
        } catch (OctreeOutOfBoundsException e) {
            throw new WorldOutOfBoundsException(x, y, z);
        }
    }

    public Chunk getChunk(int x, int y, int z) throws WorldOutOfBoundsException {
        Chunk chunk = null;
        try {
            Node node = chunks.getLeafAt(x, y, z);
            if (node != null){
                chunk = (Chunk) node.getObject();
            }
        } catch (OctreeOutOfBoundsException e) {
            throw new WorldOutOfBoundsException(x, y, z);
        }
        return chunk;
    }
}
