package com.jsheepsim.Simulator;

public class WorldSettings {
    private boolean allowMating = true; // Can animals reproduce in general?
    private boolean allowEating = false; // Can animals eat?
    private boolean allowHunting = true; // Can animals attack others?
    private boolean logEvents = true; // Should events be logged?

    // Setting any of these to true will negatively affect the simulation
    private boolean enableEvents = false; // Enable random events?
    private boolean ignoreFoodChainLevel = false; // Ignore food chain level when determining if an animal can attack?
    private boolean allowIncest = false; // Can parents reproduce with their offspring?
    private boolean asexualReproduction = false; // Can animals reproduce asexually?
    private boolean allowEatingOwnSpecies = false; // Can animals eat their own kind?
    private boolean allowBreedingWithOtherSpecies = false; // Can animals breed with other species?
    private boolean allowChildrenEatingAdults = false; // Can animals hunt when they're a child?

    public WorldSettings(){}

    public WorldSettings(boolean allowMating, boolean allowEating, boolean allowHunting, boolean enableEvents, boolean ignoreFoodChainLevel, boolean allowIncest, boolean asexualReproduction, boolean allowEatingOwnSpecies, boolean allowBreedingWithOtherSpecies) {
        this.allowMating = allowMating;
        this.allowEating = allowEating;
        this.allowHunting = allowHunting;
        this.enableEvents = enableEvents;
        this.ignoreFoodChainLevel = ignoreFoodChainLevel;
        this.allowIncest = allowIncest;
        this.asexualReproduction = asexualReproduction;
        this.allowEatingOwnSpecies = allowEatingOwnSpecies;
        this.allowBreedingWithOtherSpecies = allowBreedingWithOtherSpecies;
    }

    public WorldSettings(boolean ignoreFoodChainLevel, boolean allowIncest, boolean asexualReproduction, boolean allowEatingOwnSpecies, boolean allowBreedingWithOtherSpecies) {
        this.ignoreFoodChainLevel = ignoreFoodChainLevel;
        this.allowIncest = allowIncest;
        this.asexualReproduction = asexualReproduction;
        this.allowEatingOwnSpecies = allowEatingOwnSpecies;
        this.allowBreedingWithOtherSpecies = allowBreedingWithOtherSpecies;
    }

    public boolean allowIncest() {
        return allowIncest;
    }

    public void setAllowIncest(boolean allowIncest) {
        this.allowIncest = allowIncest;
    }

    public boolean allowMating() {
        return allowMating;
    }

    public void setAllowMating(boolean allowMating) {
        this.allowMating = allowMating;
    }

    public boolean allowEating() {
        return allowEating;
    }

    public void setAllowEating(boolean allowEating) {
        this.allowEating = allowEating;
    }

    public boolean allowHunting() {
        return allowHunting;
    }

    public void setAllowHunting(boolean allowHunting) {
        this.allowHunting = allowHunting;
    }

    public boolean asexualReproduction() {
        return asexualReproduction;
    }

    public void setAsexualReproduction(boolean asexualReproduction) {
        this.asexualReproduction = asexualReproduction;
    }

    public boolean enableEvents() {
        return enableEvents;
    }

    public void setEnableEvents(boolean enableEvents) {
        this.enableEvents = enableEvents;
    }

    public boolean ignoreFoodChainLevel() {
        return ignoreFoodChainLevel;
    }

    public void setIgnoreFoodChainLevel(boolean ignoreFoodChainLevel) {
        this.ignoreFoodChainLevel = ignoreFoodChainLevel;
    }

    public boolean allowEatingOwnSpecies() {
        return allowEatingOwnSpecies;
    }

    public void setAllowEatingOwnSpecies(boolean allowEatingOwnSpecies) {
        this.allowEatingOwnSpecies = allowEatingOwnSpecies;
    }

    public boolean allowBreedingWithOtherSpecies() {
        return allowBreedingWithOtherSpecies;
    }

    public void setAllowBreedingWithOtherSpecies(boolean allowBreedingWithOtherSpecies) {
        this.allowBreedingWithOtherSpecies = allowBreedingWithOtherSpecies;
    }

    public boolean allowChildrenEatingAdults() {
        return allowChildrenEatingAdults;
    }

    public void setAllowChildrenEatingAdults(boolean allowChildrenEatingAdults) {
        this.allowChildrenEatingAdults = allowChildrenEatingAdults;
    }

    public boolean logEvents() {
        return logEvents;
    }

    public void setLogEvents(boolean logEvents) {
        this.logEvents = logEvents;
    }
    
    @Override
    public String toString(){
        return String.format("""
        World Settings:
        Log Events: %b
        Allow Mating: %b
        Allow Eating: %b
        Allow Hunting: %b
        
        * Changing the following will severely impact your simulation!*
        Enable Events: %b
        Ignore Food-chain level: %b
        Asexual Reproduction: %b
        Allow Eating Own Species: %b
        Allow Breeding With Other Species: %b
        Allow Incest: %b
        Allow Children To Hunt Adults: %b%n
        """,logEvents(), allowMating(), allowEating(), allowHunting(), enableEvents(),
                ignoreFoodChainLevel(), asexualReproduction(), allowEatingOwnSpecies(),
                allowBreedingWithOtherSpecies(), allowIncest(), allowChildrenEatingAdults());
    }
}