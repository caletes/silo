package com.caletes.game.models;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.caletes.game.IsoConverter;
import com.caletes.game.models.items.Item;
import com.caletes.game.octree.Node;
import com.caletes.game.octree.Octree;
import com.caletes.game.octree.OctreeOutOfBoundsException;

public class Chunk {

    private int size;
    private Octree<Item> items;
    private IsoConverter isoConverter;

    public Chunk(int size, IsoConverter isoConverter) {
        this.size = size;
        this.items = new Octree(nextPowOf2(size));
        this.isoConverter = isoConverter;
    }

    public Octree<Item> getItems() {
        return items;
    }

    public Node pushObjectAt(Item item, WorldPosition worldPosition) throws OctreeOutOfBoundsException {

        Vector2 screenPosition = isoConverter.toScreen(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ());
        item.setPosition(screenPosition.x, screenPosition.y);

        Vector3 inChunkPos = worldPosition.getItemPositionInChunk(size);
        return items.setObjectAt(item, (int) inChunkPos.x, (int) inChunkPos.y, (int) inChunkPos.z);
    }

    public static int nextPowOf2(int a) {
        int nextPow;
        for (nextPow = 1; nextPow < a; nextPow <<= 1) ;
        return nextPow;
    }
}
