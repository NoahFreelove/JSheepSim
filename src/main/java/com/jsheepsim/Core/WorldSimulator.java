package com.jsheepsim.Core;

import com.JEngine.Game.Visual.Scenes.JScene;
import com.JEngine.Game.Visual.Scenes.JSceneManager;
import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.Thing;
import com.jsheepsim.Grass;

public class WorldSimulator {
    private JScene scene; // Holds the Images and objects of the world
    private WorldData worldData; // Handles the data of the world including movement

    private final Thread simulationThread; // Thread that runs the simulation
    private boolean isRunning = false; // Whether the simulation is running or not

    private final int simSpeed = 1; // Speed of the simulation

    // Grid is 16x16, 32x32 pixels per square
    public WorldSimulator(String sceneName, long worldSeed, int xSize, int ySize) {
        this.scene = new JScene(500, sceneName);
        worldData = new WorldData(xSize, ySize, 32, this);
        simulationThread = new Thread(this::simulationUpdate);
        generateAnimals(worldSeed);
        generateGrass(worldSeed);
        startThread();
    }

    public JScene getScene() {
        return scene;
    }

    public void setScene(JScene scene) {
        this.scene = scene;
    }

    private void simulationUpdate(){
        while(true){


            try {
                Thread.sleep(1000/simSpeed);
            } catch (InterruptedException ignore) {}
            if(!isRunning)
                continue;
            for ( Animal[] animalArr : worldData.getAnimals() ) {
                for ( Animal animal : animalArr ) {
                    if(animal!=null)
                    {
                        if(!animal.hasUpdated)
                        {
                            animal.simUpdate();
                            animal.hasUpdated = true;
                        }
                    }
                }
            }
            for ( Animal[] animalArr : worldData.getAnimals() ) {
                for ( Animal animal : animalArr ) {
                    if(animal!=null)
                    {
                        animal.hasUpdated = false;
                    }
                }
            }

            Thing.LogExtra("Simulation Update");
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

        JSceneManager.getWindow().resume();
    }

    public void pauseSimulation(){
        isRunning = false;
        JSceneManager.getWindow().pause();
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
    public Grass[] getGrassInRange(int x, int y, int range) {
        return worldData.getGrassInRange(x, y, range);
    }

    public WorldData getWorldData() {
        return worldData;
    }

    public boolean removeGrass(int x, int y) {
        return worldData.removeGrass(x, y);
    }
}
