package com.jsheepsim.Entities.Animals.BaseClasses;

import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.Identity;
import com.jsheepsim.Entities.Plants.Plant;
import com.jsheepsim.Simulator.Coord;
import com.jsheepsim.Simulator.WorldSimulator;

import java.io.File;

public class Herbivore extends Animal {
    public Herbivore(Identity identity, Coord arrPos, WorldSimulator wmRef, File imagePath, int maxDaysToLive, boolean isChild, int foodChainLevel) {
        super(identity, arrPos, wmRef, imagePath, maxDaysToLive, isChild, foodChainLevel);
    }

    // Let subclasses override this method to do their own thing
    @Override
    protected Animal breed(Animal animal) {
        return null;
    }

    public boolean lookForPlants(){
        // If the world allows herbivores to eat plants, then we can look for plants
        if(!worldSettings.allowEating())
            return false;
        // If the herbivore is not hungry, then we can't look for plants
        if(!hasEaten()){
            for (Plant plant: worldSimulator.getPlantInRange(getX(), getY(), 1)){
                if(plant!=null){
                    plant.eatPlant();
                    setHasEaten(true);
                    // if another animal is on the grass we can't move to it, but we can still eat it
                    if(!worldSimulator.isOccupied(plant.getX(), plant.getY())){
                        moveAbsolute(plant.getX(),plant.getY());
                        return true;
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
