package com.jsheepsim.Simulator;

import com.JEngine.Game.Visual.Scenes.GameScene;
import com.JEngine.Game.Visual.Scenes.SceneManager;
import com.JEngine.PrimitiveTypes.FlipFlop;
import com.JEngine.PrimitiveTypes.Position.Vector2;
import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.Thing;
import com.jsheepsim.Entities.Animals.BaseClasses.Animal;
import com.jsheepsim.Entities.Animals.Bunny;
import com.jsheepsim.Entities.Plants.Plant;

/*
World Simulator : The most important class in the project

The world simulator bridges WorldData and the GameScene so that the animals are rendered, and they update on a tick basis
 */
public class WorldSimulator {
    private final WorldData worldData; // Handles the data of the world including movement
    private final WorldSettings worldSettings;
    private GameScene scene; // Holds the Images and objects of the world

    private final Thread simulationThread; // Thread that runs the simulation
    private boolean isRunning = false; // Whether the simulation is running or not


    private final FlipFlop grassFlip; // Every other tick this will generate grass
    private double simSpeed = 1; // Speed of the simulation
    private int day = 1; // Current day


    public WorldSimulator(String sceneName, long worldSeed, int xSize, int ySize, int tileSize, double simSpeed) {
        // If you leave an empty seed a random seed will be generated
        if(worldSeed == 0)
        {
            worldSeed = System.currentTimeMillis();
        }
        this.worldSettings = new WorldSettings();
        this.scene = new GameScene(500, sceneName);
        this.simSpeed = simSpeed;
        this.grassFlip = new FlipFlop();
        worldData = new WorldData(xSize, ySize, tileSize, worldSeed, this);
        simulationThread = new Thread(this::simTick);
        generateAnimals(worldSeed);
        generateGrass(worldSeed);
        startThread();
    }

    public GameScene getScene() {
        return scene;
    }

    public void setScene(GameScene scene) {
        this.scene = scene;
    }

    // Infinite loop that runs the simulation, will call simUpdate() simSpeed times per second
    private void simTick(){
        while(true){
            try {
                Thread.sleep((long) (1000/simSpeed));
            } catch (InterruptedException ignore) {
                // if the thread is interrupted, don't log error
            }
            // If the sim is paused, wait for it to be un-paused, don't stop the thread
            if(!isRunning)
                continue;
            simUpdate(); // update all animals
            WorldEvent.createNewEvent(this); // Roll the dice for a new event
        }
    }

    /**
     * Is run every sim tick
     */
    private void simUpdate() {
        day++;
        logEvent(String.format("Day: %d", day),true);
        for ( Animal[] animalArr : worldData.getAnimals() ) {
            for ( Animal animal : animalArr ) {
                if(animal!=null)
                {
                    if(!animal.hasUpdated())
                    {
                        animal.simUpdate(); // Update the animal
                        // we need to have a hasUpdated flag so that we don't update the same animal twice in one tick
                        // ex. we could update the square (0,0) then the animal moves to (0,1) and we update them again
                        animal.setHasUpdated(true); 
                    }
                }
            }
        }
        // Reset the hasUpdated flags
        for ( Animal[] animalArr : worldData.getAnimals() ) {
            for ( Animal animal : animalArr ) {
                if(animal!=null)
                {
                    animal.setHasUpdated(false);
                }
            }
        }
        // Generate grass every other tick at a random position
        if(grassFlip.getState())
        {
            // random pos in range
            int x = (int) (Math.random() * worldData.getXSize());
            int y = (int) (Math.random() * worldData.getYSize());
            worldData.addGrass(x,y);
        }
    }

    // starts the thread of the simulation, only run once
    public void startThread(){
        if(!simulationThread.isAlive())
        {
            simulationThread.start();
        }
    }
    
    // Start/un-pause the simulation
    public void startSimulation(){
        if(isRunning)
            return;
        isRunning = true;

    }

    // Pause/stop the simulation
    public void pauseSimulation(){
        isRunning = false;
    }
    
    // don't call this unless you want to stop the simulation until you restart this app
    public void stopThread(){
        simulationThread.interrupt();
    }

    //region WorldData Bridge Methods
    
    /**
     * The Following methods are used to access the worldData object, they bridge between the simulation and the World Data
     * Saves time, so you don't have to do worldSimulator.worldData.method() every time
     * 
     * You shouldn't call WorldData methods directly, but they will still work if you do.
     */
    public void generateAnimals(long seed){
        worldData.generateAnimals(seed);
    }

    public void generateGrass(long seed){ worldData.generatePlants(seed);}

    public boolean addAnimal(Animal animal) {
        return worldData.addAnimal(animal);
    }

    public boolean removeAnimal(Animal animal) {
        return worldData.removeAnimal(animal);
    }

    public boolean removePlant(int x, int y) {
        return worldData.removePlant(x, y);
    }

    public boolean moveAnimal(Animal animal, int x, int y) {
        return worldData.moveAnimal(animal, x, y);
    }

    public boolean isOccupied(int x, int y) {
        return worldData.isOccupied(x, y);
    }

    public Coord getAvailableSpotInRange(int x, int y, int range) {
        return worldData.getAvailableSpotInRange(x, y, range);
    }

    public Animal[] getAnimalsInRange(int x, int y, int range) {
        return worldData.getAnimalsInRange(x, y, range);
    }
    
    public Animal[] getAnimalsInRangeExclusive(int x, int y, int range, Animal animal) {
        return worldData.getAnimalsInRangeExclusive(x, y, range, animal);
    }
    
    public Plant[] getPlantInRange(int x, int y, int range) {
        return worldData.getPlantsInRange(x, y, range);
    }

    public Coord getAvailableSpotAnywhere(){
        return worldData.getAvailableSpotAnywhere();
    }
    public Animal[] getAliveAnimals(){
        return worldData.getAliveAnimals();
    }

    public Animal[] getAllAnimalsOfClass(Class<? extends Animal> animalClass) {
        return worldData.getAllAnimalsOfClass(animalClass);
    }

    public WorldSettings getWorldSettings() {
        return worldSettings;
    }

    public int getTileSize() {
        return worldData.getTileSize();
    }

    public int getDay(){
        return day;
    }
    public void setDay(int day){
        this.day = day;
    }

    public double getSimSpeed() {
        return simSpeed;
    }

    public void setSimSpeed(double simSpeed) {
        this.simSpeed = simSpeed;
    }

    public void loadFromFile(String fileName) {
        worldData.loadFromFile(fileName);
    }

    public void saveToFile(String fileName) {
        worldData.saveToFile(fileName);
    }
    public void reloadWorld(){
        worldData.reloadWorld();
    }
    public void step(){
        simUpdate();
    }
    //endregion
    
    // Set the window size to change based off the simulation size
    public void adjustWindowSize(){
        int tileSize = worldData.getTileSize();
        SceneManager.getWindow().getStage().setWidth(worldData.getXSize()*tileSize + tileSize);
        SceneManager.getWindow().getStage().setHeight(worldData.getYSize()*tileSize + 80);
    }
    // get the size of the window of the current simulation
    public Vector2 getWindowSize(){
        int tileSize = worldData.getTileSize();
        return new Vector2(worldData.getXSize()*tileSize + tileSize, worldData.getYSize()*tileSize + 80);

    }

    // Logs to the console window
    public void logEvent(String event){
        if (!worldSettings.logEvents())
            return;

        System.out.printf("'%s': %s%n", getScene().getSceneName(), event);
    }
    
    // Logs to the console window but with a few newlines before the text
    public void logEvent(String event, boolean lineSeparator){
        if (!worldSettings.logEvents())
            return;
        if(lineSeparator)
        {
            System.out.printf("\n\n'%s': %s%n", getScene().getSceneName(), event);
            return;
        }
        System.out.printf("'%s': %s%n", getScene().getSceneName(), event);
    }

    @Override
    public String toString(){
        return String.format("World: '%s' - Day: %d", getScene().getSceneName(), day);
    }
}
