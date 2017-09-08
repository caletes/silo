package com.caletes.game.models;

import com.badlogic.gdx.math.Vector3;

public class WorldOutOfBoundsException extends Exception {
    public WorldOutOfBoundsException(Vector3 position) {
        super(String.format("%f,%f,%f is out of the world!", position.x, position.y, position.z));
    }
}
