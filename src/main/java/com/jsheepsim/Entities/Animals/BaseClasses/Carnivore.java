package com.jsheepsim.Entities.Animals.BaseClasses;

import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.Identity;
import com.jsheepsim.Simulator.Coord;
import com.jsheepsim.Simulator.WorldSimulator;

import java.io.File;


public class Carnivore extends Animal {
    public Carnivore(Identity identity, Coord arrPos, WorldSimulator wmRef, File imagePath, int maxDaysToLive, boolean isChild, int foodChainLevel) {
        super(identity, arrPos, wmRef, imagePath, maxDaysToLive, isChild, foodChainLevel);
    }

    @Override
    protected Animal breed(Animal animal) {
        return null;
    }

    public boolean hunt() {
        if(!worldSettings.allowHunting())
            return false;
        if(isChild && !worldSettings.allowChildrenEatingAdults())
            return false;

        for (Animal a:worldSimulator.getAnimalsInRange(getX(), getY(), 1)) {
            if(a!=null && a!= this){
                // Dont hunt friendlies and dont hunt stronger animals
                if((a.getClass() != getClass() || worldSettings.allowEatingOwnSpecies()) && ((a.getFoodChainLevel() <= getFoodChainLevel()) || worldSettings.ignoreFoodChainLevel())){
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
