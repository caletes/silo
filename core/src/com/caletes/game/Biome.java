package com.caletes.game;

import java.awt.*;

public enum Biome {
    OCEAN(0.1, new Color(33, 62, 113)),
    BEACH(0.15, new Color(200, 200, 140)),
    GRASSLAND(0.4, new Color(63, 142, 12)),
    STONE(0.6, new Color(150, 150, 150)),
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
        return null;
    }

    public Color getColor() {
        return color;
    }

}
