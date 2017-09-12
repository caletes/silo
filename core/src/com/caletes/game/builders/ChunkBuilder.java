package com.caletes.game.builders;

import com.caletes.game.Biome;
import com.caletes.game.Elevations;
import com.caletes.game.generators.ElevationsGenerator;
import com.caletes.game.models.Chunk;
import com.caletes.game.models.WorldPosition;
import com.caletes.game.models.items.cubes.Cube;
import com.caletes.game.models.items.cubes.CubeFactory;
import com.caletes.game.octree.OctreeOutOfBoundsException;

public class ChunkBuilder {

    private Chunk chunk;
    private ElevationsGenerator elevationsGenerator;
    private Elevations elevations;
    private int maxHeight;
    private CubeFactory cubeFactory;

    public ChunkBuilder(ElevationsGenerator elevationsGenerator, int maxHeight, CubeFactory cubeFactory) {
        this.elevationsGenerator = elevationsGenerator;
        this.elevations = elevationsGenerator.generate();
        this.chunk = new Chunk(elevations.getSize());
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
                float worldX = worldPosition.getX() + x;
                float worldY = worldPosition.getY() + y;
                double northZ = elevationsGenerator.getNorthElevations((int) worldX, (int) worldY);
                double eastZ= elevationsGenerator.getEastElevations((int) worldX, (int) worldY);
                double southZ = elevationsGenerator.getSouthElevations((int) worldX, (int) worldY);
                double westZ = elevationsGenerator.getWestElevations((int) worldX, (int) worldY);
                double zMinAround = Math.min(Math.min(northZ, eastZ), Math.min(southZ, westZ));
                int zMin = ocean ? zMax : toZ(zMinAround);
                // puis on commence juste au dessus pour avoir le moins de cubes possibles tout en gardant un ensemble continu
                zMin = zMin < zMax ? zMin + 1 : zMax;
                try {
                    for (int z = zMin; z <= zMax; z++) {
                        float worldZ = worldPosition.getZ() + z;
                        WorldPosition cubePos = new WorldPosition(worldX, worldY, worldZ);
                        // on définit si le cube doit avoir une bordure à gauche ou à droite en fonction du z des voisins
                        boolean boderLeft = !ocean && z > toZ(westZ);
                        boolean boderRight = !ocean && z > toZ(northZ);
                        Cube cube = getCubeFromBiome(biome, boderLeft, boderRight);
                        chunk.pushItem(cube, cubePos);
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

    private Cube getCubeFromBiome(Biome biome, boolean borderLeft, boolean borderRight) {
        Cube cube = null;
        switch (biome) {
            case OCEAN:
                cube = cubeFactory.createWaterCube(borderLeft, borderRight);
                break;
            case BEACH:
                cube = cubeFactory.createSandCube(borderLeft, borderRight);
                break;
            case GRASSLAND:
                cube = cubeFactory.createGrassCube(borderLeft, borderRight);
                break;
            case STONE:
                cube = cubeFactory.createStoneCube(borderLeft, borderRight);
                break;
            case SNOW:
                cube = cubeFactory.createSnowCube(borderLeft, borderRight);
                break;
        }
        return cube;
    }

}
