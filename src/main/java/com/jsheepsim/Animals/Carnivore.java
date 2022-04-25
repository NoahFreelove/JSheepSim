package com.jsheepsim.Animals;

import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.JIdentity;
import com.jsheepsim.Core.Coord;
import com.jsheepsim.Core.WorldSimulator;

import java.io.File;


public class Carnivore extends Animal {
    public Carnivore(JIdentity jIdentity, Coord arrPos, WorldSimulator wmRef, File imagePath, int maxDaysToLive, boolean isChild, int foodChainLevel) {
        super(jIdentity, arrPos, wmRef, imagePath, maxDaysToLive, isChild, foodChainLevel);
    }

    @Override
    protected Animal breed(Animal animal) {
        return null;
    }

    public boolean hunt() {
        for (Animal a:worldSimulator.getAnimalsInRange(getX(), getY(), 1)) {
            if(a!=null && a!= this){
                // Dont hunt friendlies and dont hunt stronger animals
                if(a.getClass() != getClass() && a.getFoodChainLevel() < getFoodChainLevel()){
                    a.attacked(this);
                    setHasEaten(true);
                    moveAbsolute(a.getX(),a.getY());
                    return true;
                }
            }
        }
        return false;
    }
}
