package com.jsheepsim;

import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.JIdentity;
import com.jsheepsim.Core.Animal;
import com.jsheepsim.Core.Coord;
import com.jsheepsim.Core.Interfaces.IAttackable;
import com.jsheepsim.Core.Interfaces.IBreedable;
import com.jsheepsim.Core.Interfaces.IHunter;
import com.jsheepsim.Core.WorldSimulator;

import java.io.File;

public class Wolf extends Animal implements IBreedable, IHunter {
    public Wolf(Coord arrPos, WorldSimulator worldSimulator, String name) {
        super(new JIdentity(name, "animal"), arrPos, worldSimulator, new File("images/wolf.png"));
    }

    @Override
    public Animal breed(Animal animal) {
        setHasEaten(false);
        Wolf w = new Wolf(worldSimulator.getAvailableSpotInRange(getX(),getY(),1), worldSimulator, getName() + " child");
        child = w;
        worldSimulator.addAnimal(w);
        return w;
    }

    @Override
    public boolean hunt() {
        for (Animal a:worldSimulator.getAnimalsInRange(getX(), getY(), 1)) {
            if(a!=null && a!= this){
                if(a instanceof IAttackable attackable){
                    attackable.attacked(this);
                    setHasEaten(true);
                    moveAbsolute(a.getX(),a.getY());
                    System.out.println("Killed sheep");
                    return true;
                }
            }
        }
        return false;
    }
}
