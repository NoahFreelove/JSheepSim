package com.jsheepsim.Core;

import com.JEngine.Game.Visual.Scenes.JScene;

public class WorldSimulator {
    private JScene scene;
    private WorldData worldData;
    private final Thread simulationThread;

    // Grid is 16x16, 32x32 pixels per square
    public WorldSimulator(String sceneName) {
        this.scene = new JScene(500, sceneName);
        simulationThread = new Thread(this::simulationUpdate);
        simulationThread.start();
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
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for ( Animal[] animalArr : worldData.getAnimals() ) {
                for ( Animal animal : animalArr ) {
                        animal.simUpdate();
                }
            }
        }
    }


    public void stopThread(){
        simulationThread.interrupt();
    }

    public void generateAnimals(long seed){
        worldData.generateAnimals(seed);
    }

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
}
