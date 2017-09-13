package com.caletes.game.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.caletes.game.IsoConverter;

public class WorldPosition {
    private Vector3 position;

    public WorldPosition(Vector3 position) {
        this.position = position;
    }
    public WorldPosition(float x, float y, float z) {
        this.position = new Vector3(x, y, z);
    }

    public static WorldPosition createFromChunkPosition(float x, float y, float z, int chunkSize) {
        return new WorldPosition(x * chunkSize, y * chunkSize, z * chunkSize);
    }

    public Vector3 getPosition() {
        return position;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getZ() {
        return position.z;
    }

    public Vector3 getChunkPosition(int chunkSize) {
        float x = position.x / chunkSize;
        if (x < 0) x--;
        float y = position.y / chunkSize;
        if (y < 0) y--;
        float z = position.z / chunkSize;
        if (z < 0) z--;
        return new Vector3((int) x, (int) y, (int) z);
    }

    public Vector3 getItemPositionInChunk(int chunkSize) {
        return new Vector3((int) position.x % chunkSize, (int) position.y % chunkSize, (int) position.z % chunkSize);
    }

    public Vector3 getItemPositionInNode() {
        String xs = String.valueOf(position.x);
        String ys = String.valueOf(position.y);
        String zs = String.valueOf(position.z);
        float dx = Float.valueOf(xs.substring((xs.indexOf("."))));
        float dy = Float.valueOf(ys.substring((ys.indexOf("."))));
        float dz = Float.valueOf(zs.substring((zs.indexOf("."))));
        return new Vector3(dx, dy, dz);
    }

    public Vector2 getSpritePosition() {
        return IsoConverter.getInstance().toScreen(getX(), getY(), getZ());
    }

}
