package com.caletes.game.generators;

import com.caletes.game.Elevations;
import com.caletes.game.IsoConverter;
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
        ElevationsGenerator generator = new ElevationsGenerator(worldPosition.getX(), worldPosition.getY(), chunkSize, seed);
        Elevations elevations = generator.generate();
        ChunkBuilder builder = new ChunkBuilder(elevations, MAX_HEIGHT, cubeFactory);
        return builder.build(worldPosition);
    }
}
