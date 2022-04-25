package com.jsheepsim.Animals;

import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.JIdentity;
import com.jsheepsim.Core.Coord;
import com.jsheepsim.Core.WorldSimulator;

import java.io.File;

public class Bear extends Carnivore{
    public Bear(Coord arrPos, WorldSimulator worldSimulator, String name, boolean isChild) {
        super(new JIdentity(name, "animal"), arrPos, worldSimulator, new File("images/bear.png"), 30, isChild, 3);
    }
    @Override
    protected Animal breed(Animal animal) {
        setHasEaten(false);
        Bear newBear = new Bear(worldSimulator.getAvailableSpotInRange(getX(),getY(),1), worldSimulator, getName() + " child", true);
        child = newBear;
        worldSimulator.addAnimal(newBear);
        return newBear;
    }
}
