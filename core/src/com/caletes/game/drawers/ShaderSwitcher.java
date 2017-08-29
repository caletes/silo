package com.caletes.game.drawers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.caletes.game.models.items.Item;
import com.caletes.game.models.items.cubes.WaterCube;

public class ShaderSwitcher {
    private static SpriteBatch batch;
    private static final String vertexShader = new FileHandle("assets/shaders/vertexShader.glsl").readString();
    private static final String fragmentShader = new FileHandle("assets/shaders/defaultPixelShader.glsl").readString();

    private static ShaderProgram waterShader;
    private static ShaderProgram defaultShader;
    private static ShaderProgram currentShader;

    private float amplitudeWave = 5f;
    private float angleWave = 0.0f;
    private float angleWaveSpeed = 3.0f;
    private static final float PI2 = 3.1415926535897932384626433832795f * 2.0f;

    public ShaderSwitcher(SpriteBatch batch) {
        this.batch = batch;
        this.waterShader = new ShaderProgram(vertexShader, fragmentShader);
        this.defaultShader = SpriteBatch.createDefaultShader();
    }

    public void process(){
        processWaterShader();
    }

    private void processWaterShader() {
        float dt = Gdx.graphics.getRawDeltaTime();
        angleWave += dt * angleWaveSpeed;
        while (angleWave > PI2)
            angleWave -= PI2;
        waterShader.begin();
        waterShader.setUniformf("waveData", angleWave, amplitudeWave);
        waterShader.end();
    }

    public void switchFor(Item item) {
        if (item instanceof WaterCube) {
            setShader(waterShader);
        } else {
            setShader(defaultShader);
        }
    }

    public void backToDefault(){
        setShader(defaultShader);
    }

    private void setShader(ShaderProgram shader) {
        if (currentShader != shader) {
            batch.flush();
            batch.setShader(shader);
            currentShader = shader;
        }
    }
}
