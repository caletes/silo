package com.caletes.game.octree;

public class OctreeOutOfBoundsException extends Exception {
    public OctreeOutOfBoundsException(long mortonMax, long morton) {
        super("Max : " + MortonCode.unpack(mortonMax) + ", Current : " + MortonCode.unpack(morton));
    }
}
