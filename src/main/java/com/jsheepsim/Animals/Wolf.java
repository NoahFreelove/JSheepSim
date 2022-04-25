package com.jsheepsim.Animals;

import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.JIdentity;
import com.jsheepsim.Core.Coord;
import com.jsheepsim.Core.WorldSimulator;

import java.io.File;

public class Wolf extends Carnivore {
    public Wolf(Coord arrPos, WorldSimulator worldSimulator, String name, boolean isChild) {
        super(new JIdentity(name, "animal"), arrPos, worldSimulator, new File("images/wolf.png"), 30, isChild, 2);
    }

    @Override
    protected Animal breed(Animal animal) {
        setHasEaten(false);
        Wolf newWolf = new Wolf(worldSimulator.getAvailableSpotInRange(getX(),getY(),1), worldSimulator, getName() + " child", true);
        child = newWolf;
        worldSimulator.addAnimal(newWolf);
        return newWolf;
    }
}
