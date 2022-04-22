package com.jsheepsim;

import com.JEngine.PrimitiveTypes.JImage;
import com.JEngine.PrimitiveTypes.Position.Transform;
import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.JIdentity;
import com.jsheepsim.Core.Animal;
import com.jsheepsim.Core.Coord;
import com.jsheepsim.Core.WorldSimulator;

import java.io.File;

public class Wolf extends Animal {
    public Wolf(Coord arrPos, WorldSimulator worldSimulator, String name) {
        super(new JIdentity(name, "animal"), arrPos, worldSimulator, new File("images/wolf.png"));
    }
}
