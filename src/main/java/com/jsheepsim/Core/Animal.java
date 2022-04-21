package com.jsheepsim.Core;

import com.JEngine.PrimitiveTypes.JImage;
import com.JEngine.PrimitiveTypes.Position.Transform;
import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.JIdentity;

public class Animal extends Entity {
    public Animal(Transform transform, JImage newSprite, JIdentity jIdentity) {
        super(transform, newSprite, jIdentity);
    }
}
