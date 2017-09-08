package com.caletes.game.models;

import com.badlogic.gdx.math.Vector3;

public class Position {
    private float x;
    private float y;
    private float z;

    public Position(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3 getWorldPosition() {
        return new Vector3(x, y, z);
    }

    public Vector3 getChunkPosition(int chunkSize) {
        return new Vector3((int) x / chunkSize, (int) y / chunkSize, (int) z / chunkSize);
    }

    public Vector3 getItemPosition() {
        String xs = String.valueOf(x);
        String ys = String.valueOf(y);
        String zs = String.valueOf(z);
        float dx = Float.valueOf(xs.substring((xs.indexOf("."))));
        float dy = Float.valueOf(ys.substring((xs.indexOf("."))));
        float dz = Float.valueOf(zs.substring((xs.indexOf("."))));
        return new Vector3(dx, dy, dz);
    }
}
