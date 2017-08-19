package com.caletes.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Logger {
    public static final int PADDING = 10;
    private static SpriteBatch batch;
    private int screenWidth, screenHeight;
    private int fps;
    private int[] cameraWorldPosition;

    private static BitmapFont font;

    public Logger(SpriteBatch batch) {
        this.batch = batch;
        this.font = new BitmapFont();
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public void setCameraWorldPosition(int[] cameraWorldPosition) {
        this.cameraWorldPosition = cameraWorldPosition;
    }

    public void render() {
        String message = fps + " fps";
        message += "\nCamera position " + cameraWorldPosition[0] + "," + cameraWorldPosition[1];
        font.draw(batch, message, -screenWidth / 2 + PADDING, screenHeight / 2 - PADDING);
    }


}
