package com.jsheepsim;

import com.JEngine.Utility.Misc.GameUtility;
import com.jsheepsim.Entities.Animals.BaseClasses.Animal;
import com.jsheepsim.Entities.Animals.Bear;
import com.jsheepsim.Entities.Animals.Bunny;
import com.jsheepsim.Entities.Animals.Sheep;
import com.jsheepsim.Entities.Animals.Wolf;
import com.jsheepsim.Simulator.WorldSettings;
import com.jsheepsim.Simulator.WorldSimulator;

import java.util.Scanner;

public class Console {
    public boolean isRunning;

    String commandList =
                """
                help: show this help
                exit: exit the program (esc)
                clear: print some newlines to clear the console
                start: start world (F1)
                stop: pause world (F2)
                reload: reload world (F3)
                step: simulate one day (F4)
                setworld: start watching a world (num keys)
                worldstatus: print the status of a world
                status: print the status of the current world
                settings: print world settings
                load: load a world from a file
                save: save a world to a file
                gc: run the garbage collector
                """;
    public void console(){
        System.out.println("Enter a command:");
        isRunning = true;
        while (isRunning){
            Scanner s = new Scanner(System.in);
            System.out.print("> ");
            String input = s.nextLine();
            processInput(input);
        }
    }

    void processInput(String input){
        input = input.toLowerCase().trim();

        switch (input) {
            case "exit" -> GameUtility.exitApp();
            case "help" -> System.out.println(commandList);
            case "clear" -> System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            case "start" -> Main.worlds[Main.selectedWorld].startSimulation();
            case "stop" -> Main.worlds[Main.selectedWorld].pauseSimulation();
            case "reload" -> Main.worlds[Main.selectedWorld].reloadWorld();
            case "step" -> Main.worlds[Main.selectedWorld].step();
            case "setworld" -> changeWorldNum();
            case "worldstatus" -> worldStatus(-1);
            case "status" -> worldStatus(Main.selectedWorld);
            case "settings" -> getWorldSettings();
            case "load" -> loadWorld();
            case "save" -> saveWorld();
            case "gc" -> System.gc();
        }
    }

    void changeWorldNum()
    {
        int worldNum = getWorldNum();
        if(worldNum != -1)
        {
            Main.switchWorld(worldNum);
        }
    }

    void getWorldSettings(){
        WorldSettings ws = Main.worlds[Main.selectedWorld].getWorldSettings();
        System.out.printf(
        """
        World Settings:
        -Basic Settings-
        Allow Mating: %b
        Allow Eating: %b
        Allow Hunting: %b
        Enable Events: %b
        
        * Changing these will severely impact your simulation!*
        -Advanced Settings-
        Ignore Food-chain level: %b
        Asexual Reproduction: %b
        Allow Eating Own Species: %b
        Allow Breeding With Other Species: %b
        Allow Incest: %b%n
        """, ws.allowMating(), ws.allowEating(), ws.allowHunting(), ws.enableEvents(),
                ws.ignoreFoodChainLevel(), ws.asexualReproduction(), ws.allowEatingOwnSpecies(),
                ws.allowBreedingWithOtherSpecies(), ws.allowIncest());
    }

    void worldStatus(int defaultNum)
    {
        int worldNum;
        if(defaultNum == -1)
        {
            worldNum = getWorldNum();
        }
        else {
            worldNum = defaultNum;
        }
        if(worldNum != -1)
        {
            WorldSimulator ws = Main.worlds[worldNum];
            Animal[] animalsAlive = ws.getWorldData().getAliveAnimals();
            Animal[] sheepAlive = ws.getWorldData().getAllAnimalsOfClass(Sheep.class);
            Animal[] wolvesAlive = ws.getWorldData().getAllAnimalsOfClass(Wolf.class);
            Animal[] bunniesAlive = ws.getWorldData().getAllAnimalsOfClass(Bunny.class);
            Animal[] bearsAlive = ws.getWorldData().getAllAnimalsOfClass(Bear.class);

            System.out.printf("World '%s' Status:%n", ws.getScene().getSceneName());
            System.out.printf("Day:%d%n", ws.getDay());
            System.out.printf("%d animals alive%n", animalsAlive.length);
            System.out.printf("%d sheep alive%n", sheepAlive.length);
            System.out.printf("%d bunnies alive%n", bunniesAlive.length);
            System.out.printf("%d wolves alive%n%n", wolvesAlive.length);
            System.out.printf("%d bears alive%n", bearsAlive.length);
        }
    }

    void loadWorld()
    {
        String fileName = getFileName();
        Main.worlds[Main.selectedWorld].loadFromFile(fileName);
    }
    void saveWorld(){
        String fileName = getFileName();
        Main.worlds[Main.selectedWorld].saveToFile(fileName);
    }

    int getWorldNum(){
        System.out.print("Enter a world number: ");
        Scanner s = new Scanner(System.in);
        int worldNum = s.nextInt();
        if(worldNum >= 0 && worldNum < Main.worlds.length)
        {
            return worldNum;
        }
        else
        {
            System.out.println("Invalid world number");
            return -1;
        }
    }

    String getFileName()
    {
        System.out.print("Enter a file name: ");
        return new Scanner(System.in).next();

    }

    public void stop(){
        isRunning = false;
    }
}
