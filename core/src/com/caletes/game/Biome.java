package com.caletes.game;

import java.awt.*;

public enum Biome {
    OCEAN(0.25, new Color(33, 62, 113)),
    BEACH(0.3, new Color(200, 200, 140)),
    GRASSLAND(0.7, new Color(63, 142, 12)),
    STONE(0.8, new Color(150, 150, 150)),
    SNOW(1, new Color(255, 255, 255));

    private double elevationMax;

    private Color color;

    Biome(double elevationMax, Color color) {
        this.elevationMax = elevationMax;
        this.color = color;
    }

    public static Biome find(double elevation) {
        for (Biome biome : values()) {
            if (elevation <= biome.elevationMax) {
                return biome;
            }
        }
        return SNOW;
    }

    public double getElevationMax() {
        return elevationMax;
    }

    public Color getColor() {
        return color;
    }

}
