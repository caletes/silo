package com.caletes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.caletes.game.models.WorldPosition;

public class Camera extends OrthographicCamera {

    private static final int TRANSLATION_VELOCITY = 7;
    private static final int ZOOM_MAX = 1;
    private static final int ZOOM_DEFAULT = 2;
    private static final int ZOOM_MIN = 20;
    private static final float ZOOM_VELOCITY = 0.25f;
    private static IsoConverter isoConverter;

    public Camera(float viewportWidth, float viewportHeight, IsoConverter isoConverter) {
        super(viewportWidth, viewportHeight);
        this.isoConverter = isoConverter;
        zoomDefault();
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
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_RIGHT)) {
            zoomDefault();
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

    private void zoomDefault() {
        zoom = ZOOM_DEFAULT;
    }

    private float getVelocity() {
        return TRANSLATION_VELOCITY * zoom;
    }

    public WorldPosition getWorldPosition() {
        Vector2 wposition = isoConverter.toWorld(position.x, position.y);
        return new WorldPosition(wposition.x, wposition.y, position.z);
    }

    public void setWorldPosition(WorldPosition worldPosition) {
        Vector2 screenPosition = isoConverter.toScreen(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ());
        position.x = screenPosition.x;
        position.y = screenPosition.y;
        position.z = worldPosition.getZ();
    }

    @Override
    public void update() {
        super.update();
    }
}
