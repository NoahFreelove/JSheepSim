package com.jsheepsim.Simulator;

/**
 * Coordinates of a point in the simulation.
 */
public class Coord {
    public int x;
    public int y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString(){
        return "(" + x + "," + y + ")";
    }
}
