package com.jsheepsim.Simulator;

import com.jsheepsim.Entities.Animals.Monster;

import java.util.Random;

public class EventHandler {

    public static void startEvent(WorldSimulator ws) {
        // Create random between 1 and 100;
        Random rand = new Random();
        int random = rand.nextInt(100) + 1;
        
        // If random is between 1 and 10, create a new event
        if (random <5) {
            Monster m = new Monster(ws.getAvailableSpotAnywhere(), ws, "Monster",false);
            ws.addAnimal(m);
            eventLog("A new monster has appeared!");
        }
    }
    private static void eventLog(String s){
        System.out.println("EVENT: " + s);
    }
}
