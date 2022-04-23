package com.jsheepsim;

import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.JIdentity;
import com.jsheepsim.Core.Animal;
import com.jsheepsim.Core.Coord;
import com.jsheepsim.Core.Entity;
import com.jsheepsim.Core.Interfaces.IAttackable;
import com.jsheepsim.Core.Interfaces.IBreedable;
import com.jsheepsim.Core.WorldSimulator;

import java.io.File;

public class Sheep extends Animal implements IAttackable, IBreedable {
    public Sheep(Coord arrPos, WorldSimulator worldSimulator, String name) {
        super(new JIdentity(name, "animal"), arrPos, worldSimulator, new File("images/sheep.png"));
    }

    @Override
    public void attacked(Entity entity) {
        if (entity instanceof Wolf) {
            die();
        }
    }

    @Override
    public Animal breed(Animal animal) {
        setHasEaten(false);
        Sheep s = new Sheep(worldSimulator.getAvailableSpotInRange(getX(),getY(),1), worldSimulator, getName() + " child");
        worldSimulator.addAnimal(s);
        child = s;
        return s;
    }

    public void lookForGrass(){
        if(!hasEaten()){
            for (Grass g: worldSimulator.getGrassInRange(getX(), getY(), 1)){
                if(g!=null){
                    g.eatGrass();
                    setHasEaten(true);
                    if(!worldSimulator.isOccupied(g.getX(), g.getY())){
                        moveAbsolute(g.getX(),g.getY());
                        System.out.println("Sheep ate grass");
                    }
                }
            }
        }
    }

}
