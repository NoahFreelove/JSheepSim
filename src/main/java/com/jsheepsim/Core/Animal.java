package com.jsheepsim.Core;

import com.JEngine.PrimitiveTypes.JImage;
import com.JEngine.PrimitiveTypes.Position.Transform;
import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.JIdentity;

import java.io.File;

public class Animal extends Entity {

    private boolean isAlive = true;
    private boolean hasEaten = false;
    public int daysToLive = 30;

    public Animal(JIdentity jIdentity, Coord arrPos, WorldSimulator wmRef, File imagePath) {
        super(Transform.simpleTransform(arrPos.x*wmRef.getWorldData().getTileSize(), arrPos.y*wmRef.getWorldData().getTileSize(), 2), jIdentity, arrPos,wmRef, imagePath);
    }

    @Override
    public void Update(){
    }

    @Override
    public void simUpdate(){
        if(daysToLive > 0) {
            daysToLive--;
            if (daysToLive == 0) {
                die();
            }
        }
    }


    protected void die()
    {
        isAlive = false;
        worldSimulator.removeAnimal(this);
    }
    protected void randomMove()
    {
        int randX = (int)(Math.random()*3)-1;
        int randY = (int)(Math.random()*3)-1;
        move(randX,randY);
    }
    protected void move(int deltaX, int deltaY)
    {
        worldSimulator.moveAnimal(this, getX()+deltaX, getY()+deltaY);
    }
}

