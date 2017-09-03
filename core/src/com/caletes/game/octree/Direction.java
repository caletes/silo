package com.caletes.game.octree;

import java.util.ArrayList;
import java.util.List;

/**
 * Ne pas changer l'ordre de l'énumération
 * Celui ci correspond à l'odre de lecture de l'octree
 * ansi qu'au bon ordre d'affichage des cubes
 */
public enum Direction {
    TOP_NORTH_WEST(-1, -1, 1),
    TOP_NORTH(0, -1, 1),
    TOP_NORTH_EAST(1, -1, 1),
    TOP_WEST(-1, 0, 1),
    TOP(0, 0, 1),
    TOP_EAST(1, 0, 1),
    TOP_SOUTH_WEST(-1, 1, 1),
    TOP_SOUTH(0, 1, 1),
    TOP_SOUTH_EAST(1, 1, 1),

    NORTH_WEST(-1, -1, 0),
    NORTH(0, -1, 0),
    NORTH_EAST(1, -1, 0),
    WEST(-1, 0, 0),
    NONE(0, 0, 0),
    EAST(1, 0, 0),
    SOUTH_WEST(-1, 1, 0),
    SOUTH(0, 1, 0),
    SOUTH_EAST(1, 1, 0),


    BOTTOM_NORTH_WEST(-1, -1, -1),
    BOTTOM_NORTH(0, -1, -1),
    BOTTOM_NORTH_EAST(1, -1, -1),
    BOTTOM_WEST(-1, 0, -1),
    BOTTOM(0, 0, -1),
    BOTTOM_EAST(1, 0, -1),
    BOTTOM_SOUTH_WEST(-1, 1, -1),
    BOTTOM_SOUTH(0, 1, -1),
    BOTTOM_SOUTH_EAST(1, 1, -1);


    private Delta delta = new Delta();

    Direction(int dx, int dy, int dz) {
        this.delta.x = dx;
        this.delta.y = dy;
        this.delta.z = dz;
    }

    public Delta getDelta() {
        return delta;
    }

    public static Direction getFromDeltas(float deltaX, float deltaY, float deltaZ) {
        int dx = normalize(deltaX);
        int dy = normalize(deltaY);
        int dz = normalize(deltaZ);
        return find(dx, dy, dz);
    }

    private static int normalize(float delta) {
        if (delta < 0)
            return -1;
        if (delta > 0)
            return 1;
        return 0;
    }

    private static Direction find(int dx, int dy, int dz) {
        for (Direction direction : values()) {
            if (hasDelta(direction, dx, dy, dz))
                return direction;
        }
        return null;
    }

    private static boolean hasDelta(Direction direction, int dx, int dy, int dz) {
        return direction.delta.x == dx && direction.delta.y == dy && direction.delta.z == dz;
    }

    public class Delta {
        public int x;
        public int y;
        public int z;
    }

    public static List<Direction> getCardinals() {
        List<Direction> cardinals = new ArrayList<>();
        cardinals.add(NORTH_WEST);
        cardinals.add(NORTH);
        cardinals.add(NORTH_EAST);
        cardinals.add(WEST);
        cardinals.add(NONE);
        cardinals.add(EAST);
        cardinals.add(SOUTH_WEST);
        cardinals.add(SOUTH);
        cardinals.add(SOUTH_EAST);
        return cardinals;
    }
}