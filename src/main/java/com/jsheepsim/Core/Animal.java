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

    public int maxDaysToLive = 30;
    public int daysToLive;
    private float animProgress;
    private Vector3 previousPosition;
    private Vector3 targetPosition;

    public Animal(JIdentity jIdentity, Coord arrPos, WorldSimulator wmRef, File imagePath) {
        super(Transform.simpleTransform(arrPos.x*wmRef.getWorldData().getTileSize(), arrPos.y*wmRef.getWorldData().getTileSize(), 5), jIdentity, arrPos,wmRef, imagePath);
        previousPosition = getTransform().getPosition();
        targetPosition = getTransform().getPosition();
        animProgress = 1;
        daysToLive = maxDaysToLive;
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
            if(daysToLive>maxDaysToLive-10)
            {
                getTransform().setScale(new Vector3(0.5f,0.5f,0.5f));
            }
            else
            {
                getTransform().setScale(new Vector3(1,1,1));
            }
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
                    return;
                }
                // If they could hunt, don't move this day
                return;
            }
            if(this instanceof Sheep sheep)
            {
                sheep.lookForGrass();
            }
            // If they aren't a hunter, move randomly
            randomMove();
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
        int deltaX = (int)(Math.random()*3)-1;
        int deltaY = (int)(Math.random()*3)-1;
        if(deltaX == 0 && deltaY == 0)
        {
            return;
        }
        move(deltaX, deltaY);
    }
    protected void move(int deltaX, int deltaY)
    {
        worldSimulator.moveAnimal(this, getX()+deltaX, getY()+deltaY);
        updateTargetPosition();
    }
    protected void moveAbsolute(int x, int y)
    {
        worldSimulator.moveAnimal(this, x, y);
        updateTargetPosition();
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

