package com.jsheepsim.Entities;

import com.JEngine.Core.Identity;
import com.jsheepsim.Entities.Animals.BaseClasses.Animal;
import com.jsheepsim.Simulator.Coord;
import com.jsheepsim.Simulator.WorldSimulator;

import java.io.File;

// Fence extends animal because it's much easier to implement.
public class Fence extends Animal {
    public Fence(Coord arrPos, WorldSimulator worldSimulator, String name) {
        super(new Identity(name,"entity"), arrPos, worldSimulator, new File("images/fence.png"), 60, false, 1000);
    }

    // Override sim update so the fence doesn't die
    @Override
    public void simUpdate(){}

    // it would be funny if I implemented this, but I won't because it's a fence.
    @Override
    protected Animal breed(Animal animal) {
        return null;
    }

    @Override
    public void Update(){}

}
