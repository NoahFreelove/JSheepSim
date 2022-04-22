package com.jsheepsim;

import com.JEngine.PrimitiveTypes.JImage;
import com.JEngine.PrimitiveTypes.Position.Transform;
import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.JIdentity;
import com.jsheepsim.Core.Coord;
import com.jsheepsim.Core.Entity;
import com.jsheepsim.Core.WorldSimulator;

import java.io.File;

public class Grass extends Entity {
    public Grass(Transform transform, JIdentity jIdentity, Coord arrPos, WorldSimulator wmRef) {
        super(transform, jIdentity, arrPos, wmRef, new File("images/grass.png"));
    }
}
