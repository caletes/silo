package com.caletes.game.drawers;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
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

    public void process(float delta, Vector3 camPos) {
        processWaterShader(delta, camPos);
    }

    private void processWaterShader(float delta, Vector3 camPos) {
        angleWave += delta * angleWaveSpeed;
        while (angleWave > PI2)
            angleWave -= PI2;
        waterShader.begin();
        waterShader.setUniformf("angleWave", angleWave);
        waterShader.setUniformf("amplitudeWave", amplitudeWave);
        // On passe la position de la camera pour rescaler la position du shader, sinon probleme avec les fonctions trigo
        // On arrondi à 10000 pour éviter de changer la valeur à chaque déplacement de la caméra
        waterShader.setUniformf("camPosX", (int)camPos.x/10000*10000);
        waterShader.setUniformf("camPosY", (int)camPos.y/10000*10000);
        waterShader.end();
    }

    public boolean switchFor(Item item) {
        if (item instanceof WaterCube)
            return setShader(waterShader);
        return setShader(defaultShader);
    }

    public void backToDefault() {
        setShader(defaultShader);
    }

    private boolean setShader(ShaderProgram shader) {
        if (currentShader != shader) {
            batch.setShader(shader);
            currentShader = shader;
            return true;
        }
        return false;
    }
}
