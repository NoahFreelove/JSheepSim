package com.jsheepsim.Entities.Animals;

import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.JIdentity;
import com.jsheepsim.Entities.Animals.BaseClasses.Animal;
import com.jsheepsim.Entities.Animals.BaseClasses.Herbivore;
import com.jsheepsim.Simulator.Coord;
import com.jsheepsim.Simulator.WorldSimulator;

import java.io.File;

public class Sheep extends Herbivore {

    public Sheep(Coord arrPos, WorldSimulator worldSimulator, String name, boolean isChild) {
        super(new JIdentity(name, "animal"), arrPos, worldSimulator, new File("images/sheep.png"), 45, isChild, 1);
    }

    @Override
    public Animal breed(Animal animal) {
        setHasEaten(false);
        Sheep newSheep = new Sheep(worldSimulator.getAvailableSpotInRange(getX(),getY(),1), worldSimulator, getName() + " child", true);
        worldSimulator.addAnimal(newSheep);
        child = newSheep;
        return newSheep;
    }
}
