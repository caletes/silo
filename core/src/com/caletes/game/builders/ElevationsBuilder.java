package com.caletes.game.builders;

import com.caletes.game.Elevations;
import com.caletes.game.models.World;
import com.caletes.game.models.items.Item;
import com.caletes.game.models.items.cubes.GrassCube;
import com.caletes.game.models.items.cubes.GroundCube;

public class ElevationsBuilder extends Builder {

    private Elevations elevations;
    private int maxHeight;

    public ElevationsBuilder(Elevations elevations, int maxHeight) {
        super(elevations.getWidth(), elevations.getHeight());
        this.elevations = elevations;
        this.maxHeight = maxHeight;
    }

    @Override
    public World build() {
        for (int y = 0; y < elevations.getHeight(); y++) {
            for (int x = 0; x < elevations.getWidth(); x++) {
                double elevation = elevations.get(x, y);
                int peak = toZ(elevation);
                for (int z = 0; z <= peak; z++) {
                    Item item = z < peak ? new GroundCube() : new GrassCube();
                    world.pushObjectAt(item, x, y, z);
                }
            }
        }
        return world;
    }

    private int toZ(double elevation) {
        return (int) Math.round(elevation * maxHeight);
    }
}
