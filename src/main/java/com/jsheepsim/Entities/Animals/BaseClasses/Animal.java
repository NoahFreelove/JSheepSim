package com.jsheepsim.Entities.Animals.BaseClasses;

import com.JEngine.Core.Position.Transform;
import com.JEngine.Core.Position.Vector3;
import com.JEngine.Core.Identity;
import com.JEngine.Utility.GameMath;
import com.jsheepsim.Entities.Entity;
import com.jsheepsim.Simulator.Coord;
import com.jsheepsim.Simulator.WorldSimulator;

import java.io.File;

// Class is only abstract for the breed method as every animal has its own breed method
public abstract class Animal extends Entity {

    // Basic info
    protected boolean isAlive = true;
    private boolean hasEaten = false;
    protected final int foodChainLevel;

    // Children stuff
    protected Animal child = null; // If the animal has had a child, the reference to it is stored here
    protected boolean isChild; // If the animal is a child

    // Life/Food stuff
    private final int maxDaysToLive;
    private int daysToLive;
    private int daysSinceLastMeal;

    // Animation stuff
    private float animProgress;
    private Vector3 previousPosition;
    private Vector3 targetPosition;
    private float posOffset = 0f;

    public Animal(Identity identity, Coord arrPos, WorldSimulator wmRef, File imagePath, int maxDaysToLive, boolean isChild, int foodChainLevel) {
        super(Transform.simpleTransform(arrPos.x*wmRef.getTileSize(), arrPos.y*wmRef.getTileSize(), 5), identity, arrPos,wmRef, imagePath);

        // set anim target position to current position because it just spawned and shouldn't run across the screen
        previousPosition = getTransform().getPosition();
        targetPosition = getTransform().getPosition();

        this.foodChainLevel = foodChainLevel;
        this.isChild = isChild;
        this.maxDaysToLive = maxDaysToLive;

        animProgress = 1;
        daysToLive = maxDaysToLive;

        // If it is a starting animal (was spawned by the world generator, make its life shorter because its an adult
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
        previousPosition = new Vector3(getX()*worldSimulator.getTileSize(), getY()*worldSimulator.getTileSize(), getTransform().position.z);

        checkLife();

        doUpdateAction();
    }

    /**
     * Ran every sim update this animal is alive for. Will either, randomly move, breed, or eat.
     */
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
                // If they're a herbivore, they can't hunt, so look for plants
                if(herbivore.lookForPlants())
                {
                    daysSinceLastMeal = 0;
                }
            }
            // If they aren't a hunter, move randomly
            randomMove();
        }
    }

    /**
     * Method checks if the animal should die of age, hunger. Also checks if its a child
     */
    private void checkLife() {
        if(!isAlive)
            return;
        if(daysToLive > 0) {
            daysToLive--;
            daysSinceLastMeal++;
            checkIfChild();
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
     * Adjusts the scale and offset accordingly
     */
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

    // update the animal's target position to its target tile converted into world coordinates
    private void updateTargetPosition()
    {
        targetPosition =  new Vector3(getX()*worldSimulator.getTileSize(), getY()*worldSimulator.getTileSize(), getTransform().position.z);
    }


    // This is only called when an animal is hunted, forcefully removed, or dies of age
    protected void die(DeathReason reason)
    {
        // Remove the animal from the world
        isAlive = false;
        worldSimulator.removeAnimal(this);
        worldSimulator.logEvent(String.format("%s died (%s)", getClass().getSimpleName(), reason.toString().toLowerCase()));
    }

    // This is called when an animal is not in range of food or a mate and will make the animal move randomly in a 1 tile radius
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

    // move the animal relative to its current position
    protected void move(int deltaX, int deltaY)
    {
        worldSimulator.moveAnimal(this, getX()+deltaX, getY()+deltaY);
        updateTargetPosition();
    }

    // move the animal to a specific tile
    protected void moveAbsolute(int x, int y)
    {
        worldSimulator.moveAnimal(this, x, y);
        updateTargetPosition();
    }

    /**
     * Find a mate in a 1 tile radius
     * @return True if found a mate and bred, false if no mate found
     */
    protected boolean lookForMate()
    {
        // If they haven't eaten or the world doesn't allow mating, return false
        if(!hasEaten() || !worldSettings.allowMating())
            return false;

        // Look for every animal in a 1 tile radius
        for (Animal otherAnimal: worldSimulator.getAnimalsInRange(getPos().x, getPos().y, 1)){
            // Make sure the animal isn't breeding with itself, unless allowed
            if(otherAnimal == null || (otherAnimal == this && !worldSettings.asexualReproduction()))
                continue;
            // If they're of the same species, have eaten, are alive, continue with the process
            if(otherAnimal.getClass() == getClass() || worldSettings.allowBreedingWithOtherSpecies())
            {
                if(otherAnimal.hasEaten() && otherAnimal.isAlive)
                {
                    // Parents cannot breed with their children, or any animal who is a child
                    if(worldSettings.allowIncest() || (otherAnimal.child != this && child != otherAnimal && !otherAnimal.isChild && !isChild))
                    {
                        child = otherAnimal.breed(this);
                        setHasEaten(false);
                    }
                }
            }
        }
        return false;
    }

    // Default getters/setters
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

    // When attacked, die
    public void attacked(Animal animal) {
        die(DeathReason.HUNTED);
    }

    // Abstract breed method to be implemented by subclasses
    protected abstract Animal breed(Animal animal);

    @Override
    public String toString(){
        return String.format("%s '%s' - Is Child:%b - Has Eaten:%b - Days To Live:%d - Last Ate:%d", getClass().getSimpleName(), getIdentity().getName(), isChild, hasEaten, daysToLive,daysSinceLastMeal);
    }
}

