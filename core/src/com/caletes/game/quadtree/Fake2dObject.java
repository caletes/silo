package com.caletes.game.quadtree;

public class Fake2dObject implements Positionable2D {
    float x, y;

    public Fake2dObject(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }
}
