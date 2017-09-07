package com.caletes.game.models;


import com.badlogic.gdx.math.Vector3;
import com.caletes.game.generators.ChunkGenerator;
import com.caletes.game.models.items.Item;
import com.caletes.game.octree.Direction;
import com.caletes.game.octree.Node;
import com.caletes.game.octree.Octree;
import com.caletes.game.octree.OctreeOutOfBoundsException;

import java.util.ArrayList;
import java.util.List;

public class World {
    private Octree<Chunk> chunks;
    private int chunkSize;
    private ChunkGenerator chunkGenerator;

    public World(int size, int chunkSize, ChunkGenerator chunkGenerator) {
        chunks = new Octree<>(size);
        this.chunkSize = chunkSize;
        this.chunkGenerator = chunkGenerator;
    }

    public boolean isWithinBounds(Vector3 position) {
        return chunkPositionIsWithinBounds(worldPositionToChunkPosition(position));
    }

    public List<Chunk> getChunksAround(Vector3 position) throws WorldOutOfBoundsException {
        Vector3 chunkPos = worldPositionToChunkPosition(position);
        List<Chunk> chunks = new ArrayList<>();
        for (Direction cardinal : Direction.getCardinals()) {
            Direction.Delta cardinalDt = cardinal.getDelta();
            Vector3 nextChunkPos = new Vector3((int) chunkPos.x + cardinalDt.x, (int) chunkPos.y + cardinalDt.y, (int) chunkPos.z + cardinalDt.z);
            if (chunkPositionIsWithinBounds(nextChunkPos)) {
                Chunk chunk = getChunk(nextChunkPos);
                if (chunk == null) {
                    chunk = chunkGenerator.generate(nextChunkPos, chunkSize);
                    setChunk(chunk, nextChunkPos);
                }
                chunks.add(chunk);
            }
        }
        return chunks;
    }

    public void setItemAt(Item item, Vector3 position) throws WorldOutOfBoundsException {
        //todo
    }

    private boolean chunkPositionIsWithinBounds(Vector3 chunkPosition) {
        return chunks.isWithinBounds((int) chunkPosition.x, (int) chunkPosition.y, (int) chunkPosition.z);
    }

    private void setChunk(Chunk chunk, Vector3 chunkPos) throws WorldOutOfBoundsException {
        try {
            chunks.setObjectAt(chunk, (int) chunkPos.x, (int) chunkPos.y, (int) chunkPos.z);
        } catch (OctreeOutOfBoundsException e) {
            throw new WorldOutOfBoundsException(chunkPos);
        }
    }

    private Chunk getChunk(Vector3 chunkPosition) throws WorldOutOfBoundsException {
        Chunk chunk = null;
        try {
            Node node = chunks.getLeafAt((int) chunkPosition.x, (int) chunkPosition.y, (int) chunkPosition.z);
            if (node != null) {
                chunk = (Chunk) node.getObject();
            }
        } catch (OctreeOutOfBoundsException e) {
            throw new WorldOutOfBoundsException(chunkPosition);
        }
        return chunk;
    }


    private Vector3 worldPositionToChunkPosition(Vector3 worldPosition) {
        return new Vector3((int) worldPosition.x / chunkSize, (int) worldPosition.y / chunkSize, (int) worldPosition.z / chunkSize);
    }


}
