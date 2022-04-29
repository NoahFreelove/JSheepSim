package com.jsheepsim.Entities.Animals.BaseClasses;

import com.JEngine.PrimitiveTypes.Position.Transform;
import com.JEngine.PrimitiveTypes.Position.Vector3;
import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.Identity;
import com.JEngine.Utility.GameMath;
import com.jsheepsim.Entities.Entity;
import com.jsheepsim.Simulator.Coord;
import com.jsheepsim.Simulator.WorldSimulator;

import java.io.File;

public abstract class Animal extends Entity {

    // Basic info
    protected boolean isAlive = true;
    private boolean hasEaten = false;
    protected final int foodChainLevel;

    // Children stuff
    protected Animal child = null; // If the animal has had a child, the reference to it is stored here
    protected boolean isChild; // If the animal is a child

    // Age stuff
    private final int maxDaysToLive;
    private int daysToLive;
    private int daysSinceLastMeal;

    // Animation stuff
    private float animProgress;
    private Vector3 previousPosition;
    private Vector3 targetPosition;
    private float posOffset = 0f;

    public Animal(Identity identity, Coord arrPos, WorldSimulator wmRef, File imagePath, int maxDaysToLive, boolean isChild, int foodChainLevel) {
        super(Transform.simpleTransform(arrPos.x*wmRef.getWorldData().getTileSize(), arrPos.y*wmRef.getWorldData().getTileSize(), 5), identity, arrPos,wmRef, imagePath);
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
        checkIfStillChild();
    }

    @Override
    public void Update(){
        if(animProgress<1)
        {
            animProgress+= 0.04f*worldSimulator.getSimSpeed();
            Vector3 interpolatedPosition = GameMath.interpolate(previousPosition, targetPosition, animProgress);
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
                daysSinceLastMeal = 0;
                // If they could hunt, don't move this day
                return;
            }
            else if(this instanceof Herbivore herbivore)
            {
                if(herbivore.lookForPlants())
                {
                    daysSinceLastMeal = 0;
                }
            }
            // If they aren't a hunter, move randomly
            randomMove();
        }
    }

    private void checkLife() {
        if(!isAlive)
            return;
        if(daysToLive > 0) {
            daysToLive--;
            daysSinceLastMeal++;
            checkIfStillChild();
            if (daysToLive == 0) {
                die(DeathReason.OLD_AGE);
            } else if (daysSinceLastMeal > 20)
            {
                die(DeathReason.STARVATION);
            }
        }
        else {
            // Just in case the animal is dead, but the daysToLive is negative
            die(DeathReason.OLD_AGE);
        }
    }

    /**
     * Check if the animal has grown past the child stage
     */
    private void checkIfStillChild(){
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


    protected void die(DeathReason reason)
    {
        isAlive = false;
        worldSimulator.removeAnimal(this);
        worldSimulator.logEvent(String.format("%s died (%s)", getClass().getSimpleName(), reason.toString().toLowerCase()));
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
        if(!hasEaten() || !worldSettings.allowMating())
            return false;

        for (Animal a: worldSimulator.getAnimalsInRange(getPos().x, getPos().y, 1)){
            if(a == null || (a == this && !worldSettings.asexualReproduction()))
                continue;

            if(a.getClass() == getClass() || worldSettings.allowBreedingWithOtherSpecies())
            {
                if(a.hasEaten() && a.isAlive)
                {
                    // Parents cannot breed with their children, unless allowed
                    if(worldSettings.allowIncest() || (a.child != this && child != a && !a.isChild && !isChild))
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
        return String.format("%s '%s' - IsChild:%b - HasEaten:%b - DaysToLive:%d", getClass().getSimpleName(), getIdentity().getName(), isChild, hasEaten, daysToLive);
    }

    public void attacked(Animal animal) {
        die(DeathReason.HUNTED);
    }

    protected abstract Animal breed(Animal animal);

}

