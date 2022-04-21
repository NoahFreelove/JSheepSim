package com.jsheepsim.Core;

import com.JEngine.Game.PlayersAndPawns.JPawn;
import com.JEngine.PrimitiveTypes.JImage;
import com.JEngine.PrimitiveTypes.Position.Transform;
import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.JIdentity;

public class Entity extends JPawn {
    public Entity(Transform transform, JImage newSprite, JIdentity jIdentity) {
        super(transform, newSprite, jIdentity);
    }
}
