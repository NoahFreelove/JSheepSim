package com.jsheepsim.Simulator;

import com.JEngine.Game.Visual.Scenes.GameScene;
import com.JEngine.Game.Visual.Scenes.SceneManager;
import com.JEngine.PrimitiveTypes.FlipFlop;
import com.JEngine.PrimitiveTypes.Position.Vector2;
import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.Thing;
import com.jsheepsim.Entities.Animals.BaseClasses.Animal;
import com.jsheepsim.Entities.Plants.Plant;

public class WorldSimulator {
    private GameScene scene; // Holds the Images and objects of the world
    private final WorldData worldData; // Handles the data of the world including movement
    private final FlipFlop grassFlip;
    private final Thread simulationThread; // Thread that runs the simulation
    private boolean isRunning = false; // Whether the simulation is running or not

    private double simSpeed = 1; // Speed of the simulation

    private int day = 1; // Current day

    private final WorldSettings worldSettings;
    // Grid is 16x16, 32x32 pixels per square
    public WorldSimulator(String sceneName, long worldSeed, int xSize, int ySize, int tileSize, double simSpeed) {
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

    private void simTick(){
        while(true){
            try {
                Thread.sleep((long) (1000/simSpeed));
            } catch (InterruptedException ignore) {}
            if(!isRunning)
                continue;
            simUpdate();
            WorldEvent.createNewEvent(this);
            Thing.LogExtra("Simulation Update");
        }
    }

    private void simUpdate() {
        day++;
        logEvent(String.format("Day: %d", day),true);
        for ( Animal[] animalArr : worldData.getAnimals() ) {
            for ( Animal animal : animalArr ) {
                if(animal!=null)
                {
                    if(!animal.hasUpdated())
                    {
                        animal.simUpdate();
                        animal.setHasUpdated(true);
                    }
                }
            }
        }
        for ( Animal[] animalArr : worldData.getAnimals() ) {
            for ( Animal animal : animalArr ) {
                if(animal!=null)
                {
                    animal.setHasUpdated(false);
                }
            }
        }
        if(grassFlip.getState())
        {
            // random pos in range
            int x = (int) (Math.random() * worldData.getXSize());
            int y = (int) (Math.random() * worldData.getYSize());
            worldData.addGrass(x,y);
        }
    }

    public void startThread(){
        if(!simulationThread.isAlive())
        {
            simulationThread.start();
        }
    }
    public void startSimulation(){
        if(isRunning)
            return;
        isRunning = true;

    }

    public void pauseSimulation(){
        isRunning = false;
    }


    public void stopThread(){
        simulationThread.interrupt();
    }

    public void generateAnimals(long seed){
        worldData.generateAnimals(seed);
    }

    public void generateGrass(long seed){ worldData.generateGrass(seed);}

    public boolean addAnimal(Animal animal) {
        return worldData.addAnimal(animal);
    }

    public boolean removeAnimal(Animal animal) {
        return worldData.removeAnimal(animal);
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
        return worldData.getGrassInRange(x, y, range);
    }

    public Coord getAvailableSpotAnywhere(){
        return worldData.getAvailableSpotAnywhere();
    }

    public WorldData getWorldData() {
        return worldData;
    }

    public boolean removePlant(int x, int y) {
        return worldData.removeGrass(x, y);
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

    public void adjustWindowSize(){
        int tileSize = getWorldData().getTileSize();
        SceneManager.getWindow().getStage().setWidth(getWorldData().getXSize()*tileSize + tileSize);
        SceneManager.getWindow().getStage().setHeight(getWorldData().getYSize()*tileSize + 80);
    }

    public Vector2 getWindowSize(){
        int tileSize = getWorldData().getTileSize();
        return new Vector2(getWorldData().getXSize()*tileSize + tileSize, getWorldData().getYSize()*tileSize + 80);

    }

    public void logEvent(String event){
        if (!worldSettings.logEvents())
            return;

        System.out.printf("'%s': %s%n", getScene().getSceneName(), event);
    }

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

    public WorldSettings getWorldSettings() {
        return worldSettings;
    }
}
