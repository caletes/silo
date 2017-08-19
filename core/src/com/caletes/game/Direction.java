package com.caletes.game;

public enum Direction {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;

    public static Direction getFromDeltas(float deltaX, float deltaY) {
        Direction direction = null;
        if (deltaX < 0 && deltaY < 0) {
            direction = SOUTH_WEST;
        } else if (deltaX < 0 && deltaY > 0) {
            direction = NORTH_WEST;
        } else if (deltaX > 0 && deltaY > 0) {
            direction = NORTH_EAST;
        } else if (deltaX > 0 && deltaY < 0) {
            direction = SOUTH_EAST;
        } else if (deltaX < 0) {
            direction = WEST;
        } else if (deltaX > 0) {
            direction = EAST;
        } else if (deltaY < 0) {
            direction = SOUTH;
        } else if (deltaY > 0) {
            direction = NORTH;
        }
        return direction;
    }
}