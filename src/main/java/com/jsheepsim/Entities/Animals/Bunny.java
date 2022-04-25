package com.jsheepsim.Entities.Animals;

import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.JIdentity;
import com.jsheepsim.Entities.Animals.BaseClasses.Animal;
import com.jsheepsim.Entities.Animals.BaseClasses.Herbivore;
import com.jsheepsim.Simulator.Coord;
import com.jsheepsim.Simulator.WorldSimulator;

import java.io.File;

public class Bunny extends Herbivore {
    public Bunny(Coord arrPos, WorldSimulator worldSimulator, String name, boolean isChild) {
        super(new JIdentity(name, "animal"), arrPos, worldSimulator, new File("images/bunny.png"), 45, isChild, 1);
    }

    @Override
    public Animal breed(Animal animal) {
        setHasEaten(false);
        Bunny newBunny = new Bunny(worldSimulator.getAvailableSpotInRange(getX(),getY(),1), worldSimulator, getName() + " child", true);
        worldSimulator.addAnimal(newBunny);
        child = newBunny;
        return newBunny;
    }
}
