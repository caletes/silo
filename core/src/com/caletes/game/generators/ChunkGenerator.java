package com.caletes.game.generators;

import com.badlogic.gdx.math.Vector3;
import com.caletes.game.Elevations;
import com.caletes.game.IsoConverter;
import com.caletes.game.builders.ChunkBuilder;
import com.caletes.game.models.Chunk;
import com.caletes.game.models.items.cubes.CubeFactory;

public class ChunkGenerator {
    public static final int MAX_HEIGHT = 30;
    private static CubeFactory cubeFactory;
    private static IsoConverter isoConverter;
    private static long seed;

    public ChunkGenerator(CubeFactory cubeFactory, IsoConverter isoConverter, long seed) {
        this.cubeFactory = cubeFactory;
        this.isoConverter = isoConverter;
        this.seed = seed;
    }

    public Chunk generate(Vector3 start, int chunkSize) {
        ElevationsGenerator generator = new ElevationsGenerator((int) start.x * chunkSize, (int) start.y * chunkSize, chunkSize, seed);
        Elevations elevations = generator.generate();
        ChunkBuilder builder = new ChunkBuilder(elevations, MAX_HEIGHT, cubeFactory, isoConverter);
        return builder.build((int) start.x, (int) start.y);
    }
}
