package com.jsheepsim.Entities.Plants;

import com.JEngine.PrimitiveTypes.Position.Transform;
import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.Identity;
import com.jsheepsim.Entities.Entity;
import com.jsheepsim.Simulator.Coord;
import com.jsheepsim.Simulator.WorldSimulator;

import java.io.File;

/**
 * Plants are very basic entities as they don't do anything during the sim tick and can only be eaten by animals.
 */
public class Plant extends Entity {
    public Plant(Coord arrPos, WorldSimulator wmRef, String name, File imageFile) {
        super(Transform.simpleTransform(arrPos.x*wmRef.getTileSize(), arrPos.y*wmRef.getTileSize(), 0), new Identity(name, "entity"), arrPos, wmRef, imageFile);
    }

    // Called when a herbivore eats this plant and removes it from the world.
    public void eatPlant() {
        getWorldSimulator().removePlant(getX(),getY());
        worldSimulator.getScene().remove(this);
    }

    @Override
    public void simUpdate() {
        // Do nothing
    }

    @Override
    public String toString(){
        return String.format("'%s' (Plant) at %s", getClass().getSimpleName(), getPosition());
    }
}
