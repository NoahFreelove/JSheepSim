package com.jsheepsim.Simulator;

public class WorldSettings {
    private boolean allowMating = true; // Can animals reproduce in general?
    private boolean allowEating = true; // Can animals eat?
    private boolean allowHunting = true; // Can animals attack others?
    private boolean enableEvents = true; // Enable random events?

    // Setting any of these to true will set of a great unbalance in the world
    private boolean ignoreFoodChainLevel = false; // Ignore food chain level when determining if an animal can attack?
    private boolean allowIncest = false; // Can parents reproduce with their offspring?
    private boolean parthenogenesis = false; // Can animals reproduce asexually?
    private boolean allowEatingOwnKind = true; // Can animals eat their own kind?
    private boolean allowBreedingWithOtherSpecies = false; // Can animals breed with other species?

    public WorldSettings(){}
    public WorldSettings(boolean allowIncest, boolean allowMating, boolean allowEating, boolean allowHunting, boolean parthenogenesis, boolean enableEvents) {
        this.allowIncest = allowIncest;
        this.allowMating = allowMating;
        this.allowEating = allowEating;
        this.allowHunting = allowHunting;
        this.parthenogenesis = parthenogenesis;
        this.enableEvents = enableEvents;
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

    public boolean parthenogenesis() {
        return parthenogenesis;
    }

    public void setParthenogenesis(boolean parthenogenesis) {
        this.parthenogenesis = parthenogenesis;
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

    public boolean allowEatingOwnKind() {
        return allowEatingOwnKind;
    }

    public void setAllowEatingOwnKind(boolean allowEatingOwnKind) {
        this.allowEatingOwnKind = allowEatingOwnKind;
    }

    public boolean allowBreedingWithOtherSpecies() {
        return allowBreedingWithOtherSpecies;
    }

    public void setAllowBreedingWithOtherSpecies(boolean allowBreedingWithOtherSpecies) {
        this.allowBreedingWithOtherSpecies = allowBreedingWithOtherSpecies;
    }
}
