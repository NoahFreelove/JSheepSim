package com.jsheepsim;

import com.JEngine.Utility.Misc.GameUtility;
import com.jsheepsim.Entities.Animals.BaseClasses.Animal;
import com.jsheepsim.Entities.Animals.Bear;
import com.jsheepsim.Entities.Animals.Bunny;
import com.jsheepsim.Entities.Animals.Sheep;
import com.jsheepsim.Entities.Animals.Wolf;
import com.jsheepsim.Simulator.WorldSimulator;

import java.util.Scanner;

/**
 * Console class manages input via the console and calls the appropriate methods in the WorldSimulator class.
 */
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
                setspeed: set the simulation speed (>0)
                enablelogging: enable logging
                worldstatus: print the status of a world
                status: print the status of the current world
                getsettings: print world settings
                load: load a world from a file
                save: save a world to a file
                gc: run the garbage collector
                """;


    public void startConsole(){
        System.out.println("Enter a command:");
        isRunning = true;
        while (isRunning){
            Scanner s = new Scanner(System.in);
            System.out.print("> ");
            try {
                String input = s.nextLine();
                processInput(input);
            }catch (Exception e){
                //ignore
            }
        }
    }

    /**
     * Determine if the input is a valid command
     * @param input the input string
     */
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
            case "setspeed" -> Main.worlds[Main.selectedWorld].setSimSpeed(getDouble());
            case "enablelogging" -> Main.worlds[Main.selectedWorld].getWorldSettings().setLogEvents(getBoolean());
            case "worldstatus" -> worldStatus(-1);
            case "status" -> worldStatus(Main.selectedWorld);
            case "getsettings" -> System.out.println(Main.worlds[Main.selectedWorld].getWorldSettings().toString());
            case "load" -> loadWorld();
            case "save" -> saveWorld();
            case "gc" -> gc();
        }
    }

    /**
     * Run the garbage collector and print the results
     */
    void gc(){
        long beforeUsedMem=Runtime.getRuntime().totalMemory();
        System.gc();
        long afterUsedMem=Runtime.getRuntime().totalMemory();
        long savedMemory = beforeUsedMem-afterUsedMem;
        System.out.println("Freed " + savedMemory/1024/1024 + " MB");
    }

    /**
     * change the spectated world
     */
    void changeWorldNum()
    {
        int worldNum = getWorldNum();
        if(worldNum != -1)
        {
            Main.setSpectatedWorld(worldNum);
        }
    }

    /**
     * Output world stats for a specific world
     * @param defaultNum
     */
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
            Animal[] animalsAlive = ws.getAliveAnimals();
            Animal[] sheepAlive = ws.getAllAnimalsOfClass(Sheep.class);
            Animal[] wolvesAlive = ws.getAllAnimalsOfClass(Wolf.class);
            Animal[] bunniesAlive = ws.getAllAnimalsOfClass(Bunny.class);
            Animal[] bearsAlive = ws.getAllAnimalsOfClass(Bear.class);

            System.out.printf("World '%s' Status:%n", ws.getScene().getSceneName());
            System.out.printf("Day:%d%n", ws.getDay());
            System.out.printf("%d animals alive%n", animalsAlive.length);
            System.out.printf("%d sheep alive%n", sheepAlive.length);
            System.out.printf("%d bunnies alive%n", bunniesAlive.length);
            System.out.printf("%d wolves alive%n%n", wolvesAlive.length);
            System.out.printf("%d bears alive%n", bearsAlive.length);
        }
    }

    // Get a world from file
    void loadWorld()
    {
        String fileName = getFileName();
        Main.worlds[Main.selectedWorld].loadFromFile(fileName);
    }
    // Save a world to file
    void saveWorld(){
        String fileName = getFileName();
        Main.worlds[Main.selectedWorld].saveToFile(fileName);
    }

    // get a world num from the user
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

    // get a boolean answer from the user
    boolean getBoolean(){
        System.out.print("new true/false value: ");
        Scanner s = new Scanner(System.in);

        return s.nextBoolean();
    }

    // get a double answer from the user
    double getDouble(){
        System.out.print("Enter new Value (>0): ");
        Scanner s = new Scanner(System.in);
        double newValue = s.nextDouble();
        if(newValue > 0)
        {
            return newValue;
        }
        else
        {
            System.out.println("Set as 1 (invalid input)");
            return 1;
        }
    }

    // get a file name from the user
    String getFileName()
    {
        System.out.print("Enter a file name: ");
        return new Scanner(System.in).next();

    }

    // stop the console
    public void stop(){
        isRunning = false;
    }
}
