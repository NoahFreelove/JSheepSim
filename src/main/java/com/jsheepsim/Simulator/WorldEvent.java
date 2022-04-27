package com.jsheepsim.Simulator;

import com.jsheepsim.Entities.Animals.BaseClasses.Animal;
import com.jsheepsim.Entities.Animals.Monster;

import java.util.Random;

public class WorldEvent {

    public static void createNewEvent(WorldSimulator ws) {
        if(!ws.getWorldSettings().enableEvents())
            return;

        // Create random between 1 and 100;
        Random rand = new Random();
        int random = rand.nextInt(100) + 1;

        if (random <3) {
            Monster m = new Monster(ws.getAvailableSpotAnywhere(), ws, "Monster",false);
            ws.addAnimal(m);
            eventLog("A new monster has appeared!");
        }
        else if (random <6){
            for (Animal a: ws.getWorldData().getAliveAnimals()
                 ) {
                a.setHasEaten(true);
            }
            eventLog("Every Animal Has Been Fed!");
        }
    }
    private static void eventLog(String s){
        System.out.println("EVENT: " + s);
    }
}
