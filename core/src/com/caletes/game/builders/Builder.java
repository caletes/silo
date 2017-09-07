package com.caletes.game.builders;

import com.caletes.game.IsoConverter;
import com.caletes.game.models.Chunk;

public abstract class Builder {

    protected int size;
    protected Chunk chunk;


    public Builder(int size, IsoConverter isoConverter) {
        this.size = size;
        this.chunk = new Chunk(nextPowOf2(size), isoConverter);
    }

    public static int nextPowOf2(int a) {
        int nextPow;
        for (nextPow = 1; nextPow < a; nextPow <<= 1) ;
        return nextPow;
    }

    public abstract Chunk build(int x, int y);
}
