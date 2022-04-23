package com.jsheepsim.Core;

import com.JEngine.PrimitiveTypes.Position.Transform;
import com.JEngine.PrimitiveTypes.Position.Vector3;
import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.JIdentity;
import com.JEngine.Utility.JMath;
import com.jsheepsim.Core.Interfaces.IBreedable;
import com.jsheepsim.Core.Interfaces.IHunter;
import com.jsheepsim.Sheep;

import java.io.File;

public class Animal extends Entity {

    protected boolean isAlive = true;
    private boolean hasEaten = false;
    protected Animal child = null;


    public int daysToLive = 30;
    private float animProgress;
    private Vector3 previousPosition;
    private Vector3 targetPosition;

    public Animal(JIdentity jIdentity, Coord arrPos, WorldSimulator wmRef, File imagePath) {
        super(Transform.simpleTransform(arrPos.x*wmRef.getWorldData().getTileSize(), arrPos.y*wmRef.getWorldData().getTileSize(), 5), jIdentity, arrPos,wmRef, imagePath);
        previousPosition = getTransform().getPosition();
        targetPosition = getTransform().getPosition();
        animProgress = 1;
    }

    @Override
    public void Update(){
        if(animProgress<1)
        {
            animProgress+= 0.04f;
            getTransform().setPosition(JMath.interpolate(previousPosition, targetPosition, animProgress));
        }
        super.Update();
    }

    @Override
    public void simUpdate(){
        animProgress = 0;
        previousPosition = new Vector3(getX()*worldSimulator.getWorldData().getTileSize(), getY()*worldSimulator.getWorldData().getTileSize(), getTransform().position.z);
        if(daysToLive > 0) {
            daysToLive--;
            if (daysToLive == 0) {
                die();
            }
        }
        // If they have eaten they have the possibility to breed, if not just move
        if(hasEaten())
        {
            if(!lookForMate())
            {
                randomMove();
                updateTargetPosition();
            }
        }
        else
        {
            // If the animal can hunt, look for prey
            if(this instanceof IHunter hunter)
            {
                if(!hunter.hunt())
                {
                    // If they can't hunt, move randomly
                    randomMove();
                    updateTargetPosition();
                    return;
                }
                // If they could hunt, don't move this day
                updateTargetPosition();
                return;
            }
            if(this instanceof Sheep sheep)
            {
                sheep.lookForGrass();
            }
            // If they aren't a hunter, move randomly
            randomMove();
            updateTargetPosition();
        }
    }
    private void updateTargetPosition()
    {
        targetPosition =  new Vector3(getX()*worldSimulator.getWorldData().getTileSize(), getY()*worldSimulator.getWorldData().getTileSize(), getTransform().position.z);
    }


    protected void die()
    {
        isAlive = false;
        worldSimulator.removeAnimal(this);
    }
    protected void randomMove()
    {
        // random up down left right
        int rand = (int) (Math.random() * 4);
        switch (rand) {
            case 0 -> move(0, -1);
            case 1 -> move(0, 1);
            case 2 -> move(-1, 0);
            case 3 -> move(1, 0);
        }
    }
    protected void move(int deltaX, int deltaY)
    {
        worldSimulator.moveAnimal(this, getX()+deltaX, getY()+deltaY);
    }
    protected void moveAbsolute(int x, int y)
    {
        worldSimulator.moveAnimal(this, x, y);
    }

    protected boolean lookForMate()
    {
        if(!hasEaten())
            return false;

        for (Animal a: worldSimulator.getAnimalsInRange(getPos().x, getPos().y, 1)){
            if(a == null)
                continue;

            if(a.getClass() == getClass() && a instanceof IBreedable b)
            {
                if(a.hasEaten())
                {
                    // Parents cannot breed with their children
                    if((a.child != this && child != a) || child == null)
                    {
                        if(a != this)
                        {
                            child = b.breed(this);
                            setHasEaten(false);
                        }
                    }
                }
            }
        }
        return false;
    }

    protected boolean lookForFood()
    {
        return false;
    }

    public boolean hasEaten() {
        return hasEaten;
    }

    public void setHasEaten(boolean hasEaten) {
        this.hasEaten = hasEaten;
    }
}

