package com.jsheepsim.Entities.Plants;

import com.jsheepsim.Simulator.Coord;
import com.jsheepsim.Simulator.WorldSimulator;

import java.io.File;

public class Grass extends Plant {
    public Grass(Coord arrPos, WorldSimulator wmRef) {
        super(arrPos, wmRef, "Grass", new File("images/grass.png"));
    }
}
