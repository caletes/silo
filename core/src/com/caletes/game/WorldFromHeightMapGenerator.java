package com.caletes.game;

import com.caletes.game.models.World;
import com.caletes.game.models.items.Item;
import com.caletes.game.models.items.cubes.GrassCube;
import com.caletes.game.models.items.cubes.GroundCube;

public class WorldFromHeightMapGenerator {

    private HeightMap heightMap;
    private int width;
    private int height;
    private World world;

    public WorldFromHeightMapGenerator(HeightMap heightMap) {
        this.heightMap = heightMap;
        this.width = heightMap.getWidth();
        this.height = heightMap.getHeight();
        this.world = new World(computeWorldSize());
    }

    public World generate() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int elevation = heightMap.getElevation(x, y);
                for (int z = 1; z <= elevation; z++) {
                    Item item = z < elevation ? new GroundCube() : new GrassCube();
                    world.pushObjectAt(item, x, y, z);
                }
            }
        }
        return world;
    }

    private int computeWorldSize() {
        return nextPowOf2(Math.max(height, width));
    }

    public static int nextPowOf2(int a) {
        int nextPow;
        for (nextPow = 1; nextPow < a; nextPow <<= 1) ;
        return nextPow;
    }
}
