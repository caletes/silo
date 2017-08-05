package com.caletes.game.models;

public class Coordinates {

    private float x;

    private float y;

    private float z;

    public Coordinates(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public Coordinates toIsometric(float tileWidth, float tileheight){
        float orthoX = getX();
        float orthoY = getY();
        float isoX = (orthoX + orthoY) * tileWidth / 2;
        float isoY = (orthoY - orthoX) * tileheight / 2;
        return new Coordinates(isoX, isoY, getZ());
    }

}
