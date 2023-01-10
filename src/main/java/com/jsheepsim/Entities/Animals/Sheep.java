package com.jsheepsim.Entities.Animals;

import com.JEngine.Core.Identity;
import com.jsheepsim.Entities.Animals.BaseClasses.Animal;
import com.jsheepsim.Entities.Animals.BaseClasses.Herbivore;
import com.jsheepsim.Simulator.Coord;
import com.jsheepsim.Simulator.WorldSimulator;

import java.io.File;

public class Sheep extends Herbivore {

    public Sheep(Coord arrPos, WorldSimulator worldSimulator, String name, boolean isChild) {
        super(new Identity(name, "animal"), arrPos, worldSimulator, new File("images/sheep.png"), 100, isChild, 1);
    }

    // Add a new sheep to the world
    @Override
    public Animal breed(Animal animal) {
        setHasEaten(false);
        Sheep newSheep = new Sheep(worldSimulator.getAvailableSpotInRange(getX(),getY(),1), worldSimulator, getName() + " child", true);
        worldSimulator.addAnimal(newSheep);
        child = newSheep;
        return newSheep;
    }
}
