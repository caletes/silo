package com.caletes.game.octree;

public class MortonCode {

    private static final long MASK_1 = 0x1fffffL;
    private static final long MASK_2 = 0x1f00000000ffffL;
    private static final long MASK_3 = 0x1f0000ff0000ffL;
    private static final long MASK_4 = 0x100f00f00f00f00fL;
    private static final long MASK_5 = 0x10c30c30c30c30c3L;
    private static final long MASK_6 = 0x1249249249249249L;

    public static long pack(int x, int y, int z) {
        return splitBy3(x) | splitBy3(y) << 1 | splitBy3(z) << 2;
    }

    private static long splitBy3(int a) {
        long b = a & MASK_1;
        b = (b | b << 32) & MASK_2;
        b = (b | b << 16) & MASK_3;
        b = (b | b << 8) & MASK_4;
        b = (b | b << 4) & MASK_5;
        b = (b | b << 2) & MASK_6;
        return b;
    }

    public static Vector3 unpack(long mortonCode) {
        return new Vector3(getThirdBits(mortonCode), getThirdBits(mortonCode >> 1), getThirdBits(mortonCode >> 2));
    }

    private static int getThirdBits(long b) {
        long a = b & MASK_6;
        a = (a ^ (a >> 2)) & MASK_5;
        a = (a ^ (a >> 4)) & MASK_4;
        a = (a ^ (a >> 8)) & MASK_3;
        a = (a ^ (a >> 16)) & MASK_2;
        a = (a ^ (a >> 32)) & MASK_1;
        return (int) a;
    }

    public static class Vector3 {
        public final int x, y, z;

        public Vector3(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public String toString() {
            return x + "," + y + "," + z;
        }

        @Override
        public boolean equals(Object obj) {
            return this.toString().equals(obj.toString());
        }
    }
}
