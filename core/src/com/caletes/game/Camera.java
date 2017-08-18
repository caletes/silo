package com.caletes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Camera extends OrthographicCamera {
    public Camera(float viewportWidth, float viewportHeight) {
        super(viewportWidth, viewportHeight);
    }

    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            translate(-getSpeed(), 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            translate(getSpeed(), 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            translate(0, -getSpeed(), 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            translate(0, getSpeed(), 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.PAGE_UP)) {
            if (zoom > 1)
                zoom -= 0.5f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.PAGE_DOWN)) {
            if (zoom < 10)
                zoom += 0.5f;
        }
    }

    private float getSpeed() {
        return 3 * zoom;
    }
}
