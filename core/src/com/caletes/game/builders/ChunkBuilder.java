package com.caletes.game.builders;

import com.caletes.game.Biome;
import com.caletes.game.Elevations;
import com.caletes.game.models.Chunk;
import com.caletes.game.models.WorldPosition;
import com.caletes.game.models.items.cubes.Cube;
import com.caletes.game.models.items.cubes.CubeFactory;
import com.caletes.game.octree.OctreeOutOfBoundsException;

public class ChunkBuilder {

    private Chunk chunk;
    private Elevations elevations;
    private int maxHeight;
    private CubeFactory cubeFactory;

    public ChunkBuilder(Elevations elevations, int maxHeight, CubeFactory cubeFactory) {
        this.chunk = new Chunk(elevations.getSize());
        this.elevations = elevations;
        this.maxHeight = maxHeight;
        this.cubeFactory = cubeFactory;
    }

    public Chunk build(WorldPosition worldPosition) {
        for (int y = 0; y < elevations.getSize(); y++) {
            for (int x = 0; x < elevations.getSize(); x++) {
                double elevation = elevations.get(x, y);
                Biome biome = Biome.find(elevation);
                boolean ocean = biome == Biome.OCEAN;
                int zMax = ocean ? toZ(biome.getElevationMax()) : toZ(elevation);
                // On cherche l'élévation la plus basse autour de x,y pour trouver le zMin
                int zMin = ocean ? zMax : toZ(elevations.getMinAround(x, y));
                // puis on commence juste au dessus pour avoir le moins de cubes possibles tout en gardant un ensemble continu
                zMin = zMin < zMax ? zMin + 1 : zMax;
                try {
                    for (int z = zMin; z <= zMax; z++) {
                        WorldPosition cubePos = new WorldPosition(worldPosition.getX() + x, worldPosition.getY() + y, worldPosition.getZ() + z);
                        Cube cube = getCubeFromBiome(biome, cubePos);
                        chunk.pushItem(cube);
                    }
                } catch (OctreeOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        }
        return chunk;
    }

    private int toZ(double elevation) {
        return (int) Math.round(elevation * maxHeight);
    }

    private Cube getCubeFromBiome(Biome biome, WorldPosition cubePosition) {
        Cube cube = null;
        switch (biome) {
            case OCEAN:
                cube = cubeFactory.createWaterCube(cubePosition);
                break;
            case BEACH:
                cube = cubeFactory.createSandCube(cubePosition);
                break;
            case GRASSLAND:
                cube = cubeFactory.createGrassCube(cubePosition);
                break;
            case STONE:
                cube = cubeFactory.createStoneCube(cubePosition);
                break;
            case SNOW:
                cube = cubeFactory.createSnowCube(cubePosition);
                break;
        }
        return cube;
    }
}
