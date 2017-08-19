package com.caletes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Camera extends OrthographicCamera {

    private static final int TRANSLATION_VELOCITY = 5;
    private static final int ZOOM_MAX = 1;
    private static final int ZOOM_MIN = 10;
    private static final float ZOOM_VELOCITY = 0.25f;
    private float previousX;
    private float previousY;
    private Direction direction = null;

    public Camera(float viewportWidth, float viewportHeight) {
        super(viewportWidth, viewportHeight);
        previousX = position.x;
        previousY = position.y;
    }

    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            moveUp();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            moveRight();
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            moveDown();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            moveLeft();
        if (Gdx.input.isKeyPressed(Input.Keys.PAGE_UP)) {
            zoomOut();
        } else if (Gdx.input.isKeyPressed(Input.Keys.PAGE_DOWN)) {
            zoomIn();
        }
    }

    private void moveUp() {
        translate(0, getVelocity(), 0);
    }

    private void moveRight() {
        translate(getVelocity(), 0, 0);
    }

    private void moveDown() {
        translate(0, -getVelocity(), 0);
    }

    private void moveLeft() {
        translate(-getVelocity(), 0, 0);
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
        return IsoConverter.toWorld(this.position.x, this.position.y);
    }

    public void setPositionToWorld(int x, int y, int z) {
        int[] screenPosition = IsoConverter.toScreen(x, y, z);
        this.position.x = screenPosition[0];
        this.position.y = screenPosition[1];
        this.position.z = z;
    }

    @Override
    public void update() {
        super.update();
        findDirection();
    }

    private void findDirection() {
        float deltaX = position.x - previousX;
        float deltaY = position.y - previousY;
        direction = Direction.getFromDeltas(deltaX, deltaY);
        previousX = position.x;
        previousY = position.y;
    }

    public Direction getDirection() {
        return direction;
    }
}
