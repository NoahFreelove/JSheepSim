package com.jsheepsim.Entities.Animals;

import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.Identity;
import com.jsheepsim.Entities.Animals.BaseClasses.Animal;
import com.jsheepsim.Entities.Animals.BaseClasses.Carnivore;
import com.jsheepsim.Simulator.Coord;
import com.jsheepsim.Simulator.WorldSimulator;

import java.io.File;

public class Bear extends Carnivore {
    public Bear(Coord arrPos, WorldSimulator worldSimulator, String name, boolean isChild) {
        super(new Identity(name, "animal"), arrPos, worldSimulator, new File("images/bear.png"), 50, isChild, 3);
    }
    // Creates a new bear and adds it into the world
    @Override
    protected Animal breed(Animal animal) {
        setHasEaten(false);
        Bear newBear = new Bear(worldSimulator.getAvailableSpotInRange(getX(),getY(),1), worldSimulator, getName() + " child", true);
        child = newBear;
        worldSimulator.addAnimal(newBear);
        return newBear;
    }
}
