package com.caletes.game.models;

public class Coordinates3D extends Coordinates2D {

    private float z;

    public Coordinates3D(float x, float y, float z) {
        super(x, y);
        this.z = z;
    }

    public float getZ() {
        return z;
    }

    public Coordinates2D toIsometric(float tileWidth, float tileheight) {
        float isoX = (x + y) * tileWidth / 2;
        float isoY = (y - x) * tileheight / 2 + z * tileheight;
        return new Coordinates2D(isoX, isoY);
    }

}
