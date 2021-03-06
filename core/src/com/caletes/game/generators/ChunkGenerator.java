package com.caletes.game.generators;

import com.caletes.game.builders.ChunkBuilder;
import com.caletes.game.models.Chunk;
import com.caletes.game.models.WorldPosition;
import com.caletes.game.models.items.cubes.CubeFactory;

public class ChunkGenerator {
    public static final int MAX_HEIGHT = 30;
    private static CubeFactory cubeFactory;
    private static long seed;

    public ChunkGenerator(CubeFactory cubeFactory, long seed) {
        this.cubeFactory = cubeFactory;
        this.seed = seed;
    }

    public Chunk generate(WorldPosition worldPosition, int chunkSize) {
        ElevationsGenerator generator = new ElevationsGenerator((int) worldPosition.getX(), (int) worldPosition.getY(), chunkSize, seed);
        ChunkBuilder builder = new ChunkBuilder(generator, MAX_HEIGHT, cubeFactory);
        return builder.build(worldPosition);
    }
}
