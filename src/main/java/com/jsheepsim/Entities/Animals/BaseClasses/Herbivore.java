package com.jsheepsim.Entities.Animals.BaseClasses;

import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.JIdentity;
import com.jsheepsim.Simulator.Coord;
import com.jsheepsim.Entities.Plants.Plant;
import com.jsheepsim.Simulator.WorldSimulator;

import java.io.File;

public class Herbivore extends Animal {
    public Herbivore(JIdentity jIdentity, Coord arrPos, WorldSimulator wmRef, File imagePath, int maxDaysToLive, boolean isChild, int foodChainLevel) {
        super(jIdentity, arrPos, wmRef, imagePath, maxDaysToLive, isChild, foodChainLevel);
    }

    @Override
    protected Animal breed(Animal animal) {
        return null;
    }

    public boolean lookForPlants(){
        if(!worldSettings.allowEating())
            return false;

        if(!hasEaten()){
            for (Plant g: worldSimulator.getPlantInRange(getX(), getY(), 1)){
                if(g!=null){
                    g.eatPlant();
                    setHasEaten(true);
                    if(!worldSimulator.isOccupied(g.getX(), g.getY())){
                        moveAbsolute(g.getX(),g.getY());
                        System.out.println("Sheep ate plant");
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
