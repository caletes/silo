package com.caletes.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.caletes.game.models.WorldPosition;


public class Logger {
    public static final int PADDING = 10;
    private static SpriteBatch batch;
    private int viewportWidth, viewportHeight;
    private int fps;
    private Vector3 cameraPosition;
    private WorldPosition playerPosition;

    private static BitmapFont font;

    public Logger(SpriteBatch batch) {
        this.batch = batch;
        this.font = new BitmapFont();
    }

    public void setViewportWidth(int viewportWidth) {
        this.viewportWidth = viewportWidth;
    }

    public void setViewportHeight(int viewportHeight) {
        this.viewportHeight = viewportHeight;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }


    public void setCameraPosition(WorldPosition worldPosition) {
        this.cameraPosition = worldPosition.getPosition();
    }
    public void setPlayerPosition(WorldPosition worldPosition) {
        this.playerPosition = worldPosition;
    }
    public void render() {
        String message = fps + " fps";
        message += "\nCamera position " + (int) cameraPosition.x + "," + (int) cameraPosition.y + "," + (int) cameraPosition.z;
        message += "\nPlayer position " + playerPosition.getPosition();
        font.draw(batch, message, -viewportWidth / 2 + PADDING, viewportHeight / 2 - PADDING);
    }


}
