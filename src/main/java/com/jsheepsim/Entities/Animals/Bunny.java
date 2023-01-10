package com.jsheepsim.Entities.Animals;

import com.JEngine.Core.Identity;
import com.jsheepsim.Entities.Animals.BaseClasses.Animal;
import com.jsheepsim.Entities.Animals.BaseClasses.Herbivore;
import com.jsheepsim.Simulator.Coord;
import com.jsheepsim.Simulator.WorldSimulator;

import java.io.File;

public class Bunny extends Herbivore {
    // Bunny is not currently used in the world generation process because it gave too much food for the carnivores
    public Bunny(Coord arrPos, WorldSimulator worldSimulator, String name, boolean isChild) {
        super(new Identity(name, "animal"), arrPos, worldSimulator, new File("images/bunny.png"), 45, isChild, 1);
    }

    // Creates a new bunny and adds it to the world
    @Override
    public Animal breed(Animal animal) {
        setHasEaten(false);
        Bunny newBunny = new Bunny(worldSimulator.getAvailableSpotInRange(getX(),getY(),1), worldSimulator, getName() + " child", true);
        worldSimulator.addAnimal(newBunny);
        child = newBunny;
        return newBunny;
    }
}
