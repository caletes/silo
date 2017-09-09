package com.caletes.game;

public class Elevations {

    private int size;
    private double[][] elevations;

    public Elevations(int size) {
        this.size = size;
        elevations = new double[size][size];
    }

    public int getSize() {
        return size;
    }


    public double get(int x, int y) {
        return elevations[x][y];
    }

    public void pushTo(double elevation, int x, int y) {
        elevations[x][y] = elevation;
    }
}
