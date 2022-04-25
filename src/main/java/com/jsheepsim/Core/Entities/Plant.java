package com.jsheepsim.Core.Entities;

import com.JEngine.PrimitiveTypes.Position.Transform;
import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.JIdentity;
import com.jsheepsim.Core.Coord;
import com.jsheepsim.Core.WorldSimulator;

import java.io.File;

public class Plant extends Entity {
    public Plant(Coord arrPos, WorldSimulator wmRef) {
        super(Transform.simpleTransform(arrPos.x*wmRef.getWorldData().getTileSize(), arrPos.y*wmRef.getWorldData().getTileSize(), 0), new JIdentity("Grass", "entity"), arrPos, wmRef, new File("images/grass.png"));
    }

    public void eatPlant() {
        getWorldSimulator().removeGrass(getX(),getY());
        worldSimulator.getScene().remove(this);
    }
}
