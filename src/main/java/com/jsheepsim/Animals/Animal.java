package com.jsheepsim.Animals;

import com.JEngine.PrimitiveTypes.Position.Transform;
import com.JEngine.PrimitiveTypes.Position.Vector3;
import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.JIdentity;
import com.JEngine.Utility.JMath;
import com.jsheepsim.Core.Coord;
import com.jsheepsim.Core.Entities.Entity;
import com.jsheepsim.Core.WorldSimulator;

import java.io.File;

public abstract class Animal extends Entity {

    // Basic info
    protected boolean isAlive = true;
    private boolean hasEaten = false;
    protected final int foodChainLevel;

    // Children stuff
    protected Animal child = null;
    protected boolean isChild = false;

    // Age stuff
    private final int maxDaysToLive;
    private int daysToLive;

    // Animation stuff
    private float animProgress;
    private Vector3 previousPosition;
    private Vector3 targetPosition;
    private float posOffset = 0f;

    public Animal(JIdentity jIdentity, Coord arrPos, WorldSimulator wmRef, File imagePath, int maxDaysToLive, boolean isChild, int foodChainLevel) {
        super(Transform.simpleTransform(arrPos.x*wmRef.getWorldData().getTileSize(), arrPos.y*wmRef.getWorldData().getTileSize(), 5), jIdentity, arrPos,wmRef, imagePath);
        previousPosition = getTransform().getPosition();
        targetPosition = getTransform().getPosition();
        this.foodChainLevel = foodChainLevel;
        this.isChild = isChild;
        this.maxDaysToLive = maxDaysToLive;
        animProgress = 1;
        daysToLive = maxDaysToLive;
        if(!isChild)
        {
            daysToLive-=10;
        }
        checkIfChild();
    }

    @Override
    public void Update(){
        if(animProgress<1)
        {
            animProgress+= 0.04f*worldSimulator.getSimSpeed();
            Vector3 interpolatedPosition = JMath.interpolate(previousPosition, targetPosition, animProgress);
            interpolatedPosition.x += posOffset;
            interpolatedPosition.y += posOffset;
            getTransform().setPosition(interpolatedPosition);
        }
        super.Update();
    }

    @Override
    public void simUpdate(){
        animProgress = 0;
        previousPosition = new Vector3(getX()*worldSimulator.getWorldData().getTileSize(), getY()*worldSimulator.getWorldData().getTileSize(), getTransform().position.z);

        checkLife();

        doUpdateAction();
    }

    private void doUpdateAction() {
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
            if(this instanceof Carnivore carnivore)
            {
                if(!carnivore.hunt())
                {
                    // If they can't hunt, move randomly
                    randomMove();
                    return;
                }
                // If they could hunt, don't move this day
                return;
            }
            else if(this instanceof Herbivore herbivore)
            {
                if(herbivore.lookForPlants())
                {
                    return;
                }
            }
            // If they aren't a hunter, move randomly
            randomMove();
        }
    }

    private void checkLife() {
        if(daysToLive > 0) {
            daysToLive--;
            checkIfChild();
            if (daysToLive == 0) {
                die();
            }
        }
        else {

            // Just in case the animal is dead, but the daysToLive is negative
            die();
        }
    }
    private void checkIfChild(){
        if(daysToLive>maxDaysToLive-10)
        {
            getTransform().setScale(new Vector3(0.5f,0.5f,0.5f));
            posOffset = 8;
            isChild = true;
        }
        else
        {
            getTransform().setScale(new Vector3(1,1,1));
            posOffset = 0;
            isChild = false;
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

        for (Animal a: worldSimulator.getAnimalsInRangeExclusive(getPos().x, getPos().y, 1, this)){
            if(a == null)
                continue;

            if(a.getClass() == getClass())
            {
                if(a.hasEaten())
                {
                    // Parents cannot breed with their children
                    if(a.child != this && child != a && !a.isChild && !isChild)
                    {
                        child = a.breed(this);
                        setHasEaten(false);
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

    public boolean isAlive(){
        return isAlive;
    }

    public int getDaysToLive() {
        return daysToLive;
    }

    public void setDaysToLive(int daysToLive) {
        this.daysToLive = daysToLive;
    }

    public int getFoodChainLevel() {
        return foodChainLevel;
    }

    @Override
    public String toString(){
        return String.format("%s '%s' - IsChild:%b - HasEaten:%b - DaysToLive:%d", getClass().getSimpleName(), getJIdentity().getName(), isChild, hasEaten, daysToLive);
    }

    public void attacked(Animal animal) {
        if(animal.getFoodChainLevel() > getFoodChainLevel())
        {
            die();
        }
    }

    protected abstract Animal breed(Animal animal);

}

