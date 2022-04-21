package com.jsheepsim;

import com.JEngine.PrimitiveTypes.JImage;
import com.JEngine.PrimitiveTypes.Position.Transform;
import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.JIdentity;
import com.jsheepsim.Core.Animal;

public class Wolf extends Animal {
    public Wolf(Transform transform, JImage newSprite, JIdentity jIdentity) {
        super(transform, newSprite, jIdentity);
    }
}
