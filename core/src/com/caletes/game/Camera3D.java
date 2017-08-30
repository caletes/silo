package com.caletes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;

public class Camera3D extends PerspectiveCamera {

    private static final int TRANSLATION_VELOCITY = 3;

    public Camera3D(float viewportWidth, float viewportHeight) {
        super(67, viewportWidth, viewportHeight);
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


    private float getVelocity() {
        return TRANSLATION_VELOCITY;
    }

    @Override
    public void update() {
        super.update();
    }
}
