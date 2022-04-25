package com.jsheepsim.Entities.Animals;

import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.JIdentity;
import com.jsheepsim.Entities.Animals.BaseClasses.Animal;
import com.jsheepsim.Entities.Animals.BaseClasses.Carnivore;
import com.jsheepsim.Simulator.Coord;
import com.jsheepsim.Simulator.WorldSimulator;

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
