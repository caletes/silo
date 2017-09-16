package com.caletes.game.models;


import com.badlogic.gdx.math.Vector3;
import com.caletes.game.generators.ChunkGenerator;
import com.caletes.game.models.items.Item;
import com.caletes.game.models.items.Player;
import com.caletes.game.octree.Direction;
import com.caletes.game.octree.Node;
import com.caletes.game.octree.OctreeFactory;
import com.caletes.game.octree.OctreeOutOfBoundsException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class World {
    private Node<Chunk> chunks;
    private int chunkSize;
    private ChunkGenerator chunkGenerator;

    public World(int size, int chunkSize, ChunkGenerator chunkGenerator) {
        chunks = OctreeFactory.createWithSize(Chunk.class, size);
        this.chunkSize = chunkSize;
        this.chunkGenerator = chunkGenerator;
    }


    //TODO à déplacer dans un WorldGenerator ?
    public List<Chunk> getChunksAround(WorldPosition worldPosition) throws WorldOutOfBoundsException {
        Vector3 chunkPos = worldPosition.getChunkPosition(chunkSize);
        List<Chunk> chunks = new ArrayList<>();
        for (Direction cardinal : Direction.getCardinals()) {
            Direction.Delta cardinalDt = cardinal.getDelta();
            float nextX = chunkPos.x + cardinalDt.x;
            float nextY = chunkPos.y + cardinalDt.y;
            float nextZ = chunkPos.z + cardinalDt.z;
            WorldPosition nextPos = WorldPosition.createFromChunkPosition(nextX, nextY, nextZ, chunkSize);
            if (isWithinBounds(nextPos)) {
                Chunk chunk = getChunk(nextPos);
                if (chunk == null) {
                    chunk = chunkGenerator.generate(nextPos, chunkSize);
                    setChunk(chunk, nextPos);
                }
                chunks.add(chunk);
            }
        }
        return chunks;
    }

    public Set<Item> getItemsAround(WorldPosition worldPosition) throws WorldOutOfBoundsException {
        Set<Item> items = new HashSet<>();
        for (Direction direction : Direction.values()) {
            if (direction != Direction.NONE) {
                Direction.Delta delta = direction.getDelta();
                float nextX = worldPosition.getX() + delta.x;
                float nextY = worldPosition.getY() + delta.y;
                float nextZ = worldPosition.getZ() + delta.z;
                Item item = getItem(new WorldPosition(nextX, nextY, nextZ));
                if (item != null && !(item instanceof Player))
                    items.add(item);
            }
        }
        return items;
    }

    public void pushItem(Item item, WorldPosition worldPosition) throws WorldOutOfBoundsException {
        Chunk chunk = getChunk(worldPosition);
        if (chunk == null) {
            chunk = createChunk(worldPosition);
        }
        try {
            chunk.pushItem(item, worldPosition);
        } catch (OctreeOutOfBoundsException e) {
            throw new WorldOutOfBoundsException(worldPosition);
        }
    }

    private Chunk createChunk(WorldPosition worldPosition) throws WorldOutOfBoundsException {
        Vector3 chunkPos = worldPosition.getChunkPosition(chunkSize);
        WorldPosition nextPos = WorldPosition.createFromChunkPosition(chunkPos.x, chunkPos.y, chunkPos.z, chunkSize);
        Chunk chunk = chunkGenerator.generate(nextPos, chunkSize);
        setChunk(chunk, worldPosition);
        return chunk;
    }

    public void removeItem(WorldPosition worldPosition) throws WorldOutOfBoundsException {
        Chunk chunk = getChunk(worldPosition);
        if (chunk == null) {
            chunk = createChunk(worldPosition);
        }
        try {
            chunk.removeItem(worldPosition);
        } catch (OctreeOutOfBoundsException e) {
            throw new WorldOutOfBoundsException(worldPosition);
        }
    }


    public Item getItem(WorldPosition worldPosition) throws WorldOutOfBoundsException {
        Item item;
        Chunk chunk = getChunk(worldPosition);
        if (chunk == null) {
            chunk = createChunk(worldPosition);
        }
        try {
            item = chunk.getItem(worldPosition);
        } catch (OctreeOutOfBoundsException e) {
            throw new WorldOutOfBoundsException(worldPosition);
        }
        return item;
    }

    public boolean isWithinBounds(WorldPosition worldPosition) {
        Vector3 chunkPos = worldPosition.getChunkPosition(chunkSize);
        return chunks.isWithinBounds((int) chunkPos.x, (int) chunkPos.y, (int) chunkPos.z);
    }

    private void setChunk(Chunk chunk, WorldPosition worldPosition) throws WorldOutOfBoundsException {
        Vector3 chunkPos = worldPosition.getChunkPosition(chunkSize);
        try {
            chunks.setObjectAt(chunk, (int) chunkPos.x, (int) chunkPos.y, (int) chunkPos.z);
        } catch (OctreeOutOfBoundsException e) {
            throw new WorldOutOfBoundsException(worldPosition);
        }
    }

    private Chunk getChunk(WorldPosition worldPosition) throws WorldOutOfBoundsException {
        Vector3 chunkPos = worldPosition.getChunkPosition(chunkSize);
        Chunk chunk = null;
        try {
            Node node = chunks.getLeafAt((int) chunkPos.x, (int) chunkPos.y, (int) chunkPos.z);
            if (node != null) {
                chunk = (Chunk) node.getObject();
            }
        } catch (OctreeOutOfBoundsException e) {
            throw new WorldOutOfBoundsException(worldPosition);
        }
        return chunk;
    }
}
