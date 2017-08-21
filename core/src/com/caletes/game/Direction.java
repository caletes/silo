package com.caletes.game;

public enum Direction {
    BOTTOM_NORTH_WEST(25),
    BOTTOM_NORTH(7),
    BOTTOM_NORTH_EAST(16),
    BOTTOM_WEST(19),
    BOTTOM(1),
    BOTTOM_EAST(10),
    BOTTOM_SOUTH_WEST(22),
    BOTTOM_SOUTH(4),
    BOTTOM_SOUTH_EAST(13),
    NORTH_WEST(24),
    NORTH(6),
    NORTH_EAST(15),
    WEST(18),
    NONE(0),
    EAST(9),
    SOUTH_WEST(21),
    SOUTH(3),
    SOUTH_EAST(12),
    TOP_NORTH_WEST(26),
    TOP_NORTH(8),
    TOP_NORTH_EAST(17),
    TOP_WEST(20),
    TOP(2),
    TOP_EAST(11),
    TOP_SOUTH_WEST(23),
    TOP_SOUTH(5),
    TOP_SOUTH_EAST(14);
    
    /**
     * Code de la direction en base3
     * concaténation des deltas x,y et z
     * delta == 0 = 0, delta > 0 = 1, delta < 0 = 2
     * exemple :
     * BOTTOM_NORTH_WEST -> <0 , <0 , >0 -> 221 -> 25
     */
    private int code;

    Direction(int code) {
        this.code = code;
    }

    public static Direction getFromDeltas(float deltaX, float deltaY, float deltaZ) {
        String base3Code = computeBase3Code(deltaX, deltaY, deltaZ);
        int code = getCodeFromBase3Code(base3Code);
        return findByCode(code);
    }

    private static Direction findByCode(int code) {
        for (Direction direction : values()) {
            if (direction.code == code)
                return direction;
        }
        return null;
    }

    private static String computeBase3Code(float deltaX, float deltaY, float deltaZ) {
        return "" + getBase3Char(deltaX) + getBase3Char(deltaY) + getBase3Char(deltaZ);
    }

    private static char getBase3Char(float delta) {
        if (delta > 0)
            return '1';
        if (delta < 0)
            return '2';
        return '0';
    }

    private static int getCodeFromBase3Code(String base3Code) {
        return Integer.valueOf(base3Code, 3);
    }


}