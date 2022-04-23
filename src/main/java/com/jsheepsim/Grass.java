package com.jsheepsim;

import com.JEngine.PrimitiveTypes.JImage;
import com.JEngine.PrimitiveTypes.Position.Transform;
import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.JIdentity;
import com.jsheepsim.Core.Coord;
import com.jsheepsim.Core.Entity;
import com.jsheepsim.Core.Interfaces.IAttackable;
import com.jsheepsim.Core.WorldSimulator;

import java.io.File;

public class Grass extends Entity implements IAttackable {
    public Grass(Coord arrPos, WorldSimulator wmRef) {
        super(Transform.simpleTransform(arrPos.x*wmRef.getWorldData().getTileSize(), arrPos.y*wmRef.getWorldData().getTileSize(), 0), new JIdentity("Grass", "entity"), arrPos, wmRef, new File("images/grass.png"));
    }

    @Override
    public void attacked(Entity entity) {
        getWorldSimulator().removeGrass(getX(),getY());
    }
}
