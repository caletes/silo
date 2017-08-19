package com.caletes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Camera extends OrthographicCamera {

    public static final int TRANSLATION_VELOCITY = 3;
    public static final int ZOOM_MAX = 1;
    public static final int ZOOM_MIN = 10;
    public static final float ZOOM_VELOCITY = 0.25f;

    public Camera(float viewportWidth, float viewportHeight) {
        super(viewportWidth, viewportHeight);
    }

    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            translate(-getVelocity(), 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            translate(getVelocity(), 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            translate(0, -getVelocity(), 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            translate(0, getVelocity(), 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.PAGE_UP)) {
            if (zoom > ZOOM_MAX)
                zoom -= ZOOM_VELOCITY;
            else zoom = ZOOM_MAX;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.PAGE_DOWN)) {
            if (zoom < ZOOM_MIN)
                zoom += ZOOM_VELOCITY;
            else
                zoom = ZOOM_MIN;
        }
    }

    private float getVelocity() {
        return TRANSLATION_VELOCITY * zoom;
    }

    public int[] getPositionFromWorld() {
        return IsoConverter.toWorld(this.position.x, this.position.y);
    }

    public void setPositionToWorld(int x, int y, int z) {
        int[] screenPosition = IsoConverter.toScreen(x, y, z);
        this.position.x = screenPosition[0];
        this.position.y = screenPosition[1];
        this.position.z = z;
    }
}
