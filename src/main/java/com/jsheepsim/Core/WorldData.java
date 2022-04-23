package com.jsheepsim.Core;

import com.JEngine.Game.Visual.Scenes.JSceneManager;
import com.JEngine.PrimitiveTypes.VeryPrimitiveTypes.JIdentity;
import com.jsheepsim.Grass;
import com.jsheepsim.Sheep;
import com.jsheepsim.Wolf;

import java.util.Random;

public class WorldData {
    private int xSize;
    private int ySize;
    private int tileSize;
    private Animal[][] animals;
    private Grass[][] grass;
    private final WorldSimulator worldSimulator;

    public WorldData(int xSize, int ySize, int tileSize, WorldSimulator worldSimulator) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.tileSize = tileSize;
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

    public void generateAnimals(long seed)
    {
        Random random = new Random(seed);
        for(int i = 0; i < xSize; i++) {
            for(int j = 0; j < ySize; j++) {
                int result = random.nextInt(100);

                if(result < 10) {
                    addAnimal(new Sheep(new Coord(i,j), worldSimulator, "sheep"));
                }
                else if(result < 12) {
                    addAnimal(new Wolf(new Coord(i,j), worldSimulator, "wolf"));
                }
            }
        }
    }

    public void generateGrass(long seed)
    {
        Random random = new Random(seed);
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

}
