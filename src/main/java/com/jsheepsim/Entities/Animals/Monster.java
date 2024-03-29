package com.jsheepsim.Entities.Animals;

import com.JEngine.Core.Identity;
import com.jsheepsim.Entities.Animals.BaseClasses.Animal;
import com.jsheepsim.Entities.Animals.BaseClasses.Carnivore;
import com.jsheepsim.Simulator.Coord;
import com.jsheepsim.Simulator.WorldSimulator;

import java.io.File;

public class Monster extends Carnivore {
    public Monster(Coord arrPos, WorldSimulator worldSimulator, String name, boolean isChild) {
        super(new Identity(name, "animal"), arrPos, worldSimulator, new File("images/monster.png"), 3000, isChild, 10);
    }
    @Override
    protected Animal breed(Animal animal) {
        return null;
    }

    // The monster is different from the other animals in that it can't breed, and can hunt in a 3 tile radius
    @Override
    public boolean hunt(){
        for (Animal a:worldSimulator.getAnimalsInRange(getX(), getY(), 3)) {
            // Monster will attack any animal within 3 tiles including other monsters and fences
            if(a!=null && a!= this){
                a.attacked(this);
                setHasEaten(false);
                moveAbsolute(a.getX(),a.getY());
                return true;
            }
        }
        return false;
    }
}
