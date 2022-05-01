package com.jsheepsim.Entities.Animals.BaseClasses;

import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.Identity;
import com.jsheepsim.Simulator.Coord;
import com.jsheepsim.Simulator.WorldSimulator;

import java.io.File;


public class Carnivore extends Animal {
    public Carnivore(Identity identity, Coord arrPos, WorldSimulator wmRef, File imagePath, int maxDaysToLive, boolean isChild, int foodChainLevel) {
        super(identity, arrPos, wmRef, imagePath, maxDaysToLive, isChild, foodChainLevel);
    }

    // Let the subclasses of Carnivore implement their own breed method
    @Override
    protected Animal breed(Animal animal) {
        return null;
    }

    public boolean hunt() {
        // If the world doesn't allow hunting, return false
        if(!worldSettings.allowHunting())
            return false;
        // If the animal is a child don't hunt unless allowed by the world settings
        if(isChild && !worldSettings.allowChildrenHunting())
            return false;

        for (Animal prey:worldSimulator.getAnimalsInRange(getX(), getY(), 1)) {

            if(prey!=null && prey!= this){
                // Dont hunt friendlies and dont hunt stronger animals
                if((prey.getClass() != getClass() || worldSettings.allowEatingOwnSpecies()) && ((prey.getFoodChainLevel() <= getFoodChainLevel()) || worldSettings.ignoreFoodChainLevel())){
                    // Attack the prey
                    prey.attacked(this);
                    // Eat the prey
                    setHasEaten(true);
                    // Move to the prey's position
                    moveAbsolute(prey.getX(),prey.getY());
                    return true;
                }
            }
        }
        return false;
    }
}
