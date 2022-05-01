package com.jsheepsim.Simulator;

import com.jsheepsim.Entities.Animals.BaseClasses.Animal;
import com.jsheepsim.Entities.Animals.Bear;
import com.jsheepsim.Entities.Animals.Sheep;
import com.jsheepsim.Entities.Animals.Wolf;
import com.jsheepsim.Entities.Fence;
import com.jsheepsim.Entities.Plants.Grass;
import com.jsheepsim.Entities.Plants.Plant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 * WorldData class holds all of a simulation's world data and handles the heavy operations of the world.
 * Ex. Moving animals, checking occupancy, etc.
 */
public class WorldData {
    private int xSize; // Width of the world
    private int ySize; // Height of the world
    private int tileSize; // Size of each tile in pixels
    private Animal[][] animals; // 2D array of animals
    private Plant[][] plants; // 2D array of plants
    private long seed; // Seed for the world generator
    private final WorldSimulator worldSimulator; // Reference to the WorldSimulator

    public WorldData(int xSize, int ySize, int tileSize, long seed, WorldSimulator worldSimulator) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.tileSize = tileSize;
        this.seed = seed;
        this.worldSimulator = worldSimulator;
        animals = new Animal[xSize][ySize];
        plants = new Plant[xSize][ySize];
    }

    // Getters and setters
    public int getXSize() {
        return xSize;
    }

    public void setXSize(int xSize) {
        this.xSize = xSize;
    }

    public int getYSize() {
        return ySize;
    }

    public void setYSize(int ySize) {
        this.ySize = ySize;
    }

    public int getTileSize() {
        return tileSize;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    public Animal[][] getAnimals() {
        return animals;
    }

    public void setAnimals(Animal[][] animals) {
        this.animals = animals;
    }

    public Plant[][] getGrass() {
        return plants;
    }

    public void setGrass(Plant[][] plants) {
        this.plants = plants;
    }

    // Add a new animal into the world data
    public boolean addAnimal(Animal animal) {
        if (animal.getX() < xSize && animal.getY() < ySize && animal.getX()>=0 && animal.getY()>=0) {
            animals[animal.getX()][animal.getY()] = animal;
            worldSimulator.getScene().add(animal);
            return true;
        }
        return false;
    }

    // Add new grass at a given coord
    public boolean addGrass(int x, int y) {
        if (x < xSize && y < ySize) {
            plants[x][y] = new Grass(new Coord(x,y), worldSimulator);
            worldSimulator.getScene().add(plants[x][y]);
            return true;
        }
        return false;
    }

    // Remove an animal from the world
    public boolean removeAnimal(Animal animal) {
        // Even if animal is out of bounds, it will be removed
        worldSimulator.getScene().remove(animal);
        // Make sure we remove the animal

        if (animal.getX() < xSize && animal.getY() < ySize) {
            animals[animal.getX()][animal.getY()] = null;
            worldSimulator.getScene().remove(animals[animal.getX()][animal.getY()]);

            return true;
        }
        return false;
    }

    // move an animal to a new location, if the new location is empty
    public boolean moveAnimal(Animal animal, int x, int y) {
        if (animal.getX() < xSize && animal.getY() < ySize) {
            if(!isOccupied(x,y)) {
                int oldX = animal.getX();
                int oldY = animal.getY();
                animals[animal.getX()][animal.getY()] = null;
                animals[x][y] = animal;
                animal.setX(x);
                animal.setY(y);
                //System.out.println("Moved " + animal.getName() + " from " + oldX + "," + oldY + " to " + x + "," + y);
                /*if(Math.abs(x - oldX) > 1 || Math.abs(y - oldY) > 1) {
                    System.out.println("Invalid movement");
                }*/
                return true;
            }
        }
        return false;
    }

    // check if a given location is occupied by an animal
    public boolean isOccupied(int x, int y) {
        if (x < xSize && y < ySize && x >= 0 && y >= 0) {
            return animals[x][y] != null;
        }
        return true;
    }
    // Checks for any free spaces in a range
    public Coord getAvailableSpotInRange(int x, int y, int range) {
        if(range<=0) {
            return new Coord(-1,-1);
        }
        // prioritize left, right, up, down
        if (!isOccupied(x - 1, y)) {
            return new Coord(x - 1, y);
        }
        else if (!isOccupied(x + 1, y)) {
            return new Coord(x + 1, y);
        }
        else if (!isOccupied(x, y - 1)) {
            return new Coord(x, y - 1);
        }
        else if (!isOccupied(x, y + 1)) {
            return new Coord(x, y + 1);
        }
        for(int i = x - range; i <= x + range; i++) {
            for(int j = y - range; j <= y + range; j++) {
                if(!isOccupied(i,j)) {
                    return new Coord(i,j);
                }
            }
        }
        return new Coord(-1,-1);
    }

    // get all the animals in a given range
    public Animal[] getAnimalsInRange(int x, int y, int range) {
        Animal[] animalsInRange = new Animal[xSize*ySize];
        int index = 0;
        for(int i = x - range; i <= x + range; i++) {
            for(int j = y - range; j <= y + range; j++) {
                if(i >= 0 && j >= 0 && i < xSize && j < ySize) {
                    if(isOccupied(i,j)) {
                        animalsInRange[index] = animals[i][j];
                        index++;
                    }
                }
            }
        }
        return animalsInRange;
    }

    // get all the animals in a given range excluding the one provided
    public Animal[] getAnimalsInRangeExclusive(int x, int y, int range, Animal animal){
        Animal[] animalsInRange = new Animal[xSize*ySize];
        int index = 0;
        for(int i = x - range; i <= x + range; i++) {
            for(int j = y - range; j <= y + range; j++) {
                if(i >= 0 && j >= 0 && i < xSize && j < ySize) {
                    if(isOccupied(i,j) && animals[i][j] != animal) {
                        animalsInRange[index] = animals[i][j];
                        index++;
                    }
                }
            }
        }
        // remove nulls
        Animal[] animalsInRange2 = new Animal[index];
        for(int i = 0; i < index; i++) {
            animalsInRange2[i] = animalsInRange[i];
        }
        return animalsInRange2;
    }

    // Get all plants in a radius
    public Plant[] getPlantsInRange(int x, int y, int range) {
        Plant[] plantInRange = new Plant[xSize*ySize];
        int index = 0;
        for(int i = x - range; i <= x + range; i++) {
            for(int j = y - range; j <= y + range; j++) {
                if(i >= 0 && j >= 0 && i < xSize && j < ySize) {
                    if(isOccupied(i,j)) {
                        plantInRange[index] = plants[i][j];
                        index++;
                    }
                }
            }
        }
        return plantInRange;
    }

    // Generate animals based off of a seeded random
    public void generateAnimals(long seed)
    {
        Random random = new Random(seed);
        for(int i = 0; i < xSize; i++) {
            for(int j = 0; j < ySize; j++) {
                int result = random.nextInt(100);
                Animal newAnimal = null;
                /* Odds:
                * 7% any space is a sheep
                * 5% any space is a wolf
                * 2% any space is a bear
                * 5% any space is a fence
                */
                if(result < 7) {
                    newAnimal = new Sheep(new Coord(i,j), worldSimulator, "sheep", false);
                }
                else if(result < 13) {
                    newAnimal = new Wolf(new Coord(i,j), worldSimulator, "wolf",false);
                }
                else if(result < 15) {
                    newAnimal = new Bear(new Coord(i,j), worldSimulator, "bear",false);
                }

                else if(result < 20) {
                    newAnimal = new Fence(new Coord(i,j), worldSimulator, "fence");
                }
                if(newAnimal != null) {
                    addAnimal(newAnimal);
                }
            }
        }
    }

    // Generate plants based off of a seeded random
    public void generatePlants(long seed)
    {
        Random random = new Random((long)(seed*0.5* new Random().nextFloat()));
        for(int i = 0; i < xSize; i++) {
            for(int j = 0; j < ySize; j++) {
                int result = random.nextInt(100);

                if(result < 35) {
                    addGrass(i,j);
                }
            }
        }
    }
    // remove a plant at a given coordinate
    public boolean removePlant(int x, int y) {
        if (x < xSize && y < ySize && x >= 0 && y >= 0) {
            plants[x][y] = null;
            return true;
        }
        return false;
    }

    // Get all animals alive in the world
    public Animal[] getAliveAnimals(){
        Animal[] aliveAnimals = new Animal[xSize*ySize];
        int index = 0;
        for(int i = 0; i < xSize; i++) {
            for(int j = 0; j < ySize; j++) {
                if(isOccupied(i,j)) {
                    if(animals[i][j].isAlive() && !(animals[i][j] instanceof Fence)) {
                        aliveAnimals[index] = animals[i][j];
                        index++;
                    }
                }
            }
        }
        // remove nulls
        Animal[] aliveAnimalsNoNulls = new Animal[index];
        for(int i = 0; i < index; i++) {
            aliveAnimalsNoNulls[i] = aliveAnimals[i];
        }
        return aliveAnimalsNoNulls;
    }
    // Get all animals of a certain species
    public Animal[] getAllAnimalsOfClass(Class<? extends Animal> animalClass){
        Animal[] animalArr = new Animal[xSize*ySize];
        int index = 0;
        for(int i = 0; i < xSize; i++) {
            for(int j = 0; j < ySize; j++) {
                if(isOccupied(i,j)) {
                    if(animals[i][j].getClass().equals(animalClass) && animals[i][j].isAlive() && animals[i][j]!=null) {
                        animalArr[index] = animals[i][j];
                        index++;
                    }
                }
            }
        }
        // remove nulls
        Animal[] animalsNoNulls = new Animal[index];
        for(int i = 0; i < index; i++) {
            animalsNoNulls[i] = animalArr[i];
        }
        return animalsNoNulls;
    }

    // Get the first available spot in the world
    public Coord getAvailableSpotAnywhere(){
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                if(!isOccupied(i,j)) {
                    return new Coord(i,j);
                }
            }
        }
        return new Coord(-1,-1);
    }

    // Load world data from a file. Does not save settings
    public void loadFromFile(String filename){
        File file = new File("save/" +filename);
        try {
            Scanner scanner = new Scanner(file);
            xSize = scanner.nextInt();
            ySize = scanner.nextInt();
            tileSize = scanner.nextInt();
            seed = scanner.nextLong();
            reloadWorld();
            System.out.println("World Data: Loaded from file");
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("World Data: Load File not found");
        }
    }

    // Save world data to a file. Does not save settings
    public void saveToFile(String filename){
        File file = new File("save/" +filename);
        try {
            PrintWriter writer = new PrintWriter(file.getAbsolutePath(), "UTF-8");
            writer.println(xSize);
            writer.println(ySize);
            writer.println(tileSize);
            writer.println(seed);
            writer.close();
            System.out.println("World Data: Saved to file");
        } catch (IOException e) {
            System.out.println("World Data: IOException: " + e.getMessage());
        }
    }

    // Reload the world. Regenerates all plants and animals
    public void reloadWorld(){
        worldSimulator.setDay(0);
        worldSimulator.getScene().purge();
        worldSimulator.adjustWindowSize();
        generateAnimals(seed);
        generatePlants(seed);
    }
}
