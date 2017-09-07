package com.caletes.game.models;

public class WorldOutOfBoundsException extends Exception {
    public WorldOutOfBoundsException(int x, int y, int z) {
        super(String.format("%d,%d,%d is out of the world!", x, y, z));
    }
}
