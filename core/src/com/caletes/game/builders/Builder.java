package com.caletes.game.builders;

import com.caletes.game.IsoConverter;
import com.caletes.game.models.World;

public abstract class Builder {

    protected int width, height;
    protected World world;


    public Builder(int width, int height, IsoConverter isoConverter) {
        this.width = width;
        this.height = height;
        this.world = new World(computeWorldSize(), isoConverter);
    }

    public int computeWorldSize() {
        return nextPowOf2(Math.max(height, width));
    }

    public static int nextPowOf2(int a) {
        int nextPow;
        for (nextPow = 1; nextPow < a; nextPow <<= 1) ;
        return nextPow;
    }

    public abstract World build();
}