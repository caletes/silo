package com.caletes.game.models;

import com.badlogic.gdx.math.Vector3;

public class WorldOutOfBoundsException extends Exception {
    public WorldOutOfBoundsException(WorldPosition position) {
        super(String.format("%f,%f,%f is out of the world!", position.getX(), position.getY(), position.getZ()));
    }
}
