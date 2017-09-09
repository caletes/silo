package com.caletes.game.octree;

public class OctreeOutOfBoundsException extends Exception {
    public OctreeOutOfBoundsException(long mortonMax, long morton) {
        super(String.format("Max : %s, Current : %s", MortonCode.unpack(mortonMax), MortonCode.unpack(morton)));
    }
}
