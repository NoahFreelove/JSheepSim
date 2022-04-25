package com.jsheepsim.Entities;

import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.JIdentity;
import com.jsheepsim.Entities.Animals.BaseClasses.Animal;
import com.jsheepsim.Simulator.Coord;
import com.jsheepsim.Simulator.WorldSimulator;

import java.io.File;

// Fence extends animal because it's much easier to implement.
public class Fence extends Animal {
    public Fence(Coord arrPos, WorldSimulator worldSimulator, String name) {
        super(new JIdentity(name,"entity"), arrPos, worldSimulator, new File("images/fence.png"), 60, false, 1000);
    }

    @Override
    public void simUpdate(){}

    @Override
    protected Animal breed(Animal animal) {
        return null;
    }

    @Override
    public void Update(){}

}
