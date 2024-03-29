package com.jsheepsim.Entities.Animals;

import com.JEngine.Core.Identity;
import com.jsheepsim.Entities.Animals.BaseClasses.Animal;
import com.jsheepsim.Entities.Animals.BaseClasses.Carnivore;
import com.jsheepsim.Simulator.Coord;
import com.jsheepsim.Simulator.WorldSimulator;

import java.io.File;

public class Wolf extends Carnivore {
    public Wolf(Coord arrPos, WorldSimulator worldSimulator, String name, boolean isChild) {
        super(new Identity(name, "animal"), arrPos, worldSimulator, new File("images/wolf.png"), 50, isChild, 2);
    }

    // Add a new wolf into the world
    @Override
    protected Animal breed(Animal animal) {
        setHasEaten(false);
        Wolf newWolf = new Wolf(worldSimulator.getAvailableSpotInRange(getX(),getY(),1), worldSimulator, getName() + " child", true);
        child = newWolf;
        worldSimulator.addAnimal(newWolf);
        return newWolf;
    }
}
