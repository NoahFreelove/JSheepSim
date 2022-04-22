package com.jsheepsim;

import com.JEngine.PrimitiveTypes.JImage;
import com.JEngine.PrimitiveTypes.Position.Transform;
import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.JIdentity;
import com.jsheepsim.Core.Animal;
import com.jsheepsim.Core.Coord;
import com.jsheepsim.Core.Interfaces.IBreedable;
import com.jsheepsim.Core.WorldSimulator;

import java.io.File;

public class Wolf extends Animal implements IBreedable {
    public Wolf(Coord arrPos, WorldSimulator worldSimulator, String name) {
        super(new JIdentity(name, "animal"), arrPos, worldSimulator, new File("images/wolf.png"));
    }

    @Override
    public void breed(Animal animal) {
        hasEaten = false;
        worldSimulator.addAnimal(new Wolf(worldSimulator.getAvailableSpotInRange(getX(),getY(),1), worldSimulator, getName() + " child"));
    }
}
