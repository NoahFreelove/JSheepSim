package com.jsheepsim.Core.Entities;

import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.JIdentity;
import com.jsheepsim.Animals.Animal;
import com.jsheepsim.Core.Coord;
import com.jsheepsim.Core.WorldSimulator;

import java.io.File;

// Fence extends animal because it's much easier to implement.
public class Fence extends Animal {
    public Fence(Coord arrPos, WorldSimulator worldSimulator, String name) {
        super(new JIdentity(name,"entity"), arrPos, worldSimulator, new File("images/fence.png"));
    }

    @Override
    public void simUpdate(){}

    @Override
    public void Update(){}

}
