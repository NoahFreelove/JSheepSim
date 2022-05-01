package com.jsheepsim.Simulator;

import com.jsheepsim.Entities.Animals.BaseClasses.Animal;
import com.jsheepsim.Entities.Animals.Monster;

import java.util.Random;

/**
 * WorldEvent : When createNewEvent is called, there is a small chance of an event happening
 */
public class WorldEvent {

    public static void createNewEvent(WorldSimulator ws) {
        if(!ws.getWorldSettings().enableEvents())
            return;

        // Create random between 1 and 100;
        Random rand = new Random();
        int random = rand.nextInt(100) + 1;

        /* Odds
         * 3% chance of a monster spawning
         * 3% of all animals being fed
         * Add your own!
         */

        if (random <3) {
            Monster m = new Monster(ws.getAvailableSpotAnywhere(), ws, "Monster",false);
            ws.addAnimal(m);
            eventLog("A new monster has appeared!");
        }
        else if (random <6){
            for (Animal a: ws.getAliveAnimals()) {
                a.setHasEaten(true);
            }
            eventLog("Every Animal Has Been Fed!");
        }
    }
    private static void eventLog(String s){
        System.out.println("EVENT: " + s);
    }
}
