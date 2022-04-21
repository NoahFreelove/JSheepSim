package com.jsheepsim;

import com.JEngine.PrimitiveTypes.JImage;
import com.JEngine.PrimitiveTypes.Position.Transform;
import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.JIdentity;
import com.jsheepsim.Core.Animal;
import com.jsheepsim.Core.Entity;
import com.jsheepsim.Core.Interfaces.IAttackable;

public class Sheep extends Animal implements IAttackable {
    public Sheep(Transform transform, JImage newSprite, JIdentity jIdentity) {
        super(transform, newSprite, jIdentity);
    }

    @Override
    public void attacked(Entity entity) {

    }
}
