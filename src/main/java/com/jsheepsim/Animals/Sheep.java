package com.jsheepsim.Animals;

import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.JIdentity;
import com.jsheepsim.Core.Coord;
import com.jsheepsim.Core.WorldSimulator;

import java.io.File;

public class Sheep extends Herbivore {

    public Sheep(Coord arrPos, WorldSimulator worldSimulator, String name, boolean isChild) {
        super(new JIdentity(name, "animal"), arrPos, worldSimulator, new File("images/sheep.png"), 45, isChild, 1);
    }

    @Override
    public Animal breed(Animal animal) {
        setHasEaten(false);
        Sheep s = new Sheep(worldSimulator.getAvailableSpotInRange(getX(),getY(),1), worldSimulator, getName() + " child", true);
        worldSimulator.addAnimal(s);
        child = s;
        return s;
    }

}
