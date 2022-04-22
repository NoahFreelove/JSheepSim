package com.jsheepsim;

import com.JEngine.PrimitiveTypes.Position.Transform;
import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.JIdentity;
import com.jsheepsim.Core.Animal;
import com.jsheepsim.Core.Coord;
import com.jsheepsim.Core.Entity;
import com.jsheepsim.Core.Interfaces.IAttackable;
import com.jsheepsim.Core.WorldSimulator;

import java.io.File;

public class Sheep extends Animal implements IAttackable {
    public Sheep(Coord arrPos, WorldSimulator worldSimulator, String name) {
        super(new JIdentity(name, "animal"), arrPos, worldSimulator, new File("images/sheep.png"));
    }

    @Override
    public void attacked(Entity entity) {
        if (entity instanceof Wolf) {
            die();
        }
    }
}
