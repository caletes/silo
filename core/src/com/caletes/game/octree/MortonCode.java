package com.caletes.game.octree;

/**
 * http://www.forceflow.be/2013/10/07/morton-encodingdecoding-through-bit-interleaving-implementations/
 * https://github.com/aavenel/mortonlib/blob/master/include/morton3d.h
 * http://bitmath.blogspot.fr/2012/11/tesseral-arithmetic-useful-snippets.html
 */
public class MortonCode {

    private static final long MASK_1 = 0x1fffffl;
    private static final long MASK_2 = 0x1f00000000ffffl;
    private static final long MASK_3 = 0x1f0000ff0000ffl;
    private static final long MASK_4 = 0x100f00f00f00f00fl;
    private static final long MASK_5 = 0x10c30c30c30c30c3l;
    private static final long MASK_6 = 0x1249249249249249l;

    private static final long X_MASK = 0x4924924924924924l; // 0b...00100100
    private static final long Y_MASK = 0x2492492492492492l; // 0b...10010010
    private static final long Z_MASK = 0x9249249249249249l; // 0b...01001001
    private static final long XY_MASK = X_MASK | Y_MASK;
    private static final long XZ_MASK = X_MASK | Z_MASK;
    private static final long YZ_MASK = Y_MASK | Z_MASK;

    public static long pack(int x, int y, int z) {
        return splitBy3(x) << 2 | splitBy3(y) << 1 | splitBy3(z) << 0;
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
        return new Vector3(getThirdBits(mortonCode >> 2), getThirdBits(mortonCode >> 1), getThirdBits(mortonCode >> 0));
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

    public static long add(long m1, long m2) {
        long xSum = (m1 | YZ_MASK) + (m2 & X_MASK);
        long ySum = (m1 | XZ_MASK) + (m2 & Y_MASK);
        long zSum = (m1 | XY_MASK) + (m2 & Z_MASK);
        return (xSum & X_MASK) | (ySum & Y_MASK) | (zSum & Z_MASK);
    }

    public static long substract(long m1, long m2) {
        long xDiff = (m1 & X_MASK) - (m2 & X_MASK);
        long yDiff = (m1 & Y_MASK) - (m2 & Y_MASK);
        long zDiff = (m1 & Z_MASK) - (m2 & Z_MASK);
        return (xDiff & X_MASK) | (yDiff & Y_MASK) | (zDiff & Z_MASK);
    }


    public static long incX(long morton) {
        long x_sum = (morton | YZ_MASK) + 4;
        return setX(morton, x_sum);
    }

    public static long incY(long morton) {
        long y_sum = (morton | XZ_MASK) + 2;
        return setY(morton, y_sum);
    }

    public static long incZ(long morton) {
        long z_sum = (morton | XY_MASK) + 1;
        return setZ(morton, z_sum);
    }

    public static long setX(long morton, long x) {
        return (x & X_MASK) | (morton & YZ_MASK);
    }

    public static long setY(long morton, long y) {
        return (y & Y_MASK) | (morton & XZ_MASK);
    }

    public static long setZ(long morton, long z) {
        return (z & Z_MASK) | (morton & XY_MASK);
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
