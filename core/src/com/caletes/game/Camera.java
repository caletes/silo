package com.caletes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Camera extends OrthographicCamera {

    private static final int TRANSLATION_VELOCITY = 7;
    private static final int ZOOM_MAX = 1;
    private static final int ZOOM_MIN = 20;
    private static final float ZOOM_VELOCITY = 0.25f;
    private static IsoConverter isoConverter;

    public Camera(float viewportWidth, float viewportHeight, IsoConverter isoConverter) {
        super(viewportWidth, viewportHeight);
        this.isoConverter = isoConverter;
    }

    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            moveToNorthEast();
        else if (Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.LEFT))
            moveToNorthWest();
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            moveToSouthEast();
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && Gdx.input.isKeyPressed(Input.Keys.LEFT))
            moveToSouthWest();
        else if (Gdx.input.isKeyPressed(Input.Keys.UP))
            moveToNorth();
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            moveToEast();
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            moveToSouth();
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            moveToWest();

        if (Gdx.input.isKeyPressed(Input.Keys.PAGE_UP)) {
            zoomOut();
        } else if (Gdx.input.isKeyPressed(Input.Keys.PAGE_DOWN)) {
            zoomIn();
        }
    }

    private void moveToNorth() {
        translate(0, getVelocity(), 0);
    }

    private void moveToNorthEast() {
        translate(getVelocity(), getVelocity() / 2, 0);
    }

    private void moveToEast() {
        translate(getVelocity(), 0, 0);
    }

    private void moveToSouthEast() {
        translate(getVelocity(), -getVelocity() / 2, 0);
    }

    private void moveToSouth() {
        translate(0, -getVelocity(), 0);
    }

    private void moveToSouthWest() {
        translate(-getVelocity(), -getVelocity() / 2, 0);
    }

    private void moveToWest() {
        translate(-getVelocity(), 0, 0);
    }

    private void moveToNorthWest() {
        translate(-getVelocity(), getVelocity() / 2, 0);
    }

    private void zoomIn() {
        if (zoom < ZOOM_MIN)
            zoom += ZOOM_VELOCITY;
        else
            zoom = ZOOM_MIN;
    }

    private void zoomOut() {
        if (zoom > ZOOM_MAX)
            zoom -= ZOOM_VELOCITY;
        else zoom = ZOOM_MAX;
    }

    private float getVelocity() {
        return TRANSLATION_VELOCITY * zoom;
    }

    public int[] getPositionFromWorld() {
        return isoConverter.toWorld(this.position.x, this.position.y);
    }

    public void setPositionToWorld(int x, int y, int z) {
        int[] screenPosition = isoConverter.toScreen(x, y, z);
        this.position.x = screenPosition[0];
        this.position.y = screenPosition[1];
        this.position.z = z;
    }

    @Override
    public void update() {
        super.update();
    }
}
