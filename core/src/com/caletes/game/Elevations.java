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

    public double getMinAround(int x, int y) {
        double inf = 9999;
        double north = (y > 0) ? get(x, y - 1) : inf;
        double east = (x < size - 1) ? get(x + 1, y) : inf;
        double south = (y < size - 1) ? get(x, y + 1) : inf;
        double west = (x > 0) ? get(x - 1, y) : inf;
        return Math.min(Math.min(north, east), Math.min(south, west));
    }
}
