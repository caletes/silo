package com.caletes.game.generators;

import com.badlogic.gdx.graphics.Pixmap;
import com.caletes.game.Biome;
import com.caletes.game.Elevations;

import java.awt.*;

/**
 * cf. http://www.redblobgames.com/maps/terrain-from-noise/
 * https://cmaher.github.io/posts/working-with-simplex-noise/
 */
public class WorldGeneratorFromNoise {

    private static OpenSimplexNoise simplexNoise1;
    private int width, height;
    private int startX, startY;
    private Elevations elevations;


    public WorldGeneratorFromNoise(int width, int height, long seed) {
        this(width, height, 0, 0, seed);
    }

    public WorldGeneratorFromNoise(int width, int height, int startX, int startY, long seed) {
        this.width = width;
        this.height = height;
        this.startX = startX;
        this.startY = startY;
        simplexNoise1 = new OpenSimplexNoise(seed);
        generateElevations();
    }

    public Elevations getElevations() {
        return elevations;
    }


    double minNoise = 999;
    double maxNoise = -999;
    static double absoluteMin = 999;
    static double absoluteMax = -999;

    private void generateElevations() {
        elevations = new Elevations(width, height);
        double persistence = 0.5;
        double scale = 0.008;
        int octaveCount = 6;
        int low = 0;
        int high = 15;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double elevation = sumOctave(octaveCount, x + startX, y + startY, persistence, scale, low, high);

                minNoise = Math.min(minNoise, elevation);
                maxNoise = Math.max(maxNoise, elevation);
                elevations.pushTo(elevation, x, y);
            }
        }
        if (minNoise < absoluteMin) {
            absoluteMin = minNoise;
            System.out.println("MIN " + absoluteMin);
        }
        if (maxNoise > absoluteMax) {
            absoluteMax = maxNoise;
            System.out.println("MAX " + absoluteMax);
        }
    }

    private double sumOctave(int octaveCount, double x, double y, double persistence, double scale, int low, int high) {
        double maxAmp = 0;
        double amp = 1;
        double freq = scale;
        double noise = 0;
        //add successively smaller, higher-frequency terms
        for (int i = 0; i < octaveCount; ++i) {
            noise += amp * simplexNoise1.eval(x * freq, y * freq);
            maxAmp += amp;
            amp *= persistence;
            freq *= 2;
        }
        //take the average value of the iterations
        noise /= maxAmp;
       // noise += 4 * simplexNoise1.eval(x * 0.0005, y * 0.0005);
        // the 2D noise is trapped within ±½√2 ≈ ±0.707
        //normalize the result between 0 and 1
        double min = -0.6;
        double max = 0.6;
        noise = (noise - min) / (max - min);
        return noise;
    }


    public Pixmap toHeightMap() {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);
        int i = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                byte greyscale = toGrayScale(elevations.get(x, y));
                pixmap.getPixels().put(i++, greyscale);
                pixmap.getPixels().put(i++, greyscale);
                pixmap.getPixels().put(i++, greyscale);
            }
        }
        return pixmap;
    }

    private byte toGrayScale(double elevation) {
        return (byte) (elevation * 255);
    }

    public Pixmap toBiomeMap() {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);
        int i = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = Biome.find(elevations.get(x, y)).getColor();
                pixmap.getPixels().put(i++, (byte) color.getRed());
                pixmap.getPixels().put(i++, (byte) color.getGreen());
                pixmap.getPixels().put(i++, (byte) color.getBlue());
            }
        }
        return pixmap;
    }
}
