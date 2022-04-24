package com.jsheepsim.Core;

import com.jsheepsim.Animals.Animal;
import com.jsheepsim.Core.Entities.Fence;
import com.jsheepsim.Core.Entities.Grass;
import com.jsheepsim.Animals.Sheep;
import com.jsheepsim.Animals.Wolf;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class WorldData {
    private int xSize;
    private int ySize;
    private int tileSize;
    private Animal[][] animals;
    private Grass[][] grass;
    private long seed;
    private final WorldSimulator worldSimulator;

    public WorldData(int xSize, int ySize, int tileSize, long seed, WorldSimulator worldSimulator) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.tileSize = tileSize;
        this.seed = seed;
        this.worldSimulator = worldSimulator;
        animals = new Animal[xSize][ySize];
        grass = new Grass[xSize][ySize];
    }

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

    public boolean addAnimal(Animal animal) {
        if (animal.getX() < xSize && animal.getY() < ySize) {
            animals[animal.getX()][animal.getY()] = animal;
            worldSimulator.getScene().add(animal);
            return true;
        }
        return false;
    }

    public boolean addGrass(int x, int y) {
        if (x < xSize && y < ySize) {
            grass[x][y] = new Grass(new Coord(x,y), worldSimulator);
            worldSimulator.getScene().add(grass[x][y]);
            return true;
        }
        return false;
    }

    public boolean removeAnimal(Animal animal) {
        if (animal.getX() < xSize && animal.getY() < ySize) {
            animals[animal.getX()][animal.getY()] = null;
            worldSimulator.getScene().remove(animal);
            return true;
        }
        return false;
    }

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

    public boolean isOccupied(int x, int y) {
        if (x < xSize && y < ySize && x >= 0 && y >= 0) {
            return animals[x][y] != null;
        }
        return true;
    }

    public Animal[][] getAnimals() {
        return animals;
    }

    public void setAnimals(Animal[][] animals) {
        this.animals = animals;
    }

    public Grass[][] getGrass() {
        return grass;
    }

    public void setGrass(Grass[][] grass) {
        this.grass = grass;
    }

    public Coord getAvailableSpotInRange(int x, int y, int range) {
        for(int i = x - range; i <= x + range; i++) {
            for(int j = y - range; j <= y + range; j++) {
                if(!isOccupied(i,j)) {
                    return new Coord(i,j);
                }
            }
        }
        return new Coord(-1,-1);
    }

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

    public Grass[] getGrassInRange(int x, int y, int range) {
        Grass[] grassInRange = new Grass[xSize*ySize];
        int index = 0;
        for(int i = x - range; i <= x + range; i++) {
            for(int j = y - range; j <= y + range; j++) {
                if(i >= 0 && j >= 0 && i < xSize && j < ySize) {
                    if(isOccupied(i,j)) {
                        grassInRange[index] = grass[i][j];
                        index++;
                    }
                }
            }
        }
        return grassInRange;
    }

    public void generateAnimals(long seed)
    {
        Random random = new Random(seed);
        for(int i = 0; i < xSize; i++) {
            for(int j = 0; j < ySize; j++) {
                int result = random.nextInt(100);
                Animal a = null;
                if(result < 10) {
                    a = new Sheep(new Coord(i,j), worldSimulator, "sheep", false);
                    addAnimal(a);
                }
                else if(result < 15) {
                    a = new Wolf(new Coord(i,j), worldSimulator, "wolf",false);
                    addAnimal(a);
                }
                else if(result < 20) {
                    a = new Fence(new Coord(i,j), worldSimulator, "fence");
                    addAnimal(a);
                }
            }
        }
    }

    public void generateGrass(long seed)
    {
        Random random = new Random((long)(seed*0.5));
        for(int i = 0; i < xSize; i++) {
            for(int j = 0; j < ySize; j++) {
                int result = random.nextInt(100);

                if(result < 20) {
                    addGrass(i,j);
                }
            }
        }
    }

    public boolean removeGrass(int x, int y) {
        if (x < xSize && y < ySize && x >= 0 && y >= 0) {
            grass[x][y] = null;
            return true;
        }
        return false;
    }

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

    public Sheep[] getSheep(){
        Sheep[] sheep = new Sheep[xSize*ySize];
        int index = 0;
        for(int i = 0; i < xSize; i++) {
            for(int j = 0; j < ySize; j++) {
                if(isOccupied(i,j)) {
                    if(animals[i][j] instanceof Sheep && animals[i][j].isAlive() && animals[i][j]!=null) {
                        sheep[index] = (Sheep) animals[i][j];
                        index++;
                    }
                }
            }
        }
        // remove nulls
        Sheep[] sheepNoNulls = new Sheep[index];
        for(int i = 0; i < index; i++) {
            sheepNoNulls[i] = sheep[i];
        }
        return sheepNoNulls;
    }

    public Wolf[] getWolves(){
        Wolf[] wolves = new Wolf[xSize*ySize];
        int index = 0;
        for(int i = 0; i < xSize; i++) {
            for(int j = 0; j < ySize; j++) {
                if(isOccupied(i,j)) {
                    if(animals[i][j] instanceof Wolf && animals[i][j].isAlive() && animals[i][j]!=null) {
                        wolves[index] = (Wolf) animals[i][j];
                        index++;
                    }
                }
            }
        }
        // remove nulls
        Wolf[] wolvesNoNull = new Wolf[index];
        for(int i = 0; i < index; i++) {
            wolvesNoNull[i] = wolves[i];
        }
        return wolvesNoNull;
    }

    public void loadFromFile(String filename){
        File file = new File("save/" +filename);
        try {
            Scanner scanner = new Scanner(file);
            xSize = scanner.nextInt();
            ySize = scanner.nextInt();
            tileSize = scanner.nextInt();
            seed = scanner.nextLong();
            worldSimulator.getScene().purge();
            worldSimulator.adjustWindowSize();
            generateAnimals(seed);
            generateGrass(seed);
            System.out.println("World Data: Loaded from file");
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("World Data: Load File not found");
        }
    }

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


}
