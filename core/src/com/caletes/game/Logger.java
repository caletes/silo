package com.caletes.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Logger {
    public static final int PADDING = 10;
    private static SpriteBatch batch;
    private int viewportWidth, viewportHeight;
    private int fps;
    private int branchExponent;
    private int[] cameraWorldPosition;

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

    public void setBranchExponent(int exponent) {
        this.branchExponent = exponent;
    }

    public void setCameraWorldPosition(int[] cameraWorldPosition) {
        this.cameraWorldPosition = cameraWorldPosition;
    }

    public void render() {
        String message = fps + " fps";
        message += "\nCamera position " + cameraWorldPosition[0] + "," + cameraWorldPosition[1];
        message += "\nBranch exponent " + branchExponent;
        font.draw(batch, message, -viewportWidth / 2 + PADDING, viewportHeight / 2 - PADDING);
    }


}
