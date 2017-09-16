package com.caletes.game.models;


import com.badlogic.gdx.math.Vector3;
import com.caletes.game.models.items.Item;
import com.caletes.game.octree.Node;
import com.caletes.game.octree.OctreeFactory;
import com.caletes.game.octree.OctreeOutOfBoundsException;

public class Chunk {

    private int size;
    private Node<Item> items;

    public Chunk(int size) {
        this.size = size;
        this.items = OctreeFactory.createWithSize(Item.class,nextPowOf2(size));
    }

    public Node<Item> getItems() {
        return items;
    }

    public Node pushItem(Item item, WorldPosition worldPosition) throws OctreeOutOfBoundsException {
        item.setWorldPosition(worldPosition);
        item.applyWorldPosition();
        Vector3 inChunkPos = item.getWorldPosition().getItemPositionInChunk(size);
        return items.setObjectAt(item, (int) inChunkPos.x, (int) inChunkPos.y, (int) inChunkPos.z);
    }

    public Item removeItem(WorldPosition worldPosition) throws OctreeOutOfBoundsException {
        Vector3 inChunkPos = worldPosition.getItemPositionInChunk(size);
        return items.clearObjectAt((int) inChunkPos.x, (int) inChunkPos.y, (int) inChunkPos.z);
    }

    public Item getItem(WorldPosition worldPosition) throws OctreeOutOfBoundsException {
        Vector3 inChunkPos = worldPosition.getItemPositionInChunk(size);
        return items.getObjectAt((int) inChunkPos.x, (int) inChunkPos.y, (int) inChunkPos.z);
    }

    public static int nextPowOf2(int a) {
        int nextPow;
        for (nextPow = 1; nextPow < a; nextPow <<= 1) ;
        return nextPow;
    }
}
