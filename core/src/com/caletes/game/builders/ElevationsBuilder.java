package com.caletes.game.builders;

import com.caletes.game.Biome;
import com.caletes.game.Elevations;
import com.caletes.game.IsoConverter;
import com.caletes.game.models.World;
import com.caletes.game.models.items.cubes.Cube;
import com.caletes.game.models.items.cubes.CubeFactory;
import com.caletes.game.octree.OctreeOutOfBoundsException;

public class ElevationsBuilder extends Builder {

    private Elevations elevations;
    private int maxHeight;
    private CubeFactory cubeFactory;

    public ElevationsBuilder(Elevations elevations, int maxHeight, CubeFactory cubeFactory, IsoConverter isoConverter) {
        super(elevations.getWidth(), elevations.getHeight(), isoConverter);
        this.elevations = elevations;
        this.maxHeight = maxHeight;
        this.cubeFactory = cubeFactory;
    }

    @Override
    public World build() {
        for (int y = 0; y < elevations.getHeight(); y++) {
            for (int x = 0; x < elevations.getWidth(); x++) {
                double elevation = elevations.get(x, y);
                Biome biome = Biome.find(elevation);
                boolean ocean = biome == Biome.OCEAN;
                int zMax = ocean ? toZ(biome.getElevationMax()) : toZ(elevation);
                // On cherche l'élévation la plus basse autour de x,y pour trouver le zMin
                int zMin = ocean ? zMax : toZ(elevations.getMinAround(x, y));
                // puis on commence juste au dessus pour avoir le moins de cubes possibles tout en gardant un ensemble continu
                if (zMin < zMax) zMin++;
                try {
                    for (int z = zMin; z <= zMax; z++) {
                        Cube cube = getCubeFromBiome(biome);
                        world.pushObjectAt(cube, x, y, z);
                    }
                } catch (OctreeOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        }
        return world;
    }

    private int toZ(double elevation) {
        return (int) Math.round(elevation * maxHeight);
    }

    private Cube getCubeFromBiome(Biome biome) {
        Cube cube = null;
        switch (biome) {
            case OCEAN:
                cube = cubeFactory.createWaterCube();
                break;
            case BEACH:
                cube = cubeFactory.createSandCube();
                break;
            case GRASSLAND:
                cube = cubeFactory.createGrassCube();
                break;
            case STONE:
                cube = cubeFactory.createStoneCube();
                break;
            case SNOW:
                cube = cubeFactory.createSnowCube();
                break;
        }
        return cube;
    }
}
